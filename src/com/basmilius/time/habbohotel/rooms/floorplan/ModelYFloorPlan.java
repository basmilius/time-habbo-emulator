package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelYFloorPlan extends IFloorPlan
{

	public ModelYFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(3);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxxxxxxxxxxx\n" +
						"x00000000xx0000000000xx0000x\n" +
						"x00000000xx0000000000xx0000x\n" +
						"000000000xx0000000000xx0000x\n" +
						"x00000000xx0000000000xx0000x\n" +
						"x00000000xx0000xx0000xx0000x\n" +
						"x00000000xx0000xx0000xx0000x\n" +
						"x00000000xx0000xx0000000000x\n" +
						"x00000000xx0000xx0000000000x\n" +
						"xxxxx0000xx0000xx0000000000x\n" +
						"xxxxx0000xx0000xx0000000000x\n" +
						"xxxxx0000xx0000xxxxxxxxxxxxx\n" +
						"xxxxx0000xx0000xxxxxxxxxxxxx\n" +
						"x00000000xx0000000000000000x\n" +
						"x00000000xx0000000000000000x\n" +
						"x00000000xx0000000000000000x\n" +
						"x00000000xx0000000000000000x\n" +
						"x0000xxxxxxxxxxxxxxxxxx0000x\n" +
						"x0000xxxxxxxxxxxxxxxxxx0000x\n" +
						"x00000000000000000000000000x\n" +
						"x00000000000000000000000000x\n" +
						"x00000000000000000000000000x\n" +
						"x00000000000000000000000000x\n" +
						"xxxxxxxxxxxxxxxxxxxxxxxxxxxx"
		);
	}

}
