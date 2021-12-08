package com.basmilius.time.communication.messages.outgoing.soundmachine;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;


public class SoundMachineSongsComposer extends MessageComposer
{

	private List<UserItem> cds;

	public SoundMachineSongsComposer(List<UserItem> cds)
	{
		this.cds = cds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SoundMachineSongs);
		response.appendInt(this.cds.size());
		for (UserItem cd : this.cds)
		{
			response.appendInt(cd.getId());
			response.appendInt(cd.getCatalogItem().getSongId());
		}
		return response;
	}

}
