package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import model.Command;
import model.inventory.Resource;

public class Farm implements Building {
	
	private int storage; //number of food items currently stored
	
	// Magic Numbers
	private final int TICKS_PER_ITEM = 50;
	private final Point POSITION;
	private final int MAX_STORAGE = 1000;
	private final int BUILDING_WIDTH = 0;
	private final int BUILDING_HEIGHT = 0;
	
	public Farm(int xPos, int yPos) {
		POSITION = new Point(xPos, yPos);
		storage = 0;
	}
	
	public void update() {
		Random random = new Random();
		if(random.nextInt(TICKS_PER_ITEM) == 1) {
			storage += 1;
		}
	}

	@Override
	public Resource getType() {
		return Resource.FOOD;
	}

	@Override
	public Command updateCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAmount() {
		return storage;
	}

	@Override
	public boolean changeQuantity(int quantity) {
		if(storage < quantity) { // in the event there isn't the amount requested, the farm gives what it can and returns false
			quantity = 0;
			return false;
		}
		storage += quantity;
		return true;
	}

	@Override
	public Point getPos() {
		return POSITION;
	}


	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
	}
}
