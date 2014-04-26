package model.task;

import java.awt.Point;

import model.Agent;
import model.inventory.Tool;

public class CraftToolTask implements Task {
	
	private Tool t;
	private Agent a;
	
	public CraftToolTask(Tool t, Agent a) {
		this.t= t;
		this.a = a;
		a.craftTool(t);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public Point getPos() {
		return (Point) a.getPos();
	}
	
	public String toString() {		
		String taskString = "Craft tool\n";
		taskString += "Type: " + t.name().toLowerCase();
		
		return taskString;
	}

}
