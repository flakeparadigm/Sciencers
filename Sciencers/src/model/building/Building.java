package model.building;

import java.awt.Dimension;
import java.awt.Point;

import model.Agent;
import model.Command;
import model.Entity;
import model.inventory.Resource;

public abstract class Building implements Entity {
	
	// Magic Numbers
	protected final int TICKS_PER_ITEM = 50;
	protected final int MAX_STORAGE_PER_TILE = 100;
		
	public abstract Command updateCommand();
	
	public abstract int getAmount(Resource r);
	
	public abstract boolean changeQuantity(Resource r, int quantity);
	
	public abstract Point getPos();
	
	public abstract Dimension getSize();
	
	public abstract boolean addWorker(Agent a);
	
	public abstract boolean removeWorker(Agent a);
	
	public abstract int getNumWorkers();
}
