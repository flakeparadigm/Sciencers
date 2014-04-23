package model.agentCommand;

import java.awt.Point;

import model.Agent;
import model.inventory.Tool;

public class CraftToolTask implements Task {
	
	public CraftToolTask(Tool t, Agent a) {
		a.craftTool(t);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPos() {
		// TODO Auto-generated method stub
		return null;
	}

}
