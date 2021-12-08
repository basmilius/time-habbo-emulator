package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.algorithm.PopularNodesRoomAlgorithm;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.habbohotel.bots.BotServe;
import com.basmilius.time.habbohotel.bots.BotServeManager;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.ObjectUtils;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RoomBot extends RoomUnit implements RoomUnit.WalkToCallback
{

	private final Bot bot;
	private final BotServeManager botServeManager;

	private final Timer timer;

	public RoomBot(Room room, Bot bot)
	{
		super(room);
		
		this.unitType = RoomUnitType.BOT;
		this.bot = bot;
		this.canWalk = true;
		this.isTeleporting = false;
		this.x = bot.getX();
		this.y = bot.getY();
		this.z = bot.getZ();

		this.botServeManager = new BotServeManager(this.bot);

		this.timer = new Timer();
		this.onTimerTick();
	}

	public Bot getBot()
	{
		return this.bot;
	}

	private void onTimerBotSpeech()
	{
		this.onTimerBotSpeech(0);
	}
	
	private void onTimerBotSpeech(final int tryCount)
	{
		if (tryCount == 3 || this.botServeManager.isGivingOperator() || this.bot.getChatLines().size() == 0)
			return;

		String chatLine = this.getBot().getRandomChatLine();
		final RoomUser randomUser = ObjectUtils.getRandomObject(this.getRoom().getRoomUnitsHandler().getUsers());
		
		if (randomUser == null)
		{
			this.onTimerBotSpeech(tryCount + 1);
			return;
		}
		
		chatLine = chatLine.replace("%randomName%", randomUser.getName());

		if (!chatLine.isEmpty() && !(RoomBot.this.getIsWalking() && Bootstrap.getEngine().getGame().getChatCommands().commandExists(chatLine)))
		{
			RoomBot.this.chatRoomMessage(ChatType.TALK, chatLine, ChatBubble.BOT);
		}

		if (Bootstrap.getEngine().getGame().getChatCommands().commandExists(chatLine))
		{
			RoomBot.this.chatRoomMessage(ChatType.TALK, chatLine, ChatBubble.BOT);
		}
	}

	private void onTimerBotWalk() throws IPixelException
	{
		if (this.botServeManager.isGivingOperator())
			return;

		final Node node = (new PopularNodesRoomAlgorithm(this.getRoom())).getRandomPopularNode(6);
		this.walkTo(node, -1, this);
	}

	private void onTimerTick()
	{
		if (!RoomBot.this.getRoom().isActive())
			return;

		this.timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if (!RoomBot.this.getRoom().isActive())
					return;

				try
				{
					final int result = (RoomBot.this.getRoom().getRandomizer().nextInt(5) % 2);
					switch(result)
					{
						case 0:
							RoomBot.this.onTimerBotWalk();
							break;
						case 1:
							RoomBot.this.onTimerBotSpeech();
							break;
					}
				}
				catch (IPixelException e)
				{
					Bootstrap.getEngine().getLogging().handleException(e);
				}
				RoomBot.this.onTimerTick();
			}
		}, (this.getRoom().getRandomizer().nextInt(5) * 12000));
	}

	public void onUserEntersRoom(@SuppressWarnings("UnusedParameters") final Habbo habbo)
	{
	}

	public boolean onUserChat(final Habbo habbo, String text)
	{
		if (this.getBot().getOwner() != null)
		{
			try
			{
				Map<String, String> botDrinks = Bootstrap.getEngine().getConfig().getPropertiesStartWith("bot.serve.");

				for (String botDrink : botDrinks.values())
				{
					final BotServe serve = new BotServe(botDrink);

					if (serve.getContainsKeyword(text))
					{
						this.botServeManager.addUnit(habbo.getRoomUser(), serve);

						return true;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public void serialize(ServerMessage response)
	{
		response.appendInt(this.getBot().getId());
		response.appendString(this.getBot().getName());
		response.appendString(this.getBot().getMotto());
		response.appendString(this.getBot().getLook());
		response.appendInt(this.getUnitId());
		response.appendInt(this.getX());
		response.appendInt(this.getY());
		response.appendString(Double.toString(this.getZ()));
		response.appendInt(this.getBodyRotation());
		response.appendInt(this.getBot().getOwner() != null ? 4 : 3);
		if (this.getBot().getOwner() != null)
		{
			response.appendString(this.getBot().getGender());
			response.appendInt(this.getBot().getOwner().getId());
			response.appendString(this.getBot().getOwner().getUsername());
			response.appendInt(5);
			response.appendShort(1); // change name
			response.appendShort(2); // give look
			response.appendShort(3); // change texts
			response.appendShort(4); // walk/stand
			response.appendShort(5); // dance
		}
	}

	@Override
	public void serializeStatus(ServerMessage response)
	{
		response.appendInt(this.getUnitId());
		response.appendInt(this.getIsWalking() && this.getWalkingFromNode() != null ? this.getWalkingFromNode().getX() : this.getX());
		response.appendInt(this.getIsWalking() && this.getWalkingFromNode() != null ? this.getWalkingFromNode().getY() : this.getY());
		response.appendString(Double.toString(this.getIsWalking() && this.getWalkingFromNode() != null ? this.getWalkingFromNode().getZ() : this.getZ()));
		response.appendInt(this.getHeadRotation());
		response.appendInt(this.getBodyRotation());

		String status = "/";

		for (Map.Entry<String, String> set : this.getStatuses().entrySet())
		{
			status += set.getKey() + " " + set.getValue() + "/";
		}

		response.appendString(status);
	}

	public void dispose()
	{
		this.timer.cancel();
	}

	@Override
	public void run()
	{
	}

	@Override
	public boolean onStep(final RoomUnit unit, final Node node)
	{
		return true;
	}

	@Override
	public void onWalkToSuccess(final RoomUnit unit)
	{
		RoomBot.this.lookAtUnit(ObjectUtils.getRandomObject(RoomBot.this.getRoom().getRoomUnitsHandler().getUnits().stream().filter(u -> u.getUnitId() != RoomBot.this.getUnitId())));
	}

	@Override
	public void onWalkToFailed(final RoomUnit unit)
	{
	}
}
