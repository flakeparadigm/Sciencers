package model.agentCommand;

import java.awt.Point;

import controller.GameTick;
import model.Agent;
import model.World;

public class AgentDeath implements Task{

	private Agent sourceAgent;
	private Point position;
	
	public AgentDeath(Agent sourceAgent, Point agentPos){
		this.sourceAgent = sourceAgent;
		this.position = agentPos;
	}
	
	@Override
	public void execute() {
		try {
			GameTick.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		World.agents.remove(sourceAgent);
	}

	@Override
	public Point getPos() {
		return position;
	}

}
