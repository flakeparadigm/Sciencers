package model.inventory;

import java.util.HashMap;

public class Inventory {
	
	private HashMap<Resource, Integer> inv;
	private final int CAPACITY;
	
	public Inventory(int capacity) {
		CAPACITY = capacity;
		inv = new HashMap<Resource, Integer>();
		for(Resource r : Resource.values()) {
			inv.put(r, 0);
		}
	}
	
	public int getAmount(Resource r) {
		return inv.get(r);
	}
	
	public void changeAmount(Resource r, int quantity) {
		//Not sure what this actually does. Putting it out for now because it is not changing the amount
//		if(inv.get(r) < quantity)
//			return false;
//		int temp = inv.remove(r);
//		temp += quantity;
//		inv.put(r, temp);
//		return true;
		
		inv.put(r, getAmount(r) + quantity);
	}
	
	public int getTotal() {
		int temp = 0;
		for(Resource r : Resource.values()) {
			temp += inv.get(r);
		}
		return temp;
	}
}
