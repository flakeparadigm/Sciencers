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
	private AgentReplacement agentSource;

	public GoMineAreaTask(Point point1, Point point2) {
		// i think the player should select a rectangle to be mined
		// rect = new Rectangle(point1.x, point1.y, point2.x, point2.y);
		this.point1 = point1;
		this.point2 = point2;
		agentSource = null;
	}

	@Override
	public void execute() {
		// if (agentSource!=null){
		// agentSource.tasks.add(agentSource.currentTask);
		// add tasks in reverse order of execution (because it is a stack)
		agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(
				point1.x, point1.y), Tile.Sky));
		agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(
				point1.x - 1, point1.y), Tile.Sky));

		System.out.println("execute");
		// }
	}

	public void setAgentSource(AgentReplacement agentSource) {
		this.agentSource = agentSource;
	}

	@Override
	public Point getPos() {
		// this is where the agent needs to go before this task can be executed
		return new Point(point1.x - 1,
				World.terrain.getAltitude(point1.x - 1) - 1);
	}

	public String toString() {

		String taskString = "Gather resources\n";
		// taskString += "Top: " + rect.getMaxY() + "\n";
		// taskString += "Bottom: " + rect.getMinY() + "\n";
		// taskString += "Left: " + rect.getMinX() + "\n";
		// taskString += "Right: " + rect.getMaxX();

		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}

}
