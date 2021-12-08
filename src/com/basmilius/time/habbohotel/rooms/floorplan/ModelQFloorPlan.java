package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelQFloorPlan extends IFloorPlan
{

	public ModelQFloorPlan()
	{
		super();

		this.setDoorX(10);
		this.setDoorY(4);
		this.setDoorZ(2);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxx\n" +
						"xxxxxxxxxxx22222222\n" +
						"xxxxxxxxxxx22222222\n" +
						"xxxxxxxxxxx22222222\n" +
						"xxxxxxxxxxx22222222\n" +
						"xxxxxxxxxxx22222222\n" +
						"xxxxxxxxxxx22222222\n" +
						"x222222222222222222\n" +
						"x222222222222222222\n" +
						"x222222222222222222\n" +
						"x222222222222222222\n" +
						"x222222222222222222\n" +
						"x222222222222222222\n" +
						"x2222xxxxxxxxxxxxxx\n" +
						"x2222xxxxxxxxxxxxxx\n" +
						"x2222211111xx000000\n" +
						"x222221111110000000\n" +
						"x222221111110000000\n" +
						"x2222211111xx000000\n" +
						"xx22xxx1111xxxxxxxx\n" +
						"xx11xxx1111xxxxxxxx\n" +
						"x1111xx1111xx000000\n" +
						"x1111xx111110000000\n" +
						"x1111xx111110000000\n" +
						"x1111xx1111xx000000\n" +
						"xxxxxxxxxxxxxxxxxxx"
		);
	}

}
