package model.agent;

import java.awt.Point;

import model.inventory.Resource;

public class SciencerAgent extends AgentReplacement{

	public SciencerAgent(Point currentPosition) {
		super(currentPosition);
		priorityResource = Resource.URANIUM;
		inventory.changePriority(priorityResource);
		agentType = EAgent.SCIENCER;
	}

	@Override
	public void update() {
		updateStats();
		
		
		getNextTaskIfNotBusy(EAgent.SCIENCER);
		
		
	}

}
