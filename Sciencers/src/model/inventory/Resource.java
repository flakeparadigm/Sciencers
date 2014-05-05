package model.inventory;

public enum Resource implements Storable {
	FOOD(1), WOOD(1), IRON(1), URANIUM(2), STONE(1);
	
	private int itemSize;
	
	private Resource(int itemSize) {
		this.itemSize = itemSize;
	}
	
	public int getItemSize() {
		return itemSize;
	}
}
