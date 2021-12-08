package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PurchaseOKComposer extends MessageComposer
{

	private CatalogueItem cItem;
	private Guild guild;

	public PurchaseOKComposer(CatalogueItem cItem)
	{
		this.cItem = cItem;
	}

	public PurchaseOKComposer(Guild guild)
	{
		this.guild = guild;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.PurchaseOK);

		if (this.guild != null)
		{
			response.appendInt(5881); // RANDOM ID FOR GUILDS (IDK HOW I CAN
			// DO THIS)
			response.appendString("CREATE_GUILD");
			response.appendBoolean(false);
			response.appendInt(10);
			response.appendInt(0);
			response.appendInt(0);
			response.appendBoolean(true);
			response.appendInt(0);
			response.appendInt(2);
			response.appendBoolean(true);
		}
		else
		{
			response.appendBody(this.cItem);
		}

		return response;
	}

}
