package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.rooms.floorplan.IFloorPlan;
import com.basmilius.time.habbohotel.rooms.floorplaneditor.FloorPlanData;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.util.ObjectUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class RoomModel
{

	private String id;
	private String heightmap;
	private String map;
	private String relativeMap;
	private int doorX;
	private int doorY;
	private int doorZ;
	private int doorRotation;
	private int mapSize;
	private int mapSizeX;
	private int mapSizeY;
	private int[][] squares;
	private double[][] squareHeights;
	private RoomTileState[][] squareStates;
	private int heighestPoint;

	public RoomModel(final String id, final IFloorPlan floorPlan)
	{
		this.id = id;
		this.heightmap = floorPlan.getHeightmap();
		this.doorX = floorPlan.getDoorX();
		this.doorY = floorPlan.getDoorY();
		this.doorZ = floorPlan.getDoorZ();
		this.doorRotation = floorPlan.getDoorRot();

		this.parse();
	}

	public RoomModel(final FloorPlanData floorPlanData) throws NoSuchAlgorithmException
	{
		this.id = StringCrypt.getSHA1Hash(floorPlanData.getFloorPlan());
		this.heightmap = floorPlanData.getFloorPlan();
		this.doorX = floorPlanData.getDoorNode().getX();
		this.doorY = floorPlanData.getDoorNode().getY();
		this.doorZ = (int)floorPlanData.getDoorNode().getZ();
		this.doorRotation = floorPlanData.getDoorRotation();

		this.parse();
	}

	public final void parse()
	{
		String[] modelTemp = this.heightmap.split("\n");

		this.mapSizeX = modelTemp[0].length();
		this.mapSizeY = modelTemp.length;
		this.squares = new int[this.mapSizeX][this.mapSizeY];
		this.squareHeights = new double[this.mapSizeX][this.mapSizeY];
		this.squareStates = new RoomTileState[this.mapSizeX][this.mapSizeY];

		this.relativeMap = "";

		for (int y = 0; y < this.mapSizeY; y++)
		{
			if (modelTemp[y].isEmpty() || modelTemp[y].length() < this.mapSizeX)
			{
				this.mapSizeY--;
				continue;
			}

			modelTemp[y] = modelTemp[y].replace("\r", "").replace("\n", "").replace("\r\n", "");

			for (int x = 0; x < this.mapSizeX; x++)
			{
				if (x >= modelTemp[y].length())
					continue;

				String Square = modelTemp[y].substring(x, x + 1).trim().toLowerCase();

				if (Square.equals("x"))
				{
					this.squareStates[x][y] = RoomTileState.INVALID;
					this.squares[x][y] = RoomTile.CLOSED;
					this.mapSize++;
				}
				else
				{
					int height;

					if (Square.isEmpty())
					{
						height = 0;
					}
					else if (ObjectUtils.isNumeric(Square))
					{
						height = Integer.parseInt(Square);
					}
					else
					{
						height = (10 + "abcdefghijklmnopqrstuvwxyz".indexOf(Square.toLowerCase()));
					}

					this.squareStates[x][y] = RoomTileState.VALID;
					this.squares[x][y] = RoomTile.OPEN;
					this.squareHeights[x][y] = height;
					this.mapSize++;

					if (this.heighestPoint < height)
					{
						this.heighestPoint = height;
					}
				}

				if (this.doorX == x && this.doorY == y)
				{
					this.squareStates[x][y] = RoomTileState.VALID;
					this.squareHeights[x][y] = this.doorZ;

					if (this.doorZ > 9)
					{
						this.relativeMap += "abcdefghijklmnopqrstuvwxyz".charAt(this.doorZ - 10);
					}
					else
					{
						this.relativeMap += Integer.toString(this.doorZ);
					}
				}
				else
				{
					this.relativeMap += Square;
				}
			}
			this.relativeMap += (char) 13;
		}

		for (String MapLine : this.heightmap.split("\n"))
		{
			if (MapLine == null || MapLine.isEmpty())
			{
				continue;
			}
			this.map += MapLine + (char) 13;
		}
	}

	public boolean getIsDoor(int x, int y)
	{
		return (this.getDoorX() == x && this.getDoorY() == y);
	}

	public String getId()
	{
		return this.id;
	}

	public void setHeightmap(String map)
	{
		this.heightmap = map;
		this.parse();
	}

	public String getMap()
	{
		return this.map;
	}

	public int getHighestPoint()
	{
		return this.heighestPoint;
	}

	public String getRelativeMap()
	{
		return this.relativeMap;
	}

	public Node getDoorNode()
	{
		return new Node(this.doorX, this.doorY, this.doorZ);
	}

	public int getDoorX()
	{
		return this.doorX;
	}

	public int getDoorY()
	{
		return this.doorY;
	}

	public double getDoorZ()
	{
		return this.doorZ;
	}

	public int getDoorRotation()
	{
		return this.doorRotation;
	}

	public int getMapSize()
	{
		return this.mapSize;
	}

	public int getMapSizeX()
	{
		return this.mapSizeX;
	}

	public int getMapSizeY()
	{
		return this.mapSizeY;
	}

	public int[][] getSquares()
	{
		return this.squares;
	}

	public double[][] getNodeHeights()
	{
		return this.squareHeights;
	}

	public RoomTileState[][] getNodeStates()
	{
		return this.squareStates;
	}

	public boolean isValidNode(Node node)
	{
		try
		{
			return node.getX() >= 0 && node.getY() >= 0 && this.squareStates.length >= node.getX() && this.squareStates[node.getX()].length >= node.getY() && (this.squareStates[node.getX()][node.getY()] == RoomTileState.VALID);
		}
		catch (ArrayIndexOutOfBoundsException ignored)
		{
			return false;
		}
	}

	public Node getRandomNode()
	{
		int x = (new Random()).nextInt(this.mapSizeX);
		int y = (new Random()).nextInt(this.mapSizeY);

		if (this.getNodeStates()[x][y] == RoomTileState.VALID && x != this.doorX && y != this.doorY)
			return new Node(x, y);
		else
			return this.getRandomNode();
	}

}
