package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.VoucherRedeem)
public class VoucherRedeemEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String voucher = packet.readString();

		connection.sendNotif(GenericAlertType.TEXT, "Redeem voucher isn't programmed yet. Your voucher was: " + voucher);
	}

}
