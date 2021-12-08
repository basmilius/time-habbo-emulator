package com.basmilius.time.habbohotel.rooms.floorplan;

public class Model3FloorPlan extends IFloorPlan
{

	public Model3FloorPlan()
	{
		super();

		this.setDoorX(0);
		this.setDoorY(10);
		this.setDoorZ(0);
		this.setDoorRot(2);

		this.setHeightmap(
				"XXXXXXXXXXXXXXXXX\n" +
						"XXX0000000000000X\n" +
						"XXX0000000000000X\n" +
						"XXX0000000000000X\n" +
						"XXX0000000000000X\n" +
						"XXX0000000000000X\n" +
						"XXX0000000000000X\n" +
						"X000000000000000X\n" +
						"X000000000000000X\n" +
						"X000000000000000X\n" +
						"X000000000000000X\n" +
						"X000000000000000X\n" +
						"X000000000000000X\n" +
						"X000000000000000X\n" +
						"XXXXXXXXXXXXXXXXX"
		);
	}

}
