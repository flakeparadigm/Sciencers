package model.agentCommand;

import model.EAgent;
import model.building.Building;

public class Task {
	
	private Building building;
	private EAgent agentType;
	
	public Task(Building building, EAgent agentType) {
		this.building = building;
		this.agentType = agentType;
	}
}
