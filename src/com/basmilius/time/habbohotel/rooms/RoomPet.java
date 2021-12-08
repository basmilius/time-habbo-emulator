package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.algorithm.PopularNodesRoomAlgorithm;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.pets.Pet;
import com.basmilius.time.habbohotel.pets.races.HorsePet;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public final class RoomPet extends RoomUnit implements RoomUnit.WalkToCallback
{

	private final Pet pet;
	
	private final Timer timer;
	
	public RoomPet(final Room room, final Pet pet)
	{
		super(room);
		
		this.pet = pet;
		this.unitType = RoomUnitType.PET;
		
		this.x = this.pet.getX();
		this.y = this.pet.getY();
		this.z = this.pet.getZ();
		this.setRotation(this.pet.getRotation());
		
		this.timer = new Timer();
		this.onTimerTick();
	}
	
	public final Pet getPet()
	{
		return this.pet;
	}
	
	private void onTimerTick()
	{
		if (!this.room.isActive())
			return;
		
		this.timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				if (!RoomPet.this.room.isActive())
					return;
				
				try
				{
					final int result = (RoomPet.this.getRoom().getRandomizer().nextInt(5) % 2);
					switch(result)
					{
						case 0:
							RoomPet.this.onTimerPetWalk();
							break;
						
						case 1:
							RoomPet.this.onTimerPetSpeech();
							break;
					}
				}
				catch(IPixelException e)
				{
					Bootstrap.getEngine().getLogging().handleException(e);
				}
				RoomPet.this.onTimerTick();
			}
			
		}, (this.room.getRandomizer().nextInt(5) * 10000));
	}
	
	private void onTimerPetSpeech()
	{
		this.chatRoomMessage(ChatType.TALK, "$(pet.speech.0)", ChatBubble.DEFAULT);
	}
	
	private void onTimerPetWalk() throws IPixelException
	{
		final Node node = (new PopularNodesRoomAlgorithm(this.getRoom())).getRandomPopularNode(6);
		this.walkTo(node, -1, this);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendInt(this.pet.getId());
		response.appendString(this.pet.getName());
		response.appendString("Brrrr...Huisdier");
		response.appendString(this.pet.getLook());
		response.appendInt(this.getUnitId());
		response.appendInt(this.getX());
		response.appendInt(this.getY());
		response.appendString(Double.toString(this.getZ()));
		response.appendInt(this.getBodyRotation());

		response.appendInt(2);
		response.appendInt(this.pet.getType());
		response.appendInt(this.pet.getHabbo().getId());
		response.appendString(this.pet.getHabbo().getUsername());
		response.appendInt(this.pet.getRarity());
		response.appendBoolean(HorsePet.class.isInstance(this.pet) && ((HorsePet) this.pet).getSaddleId() > 0); // ??
		response.appendBoolean(HorsePet.class.isInstance(this.pet) && ((HorsePet) this.pet).isAnyoneCanRide()); // ??
		response.appendBoolean(false); // ??
		response.appendBoolean(false); // ??
		response.appendBoolean(true);  // ??
		response.appendBoolean(true);  // toggle breeding permission
		response.appendInt(HorsePet.class.isInstance(this.pet) ? 1 : 0);
		response.appendString("");
	}

	@Override
	public void serializeStatus(final ServerMessage response)
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
	}

	@Override
	public void onWalkToFailed(final RoomUnit unit)
	{
	}
	
}
