package model.task;

import java.awt.Point;

import model.agent.AgentReplacement;
import model.inventory.Tool;

public class CraftToolTask implements Task {
	
	private Tool t;
	private AgentReplacement a;
	private Point agentPosition;
	
	public CraftToolTask(Tool t, AgentReplacement a, Point agentPosition) {
		this.t= t;
		this.a = a;
		this.agentPosition = agentPosition;
	}

	@Override
	public void execute() {
		a.craftTool(t);
	}

	@Override
	public Point getPos() {
		return agentPosition;
	}
	
	public String toString() {		
		String taskString = "Craft tool\n";
		taskString += "Type: " + t.name().toLowerCase();
		
		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true; //TODO should this be false?
	}

}
