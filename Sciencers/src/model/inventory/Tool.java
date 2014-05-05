package model.inventory;

public enum Tool implements Storable {
	HAMMER(20), PICKAXE(20), LIGHTSABER(20);
	
	private int itemSize;
	
	private Tool(int size) {
		itemSize = size;
	}
	
	public int getItemSize() {
		return itemSize;
	}
}
