package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelEFloorPlan extends IFloorPlan
{

	public ModelEFloorPlan()
	{
		super();

		this.setDoorX(1);
		this.setDoorY(5);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xx0000000000\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx"
		);
	}

}
