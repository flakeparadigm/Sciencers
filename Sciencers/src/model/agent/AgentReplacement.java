package model.agent;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Stack;

import view.Tile;
import model.Entity;
import model.PathFinder;
import model.World;
import model.building.Building;
import model.inventory.Inventory;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.HarvestTreeTask;
import model.task.Task;
import model.task.TaskList;

public abstract class AgentReplacement implements Entity {

	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	public final double SPEED = .1;
	public final int CAPACITY = 100;
	// every 100 ticks, hunger goes down
	public final int HUNGER_SPEED = 100;
	private final double GRAVITY_CONSTANT = .0981;
	protected int SEEK_FOOD_HUNGER = 800;

	protected int hunger = 1000;
	protected int fatigue = 1000;
	boolean isWorking = false;
	private double jumpVelocity = -.6;

	protected int tickCount;
	protected Point2D.Double currentPosition;
	protected Stack<Point> movements;
	protected Task currentTask;
	protected int taskTimer;

	private Inventory inventory;
	private ArrayList<Tile> passableTiles;
	public Tool workingTool = null;
	private Tool mainTool = null, secondaryTool = null;

	private int jumpTick = 0;

	public AgentReplacement(Point currentPosition) {
		MY_ID = currentId++;

		passableTiles = new ArrayList<Tile>();
		passableTiles.add(Tile.Sky);
		passableTiles.add(Tile.Wood);
		passableTiles.add(Tile.Leaves);

		inventory = new Inventory(CAPACITY);

		this.currentPosition = new Point2D.Double((double) currentPosition.x,
				(double) currentPosition.y);
		currentTask = null;
		taskTimer = 0;
		movements = new Stack<Point>();

	}

	public abstract void update();

	protected void executeCurrentTask() {
		if (currentTask != null) {
			if (currentTask.getPos() == null) {
				System.out.println("Null Task Position!");
			}
			// if current task exists:
			if (movements.isEmpty()
					&& currentTask.getPos() != null
					&& !sameLocation(currentPosition, new Point2D.Double(
							currentTask.getPos().getX(), currentTask.getPos()
									.getY()), .1, .1)) {
				// if movements needs updated:
				movements = goHere(currentPosition, currentTask.getPos());
			}

			if (sameLocation(currentPosition, new Point2D.Double(currentTask
					.getPos().getX(), currentTask.getPos().getY()), .1, .1)
					&& taskTimer < 0) {
				// if in location of current task:
				currentTask.execute();
				if (currentTask.equals(TaskList.getList(EAgent.FARMER).peek())) {
					TaskList.getList(EAgent.FARMER).poll();
				}
				if (currentTask.equals(TaskList.getList(EAgent.MINER).peek())){
					TaskList.getList(EAgent.MINER).poll();
				}
				if (currentTask.equals(TaskList.getList(EAgent.GENERIC).peek())) {
					TaskList.getList(EAgent.GENERIC).poll();
				}
				currentTask = null;
			}
		}
	}

	protected void getNextTaskIfNotBusy(EAgent type) {
		if (currentTask == null && !TaskList.getList(type).isEmpty()) {
			currentTask = TaskList.getList(type).peek();
		}

		if (currentTask == null && !TaskList.getList(EAgent.GENERIC).isEmpty()) {
			currentTask = TaskList.getList(EAgent.GENERIC).peek();
		}
	}

	public Point2D getPos() {
		return currentPosition;
	}
	
	public void setPos(Point2D.Double position){
		currentPosition = position;
	}

	protected void updateStats() {
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
		taskTimer--;
	}

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
				dy = GRAVITY_CONSTANT * jumpTick + jumpVelocity;
				jumpTick++;
			}
			// for jumping:
			if (jumpTick == 0
					&& (double) movements.peek().getY()
							- currentPosition.getY() < -.5) {
				jumpTick = 1;
				jumpVelocity = -.6;
			}
			// for falling:
			if (jumpTick == 0
					&& (double) movements.peek().getY()
							- currentPosition.getY() > .1) {
				jumpTick = 1;
				jumpVelocity = 0;
			}

			// safety block for jumping
			if (currentPosition.getY() + dy
					- World.terrain.getAltitude((int) currentPosition.getX()) > .1) {
				System.out.println("Safety Block");
				currentPosition
						.setLocation((double) (currentPosition.getX() + dx),
								(double) (World.terrain
										.getAltitude((int) currentPosition
												.getX()) + 1));
			} else {
				currentPosition.setLocation(
						(double) (currentPosition.getX() + dx),
						(double) (currentPosition.getY() + dy));
			}

			// adjust tolerances to allow correct stopping after jump or fall:
			if (sameLocation(currentPosition,
					new Point2D.Double(movements.peek().x, movements.peek().y),
					2.5, .15)) {
				if (dy > 0) {
					jumpTick = 0;
				}
			}
			// adjust to allow correct switching of target tiles (for jumping
			// and falling and standard)
			if ((double) movements.peek().getY() - currentPosition.getY() < -.1
					&& sameLocation(currentPosition, new Point2D.Double(
							movements.peek().x, movements.peek().y), .1, 1.5)) {
				movements.pop();
			} else if ((double) movements.peek().getY()
					- currentPosition.getY() > .1
					&& sameLocation(currentPosition, new Point2D.Double(
							movements.peek().x, movements.peek().y), 1.5, .1)) {
				movements.pop();
			} else if (sameLocation(currentPosition, new Point2D.Double(
					movements.peek().x, movements.peek().y), .1, .1)) {
				movements.pop();
			}
			System.out.println("NotHere");
		} else {
			System.out.println("Out");
			currentPosition.setLocation(
					(double) (currentPosition.getX()),
					(double) (currentPosition.getY()+SPEED));
		}
	}

	protected Stack<Point> goHere(Point2D.Double currentPosition,
			Point destination) {
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
				if (goHere(
						currentPosition,
						new Point(i + (int) currentPosition.x, World.terrain
								.getAltitude(i + (int) currentPosition.x) - 1)) != null) {
					return new Point(i + (int) currentPosition.x,
							World.terrain.getAltitude(i
									+ (int) currentPosition.x) - 1);
				}

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
				if (goHere(
						currentPosition,
						new Point(-i + (int) currentPosition.x, World.terrain
								.getAltitude(-i + (int) currentPosition.x) - 1)) != null) {
					return new Point(-i + (int) currentPosition.x,
							World.terrain.getAltitude(-i
									+ (int) currentPosition.x) - 1);
				}
			}
		}
		return null;
	}

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

	protected boolean hasTool(Tool t) {
		if (workingTool == t)
			return true;
		else if (mainTool == t)
			return true;
		else if (secondaryTool == t)
			return true;
		else
			return false;
	}

	public void craftTool(Tool t) {
		if (inventory.getAmount(Resource.WOOD) >= 3) {
			if (mainTool == null) {
				inventory.changeAmount(Resource.WOOD, -3);
				mainTool = t;
			} else if (secondaryTool == null) {
				inventory.changeAmount(Resource.WOOD, -3);
				secondaryTool = t;
			} else {
				System.out.println("Inventory full. Tool not crafted.");
			}
		} else {
			(new HarvestTreeTask(this, findNearestTree((Double) getPos()),
					World.terrain)).execute();
			craftTool(t);
		}
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
