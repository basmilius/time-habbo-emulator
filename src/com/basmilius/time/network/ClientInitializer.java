package com.basmilius.time.network;

import com.basmilius.time.network.codec.MessageDecoder;
import com.basmilius.time.network.codec.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ClientInitializer extends ChannelInitializer<SocketChannel>
{

	@Override
	protected void initChannel(final SocketChannel socketChannel) throws Exception
	{
		socketChannel.pipeline().addLast(new MessageEncoder());
		socketChannel.pipeline().addLast(new MessageDecoder());
		socketChannel.pipeline().addLast(new ClientHandler());
	}

}
