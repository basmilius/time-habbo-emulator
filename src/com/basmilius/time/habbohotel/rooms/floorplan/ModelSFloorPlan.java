package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelSFloorPlan extends IFloorPlan
{

	public ModelSFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(3);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxx\n" +
						"x00000\n" +
						"x00000\n" +
						"000000\n" +
						"x00000\n" +
						"x00000\n" +
						"x00000\n" +
						"x00000"
		);
	}

}
