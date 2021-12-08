package com.basmilius.time.habbohotel.rooms.cycles;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.outgoing.soundmachine.SoundMachineNowPlayingComposer;

import java.util.List;

public class SoundMachineCycle extends ICycle
{

	private UserItem currentCd;
	private UserItem jukebox;
	private int secondsLeft;
	private int startTime;
	private List<UserItem> cds;

	public SoundMachineCycle(Room room)
	{
		super(room);

		this.secondsLeft = 0;
	}

	public final void setSecondsLeft(final int seconds)
	{
		this.secondsLeft = seconds;
	}

	public int getStartTime()
	{
		if (this.jukebox == null)
			return 0;
		return this.startTime;
	}
	
	public final UserItem getCurrentCd()
	{
		return this.currentCd;
	}

	@Override
	public void run()
	{
		while (this.getRoom().isActive())
		{
			try
			{
				this.jukebox = Bootstrap.getEngine().getGame().getSoundMachineManager().getJukeBoxForRoom(this.getRoom());

				if (this.jukebox != null)
				{
					int currentCd = Bootstrap.getEngine().getGame().getSoundMachineManager().getCurrentCdForJukeBox(this.jukebox);
					boolean isPlaying = Bootstrap.getEngine().getGame().getSoundMachineManager().getIsJukeBoxPlaying(this.jukebox);
					if (this.cds == null)
					{
						this.cds = Bootstrap.getEngine().getGame().getSoundMachineManager().getCdsForJukeBox(this.jukebox);
					}

					if (isPlaying && this.cds.size() > 0)
					{
						UserItem cd = this.cds.get(currentCd % this.cds.size());
						this.currentCd = cd;

						if (this.secondsLeft == 9999999)
						{
							this.secondsLeft = (Bootstrap.getEngine().getGame().getSoundMachineManager().getSongById(cd.getCatalogItem().getSongId()).getLength() * 10);
							this.updateSoundMachine();
						}

						this.secondsLeft--;

						if (this.secondsLeft <= 0)
						{
							this.cds = Bootstrap.getEngine().getGame().getSoundMachineManager().getCdsForJukeBox(this.jukebox);
							currentCd++;
							if (currentCd >= this.cds.size())
							{
								currentCd = 0;
							}

							cd = this.cds.get(currentCd);
							this.currentCd = cd;

							Bootstrap.getEngine().getGame().getSoundMachineManager().saveJukeBoxData(this.jukebox, true, currentCd);
							this.secondsLeft = (Bootstrap.getEngine().getGame().getSoundMachineManager().getSongById(cd.getCatalogItem().getSongId()).getLength() * 10);
							this.updateSoundMachine();
						}

						this.startTime = ((Bootstrap.getEngine().getGame().getSoundMachineManager().getSongById(cd.getCatalogItem().getSongId()).getLength() * 10) - this.secondsLeft);

						this.currentCd = cd;
					}
					else
					{
						this.cds = Bootstrap.getEngine().getGame().getSoundMachineManager().getCdsForJukeBox(this.jukebox);
						this.currentCd = null;
						Bootstrap.getEngine().getGame().getSoundMachineManager().saveJukeBoxData(this.jukebox, false, -1);
					}
				}

				Thread.sleep(100);
			}
			catch (NumberFormatException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
			catch (InterruptedException ignored)
			{
			}
		}
	}
	
	public final void updateSoundMachine()
	{
		final UserItem jukebox = Bootstrap.getEngine().getGame().getSoundMachineManager().getJukeBoxForRoom(this.getRoom());
		
		if (jukebox == null)
			return;
		
		final int currentCd = Bootstrap.getEngine().getGame().getSoundMachineManager().getCurrentCdForJukeBox(jukebox);
		final boolean isPlaying = Bootstrap.getEngine().getGame().getSoundMachineManager().getIsJukeBoxPlaying(jukebox);
		
		if (isPlaying && this.currentCd != null)
		{
			this.getRoom().getRoomUnitsHandler().send(new SoundMachineNowPlayingComposer(this.currentCd.getCatalogItem().getSongId(), currentCd, this.startTime));
		}
		else
		{
			this.getRoom().getRoomUnitsHandler().send(new SoundMachineNowPlayingComposer(-1, -1, -1));
		}
	}

}