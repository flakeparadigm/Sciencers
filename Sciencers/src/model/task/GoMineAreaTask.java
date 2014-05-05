package model.task;

import java.awt.Point;
import java.awt.Rectangle;

import view.Tile;
import model.World;
import model.agent.AgentReplacement;
import model.inventory.Resource;

public class GoMineAreaTask implements Task {

	private Point point1;
	private Point point2;
	private Point upperRight;
	private Point lowerLeft;
	private AgentReplacement agentSource;

	public GoMineAreaTask(Rectangle r) {
		this(r.getLocation(), new Point(r.x+r.width, r.y+r.height));
	}
	
	public GoMineAreaTask(Point point1, Point point2) {
		this.point1 = point1;
		this.point2 = point2;
		agentSource = null;
		upperRight = new Point(Math.max(point1.x, point2.x), Math.min(point1.y, point2.y));
		lowerLeft = new Point(Math.min(point1.x, point2.x), Math.max(point1.y, point2.y));
	}

	@Override
	public void execute() {
		int width = lowerLeft.x - upperRight.x;
		int height = lowerLeft.y - upperRight.y;
		for (int j = height; j>=0; j--){
			for (int i = width; i>=0; i--){
				agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(
						upperRight.x + i, upperRight.y + j), Tile.Sky));
				System.out.println("TileRemve");
			}
		}
		
//		agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(
//				upperRight.x, upperRight.y), Tile.Sky));
		
	}

	public void setAgentSource(AgentReplacement agentSource) {
		this.agentSource = agentSource;
	}

	@Override
	public Point getPos() {
		// this is where the agent needs to go before this task can be executed
		return new Point(upperRight.x,
				World.terrain.getAltitude(upperRight.x) - 1);
	}

	public String toString() {

		String taskString = "Gather resources\n";
		taskString += "From: " + lowerLeft + ".";
		taskString += "To: " + upperRight + ".";

		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}

}
