package model;

import java.awt.Point;

public class Agent {
	
	// agent id tracking
	private static int currentId = 0;
	public final int MY_ID;
	
	// current status
	private int hunger, fatigue;
	private Point currentPosition, targetPosition;
	
	// player traits
	private int intelligence, motivation, speed, strength;
	
	// constants
	private final int AGENT_HEIGHT = 2;
	
	public Agent() {
		MY_ID = currentId++;
	}
	
	public boolean goHere(Point destination) {
		PathFinder thePath = new PathFinder(currentPosition, destination);
		return false;
	}
}
