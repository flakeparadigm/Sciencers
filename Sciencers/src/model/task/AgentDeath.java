package model.task;

import java.awt.Point;

import controller.GameTick;
import model.Agent;
import model.AgentReplacement;
import model.World;

public class AgentDeath implements Task{

	private AgentReplacement sourceAgent;
	private Point position;
	
	public AgentDeath(AgentReplacement sourceAgent, Point agentPos){
		this.sourceAgent = sourceAgent;
		this.position = agentPos;
	}
	
	@Override
	public void execute() {

		World.agents.remove(sourceAgent);
	
	}

	@Override
	public Point getPos() {
		return position;
	}

}
