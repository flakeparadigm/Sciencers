package model.building;

import java.awt.Point;

import model.Command;
import model.inventory.Resource;

public interface Building {
	
	public Resource getType();
	
	public Command updateCommand();
	
	public int getAmount();
	
	public boolean changeQuantity(int quantity);
	
	public Point getPos();
	
}
