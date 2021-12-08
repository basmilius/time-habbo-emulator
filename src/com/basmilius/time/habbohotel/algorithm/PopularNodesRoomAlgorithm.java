package com.basmilius.time.habbohotel.algorithm;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PopularNodesRoomAlgorithm extends IAlgorithm<Room>
{

	public PopularNodesRoomAlgorithm(final Room room)
	{
		this.setObject(room);
	}

	private boolean containsScoreNode(final List<ScoreNode> nodes, final Node node)
	{
		for (final ScoreNode n : nodes)
			if (n.getNode().getX() == node.getX() && n.getNode().getY() == node.getY())
				return true;

		return false;
	}

	private ScoreNode getScoreNode(final List<ScoreNode> nodes, final Node node)
	{
		for (final ScoreNode n : nodes)
			if (n.getNode().equals(node))
				return n;

		return null;
	}

	public Node getRandomPopularNode(final int radius)
	{
		return this.getRandomPopularNode(radius, false, 0);
	}

	public Node getRandomPopularNode(final int radius, final boolean alwaysUseAlgorithm, final int counter)
	{
		if (counter >= 3)
			return this.getObject().getRoomData().getRoomModel().getRandomNode();

		final List<Node> nodes = this.getPopularNodesInRoom(radius);
		final SecureRandom random = new SecureRandom();

		if (!alwaysUseAlgorithm && (random.nextInt(5) >= 4 || nodes.size() == 0))
		{
			return this.getObject().getRoomData().getRoomModel().getRandomNode();
		}

		final Node result = nodes.get(random.nextInt(nodes.size()));

		if (this.getObject().getRoomData().getRoomModel().isValidNode(result) && this.getObject().getRoomUnitsHandler().canWalk(result) && !this.getObject().getRoomData().getRoomModel().getDoorNode().equals(result))
			return result;

		return this.getRandomPopularNode(radius, true, (counter + 1));
	}

	public List<Node> getPopularNodesInRoom(final int radius)
	{
		final List<Node> nodes = new ArrayList<>();
		final List<ScoreNode> scoreNodes = new ArrayList<>();

		for (final RoomUnit unit : this.getObject().getRoomUnitsHandler().getUsers())
		{
			for (final Node node : this.getNodesAround(unit.getNode(), radius))
			{
				if (this.containsScoreNode(scoreNodes, node))
				{
					//noinspection ConstantConditions
					this.getScoreNode(scoreNodes, node).addScore();
				}
				else
				{
					scoreNodes.add(new ScoreNode(node));
				}
			}
		}

		Collections.sort(scoreNodes, (o1, o2) -> Integer.compare(o1.getScore(), o2.getScore()));

		for (final ScoreNode scoreNode : scoreNodes)
		{
			if (scoreNode.getScore() == 0)
				continue;

			nodes.add(scoreNode.getNode());
		}

		return nodes;
	}

	public List<Node> getNodesAround(final Node node, final int radius)
	{
		final List<Node> nodes = new ArrayList<>();

		for (int i = radius; i > 0; i--)
		{
			int x = node.getX() - i;
			int y = node.getY() - i;

			while (x < (node.getX() + i))
			{
				while (y < (node.getY() + i))
				{
					if (!this.getObject().getRoomData().getRoomModel().isValidNode(new Node(x, ++y)))
						continue;

					nodes.add(new Node(x, y));
				}
				x++;
			}
		}

		return nodes;
	}

	private class ScoreNode
	{

		private final Node node;
		private int score;

		public ScoreNode(final Node node)
		{
			this.node = node;
			this.score = 1;
		}

		public Node getNode()
		{
			return this.node;
		}

		public void addScore()
		{
			this.score = (this.score + 1);
		}

		public int getScore()
		{
			return this.score;
		}

	}

}
