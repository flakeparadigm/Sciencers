package model.task;

import java.awt.Point;

import model.World;
import model.building.EBuilding;

public class BuildBuildingTask implements Task {
	
	private EBuilding building;
	private Point location;
	
	public BuildBuildingTask(EBuilding b, Point p) {
		building = b;
		location = p;
	}
	
	@Override
	public void execute() {
		World.addBuilding(EBuilding.FARM, location);
	}

	@Override
	public Point getPos() {
		return location;
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		String pointString = location.x + ", " + location.y;
		
		String taskString = "Build Building\n";
		taskString += "Type: " + building.name().toLowerCase() + "\n";
		taskString += "Location: " + pointString;
				
		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}
	
}
