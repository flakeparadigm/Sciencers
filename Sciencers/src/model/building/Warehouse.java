package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import model.agent.AgentReplacement;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Warehouse extends Building {

	// Magic Numbers
//	private final Point POSITION;
	private final int BUILDING_WIDTH = 10;
	private final int BUILDING_HEIGHT = 1; //?
	private final int MAX_WORKERS = 2;
	
	// Variables
	private Inventory inv;
	private ArrayList<AgentReplacement> workers;
	
	public Warehouse(Point pos) {
		super(pos);
//		POSITION = pos;
	}

	@Override
	public void update() {
		
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
		return EBuilding.WAREHOUSE;
	}

}
