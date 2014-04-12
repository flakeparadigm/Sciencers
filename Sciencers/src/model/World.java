package model;

import view.Tile;

/*
 * Currently, this class is just set up to provide a method to access terrain from the view
 */

public class World{
	private Terrain terrain;
	
	public World(){
		//note that we will also want to figure out a good map size
		terrain = new Terrain("AAAAA", 100, 100);
	}
	
	public Terrain getTerrain(){
		return terrain;
	}

	
}
