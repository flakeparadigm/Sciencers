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
import model.task.GatherResources;
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
		/*
		 * The following code should be focused upon specific tasks for this
		 * type of Agent
		 */

		if (isWorking){
			taskTimer = 0;
		}
		
		// new agent death using flag variable
		if (hunger <= 0 || fatigue >= MAX_FATIGUE) {
			dead = true;
			AlertCollection.addAlert("An agent has died!");
			return;
		}

		if (hunger < 0.5 * SEEK_FOOD_HUNGER) {
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
		getNextTaskIfNotBusy(EAgent.FARMER);

		if (currentTask == null && !isWorking && farmExists()) {
			currentTask = new WorkNearbyBuildingTask(this, EBuilding.FARM,
					new Point(getCurrentX(currentPosition),
							getCurrentY(currentPosition)));
			isWorking = true;
		}
		
		//if all else fails: Wander
		if (currentTask == null && randomProb(200) && !isWorking){
			currentTask = new WanderTask(new Point(getCurrentX(currentPosition),
					getCurrentY(currentPosition)));
			taskTimer = 10;
		}
		
		if (currentTask instanceof GatherResources){
			((GatherResources) currentTask).setAgentSource(this);
		}

		executeCurrentTask();

		updateMovement(currentPosition, movements);

	}

	private boolean farmExists() {
		for (Entity b : World.buildings){
			if (((Building) b).getType() == EBuilding.FARM){
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
