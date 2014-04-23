package model.agentCommand;

import java.awt.Point;

import model.Agent;
import model.building.Building;
import model.inventory.Resource;

public class DepositInBuilding implements Task{

	private Agent sourceAgent;
	private Building sinkBuilding;
	private Resource resource;
	private int quantity;
	
	public DepositInBuilding(Agent sourceAgent, Building sinkBuilding, Resource resource, int quantity){
		this.sourceAgent = sourceAgent;
		this.sinkBuilding = sinkBuilding;
		this.resource = resource;
		this.quantity = quantity;
	}
	
	@Override
	public void execute() {
		sinkBuilding.getInventory().changeAmount(resource, quantity);
		sourceAgent.getInventory().changeAmount(resource, -quantity);
	}

	@Override
	public Point getPos() {
		return sinkBuilding.getPos();
	}

}
