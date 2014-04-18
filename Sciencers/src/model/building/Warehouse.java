package model.building;

import java.awt.Dimension;
import java.awt.Point;

import model.Command;
import model.inventory.Resource;

public class Warehouse implements Building {

	// Magic Numbers
	private final int BUILDING_WIDTH = 0;
	private final int BUILDING_HEIGHT = 0;

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Resource getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Command updateCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean changeQuantity(int quantity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
	}

}
