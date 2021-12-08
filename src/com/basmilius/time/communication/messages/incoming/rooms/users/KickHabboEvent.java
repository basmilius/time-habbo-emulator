package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.KickHabbo)
public class KickHabboEvent extends MessageEvent implements Runnable
{

	private Habbo kickHabbo;

	@Override
	public void handle() throws Exception
	{
		int habboId = packet.readInt();

		if (!(connection.getHabbo().isInRoom() && connection.getHabbo().getCurrentRoom().getRoomData().getPermissions().hasRights(connection.getHabbo())))
			return;

		this.kickHabbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(habboId);

		if (!(this.kickHabbo.isInRoom() && (!this.kickHabbo.getPermissions().contains("acc_unkickable") || (this.kickHabbo.getPermissions().contains("acc_unkickable") && connection.getHabbo().getPermissions().contains("cmd_shutdown"))) && this.kickHabbo.getCurrentRoom().getRoomData().getId() == connection.getHabbo().getCurrentRoom().getRoomData().getId()))
			return;

		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run()
	{
		if (this.kickHabbo == null || this.kickHabbo.getRoomUser() == null)
			return;
		
		this.kickHabbo.getRoomUser().setGoal(this.kickHabbo.getCurrentRoom().getRoomData().getRoomModel().getDoorX(), this.kickHabbo.getCurrentRoom().getRoomData().getRoomModel().getDoorY());

		int steps = this.kickHabbo.getRoomUser().getPath().size();

		if (steps > 6)
		{
			steps = 0;
		}

		while (steps > 0)
		{
			steps--;

			try
			{
				Thread.sleep(480);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		if (!this.kickHabbo.isInRoom())
			return;

		this.kickHabbo.getRoomUser().kick();
	}

}
