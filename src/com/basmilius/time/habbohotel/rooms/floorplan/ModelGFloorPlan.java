package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelGFloorPlan extends IFloorPlan
{

	public ModelGFloorPlan()
	{
		super();

		this.setDoorX(1);
		this.setDoorY(7);
		this.setDoorZ(1);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxx00000\n" +
						"xxxxxxx00000\n" +
						"xxxxxxx00000\n" +
						"xx1111000000\n" +
						"xx1111000000\n" +
						"xx1111000000\n" +
						"xx1111000000\n" +
						"xx1111000000\n" +
						"xxxxxxx00000\n" +
						"xxxxxxx00000\n" +
						"xxxxxxx00000\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx"
		);
	}

}
