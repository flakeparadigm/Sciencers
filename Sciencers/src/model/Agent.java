package model;

import java.awt.Point;
import java.util.Stack;

import view.Tile;

public class Agent extends OurObservable {
	
	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	
	// current status. Ints represent 100.0%
	private int hunger = 1000, fatigue = 1000;
	private boolean isWorking = false;
	private Point currentPosition, targetPosition;
	private Stack<Point> movements;
	private Terrain terrain;
	
	// player traits
	private int intelligence, motivation, speed, strength;
	
	// misc
	private boolean evenTick = false;
	
	// Magic Numbers
	private final int AGENT_HEIGHT = 2;
	private final int MAX_WORKING_HUNGER = 250; // 25.0%
	private final int MAX_WORKING_FATIGUE = 200; // 20.0%
	
	public Agent(Terrain terrain) {
		MY_ID = currentId++;
		this.terrain = terrain;
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
}
