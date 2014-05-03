package model.agent;

import java.awt.Point;

import view.Tile;
import model.AlertCollection;
import model.World;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.AgentDeath;
import model.task.BuildBuildingTask;
import model.task.ChangeTileTask;
import model.task.CraftToolTask;
import model.task.HarvestTreeTask;

public class MinerAgent extends AgentReplacement {

	public MinerAgent(Point currentPosition) {
		super(currentPosition);
	}

	@Override
	public void update() {
		updateStats();
		/*
		 * The following code should be focused upon specific tasks for this
		 * type of Agent
		 */
		// die if hunger < 0
		if (hunger < 0 && currentTask == null) {
			currentTask = (new AgentDeath(this, new Point(
					(int) currentPosition.x, (int) currentPosition.y)));
			taskTimer = 0;
		}
		
		if(hunger < 0.5 * SEEK_FOOD_HUNGER) {
			AlertCollection.addAlert("An agent is starving! D:");
		}

		// seek food if hungry
		if (hunger < SEEK_FOOD_HUNGER && currentTask == null) {
			if (findNearestTree(currentPosition) != null) {
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				taskTimer = 10;
			}
		}

		// get task from list if agent doesn't have one
		getNextTaskIfNotBusy(EAgent.MINER);

		// build hammer before building Building
		if (currentTask instanceof BuildBuildingTask && !hasTool(Tool.HAMMER)) {
			if (getInventory().getAmount(Resource.WOOD) > 3) {
				tasks.add(currentTask);
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				taskTimer = 10;
			} else {
				tasks.add(currentTask);
				currentTask = new CraftToolTask(Tool.HAMMER, this, new Point(
						(int) currentPosition.getX(),
						(int) currentPosition.getY()));
				taskTimer = 100;
			}

		} else if (currentTask instanceof ChangeTileTask) {
			// build ladder if digging down (not correctly written right now)
			if (currentTask.getPos().getY() < ((ChangeTileTask) currentTask)
					.getTileLocation().getY()) {
				((ChangeTileTask) currentTask).changeTileType(Tile.Ladder);
			}
			
			
			if (getInventory().getAmount(Resource.WOOD) < 3) {
				tasks.add(currentTask);
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				taskTimer = 10;
			} else
			// dig down to tile location if necessary
			if (!passableTiles.contains(World.terrain.getTile((int) ((ChangeTileTask) currentTask)
					.getTileLocation().getX(), (int) ((ChangeTileTask) currentTask).getTileLocation().getY()))) {
				for (int i = 1; i < 100; i++) {
					if (!passableTiles.contains(World.terrain.getTile(
							(int) ((ChangeTileTask) currentTask).getTileLocation().getX(),
							(int) ((ChangeTileTask) currentTask).getTileLocation().getY() - i))) {
						tasks.add(currentTask);
						currentTask = new ChangeTileTask(this, new Point((int) ((ChangeTileTask) currentTask)
								.getTileLocation().getX(), (int) ((ChangeTileTask) currentTask).getTileLocation().getY() - i), Tile.Sky);
					}
				}
			}
			

		}

		/*
		 * tasks is a stack of tasks that must be completed after the current
		 * task
		 */

		executeCurrentTask();

		updateMovement(currentPosition, movements);

	}
}
