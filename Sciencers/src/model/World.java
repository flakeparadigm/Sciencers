package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import model.agent.Agent;
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

public class World implements Serializable {
	// objects within the world
	public static Terrain terrain;
	public static ArrayList<Entity> agents;
	public static ArrayList<Entity> buildings;
	public static ArrayList<Projectile> projectiles;
	public static TaskList tasks;

	// tick Info
	public static GameTick agentsTick;
	public static GameTick buildingsTick;
	private static final int AGENT_TICK_TIME = 10;
	private static final int BUILDING_TICK_TIME = 100;

	// resources
	public static int playerScience;
	public static int playerMoney;
	public static final int WINNING_SCIENCE = 500;

	// generation info
	public static long seed; // (NOTE: we can use the same seed to generate
								// everything. Consistency for simulation)
	private static int width, height;

	public World(long seed, int width, int height) {
		terrain = new Terrain(seed, width, height);
		World.seed = seed;
		World.width = width;
		World.height = height;

		agents = new ArrayList<Entity>();
		buildings = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		tasks = new TaskList();
		System.out.println("World constructed");

	}

	public static void addAgent(EAgent type, int xPos) {
		// this adds an Agent at the highest point on the terrain at a certain
		// point
		Agent agent = null;
		if (type == EAgent.FARMER)
			agent = new FarmerAgent(new Point(xPos,
					terrain.getAltitude(xPos) - 1));
		if (type == EAgent.MINER)
			agent = new MinerAgent(new Point(xPos,
					terrain.getAltitude(xPos) - 1));
		// add other agent types here

		if (agent == null) {
			System.out.println("Agent is null! World.addAgent()");
		}
		agents.add(agent);
		updateInfo();
	}

	public static void addAgent(Entity agent) {
		if (agent == null) {
			System.out.println("Agent is null! World.addAgent()");
			return;
		}
		agents.add(agent);
		updateInfo();
	}

	@Deprecated
	public static void addBuilding(EBuilding type, Point pos) {
		Building building = null;
		if (type == EBuilding.FARM) {
			building = new Farm(pos);
		} else if (type == EBuilding.WAREHOUSE) {
			building = new Warehouse(pos);
		}
		// add other building types here

		if (building == null) {
			System.out.println("Building is null! World.addBuilding()");
			return;
		}
		buildings.add(building);
		updateInfo();
	}

	public static void addBuilding(Entity bldg) {
		if (bldg == null) {
			System.out.println("Building is null! World.addBuilding()");
			return;
		}
		buildings.add(bldg);
		updateInfo();
	}

	public static void updateInfo() {
		InfoObserver.updateObserver();
	}

	/*
	 * these should be unnecessary as long as their variables are public static
	 */
	// public Terrain getTerrain(){
	// return terrain;
	// }
	//
	// public ArrayList<Entity> getAgents(){
	// return agents;
	// }

	public ArrayList<Entity> getBuildings() {
		return buildings;
	}

	public static void stopTicks() {
		agentsTick.terminate();
		buildingsTick.terminate();
	}

	public static void startTicks() {
		agentsTick = new GameTick(agents, AGENT_TICK_TIME);
		buildingsTick = new GameTick(buildings, BUILDING_TICK_TIME);

		agentsTick.start();
		buildingsTick.start();
		System.out.println("Ticks started");
	}

	// Saving info.
	private Terrain saveTerrain;
	private ArrayList<Entity> saveAgents, saveBuildings;
	private ArrayList<Projectile> saveProjectiles;
	// private TaskList saveTasks;
	private int savePlayerScience, savePlayerMoney, saveResearch;
	private long saveSeed;
	private int saveWidth, saveHeight;

	public void makeSaveable() {
		saveTerrain = terrain;
		saveAgents = agents;
		saveBuildings = buildings;
		saveProjectiles = projectiles;
		// saveTasks = tasks;
		savePlayerScience = playerScience;
		savePlayerMoney = playerMoney;
		saveResearch = Research.get();
		saveSeed = seed;
		saveWidth = width;
		saveHeight = height;
	}

	public void loadSaveables() {
		terrain = saveTerrain;
		saveTerrain = null;
		agents = saveAgents;
		saveAgents = null;
		buildings = saveBuildings;
		saveBuildings = null;
		projectiles = saveProjectiles;
		saveProjectiles = null;
		// tasks = saveTasks;
		// saveTasks = null;
		playerScience = savePlayerScience;
		savePlayerScience = 0;
		playerMoney = savePlayerMoney;
		savePlayerMoney = 0;
		Research.set(saveResearch);
		saveResearch = 0;
		seed = saveSeed;
		saveSeed = 0;
		width = saveWidth;
		saveWidth = 0;
		height = saveHeight;
		saveHeight = 0;

		(new WinChecker()).run();
	}

	public static void reset() {
		stopTicks();

		terrain = new Terrain(seed, width, height);

		agents = new ArrayList<Entity>();
		buildings = new ArrayList<Entity>();
		projectiles = new ArrayList<Projectile>();
		TaskList.emptyList();

		startTicks();
	}

	public static void giveStarter() {
		World.addAgent(EAgent.FARMER, width / 2 - 6);
		World.addAgent(EAgent.FARMER, width / 2 - 2);
		World.addAgent(EAgent.MINER, width / 2 + 2);
		// World.addAgent(EAgent.MINER, width / 2 + 5);
		System.out.println("Starter agents spawned");
	}

	public static void rogueAttack() {
		Random rand = new Random();
		int rogues = rand.nextInt(agents.size() / 2);

		for (int i = 0; i <= rogues; i++) {
			World.addAgent(EAgent.ROGUE, (width / 2) + 2
					* (rand.nextInt(20) - 10));
		}
	}

}
