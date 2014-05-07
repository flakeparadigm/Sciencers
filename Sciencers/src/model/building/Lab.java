package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import model.Research;
import model.agent.AgentReplacement;
import model.inventory.Inventory;

public class Lab extends Building {
	
	//Magic numbers
//	private final int TICKS_PER_ITEM = 500; //~10 seconds
	private final int BUILDING_WIDTH = 4;
	private final int BUILDING_HEIGHT = 2; //?
	private final int MAX_WORKERS = 5;
//	private final int ITEMS_PER_UPDATE = 1;
//	private final int CAPACITY = 0;
	
	//Variables
//	private Inventory inv;
	private ArrayList<AgentReplacement> workers;
	
	//Lab-specific stats
	private final int TICK_RESEARCH = 1;

	public Lab(Point pos) {
		super(pos);
		workers = new ArrayList<AgentReplacement>();
	}

	@Override
	public void update() {
		Research.change(TICK_RESEARCH * workers.size());
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
	public boolean addWorker(AgentReplacement a) {
		if(workers.size() <= MAX_WORKERS)
			return false;
		
		workers.add(a);
		return true;
	}

	@Override
	public boolean removeWorker(AgentReplacement a) {
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
