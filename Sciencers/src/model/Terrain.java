package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import view.Tile;
import controller.SciencersObserver;

public class Terrain implements Serializable {
	private Tile[][] terrain;
	private long seed;
	private Random random;
	private int mapHeight;
	private int mapWidth;

	/*
	 * edit these following values to change how the map will generate.
	 * >Magnitude for the three functions indicates how far up or down the
	 * terrain might stretch >Frequency determines the distance between points
	 * of the specified magnitude in the plot. Thus, for a terrain with sparsely
	 * placed mountains, you would want one function to have a high magnitude
	 * and high value for frequency. (High frequency number means the points are
	 * farther apart).
	 */
	private final int averageTerrainHeight = 30;
	private final int perlin1Magnitude = 20;
	private final int perlin2Magnitude = 0;
	private final int perlin3Magnitude = 1;
	private final int perlin1NoiseFrequency = 50;
	private final int perlin2NoiseFrequency = 4;
	private final int perlin3NoiseFrequency = 3;

	/*
	 * edit these values to change how minerals and materials will be placed in
	 * the ground. Placement determines the probability of a certain mineral
	 * being set to a specific tile. Propagation determines the probability of
	 * the tile being a larger vein. Depth Ratio determines how much much more
	 * likely the mineral will be present at lower depths.
	 * 
	 * PS: don't go over .25 for propagation or you will get a stack overflow
	 * for some reason. Haven't bothered to try to fix it since we aren't going
	 * to need it nearly that high for the game.
	 */

	// stonePlacement: percentage that a dirt block is replaced with stone
	private final double stonePlacement = .01;
	// stonePropagation: percentage for a placed dirt block to expand it's vein
	// to a nearby tile
	private final double stonePropagation = 0.2;
	// stoneDepthRatio: a lower number here means the veins will be generally
	// deeper
	private final double stoneDepthRatio = .00000001;
	// stoneDepthExp: a higher number here increases the probabilities to place
	// stone exponentially,
	// and is modified by the ratio. A high Exp and low Ratio will make a stark
	// change between dirt and stone.
	private final double stoneDepthExp = 4;

	private final double ironPlacement = .002;
	private final double ironPropagation = .2;
	private final double ironDepthRatio = .0001;
	private final double ironDepthExp = .4;

	private final double uraniumPlacement = .002;
	private final double uraniumPropagation = .1;
	private final double uraniumDepthRatio = .0001;
	private final double uraniumDepthExp = .8;

	private final double treePlacement = .5;
	
	public ArrayList<Tile> passableTiles;

	public Terrain(long seed, int mapWidth, int mapHeight) {
		this.seed = seed;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		
		passableTiles = new ArrayList<Tile>();
		passableTiles.add(Tile.Sky);
		passableTiles.add(Tile.Wood);
		passableTiles.add(Tile.Leaves);
		passableTiles.add(Tile.Ladder);
		passableTiles.add(Tile.BackgroundDirt);
		
		terrain = new Tile[this.mapWidth][this.mapHeight];
		random = new Random(seed);
		generateSky();
		generateRandomTerrain();
		generateGroundDetails();
		generateTrees();
		
	}

	public void generateTrees() {
		for (int i = 2; i < terrain.length - 2; i++) {
			if (random.nextDouble() < treePlacement) {
				int alt = getAltitude(i);
				terrain[i][alt - 1] = Tile.Wood;
				int top = 0;
				for (int j = 0; j <= random.nextDouble() * 7; j++) {
					if (alt - j - 1 > 0) {
						terrain[i][alt - j - 1] = Tile.Wood;
						top = alt - j - 1;
					}
				}
				if (top - 2 > 0) {
					terrain[i][top - 1] = Tile.Leaves;
					terrain[i + 1][top - 1] = Tile.Leaves;
					terrain[i - 1][top - 1] = Tile.Leaves;
					terrain[i + 1][top - 2] = Tile.Leaves;
					terrain[i - 1][top - 2] = Tile.Leaves;
					terrain[i][top - 2] = Tile.Leaves;
				}
			}
		}
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
					nextPoint = mapWidth - 1;
				}
				double slope = (double) (smoothFunction[nextPoint] - smoothFunction[lastPoint])
						/ (double) perlin1NoiseFrequency;
				smoothFunction[i] = (int) (smoothFunction[lastPoint] + (i % perlin1NoiseFrequency)
						* slope);
			}
		}

		// plot second perlin points:
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
					nextPoint = mapWidth - 1;
				}
				double slope = (double) (smoothFunction2[nextPoint] - smoothFunction2[lastPoint])
						/ (double) perlin2NoiseFrequency;
				smoothFunction2[i] = (int) (smoothFunction2[lastPoint] + (i % perlin2NoiseFrequency)
						* slope);
			}
		}

		// plot second perlin points:
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
					nextPoint = mapWidth - 1;
				}
				double slope = (double) (smoothFunction3[nextPoint] - smoothFunction3[lastPoint])
						/ (double) perlin3NoiseFrequency;
				smoothFunction3[i] = (int) (smoothFunction3[lastPoint] + (i % perlin3NoiseFrequency)
						* slope);
			}
		}

		// Set tiles on the terrain (add all perlin functions)
		for (int i = 0; i < mapWidth; i++) {
			for (int j = smoothFunction[i] + smoothFunction2[i]
					+ smoothFunction3[i] + averageTerrainHeight; j < mapHeight; j++) {
				setTile(Tile.Dirt, i, j);
			}
		}
	}

	private int Noise1(int magnitude) {
		// return a random float between -mgnitude and magnitude
		int Min = -magnitude;
		int Max = magnitude;
		return (int) (Min + (random.nextDouble() * ((Max - Min) + 1)));
	}

	private void generateGroundDetails() {
		for (int i = 0; i < mapWidth; i++) {
			for (int j = 0; j < mapHeight; j++) {
				if (getTile(i, j).value == 'd') {
					// place these statements in the order of rarity of Tile
					if (random.nextDouble() < (stonePlacement + stoneDepthRatio
							* (Math.pow((j - averageTerrainHeight),
									stoneDepthExp)))) {
						setTile(Tile.Stone, i, j);
						propagate(Tile.Stone, i, j);
					}
					if (random.nextDouble() < (ironPlacement + ironDepthRatio
							* (Math.pow((j - averageTerrainHeight),
									ironDepthExp)))) {
						setTile(Tile.Iron, i, j);
						propagate(Tile.Iron, i, j);
					}
					if (random.nextDouble() < (uraniumPlacement + uraniumDepthRatio
							* (Math.pow((j - averageTerrainHeight),
									uraniumDepthExp)))) {
						setTile(Tile.Uranium, i, j);
						propagate(Tile.Uranium, i, j);
					}
				}
			}
		}
	}

	private void propagate(Tile type, int row, int col) {
		double propagation = 0;
		if (type.equals(Tile.Stone)) {
			propagation = stonePropagation;
		} else if (type.equals(Tile.Iron)) {
			propagation = ironPropagation;
		} else if (type.equals(Tile.Uranium)) {
			propagation = uraniumPropagation;
		}
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (random.nextDouble() < propagation
						&& getTile(row + i, col + j).equals(Tile.Dirt)) {
					setTile(type, row + i, col + j);
					propagate(type, row + i, col + j);
				}
			}
		}
	}

	public Tile[][] getTileArray() {
		return terrain;
	}

	public Tile getTile(int row, int col) {
		if (row < mapWidth && col < mapHeight && row >= 0 && col >= 0) {
			return terrain[row][col];
		}
		// in case of out of bounds, return a sky tile
		return terrain[0][0];
	}

	public void setTile(Tile tile, int row, int col) {
		if (row < mapWidth && col < mapHeight && row >= 0 && col >= 0) {
			terrain[row][col] = tile;
		}
	}

	public void updateTile(Tile tile, int row, int col) {
		setTile(tile, row, col);

		new Thread(new Runnable() {
			public void run() {
				SciencersObserver.updateObserver();
			}
		}).start();
	}

	public String toString() {
		String theOutput = "";

		theOutput += "seed: " + seed + ", mapWidth: " + mapWidth
				+ ", mapHeight: " + mapHeight + "\n\n";
		for (int i = 0; i < terrain[0].length; i++) {
			for (int j = 0; j < terrain.length; j++) {
				theOutput += terrain[j][i].value + " ";
			}
			theOutput += "\n";
		}

		return theOutput;
	}

	public int getAltitude(int xPos) {
		for (int j = 0; j < mapHeight; j++) {
			if (!passableTiles.contains(terrain[xPos][j])) {
				return j;
			}
		}
		return -1;
	}
	
	public int getDeepestPassable(int xPos){
		for (int j = mapHeight - 1; j > 0; j--) {
			if (passableTiles.contains(terrain[xPos][j])) {
				return j;
			}
		}
		return -1;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setPathVisible(Stack<Point> movements) {
		for (int i = 0; i < movements.size(); i++) {
			terrain[movements.get(i).x][movements.get(i).y].equals(Tile.Path);
		}
	}
}
