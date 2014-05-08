package model.task;

import java.awt.Point;
import java.awt.Rectangle;

import view.Tile;
import model.World;
import model.agent.Agent;
import model.inventory.Resource;

public class GoMineAreaTask implements Task {

	private Point point1;
	private Point point2;
	private Point upperLeft;
	private Point lowerRight;
	private Agent agentSource;

	public GoMineAreaTask(Rectangle r) {
		this(r.getLocation(), new Point(r.x+r.width, r.y+r.height));
		System.out.println(r.getLocation());
		System.out.println(new Point(r.x+r.width, r.y+r.height));
	}
	
	public GoMineAreaTask(Point point1, Point point2) {
		
		this.point1 = point1;
		this.point2 = point2;
		agentSource = null;
		upperLeft = new Point(Math.min(point1.x, point2.x), Math.min(point1.y, point2.y));
		lowerRight = new Point(Math.max(point1.x, point2.x), Math.max(point1.y, point2.y));
	}

	@Override
	public void execute() {
		int width = lowerRight.x - upperLeft.x;
		int height = lowerRight.y - upperLeft.y;
		for (int j = height; j>=0; j--){
			for (int i = width; i>=0; i--){
				agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(
						upperLeft.x + i, upperLeft.y + j), Tile.Sky));
				System.out.println("TileRemve");
			}
		}
		
//		agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(
//				upperRight.x, upperRight.y), Tile.Sky));
		
	}

	public void setAgentSource(Agent agentSource) {
		this.agentSource = agentSource;
	}

	@Override
	public Point getPos() {
		// this is where the agent needs to go before this task can be executed
		return new Point(upperLeft.x,
				World.terrain.getDeepestPassable(upperLeft.x));
	}

	public String toString() {

		String taskString = "Gather resources\n";
		taskString += "From: " + lowerRight + ".";
		taskString += "To: " + upperLeft + ".";

		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}

}
