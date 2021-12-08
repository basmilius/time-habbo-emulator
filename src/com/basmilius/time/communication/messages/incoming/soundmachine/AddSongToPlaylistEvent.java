package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SoundMachinePlayListFullComposer;

import java.util.List;


@Event(id = Incoming.AddSongToPlaylist)
public class AddSongToPlaylistEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final int index = packet.readInt();

		if (!connection.getHabbo().isInRoom())
			return;

		final UserItem jukebox = Bootstrap.getEngine().getGame().getSoundMachineManager().getJukeBoxForRoom(connection.getHabbo().getCurrentRoom());

		if (jukebox == null)
			return;

		final List<UserItem> cds = Bootstrap.getEngine().getGame().getSoundMachineManager().getCdsForJukeBox(jukebox);

		if (cds.size() == 25)
		{
			connection.send(new SoundMachinePlayListFullComposer());
			return;
		}

		final UserItem cd = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (cd == null)
			return;

		cds.add(index, cd);

		Bootstrap.getEngine().getGame().getSoundMachineManager().saveJukeBoxCds(jukebox, cds);

		final RequestSoundMachineSongsEvent rsmse = new RequestSoundMachineSongsEvent();
		rsmse.connection = connection;
		rsmse.packet = null;
		rsmse.handle();

		final RequestSoundMachinePlayListEvent rsmple = new RequestSoundMachinePlayListEvent();
		rsmple.connection = connection;
		rsmple.packet = null;
		rsmple.handle();
	}

}
