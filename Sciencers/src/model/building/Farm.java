package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.AlertCollection;
import model.World;
import model.agent.AgentReplacement;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Farm extends Building {
	
	// Magic Numbers
	private final int TICKS_PER_ITEM = 50;
//	private final Point POSITION;
	private final int BUILDING_WIDTH = 4;
	private final int BUILDING_HEIGHT = 3;
	private final int MAX_WORKERS = 5;
	private final int ITEMS_PER_UPDATE = 100;
	private final int CAPACITY = 100;
	
	//Variables
	private Inventory inv;
	private ArrayList<AgentReplacement> workers;
	
	public Farm(Point pos) {
		super(pos);
		workers = new ArrayList<AgentReplacement>();
		inv = new Inventory(CAPACITY, Resource.FOOD, Resource.FOOD); //allows only food in internal inv storage
//		inv.changeAmount(Resource.FOOD, 10000000);
	}
	
	public void update() {
		Random random = new Random(World.seed);
		if(random.nextInt(TICKS_PER_ITEM) == 1) {
			int foodAdd = workers.size() * ITEMS_PER_UPDATE + 1;
			inv.changeAmount(Resource.FOOD, foodAdd);
		}
		
		if(inv.getTotal() < CAPACITY * 0.95) {
			AlertCollection.addAlert("A farm is almost full!");
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

//	@Override
//	public Point getPos() {
//		return POSITION;
//	}

	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
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
	
	public Inventory getInventory(){
		return inv;
	}

	@Override
	public EBuilding getType() {
		return EBuilding.FARM;
	}
}
