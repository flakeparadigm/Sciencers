package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import model.AlertCollection;
import model.agent.Agent;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Warehouse extends Building {

	// Magic Numbers
	private final int BUILDING_WIDTH = 8;
	private final int BUILDING_HEIGHT = 2; //?
	private final int MAX_WORKERS = 2;
	private final int CAPACITY = 1000;
	
	// Variables
	private Inventory inv;
	private ArrayList<Agent> workers;
	
	public Warehouse(Point pos) {
		super(pos);
		inv = new Inventory(CAPACITY, Resource.FOOD);
		workers = new ArrayList<Agent>();
	}

	@Override
	public void update() {
		if(CAPACITY - inv.getTotal() < CAPACITY * 0.05) {
			AlertCollection.addAlert("A warehouse is almost full!");
		}
	}

//	@Override
//	public int getAmount(Resource r) {
//		return inv.getAmount(r);
//	}
//
//	@Override
//	public boolean changeQuantity(Resource r, int quantity) {
//		return inv.changeAmount(r, quantity);
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

	@Override
	public EBuilding getType() {
		return EBuilding.WAREHOUSE;
	}

}
