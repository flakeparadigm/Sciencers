package model;

import java.awt.Point;
import java.util.ArrayList;

import model.building.*;
import controller.GameTick;

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
	private static final int AGENT_TICK_TIME = 5;
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
		this.width = width;
		this.height = height;
		
		agents = new ArrayList<Entity>();
		buildings = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		
		agentsTick = new GameTick(agents, AGENT_TICK_TIME);
		buildingsTick = new GameTick(buildings, BUILDING_TICK_TIME);

		agentsTick.start();
		buildingsTick.start();
	}
	
	public static void addAgent(int xPos){
		//this adds an Agent at the highest point on the terrain at a certain point
		Agent agent = new Agent(new Point(xPos, terrain.getAltitude(xPos) - 1));
		agents.add(agent);
	}
	
	// WE WILL NEED TO REFACTOR THIS EVENTUALLY TO addBuilding(type, pos);
	public static void addBuilding(EBuilding type, Point pos){
		Building building = null;
		if(type == EBuilding.FARM)
			building = new Farm(pos);
		else if(type == EBuilding.WAREHOUSE)
			building = new Warehouse(pos);
		if(building == null)
			System.out.println("Building is null! World.addBuilding()");
		buildings.add(building);
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
		terrain = new Terrain(seed, width, height);
		
		agents = new ArrayList<Entity>();
		buildings = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();

		agentsTick = new GameTick(agents, AGENT_TICK_TIME);
		buildingsTick = new GameTick(buildings, BUILDING_TICK_TIME);

		agentsTick.start();
		buildingsTick.start();
	}
}
