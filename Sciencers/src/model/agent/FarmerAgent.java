package model.agent;

import java.awt.Point;

import model.AlertCollection;
import model.Entity;
import model.World;
import model.building.Building;
import model.building.EBuilding;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.AgentDeath;
import model.task.BuildBuildingTask;
import model.task.CraftToolTask;
import model.task.GatherForBuildingTask;
import model.task.GatherResources;
import model.task.GetFromBuilding;
import model.task.GiveToBuilding;
import model.task.HarvestTreeTask;
import model.task.WanderTask;
import model.task.WorkNearbyBuildingTask;

public class FarmerAgent extends Agent {

	public FarmerAgent(Point currentPosition) {
		super(currentPosition);
		priorityResource = Resource.FOOD;
		inventory.changePriority(priorityResource);
		agentType = EAgent.FARMER;
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

		// if (isWorking){
		// taskTimer = 0;
		// }

		// seek food if hungry
		getFoodIfNecessary();

		if (currentTask == null && !isWorking && farmExists()
				&& tasks.isEmpty()) {
			currentTask = new WorkNearbyBuildingTask(this, EBuilding.FARM,
					new Point(getCurrentX(currentPosition),
							getCurrentY(currentPosition)));
			isWorking = true;
		}

		if (isWorking && currentTask == null && buildingWorked != null
				&& tasks.isEmpty() && inventory.getAmount(Resource.FOOD) < 5) {
			currentTask = new HarvestTreeTask(this,
					findNearestTree(currentPosition), World.terrain);
			taskTimer = 10;
		}

		if (inventory.getAmount(Resource.FOOD) > 3 && farmExists()) {
			currentTask = new GiveToBuilding(this, buildingWorked,
					Resource.FOOD, 3);
		}
		// new HarvestTreeTask(this, new Point(getCurrentX(currentPosition),
		// getCurrentY(currentPosition)), World.terrain)

		// get task from list if agent doesn't have one
		getNextTaskIfNotBusy(EAgent.FARMER);

		if (currentTask instanceof GatherResources) {
			((GatherResources) currentTask).setAgentSource(this);
		}

		// if (currentTask instanceof GatherForBuildingTask){
		// ((GatherForBuildingTask) currentTask).setAgentSource(this);
		// }

		// if all else fails: Wander
		if (currentTask == null && randomProb(200) && !isWorking) {
			currentTask = new WanderTask(new Point(
					getCurrentX(currentPosition), getCurrentY(currentPosition)));
			taskTimer = 10;
			fatigue--;
		}

		executeCurrentTask();

		updateMovement(currentPosition, movements);

	}


	private boolean farmExists() {
		for (Entity b : World.buildings) {
			if (((Building) b).getType() == EBuilding.FARM) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUserFriendlyName() {
		return "Farmer";
	}
}
