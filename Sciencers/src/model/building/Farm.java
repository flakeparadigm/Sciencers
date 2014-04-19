package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

import model.Command;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Farm extends Building {
	
	private Inventory inv;
	
//	// Magic Numbers
//	private final int TICKS_PER_ITEM = 50;
	private final Point POSITION;
	private final int BUILDING_WIDTH = 5;
	private int BUILDING_HEIGHT = 1; //?
	
	public Farm(int xPos, int yPos) {
		POSITION = new Point(xPos, yPos);
	}
	
	public void update() {
		Random random = new Random();
		if(random.nextInt(TICKS_PER_ITEM) == 1) {
			inv.changeAmount(Resource.FOOD, 1);
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
}
