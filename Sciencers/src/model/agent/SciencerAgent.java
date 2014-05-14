package model.agent;

import java.awt.Point;

import view.InfoPanes;
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
import model.task.WanderTask;
import model.task.WorkNearbyBuildingTask;

public class SciencerAgent extends Agent{

	private final int SCIENCE_FREQUENCY = 50;
	
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
		getFoodIfNecessary();
		
		Building lab = null;
		
		//if stats ok, then science!
//		for(Entity e : World.buildings) {
//			if(e instanceof Lab) {
//				lab = (Building) e;
//				break;
//			}
//		}
//		if(lab == null) {
//			AlertCollection.addAlert("Sciencers have no lab!");
//			return;
//		}
//		if(currentTask == null) {
//			Task task = new WorkNearbyBuildingTask(this, EBuilding.LAB, new Point((int) currentPosition.x, (int) currentPosition.y));
//			(new GoHereTask(task.getPos())).execute();
//			task.execute();
//		}		
		if (currentTask == null && !isWorking && labExists() && tasks.isEmpty()) {
			currentTask = new WorkNearbyBuildingTask(this, EBuilding.LAB,
					new Point(getCurrentX(currentPosition),
							getCurrentY(currentPosition)));
			isWorking = true;
		}
		
		if (isWorking && tickCount % SCIENCE_FREQUENCY == 0)
		{
			World.playerScience ++;
			InfoPanes.setScience(World.playerScience);
			System.out.println(World.playerScience);
		}
		getNextTaskIfNotBusy(EAgent.SCIENCER);
		
		
		

		//if all else fails: Wander
		if (currentTask == null && randomProb(400)){
			currentTask = new WanderTask(new Point(getCurrentX(currentPosition),
					getCurrentY(currentPosition)));
			isWorking = false;
			taskTimer = 10;
			fatigue -=5;
			if (fatigue < 0){
				fatigue = 0;
			}
		}
		

		executeCurrentTask();

		updateMovement(currentPosition, movements);
	}
	
	private boolean labExists() {
		for (Entity b : World.buildings){
			if (((Building) b).getType() == EBuilding.LAB){
				return true;
			}
		}
		return false;
	}

	@Override
	public String getUserFriendlyName() {
		return "Sciencer";
	}

}
