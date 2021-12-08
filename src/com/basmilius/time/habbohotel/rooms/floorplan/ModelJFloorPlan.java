package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelJFloorPlan extends IFloorPlan
{

	public ModelJFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(10);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxxxx\n" +
						"xxxxxxxxxxx0000000000\n" +
						"xxxxxxxxxxx0000000000\n" +
						"xxxxxxxxxxx0000000000\n" +
						"xxxxxxxxxxx0000000000\n" +
						"xxxxxxxxxxx0000000000\n" +
						"xxxxxxxxxxx0000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x0000000000xxxxxxxxxx\n" +
						"x0000000000xxxxxxxxxx\n" +
						"x0000000000xxxxxxxxxx\n" +
						"x0000000000xxxxxxxxxx\n" +
						"x0000000000xxxxxxxxxx\n" +
						"x0000000000xxxxxxxxxx\n" +
						"xxxxxxxxxxxxxxxxxxxxx"
		);
	}

}
