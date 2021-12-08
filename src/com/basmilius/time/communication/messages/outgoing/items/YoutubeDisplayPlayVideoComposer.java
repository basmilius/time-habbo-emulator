package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.habbohotel.habbo.youtube.YouTubeVideo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class YoutubeDisplayPlayVideoComposer extends MessageComposer
{

	private final UserItem youtubeTv;
	private final YouTubeVideo video;

	public YoutubeDisplayPlayVideoComposer(UserItem youtubeTv, YouTubeVideo video)
	{
		this.youtubeTv = youtubeTv;
		this.video = video;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.YoutubeDisplayPlayVideo);
		response.appendInt(this.youtubeTv.getId());
		response.appendString((this.video != null) ? this.video.getVideoId() : "");
		response.appendInt((this.video != null) ? this.video.getCurrentPosition() : 0);
		response.appendInt((this.video != null) ? this.video.getVideoDuration() : 0);
		return response;
	}

}
