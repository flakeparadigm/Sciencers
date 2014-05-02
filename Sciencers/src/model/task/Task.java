package model.task;

import java.awt.Point;
import java.io.Serializable;

public interface Task extends Serializable {

	public abstract void execute();
	
	public abstract Point getPos();
	
	public abstract String toString();

	public abstract boolean shouldBeSeen();
	
}
