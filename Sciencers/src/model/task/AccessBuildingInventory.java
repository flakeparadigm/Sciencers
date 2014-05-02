package model.task;

import java.awt.Point;

import model.agent.AgentReplacement;
import model.building.Building;
import model.inventory.Resource;

public class AccessBuildingInventory implements Task{

	private AgentReplacement sourceAgent;
	private Building sinkBuilding;
	private Resource resource;
	private int quantity;
	
	public AccessBuildingInventory(AgentReplacement sourceAgent, Building sinkBuilding, Resource resource, int quantity){
		this.sourceAgent = sourceAgent;
		this.sinkBuilding = sinkBuilding;
		this.resource = resource;
		this.quantity = quantity;
	}
	
	@Override
	public void execute() {
		sinkBuilding.getInventory().changeAmount(resource, quantity);
		sourceAgent.getInventory().changeAmount(resource, -quantity);
		System.out.println("BuildingInventory:" + sinkBuilding.getInventory().getAmount(resource));	
		System.out.println("AgentInventory:" + sourceAgent.getInventory().getAmount(resource));

	}

	@Override
	public Point getPos() {
		return sinkBuilding.getPos();
	}

	@Override
	public boolean shouldBeSeen() {
		return false;
	}

}
