package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Stack;

import model.task.AgentDeath;
import model.task.HarvestTreeTask;
import model.task.Task;
import model.task.TaskList;

public class FarmerAgent extends AgentReplacement {

	private int tickCount;
	private Point2D.Double currentPosition;
	// private Point2D.Double targetPosition;
	private Task currentTask;
	private Stack<Point> movements;
	private int SEEK_FOOD_HUNGER = 800;

	public FarmerAgent(Point currentPosition) {
		this.currentPosition = new Point2D.Double((double) currentPosition.x,
				(double) currentPosition.y);
		// targetPosition = this.currentPosition;
		currentTask = null;
		movements = new Stack<Point>();
	}

	@Override
	public void update() {
		if (tickCount % HUNGER_SPEED == 0) {
			if (isWorking) {
				hunger -= 6;
				fatigue -= 3;
			} else {
				hunger -= 1;
				fatigue -= 1;
			}
		}
		tickCount++;
//		System.out.println(tickCount);
		/*
		 * The following code should be focused upon specific tasks for this type of Agent
		 */
		// die if hunger < 0
		if (hunger < 0) {
			currentTask = (new AgentDeath(this, new Point(
					(int) currentPosition.x, (int) currentPosition.y)));
		}

		// seek food if hungry
		if (hunger < SEEK_FOOD_HUNGER && currentTask == null) {
			if (findNearestTree(currentPosition) != null) {
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
			}
		}
		
		//get task from list if agent doesn't have one
		getAndExecuteNextTask();

		updateMovement(currentPosition, movements);

	}

	private void getAndExecuteNextTask() {
		if (currentTask == null && !TaskList.getList().isEmpty()){
			currentTask = TaskList.getList().poll();
		}
		
		if (currentTask != null) {
			// if current task exists:
			if (movements.isEmpty()
					&& !sameLocation(currentPosition, new Point2D.Double(
							currentTask.getPos().getX(), currentTask.getPos()
									.getY()), .1, .1)) {
				// if movements needs updated:
				movements = goHere(currentPosition, currentTask.getPos());
			}

			if (sameLocation(currentPosition, new Point2D.Double(currentTask
					.getPos().getX(), currentTask.getPos().getY()), .1, .1)) {
				// if in location of current task:
				currentTask.execute();
				currentTask = null;
			}

		}
	}

	public Point2D getPos() {
		return currentPosition;
	}
}
