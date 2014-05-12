package model.agent;

import java.awt.Point;

import model.AlertCollection;
import model.Entity;
import model.World;
import model.building.Building;
import model.building.EBuilding;
import model.building.Lab;
import model.inventory.Resource;
import model.task.AgentDeath;
import model.task.BuildBuildingTask;
import model.task.GoHereTask;
import model.task.HarvestTreeTask;
import model.task.Task;
import model.task.WorkNearbyBuildingTask;

public class SciencerAgent extends Agent{

	public SciencerAgent(Point currentPosition) {
		super(currentPosition);
		priorityResource = Resource.URANIUM;
		inventory.changePriority(priorityResource);
		agentType = EAgent.SCIENCER;
	}

	@Override
	public void update() {
		updateStats();
		if(dead) {
			return;
		}

		// seek food if hungry
		if (hunger < SEEK_FOOD_HUNGER && currentTask == null) {
			if (findNearestTree(currentPosition) != null) {
				currentTask = new HarvestTreeTask(this,
						findNearestTree(currentPosition), World.terrain);
				taskTimer = 10;
			}
		}
		
		Building lab = null;
		
		//if stats ok, then science!
		for(Entity e : World.buildings) {
			if(e instanceof Lab) {
				lab = (Building) e;
				break;
			}
		}
		if(lab == null) {
			AlertCollection.addAlert("Sciencers have no lab!");
			return;
		}
		if(currentTask == null) {
			Task task = new WorkNearbyBuildingTask(this, EBuilding.LAB, new Point((int) currentPosition.x, (int) currentPosition.y));
			(new GoHereTask(task.getPos())).execute();
			task.execute();
		}		
		
	}

	@Override
	public String getUserFriendlyName() {
		return "Sciencer";
	}

}
