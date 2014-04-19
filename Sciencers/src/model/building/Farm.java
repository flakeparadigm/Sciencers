package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Agent;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Farm extends Building {
	
	// Magic Numbers
//	private final int TICKS_PER_ITEM = 50;
	private final Point POSITION;
	private final int BUILDING_WIDTH = 5;
	private final int BUILDING_HEIGHT = 1; //?
	private final int MAX_WORKERS = 5;
	private final int ITEMS_PER_UPDATE = 1;
	private final int CAPACITY = 1000;
	
	//Variables
	private Inventory inv;
	private ArrayList<Agent> workers;
	
	public Farm(int xPos, int yPos) {
		POSITION = new Point(xPos, yPos);
		workers = new ArrayList<Agent>();
		inv = new Inventory(CAPACITY);
		inv.changeAmount(Resource.FOOD, 100);
	}
	
	public void update() {
		Random random = new Random();
		if(random.nextInt(TICKS_PER_ITEM) == 1) {
			int foodAdd = workers.size() * ITEMS_PER_UPDATE + 1;
			inv.changeAmount(Resource.FOOD, foodAdd);
		}
	}

//	@Override
//	public int getAmount(Resource r) {
//		return inv.getAmount(r);
//	}
//
//	@Override
//	public boolean changeQuantity(Resource r, int quantity) {
//		return inv.changeAmount(Resource.FOOD, quantity);
//	}

	@Override
	public Point getPos() {
		return POSITION;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
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
	
	public Inventory getInventory(){
		return inv;
	}
}
