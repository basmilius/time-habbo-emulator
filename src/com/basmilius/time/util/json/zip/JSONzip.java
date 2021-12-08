package com.basmilius.time.util.json.zip;

/*
 Copyright (c) 2013 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

/**
 * JSONzip is a binary-encoded JSON dialect. It is designed to compress the
 * messages in a session in bandwidth constrained applications, such as mobile.
 * <p>
 * JSONzip is adaptive, so with each message seen, it should
 * improve its compression. It minimizes JSON's overhead, reducing punctuation
 * to a small number of bits. It uses Huffman encoding to reduce the average
 * size of characters. It uses caches (or Keeps) to keep recently seen strings
 * and values, so repetitive content (such as object keys) can be
 * substantially reduced. It uses a character encoding called Kim (Keep it
 * minimal) that is smaller than UTF-8 for most East European, African, and
 * Asian scripts.
 * <p>
 * JSONzip tends to reduce most content by about half. If there is a lot of
 * recurring information, the reduction can be much more dramatic.
 * <p>
 * FOR EVALUATION PURPOSES ONLY. THIS PACKAGE HAS NOT YET BEEN TESTED
 * ADEQUATELY FOR PRODUCTION USE.
 *
 * @author JSON.org
 * @version 2014-04-28
 */
public abstract class JSONzip implements None, PostMortem
{
	/**
	 * Powers of 2.
	 */
	public static final int[] twos = {
			1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
			1024, 2048, 4096, 8192, 16384, 32768, 65536
	};
	/**
	 * The maximum substring length when registering many. The registration of
	 * one substring may be longer.
	 */
	public static final int maxSubstringLength = 10;
	/**
	 * The minimum substring length.
	 */
	public static final int minSubstringLength = 3;
	/**
	 * The package supports tracing for debugging.
	 */
	public static final boolean probe = false;
	/**
	 * The maximum number of substrings added to the substrings keep per
	 * string.
	 */
	public static final int substringLimit = 40;
	/**
	 * The characters in JSON numbers can be reduced to 4 bits each.
	 */
	static final byte[] bcd = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-', '+', 'E'
	};
	/**
	 * The first positive integer than cannot be encoded in 4 bits.
	 */
	static final long int4 = 16;
	/**
	 * The first positive integer than cannot be encoded in 7 bits.
	 */
	static final long int7 = 144;
	/**
	 * The first positive integer than cannot be encoded in 14 bits.
	 */
	static final long int14 = 16528;
	/**
	 * The end of string code.
	 */
	static final int end = 256;
	/**
	 * The end of number code.
	 */
	static final int endOfNumber = bcd.length;
	/**
	 * The value code for an empty object.
	 */
	static final int zipEmptyObject = 0;

	/**
	 * The value code for an empty array.
	 */
	static final int zipEmptyArray = 1;

	/**
	 * The value code for true.
	 */
	static final int zipTrue = 2;

	/**
	 * The value code for false.
	 */
	static final int zipFalse = 3;

	/**
	 * The value code for null.
	 */
	static final int zipNull = 4;

	/**
	 * The value code for a non-empty object.
	 */
	static final int zipObject = 5;

	/**
	 * The value code for an array with a string as its first element.
	 */
	static final int zipArrayString = 6;

	/**
	 * The value code for an array with a non-string value as its first element.
	 */
	static final int zipArrayValue = 7;

	/**
	 * A Huffman encoder for names.
	 */
	final Huff namehuff;

	/**
	 * A place to keep the names (keys).
	 */
	final MapKeep namekeep;

	/**
	 * A place to keep the strings.
	 */
	final MapKeep stringkeep;

	/**
	 * A Huffman encoder for string values.
	 */
	final Huff substringhuff;

	/**
	 * A place to keep the strings.
	 */
	final TrieKeep substringkeep;

	/**
	 * A place to keep the values.
	 */
	final MapKeep values;

	/**
	 * Initialize the data structures.
	 */
	JSONzip()
	{
		this.namehuff = new Huff(end + 1);
		this.namekeep = new MapKeep(9);
		this.stringkeep = new MapKeep(11);
		this.substringhuff = new Huff(end + 1);
		this.substringkeep = new TrieKeep(12);
		this.values = new MapKeep(10);

		// Increase the weights of the ASCII letters, digits, and special characters
		// because they are highly likely to occur more frequently. The weight of each
		// character will increase as it is used. The Huffman encoder will tend to
		// use fewer bits to encode heavier characters.

		this.namehuff.tick(' ', '}');
		this.namehuff.tick('a', 'z');
		this.namehuff.tick(end);
		this.namehuff.tick(end);
		this.substringhuff.tick(' ', '}');
		this.substringhuff.tick('a', 'z');
		this.substringhuff.tick(end);
		this.substringhuff.tick(end);
	}

	/**
	 * Write an end-of-line to the console.
	 */
	static void log()
	{
		log("\n");
	}

	/**
	 * Write an integer to the console.
	 *
	 * @param integer
	 */
	static void log(int integer)
	{
		log(integer + " ");
	}

	/**
	 * Write two integers, separated by ':' to the console.
	 * The second integer is suppressed if it is 1.
	 *
	 * @param integer
	 * @param width
	 */
	static void log(int integer, int width)
	{
		if (width == 1)
		{
			log(integer);
		}
		else
		{
			log(integer + ":" + width + " ");
		}
	}

	/**
	 * Write a string to the console.
	 *
	 * @param string
	 */
	static void log(String string)
	{
		System.out.print(string);
	}

	/**
	 * Write a character or its code to the console.
	 *
	 * @param integer
	 * @param width
	 */
	static void logchar(int integer, int width)
	{
		if (integer > ' ' && integer <= '}')
		{
			log("'" + (char) integer + "':" + width + " ");
		}
		else
		{
			log(integer, width);
		}
	}

	/**
	 *
	 */
	void begin()
	{
		this.namehuff.generate();
		this.substringhuff.generate();
	}

	/**
	 * This method is used for testing the implementation of JSONzip. It is not
	 * suitable for any other purpose. It is used to compare a Compressor and a
	 * Decompressor, verifying that the data structures that were built during
	 * zipping and unzipping were the same.
	 *
	 * @return true if the structures match.
	 */
	public boolean postMortem(PostMortem pm)
	{
		JSONzip that = (JSONzip) pm;
		return this.namehuff.postMortem(that.namehuff)
				&& this.namekeep.postMortem(that.namekeep)
				&& this.stringkeep.postMortem(that.stringkeep)
				&& this.substringhuff.postMortem(that.substringhuff)
				&& this.substringkeep.postMortem(that.substringkeep)
				&& this.values.postMortem(that.values);
	}
}
