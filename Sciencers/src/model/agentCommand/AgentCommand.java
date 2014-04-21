package model.agentCommand;

import model.building.Building;

/**
 * Main command class for sending Tasks from Buildings to Agents
 */

public interface AgentCommand {
	
	public abstract void execute(Building b);
}

class BuildBuildingCommand implements AgentCommand {

	@Override
	public void execute(Building b) {
		
	}
	
}

class GetResourcesCommand implements AgentCommand {

	@Override
	public void execute(Building b) {
		
	}
	
}
