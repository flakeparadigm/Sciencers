package model;

import model.inventory.Resource;

public interface Building {
	
	public String getType();
	
	public Command updateCommand();
	
	public int getAmount();
	
	public boolean changeQuantity(int quantity);
	
}
