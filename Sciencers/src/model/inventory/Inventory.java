package model.inventory;

import java.io.Serializable;
import java.util.HashMap;

public class Inventory implements Serializable {

	private HashMap<Storable, Integer> inv;
	private final int CAPACITY;
	private Resource priorityResource;

	public Inventory(int capacity, Resource priorityResource) {
		CAPACITY = capacity;
		this.priorityResource = priorityResource;
		inv = new HashMap<Storable, Integer>();
		for (Resource r : Resource.values()) {
			inv.put(r, 0);
		}
		for (Tool t : Tool.values()) {
			inv.put(t, 0);
		}
	}

	public int getAmount(Resource r) {
		return inv.get(r);
	}

	public void changeAmount(Resource r, int quantity) {
		// Not sure what this actually does. Putting it out for now because it
		// is not changing the amount
		// if(inv.get(r) < quantity)
		// return false;
		// int temp = inv.remove(r);
		// temp += quantity;
		// inv.put(r, temp);
		// return true;

		// only add to inventory if not already full
		// inv.put(r, getAmount(r) + quantity);
		if (quantity > 0) {
			if (getTotal() != CAPACITY) {
				inv.put(r, resourceLowestAtZero(r, quantity));
			} else if (inv.get(priorityResource) != CAPACITY) {
				inv.put(nonPriorityResource(),
						getAmount(nonPriorityResource()) - 1);
				inv.put(r, getAmount(r) + 1);
			}
		} else {
			inv.put(r, resourceLowestAtZero(r, quantity));
		}
	}

	private Resource nonPriorityResource() {
		if (!priorityResource.equals(Resource.FOOD)) {
			return Resource.FOOD;
		} else if (!priorityResource.equals(Resource.WOOD)) {
			return Resource.WOOD;
		} else if (!priorityResource.equals(Resource.STONE)) {
			return Resource.STONE;
		} else if (!priorityResource.equals(Resource.IRON)) {
			return Resource.IRON;
		} else
			return Resource.URANIUM;
	}

	private int resourceLowestAtZero(Resource r, int quantity) {
		return getAmount(r) + quantity;
	}

	public int getTotal() {
		int temp = 0;
		for (Resource r : Resource.values()) {
			temp += inv.get(r);
		}
		return temp;
	}

	public int getCapacity() {
		return CAPACITY;
	}

	public void changePriority(Resource priorityResource) {
		this.priorityResource = priorityResource;

	}
}
