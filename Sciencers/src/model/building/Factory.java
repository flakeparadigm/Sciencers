package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Agent;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addWorker(Agent a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeWorker(Agent a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNumWorkers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EBuilding getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
