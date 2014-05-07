package model.building;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import model.AlertCollection;
import model.World;
import model.agent.AgentReplacement;
import model.inventory.Inventory;
import model.inventory.Resource;
import model.inventory.Storable;
import model.inventory.Tool;

public class Factory extends Building {
	
	// Magic Numbers
	private final int TICKS_PER_ITEM = 1500; //~30 seconds
	private final int BUILDING_WIDTH = 10;
	private final int BUILDING_HEIGHT = 4;
	private final int MAX_WORKERS = 5;
	private final int ITEMS_PER_UPDATE = 1;
	private final int CAPACITY = 1000;
	
	//Variables
	private Inventory inv;
	private ArrayList<AgentReplacement> workers;
	private Queue<ProductionItem> productionQueue;

	public Factory(Point pos) {
		super(pos);
		workers = new ArrayList<AgentReplacement>();
		inv = new Inventory(CAPACITY, Resource.URANIUM);
		productionQueue = new LinkedList<ProductionItem>();
	}

	@Override
	public void update() {
		Random random = new Random(World.seed);
		if(random.nextInt(TICKS_PER_ITEM) == 1) {
			ProductionItem freshItem = productionQueue.poll();
			freshItem.getAgent().giveItem(freshItem.getTool());
			System.out.println("Factory needs to give the agent the tool he requested");
		}
		
		if(inv.getTotal() < CAPACITY * 0.95) {
			AlertCollection.addAlert("A factory is almost full!");
		}
	}
	
	public void makeTool(AgentReplacement a, Tool t) {
		productionQueue.add(new ProductionItem(a, t));
	}

	@Override
	public Dimension getSize() {
		return new Dimension(BUILDING_WIDTH, BUILDING_HEIGHT);
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}

	@Override
	public boolean addWorker(AgentReplacement a) {
		if(workers.size() <= MAX_WORKERS)
			return false;
		
		workers.add(a);
		return true;
	}

	@Override
	public boolean removeWorker(AgentReplacement a) {
		if(!workers.contains(a))
			return false;
		
		workers.remove(a);
		return true;
	}

	@Override
	public int getNumWorkers() {
		return workers.size();
	}

	@Override
	public EBuilding getType() {
		return EBuilding.FACTORY;
	}

}

class ProductionItem {
	private AgentReplacement agent;
	private Tool tool;
	public ProductionItem(AgentReplacement agent, Tool tool) {
		this.agent = agent;
		this.tool = tool;
	}
	
	public AgentReplacement getAgent() {
		return agent;
	}
	
	public Tool getTool() {
		return tool;
	}
}
