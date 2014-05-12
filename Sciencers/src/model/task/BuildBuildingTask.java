package model.task;

import java.awt.Point;
import java.awt.Rectangle;

import model.Entity;
import model.World;
import model.agent.Agent;
import model.building.EBuilding;
import model.inventory.Resource;

public class BuildBuildingTask implements Task {

	private EBuilding building;
	private Entity bldg;
	private Point location;
	private Agent sourceAgent;

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
		sourceAgent.getInventory().changeAmount(Resource.WOOD, -5);
	}
	
	public void setSourceAgent(Agent sourceAgent){
		this.sourceAgent = sourceAgent;
	}

	@Override
	public Point getPos() {
		return location;
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
