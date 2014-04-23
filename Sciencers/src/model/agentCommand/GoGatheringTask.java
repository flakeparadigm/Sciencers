package model.agentCommand;

import java.awt.Point;
import java.awt.Rectangle;

import model.inventory.Resource;

public class GoGatheringTask implements Task {

	private Resource resource;
	private Rectangle rect;
	
	public GoGatheringTask(Resource r, Point point1, Point point2) { 
		//i think the player should select a rectangle to be mined
		rect = new Rectangle(point1.x, point1.y, point2.x, point2.y);
		resource = r;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return " ";
	}

}
