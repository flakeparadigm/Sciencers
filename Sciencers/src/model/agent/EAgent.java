package model.agent;

public enum EAgent {
	FARMER("F"), SCIENCER("S"), MINER("M"), GENERIC("G");
	
	public String value;

	EAgent(String c) {
		this.value = c;
	}
}
