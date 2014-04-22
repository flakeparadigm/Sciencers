package model.agentCommand;

import java.awt.Point;

public interface Task {

	public abstract void execute();
	
	public abstract Point getPos();
	
}
