package model.task;

import java.awt.Point;

import controller.GameTick;
import model.World;
import model.agent.AgentReplacement;

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

	@Override
	public boolean shouldBeSeen() {
		return false;
	}

}
