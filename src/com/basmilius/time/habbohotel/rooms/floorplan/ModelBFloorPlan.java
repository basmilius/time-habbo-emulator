package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelBFloorPlan extends IFloorPlan
{

	public ModelBFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(5);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxx\n" +
						"xxxxx0000000\n" +
						"xxxxx0000000\n" +
						"xxxxx0000000\n" +
						"xxxxx0000000\n" +
						"x00000000000\n" +
						"x00000000000\n" +
						"x00000000000\n" +
						"x00000000000\n" +
						"x00000000000\n" +
						"x00000000000\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx"
		);
	}

}
