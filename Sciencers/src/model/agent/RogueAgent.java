package model.agent;

import java.awt.Point;
import java.util.Stack;

import model.Entity;
import model.PathFinder;
import model.World;
import model.building.Building;
import model.building.Farm;
import model.inventory.Tool;
import model.task.HarvestTreeTask;
import model.task.RogueTask;

public class RogueAgent extends Agent {

	private boolean killer = true;
	private Entity prey;

	public RogueAgent(Point currentPosition) {
		super(currentPosition);

		agentType = EAgent.ROGUE;

		workingTool = Tool.DEATHBLADE;

		// killer = rand.nextBoolean();
	}

	@Override
	public void update() {
		updateStats();
		if (dead) {
			return;
		}

		/*
		 * The following code should be focused upon specific tasks for this
		 * type of Agent
		 */

		if (isWorking) {
			taskTimer = 0;
		}

		if (currentTask == null && killer) {
			Agent closest = findClosestAgent();
			currentTask = new RogueTask(this, closest);
			prey = (Entity) closest;
			taskTimer = 10;
		} else if (currentTask == null && rand.nextBoolean()) {
			Building closest = findClosestFoodBldg();
			currentTask = new RogueTask(this, closest);
			// taskTimer = 10;
		} else if (currentTask == null) {
			if (findNearestTree(currentPosition) != null) {
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				// taskTimer = 10;
			}
		}
			
		executeCurrentTask();
		

		updateMovement(currentPosition, movements);

	}

	private Agent findClosestAgent() {
		Stack<Point> shortestPath = null;
		Agent closestAgent = null;

		for (Entity e : World.agents) {
			Agent a = (Agent) e;
			if (!(a instanceof RogueAgent)) {
				Point curr = new Point(getCurrentX(currentPosition),
						getCurrentY(currentPosition));
				Point other = new Point(a.getCurrentX(a.currentPosition),
						a.getCurrentY(a.currentPosition));

				PathFinder thePath = new PathFinder(curr, other, World.terrain,
						passableTiles);

				if (shortestPath == null
						|| thePath.getPath().size() < shortestPath.size()) {
					shortestPath = thePath.getPath();
					closestAgent = a;
				}
			}
		}

		if (shortestPath != null) {
			// goHere(closestFoodBuilding.getPos());
			return closestAgent;
		}
		return null;
	}

	private Building findClosestFoodBldg() {
		Stack<Point> shortestPath = null;
		Building closestBuilding = null;

		for (Entity e : World.buildings) {
			Building b = (Building) e;
			if (b instanceof Farm) {
				Point curr = new Point(getCurrentX(currentPosition),
						getCurrentY(currentPosition));

				PathFinder thePath = new PathFinder(curr, b.getPos(),
						World.terrain, passableTiles);

				if (shortestPath == null
						|| thePath.getPath().size() < shortestPath.size()) {
					shortestPath = thePath.getPath();
					closestBuilding = b;
				}
			}
		}

		if (shortestPath != null) {
			// goHere(closestFoodBuilding.getPos());
			return closestBuilding;
		}
		return null;
	}

	@Override
	public String getUserFriendlyName() {
		return "Rogue";
	}
}
