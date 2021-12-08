package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelAFloorPlan extends IFloorPlan
{

	public ModelAFloorPlan()
	{
		super();

		this.setDoorX(3);
		this.setDoorY(5);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxx\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxx00000000\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx"
		);
	}

}
