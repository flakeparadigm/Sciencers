package model.building;

import java.awt.Dimension;
import java.awt.Point;

import model.Entity;
import model.agent.AgentReplacement;
import model.inventory.Inventory;
import model.inventory.Resource;

public abstract class Building implements Entity {
	
	// Magic Numbers
//	protected final int TICKS_PER_ITEM = 50;
	protected final int MAX_STORAGE_PER_TILE = 100;
	protected final int BLDG_ID;
	protected static int currentID = 0;
	
	private Point pos;
	
	public Building(Point pos) {
		BLDG_ID = currentID++;
		this.pos = pos;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public abstract Dimension getSize();
	
	public abstract Inventory getInventory();
	
	public abstract boolean addWorker(AgentReplacement a);
	
	public abstract boolean removeWorker(AgentReplacement a);
	
	public abstract int getNumWorkers();

	public abstract EBuilding getType();
	
	@Override
	public String toString() {
		String[] bldgName = ("" + this.getClass()).split("\\.");
		
		String output = "Type: " + bldgName[2];
		output += "\nLocation: (" + pos.x + "," + pos.y + ")";
		
		return output;
	}
}
