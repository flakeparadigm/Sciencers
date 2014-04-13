package model;

import java.util.ArrayList;
import java.util.Random;

import view.Tile;

public class Terrain extends OurObservable {
	private Tile[][] terrain;
	private long seed;
	private Random random;
	private int mapHeight;
	private int mapWidth;
	
	/*
	 * edit these following values to change how the map will generate.
	 * >Magnitude for the three functions indicates how far up or down the 
	 * terrain might stretch
	 * >Frequency determines the distance between points of the specified magnitude
	 * in the plot. Thus, for a terrain with sparsely placed mountains,
	 * you would want one function to have a high magnitude and high value for 
	 * frequency. (High frequency number means the points are farther apart).
	 *
	 */
	private final int averageTerrainHeight = 30;
	private final int perlin1Magnitude = 20;
	private final int perlin2Magnitude = 2;
	private final int perlin3Magnitude = 1;
	private final int perlin1NoiseFrequency = 50;
	private final int perlin2NoiseFrequency = 4;
	private final int perlin3NoiseFrequency = 3;

	public Terrain(long seed, int mapWidth, int mapHeight) {
		this.seed = seed;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		terrain = new Tile[this.mapWidth][this.mapHeight];
		random = new Random(seed);
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
		generateTerrainHeight();
	}

	private void generateTerrainHeight() {
		// plot points at specified frequency
		int[] smoothFunction = new int[mapWidth * perlin1NoiseFrequency];
		for (int i = 0; i < mapWidth; i++) {
			if (i % perlin1NoiseFrequency == 0) {
				smoothFunction[i] = Noise1(perlin1Magnitude);
			}
		}
		// linearly interpolate
		for (int i = 0; i < mapWidth; i++) {
			if (i % perlin1NoiseFrequency != 0) {
				int lastPoint = i - i % perlin1NoiseFrequency;
				int nextPoint;
				if (lastPoint + perlin1NoiseFrequency < mapWidth) {
					nextPoint = lastPoint + perlin1NoiseFrequency;
				} else {
					nextPoint = mapWidth-1;
				}
				double slope = (double)(smoothFunction[nextPoint] - smoothFunction[lastPoint])
						/ (double)perlin1NoiseFrequency;
				smoothFunction[i] = (int) (smoothFunction[lastPoint] + (i % perlin1NoiseFrequency)
						* slope);
			}
		}

		//plot second perlin points:
		int[] smoothFunction2 = new int[mapWidth * perlin2NoiseFrequency];
		for (int i = 0; i < mapWidth; i++) {
			if (i % perlin2NoiseFrequency == 0) {
				smoothFunction2[i] = Noise1(perlin2Magnitude);
			}
		}
		// linearly interpolate
		for (int i = 0; i < mapWidth; i++) {
			if (i % perlin2NoiseFrequency != 0) {
				int lastPoint = i - i % perlin2NoiseFrequency;
				int nextPoint;
				if (lastPoint + perlin2NoiseFrequency < mapWidth) {
					nextPoint = lastPoint + perlin2NoiseFrequency;
				} else {
					nextPoint = mapWidth-1;
				}
				double slope = (double)(smoothFunction2[nextPoint] - smoothFunction2[lastPoint])
						/ (double)perlin2NoiseFrequency;
				smoothFunction2[i] = (int) (smoothFunction2[lastPoint] + (i % perlin2NoiseFrequency)
						* slope);
			}
		}
		
		//plot second perlin points:
		int[] smoothFunction3 = new int[mapWidth * perlin3NoiseFrequency];
		for (int i = 0; i < mapWidth; i++) {
			if (i % perlin3NoiseFrequency == 0) {
				smoothFunction3[i] = Noise1(perlin3Magnitude);
			}
		}
		// linearly interpolate
		for (int i = 0; i < mapWidth; i++) {
			if (i % perlin3NoiseFrequency != 0) {
				int lastPoint = i - i % perlin3NoiseFrequency;
				int nextPoint;
				if (lastPoint + perlin3NoiseFrequency < mapWidth) {
					nextPoint = lastPoint + perlin3NoiseFrequency;
				} else {
					nextPoint = mapWidth-1;
				}
				System.out.println(smoothFunction3[lastPoint]);
				System.out.println(smoothFunction3[nextPoint]);
				double slope = (double)(smoothFunction3[nextPoint] - smoothFunction3[lastPoint])
						/ (double)perlin3NoiseFrequency;
				System.out.println("slope:" + slope);
				smoothFunction3[i] = (int) (smoothFunction3[lastPoint] + (i % perlin3NoiseFrequency)
						* slope);
			}
		}
		
		//Set tiles on the terrain (add all perlin functions)
		for (int i = 0; i < mapWidth; i++) {
			for (int j = smoothFunction[i] + smoothFunction2[i] + smoothFunction3[i] + averageTerrainHeight; j< mapHeight; j++){
				setTile(Tile.Dirt, i, j);
			}
		}
	}

	private int Noise1(int magnitude) {
		// return a random float between -1 and 1
		int Min = -magnitude;
		int Max = magnitude;
		return (int) (Min + (random.nextDouble() * ((Max - Min) + 1)));
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
