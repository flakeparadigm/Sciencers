package model.agent;


import java.awt.Point;

import model.AlertCollection;
import model.World;
import model.building.EBuilding;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.AgentDeath;
import model.task.BuildBuildingTask;
import model.task.CraftToolTask;
import model.task.HarvestTreeTask;
import model.task.WorkNearbyBuildingTask;


public class FarmerAgent extends AgentReplacement {

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
		// die if hunger < 0
		if (hunger < 0 && currentTask == null) {
			currentTask = (new AgentDeath(this, new Point(
					(int) currentPosition.x, (int) currentPosition.y)));
			taskTimer = 0;
			AlertCollection.addAlert("An agent has died!");
		}
		
		if (hunger < 0.5 * SEEK_FOOD_HUNGER)  {
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

		if (currentTask == null && !isWorking){
			currentTask = new WorkNearbyBuildingTask(this, EBuilding.FARM, new Point(getCurrentX(currentPosition), getCurrentY(currentPosition)));
			isWorking = true;
		}
		
		executeCurrentTask();

		updateMovement(currentPosition, movements);

	}

	@Override
	public String getUserFriendlyName() {
		return "Farmer";
	}
}
