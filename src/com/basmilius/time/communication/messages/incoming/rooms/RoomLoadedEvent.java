package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.poll.Poll;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.poll.PollSummaryComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomPanelComposer;

@Event(id = Incoming.RoomLoaded, secondId = Incoming.RoomLoadedSecond)
public class RoomLoadedEvent extends MessageEvent
{

	private Room room;

	@Override
	public void handle() throws Exception
	{
		this.room = connection.getHabbo().getCurrentRoom();

		if (this.room == null)
			return;
		
		if (connection.getHabbo().getRoomUser() == null)
			return;

		Bootstrap.getEngine().getLogging().log(RoomLoadedEvent.class, String.format("Room '%s' loaded for Habbo %s. Spectator: %b", this.room.getRoomData().getRoomName(), connection.getHabbo().getUsername(), connection.getHabbo().getRoomUser().isSpectator()));

		if (connection.getHabbo().getRoomUser().isSpectator())
			return;

		this.room.getRoomInteractions().userEntersRoom(connection.getHabbo().getRoomUser());

		connection.send(new RoomPanelComposer(room.getRoomData().getId(), room.getRoomData().getPermissions().isOwner(connection.getHabbo())));

		final Thread t = new Thread(() -> {
			try
			{
				Thread.sleep(3000);

				if (!(RoomLoadedEvent.this.connection != null && RoomLoadedEvent.this.connection.getHabbo() != null && RoomLoadedEvent.this.connection.getHabbo().isInRoom() && RoomLoadedEvent.this.connection.getHabbo().getCurrentRoom().getRoomData().getId() == RoomLoadedEvent.this.room.getRoomData().getId()))
					return;

				final Poll poll = Bootstrap.getEngine().getGame().getPollManager().getPollForRoom(RoomLoadedEvent.this.room);

				if (poll != null)
				{
					RoomLoadedEvent.this.connection.send(new PollSummaryComposer(poll.getId(), poll.getDescription()));
				}
			}
			catch (InterruptedException ignored)
			{

			}
		});
		t.start();
	}

}
