package model.task;

import java.awt.Point;

import model.building.Building;
import model.inventory.Resource;

/**
 * Intended to be used for getting resources out of a building
 * Use a different command for going mining or gathering
 */

public class GetResourcesTask implements Task {
	
	private Building building;
	private int amount;
	
	public GetResourcesTask(Building b, Point pos, int amount) {
		building = b;
		this.amount = amount;
	}
	
	@Override
	public void execute() {
		building.getInventory().changeAmount(Resource.FOOD, amount);///BAD
		//TODO
	}

	@Override
	public Point getPos() {
		return building.getPos();		
	}
	
	public String toString() {
		String pointString = building.getPos().x + ", " + building.getPos().y;
		
		String taskString = "Get resources\n";
		taskString += "Type: " + building.getType().name().toLowerCase() + "\n";
		taskString += "Location: " + pointString;
				
		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}
	
}