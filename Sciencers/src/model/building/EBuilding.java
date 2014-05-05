package model.building;

public enum EBuilding {
	FARM("Farm"), WAREHOUSE("Warehouse"), FACTORY("Factory"), LAB("Lab");
	
	private String name;
	EBuilding(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
