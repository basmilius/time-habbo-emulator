package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class AvatarEditorFigureSetIdsComposer extends MessageComposer
{

	private final List<Integer> partIds;
	private final List<String> partTypes;

	public AvatarEditorFigureSetIdsComposer(final List<Integer> partIds, final List<String> partTypes)
	{
		this.partIds = partIds;
		this.partTypes = partTypes;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.AvatarEditorFigureSetIds);
		response.appendInt(this.partIds.size());
		this.partIds.forEach(response::appendInt);
		response.appendInt(this.partTypes.size());
		this.partTypes.forEach(response::appendString);
		return response;
	}

}
