package model;

import view.Tile;

public class Terrain extends OurObservable {
	private Tile[][] terrain;
	private String seed;
	private int mapHeight;
	private int mapWidth;

	public Terrain(String seed, int mapWidth, int mapHeight) {
		this.seed = seed;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		terrain = new Tile[this.mapWidth][this.mapHeight];
		generateSky();
		generateRandomTerrain();
		notifyObservers();
	}

	public void generateSky() {
		// for testing: generate only sky
		for (int i = 0; i < terrain.length; i++) {
			for (int j = 0; j < terrain[0].length; j++) {
				setTile(Tile.Sky, i, j);
			}
		}
	}

	public void generateRandomTerrain() {
		// TODO: Perlin noise function
		System.out.println(Noise1());
	}

	private float Noise1() {
		//return a random float between -1 and 1
		float Min = -1;
		float Max = 1;
		return (float) (Min + (Math.random() * ((Max - Min) + 1)));
		//I'm trying to follow this outline to some degree:http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
	}
	

	public Tile[][] getTileArray() {
		return terrain;
	}

	public Tile getTile(int row, int col) {
		return terrain[row][col];
	}

	public void setTile(Tile tile, int row, int col) {
		terrain[row][col] = tile;
		// is this notification here necessary? I'm not sure
		notifyObservers();
	}
}
