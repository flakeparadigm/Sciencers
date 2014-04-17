package model;

import java.awt.Point;
import java.util.ArrayList;
import model.building.Building;

/*
 * Currently, this class is just set up to provide a method to access terrain from the view
 */

public class World{
	// objects within the world
	private Terrain terrain;
	private ArrayList<Agent> agents;
	private ArrayList<Building> buildings;
	private ArrayList<Projectile> projectiles;
	
	// resources
	private int playerScience;
	private int playerMoney;
	
	// generation info
	long seed;	// (NOTE: we can use the same seed to generate everything. Consistency for simulation)
	
	public World(long seed, int width, int height) {
		terrain = new Terrain(seed, width, height);
		this.seed = seed;
		agents = new ArrayList<Agent>();
		buildings = new ArrayList<Building>();
		projectiles = new ArrayList<Projectile>();
	}
	
	public void addAgent(int xPos){
		Agent agent = new Agent(terrain, buildings, new Point(xPos, terrain.getAltitude(xPos) - 1));
	}
	
	public Terrain getTerrain(){
		return terrain;
	}

	public ArrayList<Agent> getAgents(){
		return agents;
	}
	
	public ArrayList<Building> getBuildings(){
		return buildings;
	}
}
