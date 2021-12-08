package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

import java.util.List;

@Event(id = Incoming.RemoveSongFromPlaylist)
public class RemoveSongFromPlayListEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int index = packet.readInt();

		if (!connection.getHabbo().isInRoom())
			return;

		UserItem jukebox = Bootstrap.getEngine().getGame().getSoundMachineManager().getJukeBoxForRoom(connection.getHabbo().getCurrentRoom());

		if (jukebox == null)
			return;

		List<UserItem> cds = Bootstrap.getEngine().getGame().getSoundMachineManager().getCdsForJukeBox(jukebox);

		if (index >= cds.size())
			return;

		cds.remove(index);

		Bootstrap.getEngine().getGame().getSoundMachineManager().saveJukeBoxCds(jukebox, cds);

		RequestSoundMachineSongsEvent rsmse = new RequestSoundMachineSongsEvent();
		rsmse.connection = connection;
		rsmse.packet = null;
		rsmse.handle();

		RequestSoundMachinePlayListEvent rsmple = new RequestSoundMachinePlayListEvent();
		rsmple.connection = connection;
		rsmple.packet = null;
		rsmple.handle();
	}

}
