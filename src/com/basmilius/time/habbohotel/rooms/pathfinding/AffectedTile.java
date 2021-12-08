package com.basmilius.time.habbohotel.rooms.pathfinding;

import com.basmilius.time.habbohotel.items.UserItem;

import java.util.ArrayList;
import java.util.List;

public class AffectedTile
{

	public final int X;
	public final int Y;
	private final int I;

	public AffectedTile(int x, int y, int i)
	{
		X = x;
		Y = y;
		I = i;
	}

	public static List<AffectedTile> getAffectedTilesAt(UserItem item)
	{
		return AffectedTile.getAffectedTilesAt(item.getLength(), item.getWidth(), item.getX(), item.getY(), item.getRot());
	}

	public static List<AffectedTile> getAffectedTilesAt(int Length, int Width, int PosX, int PosY, int Rotation)
	{
		List<AffectedTile> PointList = new ArrayList<>();

		if (Length > 1)
		{
			if (Rotation == 0 || Rotation == 4)
			{
				for (int i = 1; i < Length; i++)
				{
					PointList.add(new AffectedTile(PosX, PosY + i, i));

					for (int j = 1; j < Width; j++)
					{
						PointList.add(new AffectedTile(PosX + j, PosY + i, (i < j) ? j : i));
					}
				}
			}
			else if (Rotation == 2 || Rotation == 6)
			{
				for (int i = 1; i < Length; i++)
				{
					PointList.add(new AffectedTile(PosX + i, PosY, i));

					for (int j = 1; j < Width; j++)
					{
						PointList.add(new AffectedTile(PosX + i, PosY + j, (i < j) ? j : i));
					}
				}
			}
		}

		if (Width > 1)
		{
			if (Rotation == 0 || Rotation == 4)
			{
				for (int i = 1; i < Width; i++)
				{
					PointList.add(new AffectedTile(PosX + i, PosY, i));

					for (int j = 1; j < Length; j++)
					{
						PointList.add(new AffectedTile(PosX + i, PosY + j, (i < j) ? j : i));
					}
				}
			}
			else if (Rotation == 2 || Rotation == 6)
			{
				for (int i = 1; i < Width; i++)
				{
					PointList.add(new AffectedTile(PosX, PosY + i, i));

					for (int j = 1; j < Length; j++)
					{
						PointList.add(new AffectedTile(PosX + j, PosY + i, (i < j) ? j : i));
					}
				}
			}
		}
		return PointList;
	}

	public Node getNode()
	{
		return new Node(this.X, this.Y);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof AffectedTile))
			return false;

		AffectedTile tile = (AffectedTile) obj;
		return tile.X == this.X && tile.Y == this.Y && tile.I == this.I;
	}

}
