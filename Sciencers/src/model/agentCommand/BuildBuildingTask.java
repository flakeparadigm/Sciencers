package model.agentCommand;

import java.awt.Point;

import model.World;
import model.building.Building;

public class BuildBuildingTask implements Task {

	private Building building;
	
	public BuildBuildingTask(Building b) {
		building = b;
	}
	
	@Override
	public void execute() {
		World.addBuilding(building.getType(), building.getPos());
		
	}

	@Override
	public Point getPos() {
		return null;
		// TODO Auto-generated method stub
		
	}

	
}