package com.basmilius.time.communication.messages.outgoing.handshake;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PrimeAndGeneratorComposer extends MessageComposer
{

	private String prime;
	private String generator;

	public PrimeAndGeneratorComposer(String prime, String generator)
	{
		this.prime = prime;
		this.generator = generator;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PrimeAndGenerator);

		response.appendString("6f0aa581493d9bed315cc4ba419f5505b0583ec36768ca86859b62b26e4852d8e7b88e37b8613378517b750a0bf9e94797f4cd4e3a31c319fe9e48ff1b9bac1a055f69c6cb2ef498fdea94190ad1714125aba5b8032e03f65cdccb4e9cc62f22800db51008a2b1884e8cd5d54b72738c1f984ed1cf54e87104fb698e09316ae5");
		response.appendString("1f2f242938d7ca803f13e186d2f233bf7810480e59a7fbe6148d7e8356098cbb1450801d4315f824dc30c2cd0eeb7cabedd3b85575c635f2beeafe6e74c403c30338319017ec9977999c0e6a6c451a2d36893469bf0121c39f49be760cefb5978a1d281574439158fdbc9e9d25ac789d862dfd1131f17dc11ba28be714430865");

		return response;
	}

}
