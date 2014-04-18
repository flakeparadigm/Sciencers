package model.building;

import java.awt.Dimension;
import java.awt.Point;

import model.Command;
import model.inventory.Inventory;
import model.inventory.Resource;

public class Warehouse extends Building { //collection class for ItemCrates also

	// Magic Numbers
	private final Point POSITION;
	private final int BUILDING_WIDTH = 0;
	private final int BUILDING_HEIGHT = 0;
	
	// Variables
	private Inventory inv;
	
	public Warehouse(int xPos, int yPos) {
		POSITION = new Point(xPos, yPos);
	}

	@Override
	public void update() {
		
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
		return inv.changeAmount(r, quantity);
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
