package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.update.youtube",
		syntax = ":update_youtube"
)
public class UpdateYouTubeCommand extends ICommand
{

	public UpdateYouTubeCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		((RoomUser) this.invoker).getHabbo().getSettings().getYouTubeManager().loadVideos();
		((RoomUser) this.invoker).getConnection().sendNotif(GenericAlertType.TEXT, "Playlists and video's reloaded succesfully!");
		return true;
	}

}
