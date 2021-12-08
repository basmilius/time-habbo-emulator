package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomErrorKickedComposer;

import java.util.Map;

public class RoomUser extends RoomUnit
{

	private final SocketConnection connection;

	private boolean isLeaving = false;
	private boolean isSpectator = false;

	public RoomUser(final Room room, final SocketConnection connection)
	{
		super(room);

		this.connection = connection;
		this.unitType = RoomUnitType.USER;

		if (this.getHabbo().getCurrentEffectId() != 0)
		{
			this.applyEffect(this.getHabbo().getCurrentEffectId());
		}
	}

	public SocketConnection getConnection ()
	{
		return this.connection;
	}

	public Habbo getHabbo()
	{
		return this.connection.getHabbo();
	}

	public boolean isLeaving()
	{
		return this.isLeaving;
	}

	public boolean isSpectator()
	{
		return this.isSpectator;
	}

	public void setIsLeaving(final boolean isLeaving)
	{
		this.isLeaving = isLeaving;
	}

	public void setIsSpectator(final boolean isSpectator)
	{
		this.isSpectator = isSpectator;
	}

	public void kick()
	{
		this.kick(false);
	}

	public void kick(boolean systemKick)
	{
		this.getHabbo().leaveRoom(true);

		if (!systemKick)
		{
			this.connection.send(new RoomErrorKickedComposer(RoomErrorKickedComposer.ROOM_ERROR_KICKED));
		}
	}

	@Override
	public void serialize(ServerMessage response)
	{
		response.appendInt(this.getHabbo().getId());
		response.appendString(this.getHabbo().getUsername());
		response.appendString(this.getHabbo().getMotto());
		response.appendString(this.getHabbo().getLook());
		response.appendInt(this.getUnitId());
		response.appendInt(this.getX());
		response.appendInt(this.getY());
		response.appendString(Double.toString(this.getZ()));
		response.appendInt(this.getBodyRotation());

		response.appendInt(1);
		response.appendString(this.getHabbo().getGender());
		response.appendInt((this.getHabbo().getSettings().getFavoriteGuild() != null) ? this.getHabbo().getSettings().getFavoriteGuild().getId() : -1);
		response.appendInt((this.getHabbo().getSettings().getFavoriteGuild() != null) ? 1 : -1);
		response.appendString((this.getHabbo().getSettings().getFavoriteGuild() != null) ? this.getHabbo().getSettings().getFavoriteGuild().getName() : "");
		response.appendString("");
		response.appendInt(this.getHabbo().getSettings().getAchievementScore());
		response.appendBoolean(false);
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

	@Override
	public void run()
	{

	}

}
