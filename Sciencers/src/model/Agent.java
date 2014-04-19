package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Stack;

import model.building.Building;
import model.inventory.Inventory;
import model.inventory.Resource;
import view.Tile;

public class Agent implements Entity {

	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	// making the speed too large can cause the agent to jitter (it is missing
	// the range to know it has hit a tile)
	public final double SPEED = .05;
	public final int CAPACITY = 100;
	// every 100 ticks, hunger goes down
	public final int HUNGER_SPEED = 100;

	// current status. Ints represent 100.0%
	private int hunger = 10000000, fatigue = 1000;
	private boolean isWorking = false;
	private Point2D.Double currentPosition, targetPosition;
	private Stack<Point> movements;
	private static Terrain terrain;
	private static ArrayList<Entity> buildings;
	private Inventory inventory;
	private int tickCount = 0;

	// player traits
	private int intelligence, motivation, speed, strength;

	// Magic Numbers
	private final int AGENT_HEIGHT = 1;
	private final int AGENT_WIDTH = 1;
	private final int MAX_SEEK_FOOD_HUNGER = 600;
	private final int MAX_SEEK_REST_FATIGUE = 500;
	private final int MAX_WORKING_HUNGER = 250; // 25.0%
	private final int MAX_WORKING_FATIGUE = 200; // 20.0%

	public Agent(Terrain terrain, ArrayList<Entity> buildings,
			Point currentPosition) {
		MY_ID = currentId++;
		movements = new Stack<Point>();

		this.terrain = terrain;
		this.currentPosition = new Point2D.Double((double) currentPosition.x,
				(double) currentPosition.y);
		this.buildings = buildings;
		targetPosition = this.currentPosition;
		inventory = new Inventory(CAPACITY);
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

		// assigns movement to the next point if it exists and the entity has
		// finished movement to the current tile
		if (Math.abs(currentPosition.getX() - targetPosition.getX()) < .1
				&& Math.abs(currentPosition.getY() - targetPosition.getY()) < .1) {
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
		if (hunger < MAX_SEEK_FOOD_HUNGER && movements.isEmpty()) {
			// TODO: maybe throw a random number generator in here someday

			// if agent is in same place as building, get some food
			for (int i = 0; i < buildings.size(); i++) {
				// TODO: find shortest path to a building with food
				System.out.println("Xa:"+currentPosition.getX());
				System.out.println(buildings.get(0).getPos().getX());
				System.out.println("Ya:"+currentPosition.getY());
				System.out.println(buildings.get(0).getPos().getY());

				System.out.println("x: "+Math.abs(currentPosition.getX()
						- buildings.get(i).getPos().getX()));
				System.out.println("Y: "+Math.abs(currentPosition.getY()
						- buildings.get(i).getPos().getY()));
				if (((Building) buildings.get(i)).getInventory().getAmount(
						Resource.FOOD) > 0
						&& Math.abs(currentPosition.getX()
								- buildings.get(i).getPos().getX()) < .1
						&& Math.abs(currentPosition.getY()
								- buildings.get(i).getPos().getY()) < .1) {
					// if it gets in here, the agent is on a food building
					System.out.println("ONFOOD");
					((Building) buildings.get(i)).getInventory().changeAmount(
							Resource.FOOD, -2);
					inventory.changeAmount(Resource.FOOD, 2);
					targetPosition = currentPosition;
				}

			}
			// if agent has food, eat some:
			if (inventory.getAmount(Resource.FOOD) > 0) {
				inventory.changeAmount(Resource.FOOD, -1);
				hunger += 10;
			} else {
				// if agent doesn't have food, look for building with food.
				Stack<Point> shortestPath = null;
				boolean nullPath = true;
				for (int i = 0; i < buildings.size(); i++) {
					// TODO: find shortest path to a building with food
					if (((Building) buildings.get(i)).getInventory().getAmount(
							Resource.FOOD) > 0) {
						PathFinder thePath = new PathFinder(new Point(
								(int) currentPosition.getX(),
								(int) currentPosition.getY()),
								(Point) buildings.get(i).getPos(), terrain,
								Tile.Sky);
						if (nullPath
								|| thePath.getPath().size() > shortestPath
										.size()) {
							nullPath = false;
							shortestPath = thePath.getPath();
						}
					}
				}

				if (shortestPath != null) {
					movements = shortestPath;
				}

			}
		}
	}

	// this will be what the agent uses to get the path to its next destination.
	// should develop it out later to take in a building or something similar as
	// an arg.
	public boolean goHere(Point destination) {
		// PathFinder thePath = new PathFinder((Point) currentPosition,
		// destination, terrain, Tile.Sky);
		// movements = thePath.getPath();
		// if (movements.isEmpty()) {
		// return false;
		// }
		return true;
	}

	public Point2D getPos() {
		return currentPosition;
	}

	public Dimension getSize() {
		return new Dimension(AGENT_WIDTH, AGENT_HEIGHT);
	}

	/*
	 * For testing: ability to set some stats
	 */
	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
}
