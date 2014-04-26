package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import model.Agent;
import model.Research;
import model.inventory.Inventory;

public class Lab extends Building {
	
	//Magic numbers
//	private final int TICKS_PER_ITEM = 500; //~10 seconds
	private final int BUILDING_WIDTH = 5;
	private final int BUILDING_HEIGHT = 1; //?
	private final int MAX_WORKERS = 5;
//	private final int ITEMS_PER_UPDATE = 1;
//	private final int CAPACITY = 0;
	
	//Variables
//	private Inventory inv;
	private ArrayList<Agent> workers;
	
	//Lab-specific stats
	private final int TICK_RESEARCH = 1;

	public Lab(Point pos) {
		super(pos);
	}

	@Override
	public void update() {
		Research.changeResearch(TICK_RESEARCH * workers.size());
	}

	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
	}

	@Override
	public Inventory getInventory() {
		return null; //Lab shouldn't have an inventory
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
		return EBuilding.LAB;
	}

}