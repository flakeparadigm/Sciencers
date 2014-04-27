package model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;

import view.Tile;
import model.building.Building;
import model.inventory.Inventory;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.Task;

public abstract class AgentReplacement implements Entity {

	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	public final double SPEED = .1;
	public final int CAPACITY = 100;
	// every 100 ticks, hunger goes down
	public final int HUNGER_SPEED = 100;
	protected int hunger = 1000;
	protected int fatigue = 1000;
	boolean isWorking = false;
	private final double INITIAL_JUMP_VELOCITY = -.6;
	private final double GRAVITY_CONSTANT = .0981;

	private Inventory inventory;
	private ArrayList<Tile> passableTiles;
	public Tool workingTool = null;
	private Tool mainTool = null, secondaryTool = null;

	private int jumpTick = 0;

	public AgentReplacement() {
		MY_ID = currentId++;

		passableTiles = new ArrayList<Tile>();
		passableTiles.add(Tile.Sky);
		passableTiles.add(Tile.Wood);
		passableTiles.add(Tile.Leaves);

		inventory = new Inventory(CAPACITY);

	}

	public abstract void update();

	protected void updateMovement(Point2D.Double currentPosition,
			Stack<Point> movements) {
		double dx = 0;
		double dy = 0;
		if (!movements.isEmpty()) {
			if ((double) movements.peek().getX() - currentPosition.getX() > .1) {
				dx = SPEED;
			}
			if ((double) movements.peek().getX() - currentPosition.getX() < -.1) {
				dx = -SPEED;
			}

			if (jumpTick != 0) {
				dy = GRAVITY_CONSTANT * jumpTick + INITIAL_JUMP_VELOCITY;
				System.out.println(dy);
				jumpTick++;
			}

			if (jumpTick == 0
					&& (double) movements.peek().getY() - currentPosition.getY() < -.1) {
				System.out.println("jump");
				jumpTick = 1;
			}

			currentPosition.setLocation((double) (currentPosition.getX() + dx),
					(double) (currentPosition.getY() + dy));

			// adjust tolerances to allow correct stopping after jump:
			if (sameLocation(currentPosition,
					new Point2D.Double(movements.peek().x, movements.peek().y),
					.3, .1)) {
				if (dy > 0) {
					jumpTick = 0;
					System.out.println("End jump");
				}
			}
			// adjust to allow correct switching of target tiles
			if (sameLocation(currentPosition,
					new Point2D.Double(movements.peek().x, movements.peek().y),
					.1, 1.5)) {
				System.out.println(movements.peek());
				movements.pop();
				System.out.println("POP!");
			}
		}
	}

	protected Stack<Point> goHere(Point2D.Double currentPosition, Point destination) {
		Stack<Point> movements = new Stack<Point>();
		PathFinder thePath = new PathFinder(new Point(
				(int) currentPosition.getX(), (int) currentPosition.getY()),
				destination, World.terrain, passableTiles);
		movements = thePath.getPath();

		if (movements.isEmpty()) {
			return null;
		}
		return movements;
	}

	protected Building findClosestBuildingWithType(
			Point2D.Double currentPosition, Point2D.Double targetPosition,
			Resource r) {
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
			// goHere(closestFoodBuilding.getPos());
			return closestFoodBuilding;
		}
		return null;
	}

	protected Point findNearestTree(Point2D.Double currentPosition) {
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

				return new Point(
						i + (int) currentPosition.x,
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
				return new Point(
						-i + (int) currentPosition.x,
						World.terrain.getAltitude(-i + (int) currentPosition.x) - 1);
			}
		}
		return null;
	}

	public abstract Point2D getPos();

	@Override
	public Dimension getSize() {
		// Not sure why this is necessary for entity. It doesn't make much sense
		// for the agent
		return null;
	}

	protected boolean sameLocation(Point2D.Double a, Point2D.Double b,
			double xTolerance, double yTolerance) {
		return (Math.abs(a.getY() - b.getY()) < yTolerance && Math.abs(a.getX()
				- b.getX()) < xTolerance);
	}

	private boolean hasTool(Tool t) {
		if (workingTool == t)
			return true;
		else if (mainTool == t)
			return true;
		else if (secondaryTool == t)
			return true;
		else
			return false;
	}

	// for testing
	public void setHunger(int i) {
		hunger = i;
	}

	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inventory;
	}

}
