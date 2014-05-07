package model.task;

import java.awt.Point;

import model.agent.AgentReplacement;
import model.building.Building;
import model.inventory.Resource;
import model.inventory.Storable;

public class GiveToBuilding implements Task{

	private AgentReplacement sourceAgent;
	private Building sinkBuilding;
	private Storable item;
	private int quantity;
	
	public GiveToBuilding(AgentReplacement sourceAgent, Building sinkBuilding, Storable resource, int quantity){
		this.sourceAgent = sourceAgent;
		this.sinkBuilding = sinkBuilding;
		this.item = resource;
		this.quantity = quantity;
	}
	
	@Override
	public void execute() {
		sourceAgent.getInventory().changeAmount(item, -quantity);
		sinkBuilding.getInventory().changeAmount(item, quantity);
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
