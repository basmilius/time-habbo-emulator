package com.basmilius.time.communication.messages.outgoing.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class SoundMachinePlayListComposer extends MessageComposer
{

	private final UserItem jukebox;
	private final List<UserItem> cds;

	public SoundMachinePlayListComposer(UserItem jukebox, List<UserItem> cds)
	{
		this.jukebox = jukebox;
		this.cds = cds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SoundMachinePlayList);
		response.appendInt(Bootstrap.getEngine().getGame().getSoundMachineManager().getCurrentCdForJukeBox(this.jukebox));
		response.appendInt(this.cds.size());
		int i = 0;
		for (final UserItem cd : this.cds)
		{
			response.appendInt(i++);
			response.appendInt(cd.getCatalogItem().getSongId());
		}
		return response;
	}

}
