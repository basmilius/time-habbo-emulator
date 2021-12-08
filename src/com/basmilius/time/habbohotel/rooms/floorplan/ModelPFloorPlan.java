package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelPFloorPlan extends IFloorPlan
{

	public ModelPFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(23);
		this.setDoorZ(2);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxx\n" +
						"xxxxxxx222222222222\n" +
						"xxxxxxx222222222222\n" +
						"xxxxxxx222222222222\n" +
						"xxxxxxx222222222222\n" +
						"xxxxxxx222222222222\n" +
						"xxxxxxx222222222222\n" +
						"xxxxxxx22222222xxxx\n" +
						"xxxxxxx11111111xxxx\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x222221111111111111\n" +
						"x2222xx11111111xxxx\n" +
						"x2222xx00000000xxxx\n" +
						"x2222xx000000000000\n" +
						"x2222xx000000000000\n" +
						"x2222xx000000000000\n" +
						"x2222xx000000000000\n" +
						"22222xx000000000000\n" +
						"x2222xx000000000000\n" +
						"xxxxxxxxxxxxxxxxxxx"
		);
	}

}
