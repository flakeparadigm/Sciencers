package model.agent;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import view.Tile;
import model.AlertCollection;
import model.Entity;
import model.PathFinder;
import model.Terrain;
import model.World;
import model.building.Building;
import model.inventory.Inventory;
import model.inventory.Resource;
import model.inventory.Tool;
import model.task.CraftToolTask;
import model.task.GetFromBuilding;
import model.task.HarvestTreeTask;
import model.task.Task;
import model.task.TaskList;

public abstract class Agent implements Entity {

	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	public final double SPEED = .1;
	public final int CAPACITY = 5;
	// every 100 ticks, hunger goes down
	public final int HUNGER_SPEED = 150;
	private final double GRAVITY_CONSTANT = .0581;
	protected final double MAX_JUMP_VELOCITY = -.45;
	protected final int SEEK_FOOD_HUNGER = 40;
	protected final int MAX_FATIGUE = 100;
	//
	protected int hunger = 100;
	protected int fatigue = 0;
	protected int blood = 100;
	boolean isWorking = false;
	private double jumpVelocity = 0;

	protected int tickCount;
	public Point2D.Double currentPosition;
	protected Stack<Point> movements;
	public Task currentTask;
	private boolean rejectedLastTask;
	private boolean personalTask;
	public Stack<Task> tasks;
	protected int taskTimer;
	protected EAgent agentType;
	public Building buildingWorked;

	protected Inventory inventory;
	protected ArrayList<Tile> passableTiles;
	public Tool workingTool = null;
	public Tool mainTool = null, secondaryTool = null;
	public Resource priorityResource;

	protected boolean dead = false;

	protected int jumpTick = 0;

	protected Random rand = new Random();

	public Agent(Point currentPosition) {
		MY_ID = currentId++;
		priorityResource = Resource.FOOD;
		passableTiles = World.terrain.passableTiles;
		inventory = new Inventory(CAPACITY, priorityResource);
		this.currentPosition = new Point2D.Double((double) currentPosition.x,
				(double) currentPosition.y);
		currentTask = null;
		taskTimer = 0;
		movements = new Stack<Point>();
		tasks = new Stack<Task>();

		if(!(this instanceof MinerAgent))
		tasks.push(new CraftToolTask(Tool.SWORD, this, new Point(
				(int) currentPosition.getX(),
				(int) currentPosition.getY())));
	}

	public abstract void update();

	protected void executeCurrentTask() {
		boolean setNull = false;
		if (currentTask != null) {
			if (currentTask.getPos() == null) {
				System.out.println("Null Task Position!");
			}
			// if current task exists
			if (movements.isEmpty()
					&& currentTask.getPos() != null
					&& !sameLocation(currentPosition, new Point2D.Double(
							currentTask.getPos().getX(), currentTask.getPos()
									.getY()), .1, .1)) {
				// if movements needs updated:
				movements = goHere(currentPosition, currentTask.getPos());
				if (movements.isEmpty() && currentTask != null) {
					if (!rejectedLastTask) {
						// TaskList.addTask(currentTask, agentType);
					} else {
						setNull = true;
						System.out.println("Trash task");
					}

					rejectedLastTask = true;
				} else {
					rejectedLastTask = false;
				}
			}

			// System.out.println("cur" + currentPosition);
			// System.out.println("task" + new Point2D.Double(currentTask
			// .getPos().getX(), currentTask.getPos().getY()));
			if (sameLocation(currentPosition, new Point2D.Double(currentTask
					.getPos().getX(), currentTask.getPos().getY()), .1, .1)
					&& taskTimer < 0) {
				// if in location of current task:
				// System.out.println("execute the task");
				currentTask.execute();
				setNull = true;
			}
		}
		if (setNull) {
			currentTask = null;
		}

		getNextTaskIfNotBusy(agentType);
	}

	protected void getNextTaskIfNotBusy(EAgent type) {
		if (currentTask == null && tasks.size() != 0) {
			currentTask = tasks.pop();
			personalTask = true;
			taskTimer = 10;
		}
		// if (!TaskList.getList(type).isEmpty()) {
		// System.out.println(TaskList.getList(type).peek()
		// .equals(lastTaskRejected));
		// }
		if (currentTask == null && !TaskList.getList(type).isEmpty()) {
			currentTask = TaskList.getList(type).poll();
			personalTask = false;
			taskTimer = 10;
		}

		if (currentTask == null && !TaskList.getList(EAgent.GENERIC).isEmpty()) {
			currentTask = TaskList.getList(EAgent.GENERIC).poll();
			personalTask = false;
			taskTimer = 10;
		}
	}

	public Point2D getPos() {
		return currentPosition;
	}

	public void setPos(Point2D.Double position) {
		currentPosition = position;
	}

	protected void updateStats() {
		// new agent death using flag variable

		if (blood <= 0) {
			dead = true;
			AlertCollection.addAlert("An agent has died!");
			return;
		}

		if (hunger < 0.5 * SEEK_FOOD_HUNGER) {
			AlertCollection.addAlert("An agent is starving! D:");
		}

		if (tickCount % HUNGER_SPEED == 0) {
			if (fatigue >= MAX_FATIGUE) {
				blood -= 6;
				hunger -= 6;
			} else if (hunger <= 0) {
				blood -= 6;
				fatigue += 5;
			} else if (hunger <= 10 || fatigue >= MAX_FATIGUE) {
				blood--;
				hunger -= 6;
				fatigue += 3;
			} else if (isWorking) {
				hunger -= 3;
				fatigue += 3;
			} else {
				hunger -= 1;
				fatigue += 1;
			}
		}
		tickCount++;
		taskTimer--;

		if (taskTimer < -500) {
			resetAgent();
			taskTimer = 0;
		}

		if (!isWorking)
			workingTool = null;

		if (!(this instanceof RogueAgent)) {

			for (Entity e : World.agents) {
				boolean nearby = (Math.abs(e.getPos().getX()
						- currentPosition.getX()) <= 1)
						&& (Math.abs(e.getPos().getY() - currentPosition.getY()) <= 1);

				if (nearby && e instanceof RogueAgent) {

					if (hasTool(Tool.SWORD)) {
						attack((Agent) e, Math.max(rand.nextInt(7) - 3, 0));
					} else {
						attack((Agent) e, Math.max(rand.nextInt(4) - 2, 0));
					}
				}

			}
		}
	}

	protected void resetAgent() {
		currentPosition.setLocation((double) (currentPosition.getX() + 1),
				(double) (World.terrain
						.getAltitude(getCurrentX(currentPosition) + 1) - 1));
		currentTask = null;
		movements.removeAllElements();
		tasks.removeAllElements();
		tickCount = 0;
		rejectedLastTask = false;
		personalTask = false;
		isWorking = false;
		jumpTick = 0;
	}

	protected void updateMovement(Point2D.Double currentPosition,
			Stack<Point> movements) {
		double dx = 0;
		double dy = 0;
		if (!movements.isEmpty()) {
			if ((double) movements.peek().getX() - currentPosition.getX() > .05) {
				dx = SPEED;
			}
			if ((double) movements.peek().getX() - currentPosition.getX() < -.05) {
				dx = -SPEED;
			}

			if (jumpTick != 0) {
				dy = (GRAVITY_CONSTANT * jumpTick) + jumpVelocity;
				jumpTick++;
			}
			// for jumping:
			if (jumpTick == 0
					&& (double) movements.peek().getY()
							- currentPosition.getY() < -.1
					&& passableDirectlyAbove(currentPosition)) {
				jumpTick = 1;
				jumpVelocity = MAX_JUMP_VELOCITY;
			}
			// for falling:
			if (jumpTick == 0
					&& (double) movements.peek().getY()
							- currentPosition.getY() > .1
					&& passableDirectlyBelow(currentPosition)) {
				jumpTick = 1;
				jumpVelocity = 0;
			}

			// for stopping jumpTick
			if (jumpTick != 0
					&& dy > 0
					&& (double) movements.peek().getY()
							- currentPosition.getY() < .1) {
				jumpTick = 0;
				dy = 0;
				currentPosition.setLocation((double) (currentPosition.getX()),
						(double) (movements.peek().getY()));
			}

			// set to fall if above nothing:
			if (jumpTick == 0 && passableDirectlyBelow(currentPosition)) {
				jumpTick = 1;
				jumpVelocity = 0;
			}

			if (onLadder(currentPosition)
					&& (double) movements.peek().getY()
							- currentPosition.getY() < .1) {
				dy = -SPEED;
				jumpTick = 0;
			}

			if (onLadder(currentPosition)
					&& (double) movements.peek().getY()
							- currentPosition.getY() > -.1) {
				dy = SPEED;
				jumpTick = 0;
			}

			// change position
			currentPosition.setLocation((double) (currentPosition.getX() + dx),
					(double) (currentPosition.getY() + dy));

			if (sameLocation(currentPosition,
					new Point2D.Double(movements.peek().x, movements.peek().y),
					.1, .1)) {
				movements.pop();
			}

			// if (onLadder(currentPosition)
			// || (World.terrain.getTile(getCurrentX(currentPosition),
			// getCurrentY(currentPosition) + 1).equals(
			// Tile.Ladder) && !sameLocation(currentPosition,
			// new Point2D.Double(movements.peek().getX(),
			// movements.peek().getY()), .1, .1))) {
			// jumpTick = 0;
			// if ((double) movements.peek().getY() - currentPosition.getY() <
			// -.1) {
			// dy = -SPEED / 2;
			// // System.out.println("Going up");
			// } else if ((double) movements.peek().getY()
			// - currentPosition.getY() > .05) {
			// dy = SPEED / 2;
			// // System.out.println("Going down");
			// }
			// currentPosition.setLocation((double) (currentPosition.getX()),
			// (double) (currentPosition.getY() + dy));
			// if (Math.abs(movements.peek().getY() - currentPosition.getY()) <
			// .1
			// && !onLadder(currentPosition)) {
			// currentPosition.setLocation(
			// (double) (currentPosition.getX() + dx),
			// (double) (currentPosition.getY()));
			// }
			// if (Math.abs(movements.peek().getY() - currentPosition.getY()) <
			// .15
			// && passableTiles.contains(World.terrain.getTile(
			// getCurrentX(currentPosition) + 1,
			// getCurrentY(currentPosition) + 1))) {
			// currentPosition.setLocation(
			// (double) (currentPosition.getX() + dx),
			// (double) (currentPosition.getY()));
			// }
			// } else if (currentPosition.getY()
			// + dy
			// - World.terrain.getDeepestPassable((int) Math
			// .round(currentPosition.getX())) > .1) {
			// System.out.println("Safety Block");
			// // safety block for jumping
			// System.out.println("Variable dy used for jumping:" + dy);
			// System.out
			// .println("Reseting to altitude: "
			// + World.terrain
			// .getDeepestPassable(getCurrentX(currentPosition)
			// + (int) dx));
			// jumpTick = 0;
			// currentPosition
			// .setLocation(
			// (double) (currentPosition.getX() + dx),
			// (double) (World.terrain
			// .getDeepestPassable(getCurrentX(currentPosition)
			// + (int) dx)));
			// } else {
			// currentPosition.setLocation(
			// (double) (currentPosition.getX() + dx),
			// (double) (currentPosition.getY() + dy));
			// }
			//
			// // adjust tolerances to allow correct stopping after jump or
			// fall: if (sameLocation(currentPosition, new Point2D.Double(
			// movements.peek().x, movements.peek().y), 2.5, .15)) {
			// if (dy > 0) {
			// jumpTick = 0;
			// }
			// }

		}
	}

	private boolean passableDirectlyAbove(Point2D.Double currentPosition) {
		return passableTiles.contains(World.terrain.getTile(
				(int) Math.round(currentPosition.getX()),
				(int) Math.round(currentPosition.getY() - 1)));
	}

	private boolean passableDirectlyBelow(Point2D.Double currentPosition) {
		return passableTiles.contains(World.terrain.getTile(
				(int) Math.round(currentPosition.getX()),
				(int) Math.round(currentPosition.getY() + 1)));
	}

	private boolean onLadder(Point2D.Double currentPosition) {
		return World.terrain.getTile(getCurrentX(currentPosition),
				getCurrentY(currentPosition)).equals(Tile.Ladder);
	}

	public Stack<Point> goHere(Point2D.Double currentPosition, Point destination) {
		Stack<Point> movements = new Stack<Point>();
		PathFinder thePath = new PathFinder(new Point(
				getCurrentX(currentPosition), getCurrentY(currentPosition)),
				destination, World.terrain, passableTiles);
		movements = thePath.getPath();

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
						getCurrentX(currentPosition),
						getCurrentY(currentPosition)), (Point) World.buildings
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

	protected void getFoodIfNecessary() {
		if (hunger < SEEK_FOOD_HUNGER && currentTask == null) {
			// if (findNearestTree(currentPosition) != null) {
			// currentTask = new HarvestTreeTask(this,
			// findNearestTree(currentPosition), World.terrain);
			// taskTimer = 10;
			// }
			if (findClosestBuildingWithType(currentPosition, currentPosition,
					Resource.FOOD) != null) {
				Building foodBuilding = findClosestBuildingWithType(
						currentPosition, currentPosition, Resource.FOOD);
				currentTask = new GetFromBuilding(this, foodBuilding,
						Resource.FOOD, 1);
				hunger += 5;
				if (foodBuilding.getInventory().getAmount(Resource.FOOD) > 10) {
					foodBuilding.getInventory().changeAmount(Resource.FOOD, -1);
				}
			}
		}
	}

	protected Point findNearestTree(Point2D.Double currentPosition) {
		if (currentPosition.equals(Tile.Wood)
				|| currentPosition.equals(Tile.Leaves)) {
			return new Point(getCurrentX(currentPosition),
					getCurrentY(currentPosition));
		}
		for (int i = 0; i < 40; i++) {
			if (i + getCurrentX(currentPosition) > 0
					&& i + getCurrentX(currentPosition) < World.terrain
							.getMapWidth()
					&& (World.terrain.getTile(
							i + getCurrentX(currentPosition),
							World.terrain.getAltitude(i
									+ getCurrentX(currentPosition)) - 1)
							.equals(Tile.Wood) || World.terrain.getTile(
							i + getCurrentX(currentPosition),
							World.terrain.getAltitude(i
									+ getCurrentX(currentPosition)) - 1)
							.equals(Tile.Leaves))
					&& !passableTiles.contains(World.terrain.getTile(
							i + getCurrentX(currentPosition),
							World.terrain.getAltitude(i
									+ getCurrentX(currentPosition))))) {
				if (goHere(
						currentPosition,
						new Point(i + getCurrentX(currentPosition),
								World.terrain.getAltitude(i
										+ getCurrentX(currentPosition)) - 1))
						.size() > 0) {
					return new Point(i + getCurrentX(currentPosition),
							World.terrain.getAltitude(i
									+ getCurrentX(currentPosition)) - 1);
				}

			}
			if (-i + getCurrentX(currentPosition) > 0
					&& -i + getCurrentX(currentPosition) < World.terrain
							.getMapWidth()
					&& (World.terrain.getTile(
							-i + getCurrentX(currentPosition),
							World.terrain.getAltitude(-i
									+ getCurrentX(currentPosition)) - 1)
							.equals(Tile.Wood) || World.terrain.getTile(
							-i + getCurrentX(currentPosition),
							World.terrain.getAltitude(-i
									+ getCurrentX(currentPosition)) - 1)
							.equals(Tile.Leaves)
							&& !passableTiles.contains(World.terrain.getTile(
									-i + getCurrentX(currentPosition),
									World.terrain.getAltitude(-i
											+ getCurrentX(currentPosition)))))) {
				if (goHere(
						currentPosition,
						new Point(-i + getCurrentX(currentPosition),
								World.terrain.getAltitude(-i
										+ getCurrentX(currentPosition)) - 1))
						.size() > 0) {
					return new Point(-i + getCurrentX(currentPosition),
							World.terrain.getAltitude(-i
									+ getCurrentX(currentPosition)) - 1);
				}
			}
		}
		return null;
	}

	public int getCurrentY(Point2D.Double currentPosition) {
		return (int) Math.floor(currentPosition.y);
	}

	public int getCurrentX(Point2D.Double currentPosition) {
		return (int) Math.round(currentPosition.x);
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
				System.out.println("Primary Tool: "+t);
			} else if (secondaryTool == null) {
				inventory.changeAmount(Resource.WOOD, -3);
				secondaryTool = t;
				System.out.println("Secondary Tool: "+t);
			} else {
				System.out.println("Inventory full. Tool not crafted.");
			}
		} else {
			currentTask = new HarvestTreeTask(this, findNearestTree((Double) getPos()),
					World.terrain);
		}
	}

	public ArrayList<Tile> getPassableTiles() {
		return passableTiles;
	}

	// for testing
	public void setHunger(int i) {
		hunger = i;
	}

	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return inventory;
	}

	public EAgent getType() {
		return agentType;
	}

	public void giveItem(Tool tool) { // AUTHOR: JAKE
		inventory.changeAmount(tool, 1);
		// TODO handle overfilling inventory
	}

	public void attack(Agent target, int damage) {
		target.hurt(damage);
	}

	private void hurt(int damage) {
		blood -= damage;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead() {
		dead = true;
	}

	public boolean randomProb(int probability) {
		int Low = 0;
		int High = probability;
		int R = rand.nextInt(High - Low) + Low;
		if (R == 0) {
			return true;
		}
		return false;
	}

	public abstract String getUserFriendlyName();

	public int getHunger() {
		// TODO Auto-generated method stub
		return hunger;
	}

	public int getFatigue() {
		// TODO Auto-generated method stub
		return fatigue;
	}

	public int getBlood() {
		return blood;
	}

}
