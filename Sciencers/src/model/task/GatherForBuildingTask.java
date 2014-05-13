package model.task;

import java.awt.Point;

import model.agent.Agent;
import model.building.Building;
import model.building.EBuilding;
import model.inventory.Resource;

public class GatherForBuildingTask implements Task{

	private Building sinkBuilding;
	private Resource resource;
	private Agent sourceAgent;
	
	public GatherForBuildingTask(Building sinkBuilding){
		if (sinkBuilding.getType() == EBuilding.FARM){
			resource = Resource.FOOD;
		} else if (sinkBuilding.getType() == EBuilding.FACTORY){
			resource = Resource.IRON;
		} else if (sinkBuilding.getType() == EBuilding.LAB){
			resource = Resource.URANIUM;
		} else if (sinkBuilding.getType() == EBuilding.WAREHOUSE){
			resource = Resource.STONE;
		}
		this.sinkBuilding = sinkBuilding;
	}
	
	public void setAgentSource(Agent sourceAgent){
		this.sourceAgent = sourceAgent;
	}
	
	@Override
	public void execute() {
		System.out.println("Executed Gather Task");
		sourceAgent.tasks.add(new GiveToBuilding(sourceAgent, sinkBuilding, resource, 1));
		sourceAgent.currentTask = new GatherResources(resource);
	}

	@Override
	public Point getPos() {
		return sinkBuilding.getPos();
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}

}
