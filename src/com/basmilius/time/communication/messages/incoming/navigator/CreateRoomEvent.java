package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomModel;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.CreateRoomComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.RoomNameUnacceptableComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Event(id = Incoming.CreateRoom)
public class CreateRoomEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String roomName = packet.readString();
		String roomDescription = packet.readString();
		String roomModel = packet.readString();
		int categoryId = packet.readInt();
		int maxUsers = packet.readInt();
		int tradeSetting = packet.readInt();

		if (Bootstrap.getEngine().getGame().getWordFilterManager().stringContainsBadWord(roomName))
		{
			connection.send(new RoomNameUnacceptableComposer());
			return;
		}

		RoomModel model = Bootstrap.getEngine().getGame().getRoomManager().getRoomModel(roomModel);
		if (model == null)
		{
			connection.sendNotif(GenericAlertType.TEXT, Bootstrap.getEngine().getConfig().getString("error.create.room.model.not.exists", "Sorry! This room layout doesn't exists!"));
			return;
		}

		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO rooms (owner_id, owner_name, room_name, room_description, room_model, category, users_max, trading_mode, floorplan_map) VALUES (?, ?, ?, ?, ?, ?, ?, ?, '')");
		{
			if (statement != null)
			{
				statement.setInt(1, connection.getHabbo().getId());
				statement.setString(2, connection.getHabbo().getUsername());
				statement.setString(3, roomName);
				statement.setString(4, roomDescription);
				statement.setString(5, model.getId());
				statement.setInt(6, categoryId);
				statement.setInt(7, maxUsers);
				statement.setInt(8, tradeSetting);
				statement.execute();

				ResultSet keys = statement.getGeneratedKeys();
				keys.next();
				
				final int roomId = keys.getInt(1);

				Bootstrap.getEngine().getGame().getRoomManager().loadSingleRoom(roomId);
				final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);
				connection.send(new CreateRoomComposer(room));
			}
		}
	}

}
