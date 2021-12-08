package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelFFloorPlan extends IFloorPlan
{

	public ModelFFloorPlan()
	{
		super();

		this.setDoorX(2);
		this.setDoorY(5);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxx\n" +
						"xxxxxxx0000x\n" +
						"xxxxxxx0000x\n" +
						"xxx00000000x\n" +
						"xxx00000000x\n" +
						"xxx00000000x\n" +
						"xxx00000000x\n" +
						"x0000000000x\n" +
						"x0000000000x\n" +
						"x0000000000x\n" +
						"x0000000000x\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx"
		);
	}

}
