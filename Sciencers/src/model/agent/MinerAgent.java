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
import model.task.GatherResources;
import model.task.GoMineAreaTask;
import model.task.HarvestTreeTask;
import model.task.WanderTask;

public class MinerAgent extends Agent {

	public MinerAgent(Point currentPosition) {
		super(currentPosition);
		priorityResource = Resource.WOOD;
		inventory.changePriority(priorityResource);
		agentType = EAgent.MINER;
	}

	@Override
	public void update() {
		updateStats();
		if(dead) {
			return;
		}
		
		/*
		 * The following code should be focused upon specific tasks for this
		 * type of Agent
		 */

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
		if (currentTask instanceof GoMineAreaTask) {
			((GoMineAreaTask) currentTask).setAgentSource(this);
//		} else if(currentTask instanceof HarvestTreeTask) {
//			((HarvestTreeTask) currentTask).setAgentSource(this); 
		} else if (currentTask instanceof BuildBuildingTask) {
			((BuildBuildingTask) currentTask).setSourceAgent(this);
			if (getInventory().getAmount(Resource.WOOD) < 5) {
				tasks.add(currentTask);
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				taskTimer = 10;
			} else if (!hasTool(Tool.HAMMER)){
				tasks.add(currentTask);
				currentTask = new CraftToolTask(Tool.HAMMER, this, new Point(
						(int) currentPosition.getX(),
						(int) currentPosition.getY()));
				taskTimer = 10;
			}

		} else if (currentTask instanceof ChangeTileTask) {
			priorityResource = Resource.WOOD;
			if (!passableTiles.contains(((ChangeTileTask) currentTask)
					.getTileLocation().getY())){
				Tile hereTile = World.terrain.getTile(currentTask.getPos().x, currentTask.getPos().y);
				if(hereTile == Tile.Sky || hereTile == Tile.Wood || hereTile == Tile.Leaves) {
//					((ChangeTileTask) currentTask).changeTileType(Tile.Sky);
				} else {
					((ChangeTileTask) currentTask).changeTileType(Tile.BackgroundDirt);
				}
			}
			if (currentTask.getPos().getY() < ((ChangeTileTask) currentTask)
					.getTileLocation().getY()) {
				((ChangeTileTask) currentTask).changeTileType(Tile.Ladder);
			}
			if (getInventory().getAmount(Resource.WOOD) < 3) {
				tasks.add(currentTask);
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				taskTimer = 10;
			} else if (!passableTiles.contains(World.terrain.getTile(
					(int) ((ChangeTileTask) currentTask).getTileLocation()
							.getX(), (int) ((ChangeTileTask) currentTask)
							.getTileLocation().getY()))) {
				for (int i = 1; i < 100; i++) {
					if (!passableTiles.contains(World.terrain.getTile(
							(int) ((ChangeTileTask) currentTask)
									.getTileLocation().getX(),
							(int) ((ChangeTileTask) currentTask)
									.getTileLocation().getY() - i)) && !passableTiles.contains(World.terrain.getTile(
											(int) ((ChangeTileTask) currentTask)
											.getTileLocation().getX() - 1,
									(int) ((ChangeTileTask) currentTask)
											.getTileLocation().getY()))) {
						tasks.add(currentTask);
						currentTask = new ChangeTileTask(this, new Point(
								(int) ((ChangeTileTask) currentTask)
										.getTileLocation().getX(),
								(int) ((ChangeTileTask) currentTask)
										.getTileLocation().getY() - i),
								Tile.Sky);
						taskTimer = 10;
					}
				}
			}
		} 

		if (currentTask instanceof GatherResources){
			((GatherResources) currentTask).setAgentSource(this);
		}
		
		//if all else fails: Wander
		if (currentTask == null && randomProb(200)){
			currentTask = new WanderTask(new Point(getCurrentX(currentPosition),
					getCurrentY(currentPosition)));
			taskTimer = 10;
		}

		executeCurrentTask();

		updateMovement(currentPosition, movements);

	}

	@Override
	public String getUserFriendlyName() {
		return "Miner";
	}
}
