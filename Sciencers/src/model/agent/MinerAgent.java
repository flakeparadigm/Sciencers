package model.agent;

import java.awt.Point;

import view.Tile;
import model.AlertCollection;
import model.Entity;
import model.World;
import model.building.Building;
import model.building.EBuilding;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.AgentDeath;
import model.task.BuildBuildingTask;
import model.task.ChangeTileTask;
import model.task.CraftToolTask;
import model.task.GatherResources;
import model.task.GiveToBuilding;
import model.task.GoMineAreaTask;
import model.task.HarvestTreeTask;
import model.task.WanderTask;
import model.task.WorkNearbyBuildingTask;

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
		getFoodIfNecessary();
		
		//find building to work
		if (factoryExists()){
			buildingWorked = getFactory();
		}
		
		if (currentTask == null && !isWorking && factoryExists() && tasks.isEmpty()) {
			currentTask = new WorkNearbyBuildingTask(this, EBuilding.FACTORY,
					new Point(getCurrentX(currentPosition),
							getCurrentY(currentPosition)));
			isWorking = true;
		}
		
		if (isWorking && currentTask == null && buildingWorked != null && tasks.isEmpty() && inventory.getAmount(Resource.IRON)<5){
//			currentTask = new HarvestTreeTask(this, findNearestTree(currentPosition), World.terrain);
			currentTask = new GatherResources(Resource.IRON);
			System.out.println("added gather iron");
			taskTimer = 10;
		}
		
		if (inventory.getAmount(Resource.IRON)>1 && factoryExists()){
			currentTask = new GiveToBuilding(this, buildingWorked, Resource.IRON, 1);
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
	
	private Building getFactory() {
		for (Entity b : World.buildings){
			if (((Building) b).getType() == EBuilding.FACTORY){
				return (Building)b;
			}
		}
		return null;
	}

	private boolean factoryExists() {
		for (Entity b : World.buildings){
			if (((Building) b).getType() == EBuilding.FACTORY){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUserFriendlyName() {
		return "Miner";
	}
}
