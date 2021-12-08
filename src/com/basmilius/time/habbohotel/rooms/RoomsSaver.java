package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.util.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Queue;

public class RoomsSaver extends Thread
{
	
	private boolean active;
	
	private final Queue<RoomData> roomsToUpdate;

	public RoomsSaver()
	{
		this.roomsToUpdate = Queues.newConcurrentLinkedQueue();
		this.active = true;
		this.setDaemon(true);
		this.start();
	}
	
	public final void addRoom(final Room room)
	{
		if(this.roomsToUpdate.contains(room.getRoomData()))
			return;
		
		this.roomsToUpdate.add(room.getRoomData());
	}
	
	public final void dispose()
	{
		this.active = false;
	}
	
	@Override
	public final void run()
	{
		while (this.active)
		{
			try
			{
				Thread.sleep(Bootstrap.getEngine().getConfig().getInt("server.thread.save.rooms", 30000));
				int updateCount = this.roomsToUpdate.size();
				
				while(updateCount > 0)
				{
					final List<RoomData> updateList = Lists.newLinkedList();
					String query = "INSERT INTO `rooms` (id, owner_id, room_name, room_description, room_model, room_password, door_mode, trading_mode, users_max, guild_id, category, score, paper_floor, paper_wall, paper_landscape, height_wall, thickness_wall, thickness_floor, tags, is_public, allow_other_pets, allow_other_pets_eat, allow_walkthrough, allow_hidewall, chat_mode, chat_weight, chat_speed, chat_hearing_distance, floorplan_map, floorplan_door_x, floorplan_door_y, floorplan_door_z, floorplan_door_rotation, who_can_mute, who_can_kick, who_can_ban, anti_flood_control) VALUES ";
					for (int i = 0; i < 25 && this.roomsToUpdate.peek() != null; i++)
					{
						query += "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?),";
						updateCount--;
						updateList.add(this.roomsToUpdate.poll());
					}
					query = query.substring(0, query.length() - 1) + " ";
					query += "ON DUPLICATE KEY UPDATE id = VALUES(id), owner_id = VALUES(owner_id), room_name = VALUES(room_name), room_description = VALUES(room_description), room_model = VALUES(room_model), room_password = VALUES(room_password), door_mode = VALUES(door_mode), trading_mode = VALUES(trading_mode), users_max = VALUES(users_max), guild_id = VALUES(guild_id), category = VALUES(category), score = VALUES(score), paper_floor = VALUES(paper_floor), paper_wall = VALUES(paper_wall), paper_landscape = VALUES(paper_landscape), height_wall = VALUES(height_wall), thickness_wall = VALUES(thickness_wall)";
					query += ", thickness_floor = VALUES(thickness_floor), tags = VALUES(tags), is_public = VALUES(is_public), allow_other_pets = VALUES(allow_other_pets), allow_other_pets_eat = VALUES(allow_other_pets_eat), allow_walkthrough = VALUES(allow_walkthrough), allow_hidewall = VALUES(allow_hidewall), chat_mode = VALUES(chat_mode), chat_weight = VALUES(chat_weight), chat_speed = VALUES(chat_speed), chat_hearing_distance = VALUES(chat_hearing_distance), floorplan_map = VALUES(floorplan_map), floorplan_door_x = VALUES(floorplan_door_x), floorplan_door_y = VALUES(floorplan_door_y)";
					query += ", floorplan_door_z = VALUES(floorplan_door_z), floorplan_door_rotation = VALUES(floorplan_door_rotation), who_can_mute = VALUES(who_can_mute), who_can_kick = VALUES(who_can_kick), who_can_ban = VALUES(who_can_ban), anti_flood_control = VALUES(anti_flood_control)";
					
					final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare(query);
					{
						if (statement != null)
						{
							int i = 0;
							for (final RoomData data : updateList)
							{
								statement.setInt(++i, data.getId());
								statement.setInt(++i, data.getOwner().getId());
								statement.setString(++i, data.getRoomName());
								statement.setString(++i, data.getRoomDescription());
								statement.setString(++i, data.getRoomModel().getId());
								statement.setString(++i, data.getRoomPassword());
								statement.setInt(++i, data.getDoorMode().asInt());
								statement.setInt(++i, data.getTradingMode().asInt());
								statement.setInt(++i, data.getUsersLimit());
								statement.setInt(++i, data.getGuildId());
								statement.setInt(++i, data.getCategory());
								statement.setInt(++i, data.getScore());
								statement.setString(++i, data.getRoomDecoration().getFloorpaper());
								statement.setString(++i, data.getRoomDecoration().getWallpaper());
								statement.setDouble(++i, data.getRoomDecoration().getLandscape());
								statement.setInt(++i, data.getRoomDecoration().getWallHeight());
								statement.setInt(++i, data.getRoomDecoration().getWallThickness());
								statement.setInt(++i, data.getRoomDecoration().getFloorThickness());
								statement.setString(++i, StringUtils.implode(",", data.getTags()));
								statement.setString(++i, data.getPermissions().isPublicRoom() ? "1" : "0");
								statement.setString(++i, data.getPermissions().isOtherPetsAllowed() ? "1" : "0");
								statement.setString(++i, data.getPermissions().isOtherPetsEatingAllowed() ? "1" : "0");
								statement.setString(++i, data.getPermissions().isWalkthroughAllowed() ? "1" : "0");
								statement.setString(++i, data.getRoomDecoration().isWallHidden() ? "1" : "0");
								statement.setInt(++i, data.getFreeFlowChat().getChatMode());
								statement.setInt(++i, data.getFreeFlowChat().getChatWeight());
								statement.setInt(++i, data.getFreeFlowChat().getChatSpeed());
								statement.setInt(++i, data.getFreeFlowChat().getChatHearingDistance());
								statement.setString(++i, data.getFloorPlanData().getFloorPlan());
								statement.setInt(++i, data.getFloorPlanData().getDoorNode().getX());
								statement.setInt(++i, data.getFloorPlanData().getDoorNode().getY());
								statement.setInt(++i, (int) data.getFloorPlanData().getDoorNode().getZ());
								statement.setInt(++i, data.getFloorPlanData().getDoorRotation());
								statement.setInt(++i, data.getPermissions().getMuteAccessLevel());
								statement.setInt(++i, data.getPermissions().getKickAccessLevel());
								statement.setInt(++i, data.getPermissions().getBanAccessLevel());
								statement.setInt(++i, data.getFreeFlowChat().getFloodControl());
							}
							statement.execute();
						}
					}
				}
			}
			catch (final InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
			catch (final SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}
	}
}
