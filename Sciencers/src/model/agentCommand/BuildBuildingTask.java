package model.agentCommand;

import java.awt.Point;

import model.building.EBuilding;

public class BuildBuildingTask implements Task {
	
	EBuilding building;
	Point location;
	
	public BuildBuildingTask(EBuilding b, Point p) {
		building = b;
		location = p;
	}
	
	@Override
	public void execute() {

	}

	@Override
	public Point getPos() {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		String taskString = "Build Building\n";
		taskString += "Type: " + building + "\n";
		taskString += "Location: " + location;
				
		return taskString;
	}
	
}
