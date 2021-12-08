package com.basmilius.time.communication.messages.incoming.soundmachine;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SoundMachineSongsComposer;

import java.util.List;
import java.util.stream.Collectors;

@Event(id = Incoming.RequestSoundMachineSongs)
public class RequestSoundMachineSongsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		List<UserItem> cds = connection.getHabbo().getInventory().getUserItems().stream().filter(item -> item.getCatalogItem().getSongId() > 0 && item.getExtraData().isEmpty()).collect(Collectors.toList());

		connection.send(new SoundMachineSongsComposer(cds));
	}

}
