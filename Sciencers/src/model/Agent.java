package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;

import model.agentCommand.AgentDeath;
import model.agentCommand.HarvestTreeTask;
import model.agentCommand.Task;
import model.agentCommand.TaskList;
import model.building.Building;
import model.inventory.Inventory;
import model.inventory.Resource;
import view.Tile;

public class Agent implements Entity {

	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	private EAgent type; // agent type (Jake)

	// making the speed too large can cause the agent to jitter (it is missing
	// the range to know it has hit a tile)
	public final double SPEED = .04;
	public final int CAPACITY = 100;
	// every 100 ticks, hunger goes down
	public final int HUNGER_SPEED = 100;

	// current status. Ints represent 100.0%
	private int hunger = 10000000, fatigue = 1000;
	private boolean isWorking = false;
	private Point2D.Double currentPosition, targetPosition;
	private Stack<Point> movements;
	// private static Terrain terrain;
	// private static ArrayList<Entity> buildings;
	private Inventory inventory;
	private int tickCount = 0;
	private ArrayList<Tile> passableTiles;
	private Task currentTask;

	// player traits
	private int intelligence, motivation, speed, strength;

	// Magic Numbers
	private final int AGENT_HEIGHT = 1;
	private final int AGENT_WIDTH = 1;
	private final int MAX_SEEK_FOOD_HUNGER = 600;
	private final int MAX_SEEK_REST_FATIGUE = 500;
	private final int MAX_WORKING_HUNGER = 250; // 25.0%
	private final int MAX_WORKING_FATIGUE = 200; // 20.0%

	/*
	 * for testing:
	 */
	boolean buildBuilding = false;
	Point buildingPosition;

	public Agent(Point currentPosition) {
		MY_ID = currentId++;
		movements = new Stack<Point>();

		this.currentPosition = new Point2D.Double((double) currentPosition.x,
				(double) currentPosition.y);
		targetPosition = this.currentPosition;
		inventory = new Inventory(CAPACITY);

		passableTiles = new ArrayList<Tile>();
		passableTiles.add(Tile.Sky);
		passableTiles.add(Tile.Wood);
		passableTiles.add(Tile.Leaves);
		currentTask = null;
	}

	public void update() {
		// update stats
		if (tickCount % HUNGER_SPEED == 0) {
			if (isWorking) {
				hunger -= 6;
				fatigue -= 3;
			} else {
				hunger -= 1;
				fatigue -= 1;
			}
		}
		tickCount++;
		// System.out.println(hunger);

		// die if hunger < 0
		if (hunger < 0) {
			currentTask = (new AgentDeath(this, new Point(
					(int) currentPosition.x, (int) currentPosition.y)));
			currentTask.execute();
		}

		if (movements.isEmpty()) {
			if (hunger < MAX_SEEK_FOOD_HUNGER
					&& sameLocation(currentPosition, targetPosition)) {
				// TODO: maybe throw a random number generator in here someday

				// if agent is in same place as building, get some food
				getResourceFromBuildingAtCurrentLocation(Resource.FOOD);

				// if agent has food, eat some:
				if (inventory.getAmount(Resource.FOOD) > 0) {
					inventory.changeAmount(Resource.FOOD, -1);
					hunger += 10;
				} else {
					// if agent doesn't have food, look for building with food.
					setPathToBuildingWithType(Resource.FOOD);
				}
				
				// if no food buildings around, gather food
				if (movements.isEmpty() && TaskList.getList().isEmpty()) {
					TaskList.addTask(new HarvestTreeTask(this,
							findNearestTree(), World.terrain));
				}

			}
			if (!TaskList.getList().isEmpty() && currentTask == null) {
				currentTask = TaskList.getList().poll();
				if (currentTask != null
						&& !sameLocation(
								new Point2D.Double(currentTask.getPos().x,
										currentTask.getPos().y),
								currentPosition)) {
					System.out.println("GoHere");
					goHere(currentTask.getPos());
				}
			}
		}

		if (currentTask != null
				&& sameLocation(
						currentPosition,
						new Point2D.Double(currentTask.getPos().x, currentTask
								.getPos().y))) {
			System.out.println("execute");
			currentTask.execute();
			currentTask = null;
		}

		// build building code *not fully set yet
		// if (buildBuilding) {
		// goHere(buildingPosition);
		// buildBuilding = false;
		// }
		//
		// if (sameLocation(currentPosition, new Point2D.Double(
		// buildingPosition.x, buildingPosition.y))) {
		// Building building = new Farm(buildingPosition);
		// buildings.add(building);
		// }

		moveIncremental();

	}

	private void setPathToBuildingWithType(Resource r) {
		Stack<Point> shortestPath = null;
		Building closestFoodBuilding = null;
		boolean nullPath = true;
		for (int i = 0; i < World.buildings.size(); i++) {
			// find shortest path to building with food
			if (((Building) World.buildings.get(i)).getInventory().getAmount(r) > 0) {
				PathFinder thePath = new PathFinder(new Point(
						(int) currentPosition.getX(),
						(int) currentPosition.getY()), (Point) World.buildings
						.get(i).getPos(), World.terrain, passableTiles);
				if (nullPath || thePath.getPath().size() > shortestPath.size()) {
					nullPath = false;
					shortestPath = thePath.getPath();
					closestFoodBuilding = (Building) World.buildings.get(i);
				}
			}
		}

		if (shortestPath != null) {
			goHere(closestFoodBuilding.getPos());
		}
	}

	private void getResourceFromBuildingAtCurrentLocation(Resource r) {
		for (int i = 0; i < World.buildings.size(); i++) {
			if (((Building) World.buildings.get(i)).getInventory().getAmount(r) > 0
					&& sameLocation(currentPosition, new Point2D.Double(
							World.buildings.get(i).getPos().getX(),
							World.buildings.get(i).getPos().getY()))) {
				// if it gets in here, the agent is on a food building
				((Building) World.buildings.get(i)).getInventory()
						.changeAmount(r, -2);
				inventory.changeAmount(r, 2);
			}
		}
	}

	private void moveIncremental() {
		// assigns movement to the next point if it exists and the entity has
		// finished movement to the current tile
		if (sameLocation(currentPosition, targetPosition)) {
			if (!movements.isEmpty()) {
				Point move = movements.pop();
				targetPosition = new Point2D.Double((double) move.x,
						(double) move.y);
			}
		} else {

			// here will go the more advanced programing for little hops, etc.
			// For now, it is all straight lines between tiles
			double dx = 0;
			double dy = 0;
			if (targetPosition.getX() - currentPosition.getX() > .1) {
				dx = SPEED;
			}
			if (targetPosition.getX() - currentPosition.getX() < -.1) {
				dx = -SPEED;
			}
			if (targetPosition.getY() - currentPosition.getY() > .1) {
				dy = SPEED;
			}
			if (targetPosition.getY() - currentPosition.getY() < -.1) {
				dy = -SPEED;
			}

			if (Math.abs(currentPosition.getX() - targetPosition.getX()) > .1) {
				currentPosition.setLocation(
						(double) (currentPosition.getX() + dx),
						(double) currentPosition.getY());
			}

			if (Math.abs(currentPosition.getY() - targetPosition.getY()) > .1) {
				currentPosition.setLocation((double) (currentPosition.getX()),
						(double) (currentPosition.getY() + dy));
			}
		}
	}

	private boolean sameLocation(Point2D.Double a, Point2D.Double b) {
		return (Math.abs(a.getY() - b.getY()) < .1 && Math.abs(a.getX()
				- b.getX()) < .1);
	}

	// this will be what the agent uses to get the path to its next destination.
	// should develop it out later to take in a building or something similar as
	// an arg.
	public boolean goHere(Point destination) {
		PathFinder thePath = new PathFinder(new Point(
				(int) currentPosition.getX(), (int) currentPosition.getY()),
				destination, World.terrain, passableTiles);
		movements = thePath.getPath();

		if (movements.isEmpty()) {
			return false;
		}
		return true;
	}

	public Point findNearestTree() {
		if (currentPosition.equals(Tile.Wood)
				|| currentPosition.equals(Tile.Leaves)) {
			return new Point((int) currentPosition.x, (int) currentPosition.y);
		}
		for (int i = 0; i < 20; i++) {
			if (i + (int) currentPosition.x > 0
					&& i + (int) currentPosition.x < World.terrain
							.getMapWidth()
					&& (World.terrain.getTile(
							i + (int) currentPosition.x,
							World.terrain.getAltitude(i
									+ (int) currentPosition.x) - 1).equals(
							Tile.Wood) || World.terrain.getTile(
							i + (int) currentPosition.x,
							World.terrain.getAltitude(i
									+ (int) currentPosition.x) - 1).equals(
							Tile.Leaves))
					&& !World.terrain.getTile(
							i + (int) currentPosition.x,
							World.terrain.getAltitude(i
									+ (int) currentPosition.x)).equals(
							Tile.Wood)
					&& !World.terrain.getTile(
							i + (int) currentPosition.x,
							World.terrain.getAltitude(i
									+ (int) currentPosition.x)).equals(
							Tile.Leaves)) {
				
				System.out.println("Found tree");
				return new Point(i + (int) currentPosition.x,
						World.terrain.getAltitude(i + (int) currentPosition.x) - 1);
				
			}
			if (-i + (int) currentPosition.x > 0
					&& -i + (int) currentPosition.x < World.terrain
							.getMapWidth()
					&& (World.terrain.getTile(
							-i + (int) currentPosition.x,
							World.terrain.getAltitude(-i
									+ (int) currentPosition.x) - 1).equals(
							Tile.Wood) || World.terrain.getTile(
							-i + (int) currentPosition.x,
							World.terrain.getAltitude(-i
									+ (int) currentPosition.x) - 1).equals(
							Tile.Leaves))
					&& !World.terrain.getTile(
							-i + (int) currentPosition.x,
							World.terrain.getAltitude(-i
									+ (int) currentPosition.x)).equals(
							Tile.Wood)
					&& !World.terrain.getTile(
							-i + (int) currentPosition.x,
							World.terrain.getAltitude(-i
									+ (int) currentPosition.x)).equals(
							Tile.Leaves)) {
				System.out.println("Found tree");
				return new Point(-i + (int) currentPosition.x,
						World.terrain.getAltitude(-i + (int) currentPosition.x) - 1);
			}
		}
		// more complicated version in case we have trees underground. Not
		// finished
		// for (int i = 0; i < 20; i++) {
		// for (int j = 0; j < 20; j++) {
		// if (i + (int) currentPosition.x > 0
		// && j + (int) currentPosition.y > 0
		// && i + (int) currentPosition.x < World.terrain
		// .getMapWidth()
		// && j + (int) currentPosition.y < World.terrain
		// .getMapWidth()
		// && (World.terrain.getTile(i + (int) currentPosition.x,
		// j + (int) currentPosition.y).equals(Tile.Wood) || World.terrain
		// .getTile(i + (int) currentPosition.x,
		// j + (int) currentPosition.y).equals(
		// Tile.Leaves))
		// && !World.terrain.getTile(i + (int) currentPosition.x,
		// j + (int) currentPosition.y + 1).equals(
		// Tile.Wood)
		// && !World.terrain.getTile(i + (int) currentPosition.x,
		// j + (int) currentPosition.y + 1).equals(
		// Tile.Leaves)) {
		// // return new Point();
		// }
		// }
		// }
		return null;
	}

	public Point2D getPos() {
		return currentPosition;
	}

	public Dimension getSize() {
		return new Dimension(AGENT_WIDTH, AGENT_HEIGHT);
	}

	public Inventory getInventory() {
		return inventory;
	}

	/*
	 * For testing: ability to set some stats
	 */
	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public Stack<Point> getPath() {
		return movements;
	}

	public void setBuild(boolean buildBuilding, Point position) {
		this.buildBuilding = buildBuilding;
		this.buildingPosition = position;
	}
}
