package model;

import java.awt.Point;
import java.util.ArrayList;

import model.agent.AgentReplacement;
import model.agent.EAgent;
import model.agent.FarmerAgent;
import model.agent.MinerAgent;
import model.building.Building;
import model.building.EBuilding;
import model.building.Farm;
import model.building.Warehouse;
import model.task.TaskList;
import controller.GameTick;
import controller.InfoObserver;

/*
 * Currently, this class is just set up to provide a method to access terrain from the view
 */

public class World{
	// objects within the world
	public static Terrain terrain;
	public static ArrayList<Entity> agents;
	public static ArrayList<Entity> buildings;
	public static ArrayList<Projectile> projectiles;
	
	// tick Info
	private static GameTick agentsTick;
	private static GameTick buildingsTick;
	private static final int AGENT_TICK_TIME = 50;
	private static final int BUILDING_TICK_TIME = 100;
	
	// resources
	public static int playerScience;
	public static int playerMoney;
	
	// generation info
	public static long seed;	// (NOTE: we can use the same seed to generate everything. Consistency for simulation)
	private static int width, height;
	
	public World(long seed, int width, int height) {
		terrain = new Terrain(seed, width, height);
		World.seed = seed;
		World.width = width;
		World.height = height;
		
		agents = new ArrayList<Entity>();
		buildings = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		
		agentsTick = new GameTick(agents, AGENT_TICK_TIME);
		buildingsTick = new GameTick(buildings, BUILDING_TICK_TIME);

		agentsTick.start();
		buildingsTick.start();
	}
	
	public static void addAgent(EAgent type, int xPos){
		//this adds an Agent at the highest point on the terrain at a certain point
		AgentReplacement agent = null;
		if(type == EAgent.FARMER)
			agent = new FarmerAgent(new Point(xPos, terrain.getAltitude(xPos) - 1)) ;
		if(type == EAgent.MINER)
			agent = new MinerAgent(new Point(xPos, terrain.getAltitude(xPos) - 1)) ;
		//add other agent types here
		
		if(agent == null) {
			System.out.println("Agent is null! World.addAgent()");
		}
		agents.add(agent);
		updateInfo();
	}
	
	public static void addBuilding(EBuilding type, Point pos){
		Building building = null;
		if(type == EBuilding.FARM) {
			building = new Farm(pos);
		}
		else if(type == EBuilding.WAREHOUSE) {
			building = new Warehouse(pos);
		}
		//add other building types here
		
		if(building == null) {
			System.out.println("Building is null! World.addBuilding()");
			return;
		}
		buildings.add(building);
		updateInfo();
	}
	
	public static void updateInfo() {
		InfoObserver.updateObserver();
	}

	/*
	 * these should be unnecessary as long as their variables are public static
	 */
//	public Terrain getTerrain(){
//		return terrain;
//	}
//
//	public ArrayList<Entity> getAgents(){
//		return agents;
//	}
	
	public ArrayList<Entity> getBuildings(){
		return buildings;
	}

	public static void reset() {
		agentsTick.terminate();
		buildingsTick.terminate();
		
		terrain = new Terrain(seed, width, height);
		
		agents = new ArrayList<Entity>();
		buildings = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		TaskList.emptyList();

		agentsTick = new GameTick(agents, AGENT_TICK_TIME);
		buildingsTick = new GameTick(buildings, BUILDING_TICK_TIME);

		agentsTick.start();
		buildingsTick.start();
	}
}
