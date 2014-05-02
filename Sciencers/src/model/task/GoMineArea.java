package model.task;

import java.awt.Point;
import java.awt.Rectangle;

import model.World;
import model.inventory.Resource;

public class GoMineArea implements Task {

	private Point point1;
	private Point point2;
	
	public GoMineArea(Point point1, Point point2) { 
		//i think the player should select a rectangle to be mined
//		rect = new Rectangle(point1.x, point1.y, point2.x, point2.y);
		this.point1 = point1;
		this.point2 = point2;
	}
	
	@Override
	public void execute() {
		//TODO finish this
		
		/* code:
		 * 
		 * (player selects region)
		 * agent pathfinds to selected rectangle
		 * agent clears out all of a given resource from that rectangle
		 * 
		 * dilemma, what part of this should go in the task, and what part should be in agent?
		 * Other Thing: When digging, they need to collect ALL resources.
		 */
	}

	@Override
	public Point getPos() {
		//this is where the agent needs to go before this task can be executed
		return new Point(point1.x - 1, World.terrain.getAltitude(point1.x - 1));
	}
	
	public String toString() {
		
		String taskString = "Gather resources\n";
//		taskString += "Top: " + rect.getMaxY() + "\n";
//		taskString += "Bottom: " + rect.getMinY() + "\n";
//		taskString += "Left: " + rect.getMinX() + "\n";
//		taskString += "Right: " + rect.getMaxX();
				
		return taskString;
	}

}
