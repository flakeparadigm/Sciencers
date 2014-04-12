package model;

import view.Tile;

public class Terrain extends OurObservable{
	private Tile[][] terrain;
	private String seed;
	private int mapHeight;
	private int mapWidth;
	
	public Terrain(String seed, int mapWidth, int mapHeight){
		this.seed = seed;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		terrain = new Tile[this.mapWidth][this.mapHeight];
		generateSky();
		notifyObservers();
	}
	
	public void generateSky(){
		// for testing: generate only sky
		for (int i = 0; i< terrain.length; i++){
			for (int j = 0; j< terrain[0].length; j++){
				setTile(Tile.Sky, i, j);
			}
		}
	}
	
	public void generateRandom(){
		//TODO
	}
	
	public Tile[][] getTileArray(){
		return terrain;
	}
	
	public Tile getTile(int row, int col){
		return terrain[row][col];
	}
	
	public void setTile(Tile tile, int row, int col){
		terrain[row][col] = tile;
		//is this notification here necessary? I'm not sure
		notifyObservers();
	}
}
