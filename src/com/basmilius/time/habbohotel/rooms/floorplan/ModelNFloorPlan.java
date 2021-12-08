package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelNFloorPlan extends IFloorPlan
{

	public ModelNFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(16);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxxxx\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x000000xxxxxxxx000000\n" +
						"x000000x000000x000000\n" +
						"x000000x000000x000000\n" +
						"x000000x000000x000000\n" +
						"x000000x000000x000000\n" +
						"x000000x000000x000000\n" +
						"x000000x000000x000000\n" +
						"x000000xxxxxxxx000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"x00000000000000000000\n" +
						"xxxxxxxxxxxxxxxxxxxxx"
		);
	}

}
