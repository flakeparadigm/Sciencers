package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Agent;
import model.Command;
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
	
	//Variables
	private Inventory inv;
	private ArrayList<Agent> workers;
	
	public Farm(int xPos, int yPos) {
		POSITION = new Point(xPos, yPos);
	}
	
	public void update() {
		Random random = new Random();
		if(random.nextInt(TICKS_PER_ITEM) == 1) {
			int foodAdd = workers.size() * ITEMS_PER_UPDATE + 1;
			inv.changeAmount(Resource.FOOD, foodAdd);
		}
	}

	@Override
	public Command updateCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAmount(Resource r) {
		return inv.getAmount(r);
	}

	@Override
	public boolean changeQuantity(Resource r, int quantity) {
		return inv.changeAmount(Resource.FOOD, quantity);
	}

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
}
