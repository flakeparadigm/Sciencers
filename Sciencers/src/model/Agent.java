package model;

import java.awt.Point;
import java.util.Stack;

import view.Tile;

public class Agent extends OurObservable {
	
	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	
	// current status
	private int hunger, fatigue;
	private Point currentPosition, targetPosition;
	private Stack movements;
	private Terrain terrain;
	
	// player traits
	private int intelligence, motivation, speed, strength;
	
	// Magic Numbers
	private final int AGENT_HEIGHT = 2;
	
	public Agent(Terrain terrain) {
		MY_ID = currentId++;
		this.terrain = terrain;
	}
	
	// this will be what the agent uses to get the path to its next destination.
	// should develop it out later to take in a building or something similar as an arg.
	public boolean goHere(Point destination) {
		PathFinder thePath = new PathFinder(currentPosition, destination, terrain, Tile.Sky);
		return false;
	}
}
