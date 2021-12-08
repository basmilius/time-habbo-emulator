package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelHFloorPlan extends IFloorPlan
{

	public ModelHFloorPlan()
	{
		super();

		this.setDoorX(4);
		this.setDoorY(4);
		this.setDoorZ(1);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxx111111x\n" +
						"xxxxx111111x\n" +
						"xxxxx111111x\n" +
						"xxxxx111111x\n" +
						"xxxxx111111x\n" +
						"xxxxx000000x\n" +
						"xxxxx000000x\n" +
						"xxx00000000x\n" +
						"xxx00000000x\n" +
						"xxx00000000x\n" +
						"xxx00000000x\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx\n" +
						"xxxxxxxxxxxx"
		);
	}

}
