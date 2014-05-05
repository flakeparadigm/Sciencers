package model.task;

import java.awt.Point;

import model.Entity;
import model.World;
import model.building.EBuilding;

public class BuildBuildingTask implements Task {

	private EBuilding building;
	private Entity bldg;
	private Point location;

	@Deprecated
	public BuildBuildingTask(EBuilding b, Point p) {
		building = b;
		location = p;
	}

	public BuildBuildingTask(Entity b) {
		bldg = b;
		location = new Point((int) b.getPos().getX(), (int) b.getPos().getY());
	}

	@Override
	public void execute() {
		//World.addBuilding(EBuilding.FARM, location);
		World.addBuilding(bldg);
	}

	@Override
	public Point getPos() {
		return location;
		// TODO Auto-generated method stub

	}

	public String toString() {
		String pointString = location.x + ", " + location.y;

		String taskString = "Build Building\n" + bldg;

		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}

}
