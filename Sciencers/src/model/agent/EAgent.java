package model.agent;

public enum EAgent {
	FARMER("Farmer"), SCIENCER("Sciencer"), MINER("Miner"), GENERIC("Generic");
	
	private String name;

	EAgent(String c) {
		name = c;
	}
	
	public String getName() {
		return name;
	}
}
