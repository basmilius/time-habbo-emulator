package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class VoucherAcceptedFurniComposer extends MessageComposer
{

	private final String _productName;
	private final String _productDescription;

	public VoucherAcceptedFurniComposer(String _productName, String _productDescription)
	{
		this._productName = _productName;
		this._productDescription = _productDescription;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.VoucherAcceptedFurni);
		response.appendString(this._productName);
		response.appendString(this._productDescription);
		return response;
	}

}
