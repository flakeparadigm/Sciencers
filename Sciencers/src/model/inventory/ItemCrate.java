package model.inventory;

public class ItemCrate {
	
	public Resource type;
	public int amount;
	
	public ItemCrate(Resource type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public boolean add(int a) { //boolean reports whether there was enough of the item
		if(amount < a) {
			amount = 0;
			return false;
		}
		
		amount += a;
		return true;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public Resource getType() {
		return type;
	}
}
