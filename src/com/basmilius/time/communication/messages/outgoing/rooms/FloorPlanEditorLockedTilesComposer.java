package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class FloorPlanEditorLockedTilesComposer extends MessageComposer
{

	private final List<Node> nodes;

	public FloorPlanEditorLockedTilesComposer(List<Node> nodes)
	{
		this.nodes = nodes;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.FloorPlanEditorLockedTiles);
		response.appendInt(this.nodes.size());
		for (Node node : this.nodes)
		{
			response.appendInt(node.getX());
			response.appendInt(node.getY());
		}
		return response;
	}

}
