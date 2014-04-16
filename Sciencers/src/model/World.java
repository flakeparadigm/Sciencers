package model;

import java.util.ArrayList;

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
	}
	public World(long seed, int width, int height) {
		terrain = new Terrain(seed, width, height);
	}
	
	public Terrain getTerrain(){
		return terrain;
	}

	
}
