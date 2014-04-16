package model.inventory;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * The code for inventory is probably overwrought, may yet remove it all for simplicity
 */

public class Inventory {
	
	private int capacity;
	private InvType type;
	private LinkedList<ItemCrate> itemList;
	
	public Inventory(InvType type) {
		this.type = type;
		
		itemList = new LinkedList<ItemCrate>();
		for(Resource r : Resource.values()) {
			itemList.add(new ItemCrate(r, 0));
		}
		
		if(type == InvType.WAREHOUSE)
			capacity = 10000;
		else if(type == InvType.AGENT)
			capacity = 1000;
		else if(type == InvType.FARM)
			capacity = 500;
	}
	
	public void add(Resource type, int amount) {
		for(ItemCrate i : itemList) {
			if(type == i.getType()) {
				i.add(amount);
			}
		}
	}
	
	
}
