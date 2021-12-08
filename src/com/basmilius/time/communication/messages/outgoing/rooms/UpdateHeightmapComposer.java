package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class UpdateHeightmapComposer extends MessageComposer
{

	private final List<Node> nodes;

	public UpdateHeightmapComposer(final List<Node> nodes)
	{
		this.nodes = nodes;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.UpdateHeightmap);
		response.appendByte(this.nodes.size());
		for (Node node : this.nodes)
		{
			response.appendByte(node.getX());
			response.appendByte(node.getY());
			response.appendShort((short)node.getZ());
		}
		return response;
	}

}
