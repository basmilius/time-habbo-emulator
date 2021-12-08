package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelWFloorPlan extends IFloorPlan
{

	public ModelWFloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(3);
		this.setDoorZ(2);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxxxxxxxxxx\n" +
						"x2222xx1111111111xx11111111\n" +
						"x2222xx1111111111xx11111111\n" +
						"222222111111111111111111111\n" +
						"x22222111111111111111111111\n" +
						"x22222111111111111111111111\n" +
						"x22222111111111111111111111\n" +
						"x2222xx1111111111xx11111111\n" +
						"x2222xx1111111111xx11111111\n" +
						"x2222xx1111111111xxxx1111xx\n" +
						"x2222xx1111111111xxxx0000xx\n" +
						"xxxxxxx1111111111xx00000000\n" +
						"xxxxxxx1111111111xx00000000\n" +
						"x22222111111111111000000000\n" +
						"x22222111111111111000000000\n" +
						"x22222111111111111000000000\n" +
						"x22222111111111111000000000\n" +
						"x2222xx1111111111xx00000000\n" +
						"x2222xx1111111111xx00000000\n" +
						"x2222xxxx1111xxxxxxxxxxxxxx\n" +
						"x2222xxxx0000xxxxxxxxxxxxxx\n" +
						"x2222x0000000000xxxxxxxxxxx\n" +
						"x2222x0000000000xxxxxxxxxxx\n" +
						"x2222x0000000000xxxxxxxxxxx\n" +
						"x2222x0000000000xxxxxxxxxxx\n" +
						"x2222x0000000000xxxxxxxxxxx\n" +
						"x2222x0000000000xxxxxxxxxxx"
		);
	}

}
