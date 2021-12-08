package com.basmilius.time.habbohotel.bots;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BotServeManager implements Runnable, RoomUnit.WalkToCallback
{

	private final Bot bot;

	private Queue<Operator> units;

	private Operator currentOperator;

	public BotServeManager(final Bot bot)
	{
		this.bot = bot;

		this.units = new ConcurrentLinkedQueue<>();
	}

	private void startThread()
	{
		if (this.isGivingOperator())
			return;

		(new Thread(this)).start();
	}

	public void addUnit(final RoomUser unit, final BotServe serve)
	{
		if (this.bot.getRoomBot() == null)
			return;
		
		if (this.units.size() >= 4)
		{
			this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, Bootstrap.getEngine().getConfig().getString("bot.default.messages.user.asks.drink.cant", "It's busy right now, %username! Please ask it again later!").replace("%username", unit.getName()), ChatBubble.BOT, unit.getHabbo(), false);
			this.startThread();
			return;
		}

		if (unit.getIsWalking())
		{
			this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, Bootstrap.getEngine().getConfig().getString("bot.default.messages.user.asks.drink.move", "Stop running away from me!").replace("%username", unit.getName()), ChatBubble.BOT, unit.getHabbo(), false);
			this.startThread();
			return;
		}

		if (this.isGivingOperator())
		{
			this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, Bootstrap.getEngine().getConfig().getString("bot.default.messages.user.asks.drink.wait", "I'll be right there!").replace("%username", unit.getName()), ChatBubble.BOT, unit.getHabbo(), false);
		}

		this.units.add(new Operator(unit, serve));
		this.startThread();
	}

	public boolean isGivingOperator()
	{
		return this.currentOperator != null && this.currentOperator.getUnit() != null;
	}

	@Override
	public void run()
	{
		if (this.units.peek() != null)
		{
			this.currentOperator = this.units.poll();

			if (this.currentOperator.getUnit() == null)
			{
				this.currentOperator = null;
				this.startThread();
				return;
			}

			try
			{
				if (this.bot.getRoomBot() == null)
					return;
				
				if (!this.bot.getRoom().getRoomUnitsHandler().canWalk(this.currentOperator.getUnit().getNodeInFront()))
				{
					this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, Bootstrap.getEngine().getConfig().getString("bot.default.messages.user.asks.drink.cant.reach", "I'm sorry %username! I can't bring your order because i can't reach you!").replace("%username", this.currentOperator.getUnit().getName()), ChatBubble.BOT, this.currentOperator.getUnit().getHabbo(), false);
					this.currentOperator = null;
					this.startThread();
					return;
				}

				this.bot.getRoomBot().carryItem(this.currentOperator.getServe().getItemId());
				this.bot.getRoomBot().walkTo(this.currentOperator.getUnit().getNodeInFront(), -1, this);
			}
			catch (IPixelException e)
			{
				this.currentOperator = null;
				this.startThread();
			}
		}
	}

	@Override
	public boolean onStep(final RoomUnit unit, final Node node)
	{
		return (this.isGivingOperator() && this.bot.getRoomBot() != null && this.currentOperator.getUnit().getNodeInFront().equals(this.bot.getRoomBot().getGoal()));
	}

	@Override
	public void onWalkToSuccess(final RoomUnit unit)
	{
		(new Thread(() -> {
			try
			{
				if (this.bot.getRoomBot() == null)
					return;
				
				if (unit.getNode().equals(this.currentOperator.getUnit().getNodeInFront()))
				{
					Thread.sleep(112);

					unit.lookAtUnit(this.currentOperator.getUnit());

					Thread.sleep(215);

					this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, this.currentOperator.getServe().getRandomResponse(), ChatBubble.BOT, this.currentOperator.getUnit().getHabbo(), false);
					this.currentOperator.getUnit().carryItem(this.currentOperator.getServe().getItemId());
				}
				else
				{
					this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, Bootstrap.getEngine().getConfig().getString("bot.default.messages.user.asks.drink.move", "Stop running away from me!").replace("%username", this.currentOperator.getUnit().getName()), ChatBubble.BOT, this.currentOperator.getUnit().getHabbo(), false);
				}

				this.bot.getRoomBot().carryItem(0);
				Thread.sleep(3000);
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}

			this.currentOperator = null;
			this.startThread();
		})).start();
	}

	@Override
	public void onWalkToFailed(final RoomUnit unit)
	{
		if (this.bot.getRoomBot() == null)
			return;
		
		this.bot.getRoomBot().carryItem(0);

		this.bot.getRoomBot().setGoal(this.bot.getRoomBot().getNode());
		this.bot.getRoomBot().chatRoomMessage(ChatType.TALK, Bootstrap.getEngine().getConfig().getString("bot.default.messages.user.asks.drink.move", "Stop running away from me!").replace("%username", this.currentOperator.getUnit().getName()), ChatBubble.BOT, this.currentOperator.getUnit().getHabbo(), false);

		this.bot.getRoomBot().carryItem(0);
		this.currentOperator = null;

		this.startThread();
	}

	private class Operator
	{

		private RoomUser unit;
		private BotServe serve;

		public Operator(final RoomUser unit, final BotServe serve)
		{
			this.unit = unit;
			this.serve = serve;
		}

		public RoomUser getUnit()
		{
			return this.unit;
		}

		public BotServe getServe()
		{
			return this.serve;
		}
	}

}
