package model;

import view.Tile;

public class Terrain extends OurObservable{
	private Tile[][] terrain;
	private String seed;
	// we will need to find a good value for the height.
	private int mapHeight;
	private int mapWidth;
	
	public Terrain(String seed, int mapWidth, int mapHeight){
		this.seed = seed;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		terrain = new Tile[this.mapWidth][this.mapHeight];
	}
	
	public void generateSky(){
		// for testing: generate only sky
		for (int i = 0; i< terrain.length; i++){
			for (int j = 0; j< terrain[0].length; j++){
				setTile(Tile.Sky, i, j);
			}
		}
		notifyObservers();
	}
	
	public void generateRandom(){
		//TODO
		notifyObservers();
	}
	
	public Tile getTile(int row, int col){
		return terrain[row][col];
	}
	
	public void setTile(Tile tile, int row, int col){
		terrain[row][col] = tile;
		notifyObservers();
	}
}
