package model.task;

import java.awt.Point;

import model.agent.AgentReplacement;
import model.building.Building;
import model.inventory.Resource;
import model.inventory.Storable;

public class GetFromBuilding implements Task {
	private AgentReplacement sourceAgent;
	private Building sinkBuilding;
	private Storable item;
	private int quantity;
	
	public GetFromBuilding(AgentReplacement sinkAgent, Building sourceBuilding, Storable resource, int quantity){
		this.sourceAgent = sinkAgent;
		this.sinkBuilding = sourceBuilding;
		this.item = resource;
		this.quantity = quantity;
	}
	
	@Override
	public void execute() {
		sinkBuilding.getInventory().changeAmount(item, -quantity);
		sourceAgent.getInventory().changeAmount(item, quantity);
		System.out.println("BuildingInventory:" + sinkBuilding.getInventory().getAmount(item));	
		System.out.println("AgentInventory:" + sourceAgent.getInventory().getAmount(item));

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
