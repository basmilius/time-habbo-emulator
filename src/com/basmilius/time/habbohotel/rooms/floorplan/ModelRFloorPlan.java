package com.basmilius.time.habbohotel.rooms.floorplan;

public class ModelRFloorPlan extends IFloorPlan
{

	public ModelRFloorPlan()
	{
		super();

		this.setDoorX(10);
		this.setDoorY(4);
		this.setDoorZ(3);
		this.setDoorRot(2);

		this.setHeightmap(
				"xxxxxxxxxxxxxxxxxxxxxxxxx\n" +
						"xxxxxxxxxxx33333333333333\n" +
						"xxxxxxxxxxx33333333333333\n" +
						"xxxxxxxxxxx33333333333333\n" +
						"xxxxxxxxxx333333333333333\n" +
						"xxxxxxxxxxx33333333333333\n" +
						"xxxxxxxxxxx33333333333333\n" +
						"xxxxxxx333333333333333333\n" +
						"xxxxxxx333333333333333333\n" +
						"xxxxxxx333333333333333333\n" +
						"xxxxxxx333333333333333333\n" +
						"xxxxxxx333333333333333333\n" +
						"xxxxxxx333333333333333333\n" +
						"x4444433333xxxxxxxxxxxxxx\n" +
						"x4444433333xxxxxxxxxxxxxx\n" +
						"x44444333333222xx000000xx\n" +
						"x44444333333222xx000000xx\n" +
						"xxx44xxxxxxxx22xx000000xx\n" +
						"xxx33xxxxxxxx11xx000000xx\n" +
						"xxx33322222211110000000xx\n" +
						"xxx33322222211110000000xx\n" +
						"xxxxxxxxxxxxxxxxx000000xx\n" +
						"xxxxxxxxxxxxxxxxx000000xx\n" +
						"xxxxxxxxxxxxxxxxx000000xx\n" +
						"xxxxxxxxxxxxxxxxx000000xx\n" +
						"xxxxxxxxxxxxxxxxxxxxxxxxx"
		);
	}

}
