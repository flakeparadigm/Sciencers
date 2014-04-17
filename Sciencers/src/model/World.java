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
	
	public World(){
		//note that we will also want to figure out a good map size
		terrain = new Terrain(12345, 500, 100);
		
		agents = new ArrayList<Agent>();
		buildings = new ArrayList<Building>();
		projectiles = new ArrayList<Projectile>();
		
		//add initial agents TODO: find correct points to add them at.
		agents.add(new Agent(terrain, buildings, new Point(3,3)));
	}
	
	public World(long seed, int width, int height) {
		terrain = new Terrain(seed, width, height);
	}
	
	public Terrain getTerrain(){
		return terrain;
	}

	public ArrayList<Agent> getAgents(){
		return agents;
	}
}
