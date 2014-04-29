package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.agent.Agent;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Factory extends Building {
	
	// Magic Numbers
	private final int TICKS_PER_ITEM = 500; //~10 seconds
	private final int BUILDING_WIDTH = 5;
	private final int BUILDING_HEIGHT = 2; //?
	private final int MAX_WORKERS = 5;
	private final int ITEMS_PER_UPDATE = 1;
	private final int CAPACITY = 1000;
	
	//Variables
	private Inventory inv;
	private ArrayList<Agent> workers;

	public Factory(Point pos) {
		super(pos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		
	}

	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

	@Override
	public boolean addWorker(Agent a) {
		if(workers.size() <= MAX_WORKERS)
			return false;
		
		workers.add(a);
		return true;
	}

	@Override
	public boolean removeWorker(Agent a) {
		if(!workers.contains(a))
			return false;
		
		workers.remove(a);
		return true;
	}

	@Override
	public int getNumWorkers() {
		return workers.size();
	}

	@Override
	public EBuilding getType() {
		return EBuilding.FACTORY;
	}

}
