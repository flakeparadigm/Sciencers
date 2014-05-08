package model.task;

import java.awt.Point;

import model.agent.Agent;
import model.building.Building;
import model.building.Factory;
import model.building.Farm;
import model.building.Warehouse;
import model.inventory.Resource;
import model.inventory.Storable;
import model.inventory.Tool;

public class MoveResourcesTask implements Task {

	private Building getFrom, giveTo;
	private Agent agentSource;
	
	public MoveResourcesTask(Building getFrom, Building giveTo) {
		this.getFrom = getFrom;
		this.giveTo = giveTo;
		if(!(giveTo instanceof Warehouse)) {
			System.out.println("Should we really be delivering items to a non-warehouse?");
		}
		agentSource = null;
	}
	
	@Override
	public void execute() {
		
		Storable s = null;
		int amt = 0;
		
		if(getFrom instanceof Farm) {
			s = Resource.FOOD;
			amt = 20;
		}
		else if(getFrom instanceof Factory) {
			s = Resource.FOOD;
			amt = 2;
		}
		else {
			System.out.println("MoveResources task needs work!");
		}
		
//		agentSource.tasks.add(new GoHereTask(getFrom.getPos()));
		agentSource.tasks.add(new GetFromBuilding(agentSource, getFrom, s, amt));
		agentSource.tasks.add(new GoHereTask(giveTo.getPos()));
		agentSource.tasks.add(new GiveToBuilding(agentSource, giveTo, s, amt));
	}

	@Override
	public Point getPos() {
		System.out.println("Why do we need to know the position of a MoveResourcesTask?");
		return getFrom.getPos();
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}
	
	public void setAgentSource(Agent agentSource) {
		this.agentSource = agentSource;
	}

}
