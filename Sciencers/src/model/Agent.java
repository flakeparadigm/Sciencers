package model;

import java.awt.Dimension;
import java.awt.Point;
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
	
	// current status. Ints represent 100.0%
	private int hunger = 1000, fatigue = 1000;
	private boolean isWorking = false;
	private Point currentPosition, targetPosition;
	private Stack<Point> movements;
	private static Terrain terrain;
	private static ArrayList<Building> buildings;
	private Inventory inventory;
	
	// player traits
	private int intelligence, motivation, speed, strength;
	
	// misc
	private boolean evenTick = false;
	
	// Magic Numbers
	private final int AGENT_HEIGHT = 1;
	private final int AGENT_WIDTH = 1;
	private final int MAX_SEEK_FOOD_HUNGER = 600;
	private final int MAX_SEEK_REST_FATIGUE = 500;
	private final int MAX_WORKING_HUNGER = 250; // 25.0%
	private final int MAX_WORKING_FATIGUE = 200; // 20.0%
	
	public Agent(Terrain terrain, ArrayList<Building> buildings, Point currentPosition) {
		MY_ID = currentId++;
		movements = new Stack<Point>();
		
		this.terrain = terrain;
		this.currentPosition = currentPosition;
		this.buildings = buildings;
	}
	
	public void update() {
		// update stats
		if(evenTick) {
			if(isWorking) {
				hunger -= 6;
				fatigue -= 3;
			} else {
				hunger -= 1;
				fatigue -= 1;
			}
		}
		
		if(!movements.isEmpty())
			currentPosition = movements.pop();
		
		if (hunger < MAX_SEEK_FOOD_HUNGER){
			//TODO: maybe throw a random number generator in here someday
			//if agent has food, eat some:
			if (inventory.getAmount(Resource.FOOD) > 0){
				inventory.changeAmount(Resource.FOOD, -1);
				hunger += 10;
			}
			//if agent doesn't have food, look for building with food.
			Stack<Point> shortestPath = null;
			for (int i = 0; i<buildings.size(); i++){
				//TODO: find shortest path to a building with food
				if (buildings.get(i).getInventory().getAmount(Resource.FOOD) > 0){
					PathFinder thePath = new PathFinder(currentPosition, buildings.get(i).getPos(), terrain, Tile.Sky);
					if (thePath.getPath().size() > shortestPath.size()){
						shortestPath = thePath.getPath();
					}
				}
			}
			
			if (shortestPath != null){
				movements = shortestPath;
			}
		}
		
		evenTick = !evenTick;
	}
	
	// this will be what the agent uses to get the path to its next destination.
	// should develop it out later to take in a building or something similar as an arg.
	public boolean goHere(Point destination) {
		PathFinder thePath = new PathFinder(currentPosition, destination, terrain, Tile.Sky);
		movements = thePath.getPath();
		if(movements.isEmpty()){
			return false;
		}
		return true;
	}
	
	public Point getPos(){
		return currentPosition;
	}
	
	public Dimension getSize() {
		return new Dimension(AGENT_WIDTH, AGENT_HEIGHT);
	}
}
