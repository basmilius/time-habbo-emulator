package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.youtube.YouTubePlaylist;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class YoutubeDisplayPlaylistsComposer extends MessageComposer
{

	private final UserItem youtubeDisplay;
	private final Habbo habbo;

	public YoutubeDisplayPlaylistsComposer(UserItem youtubeDisplay, Habbo habbo)
	{
		this.youtubeDisplay = youtubeDisplay;
		this.habbo = habbo;

		this.habbo.loadSettings();
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.YoutubeDisplayPlaylists);
		response.appendInt(this.youtubeDisplay.getId());
		response.appendInt(this.habbo.getSettings().getYouTubeManager().getPlaylists().size());
		for (YouTubePlaylist playlist : this.habbo.getSettings().getYouTubeManager().getPlaylists())
		{
			response.appendString(Integer.toString(playlist.getId()));
			response.appendString(playlist.getTitle());
			response.appendString(playlist.getDescription());
		}
		response.appendString(this.youtubeDisplay.getExtraData());
		return response;
	}

}
