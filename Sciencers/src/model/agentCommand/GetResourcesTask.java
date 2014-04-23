package model.agentCommand;

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
		return null;
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return " ";
	}
	
}