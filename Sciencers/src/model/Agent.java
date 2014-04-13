package model;

import java.awt.Point;

public class Agent {
	
	// agent id tracking
	private static int currentId = 0;
	public final int myID;
	
	// current status
	private int hunger, fatigue;
	private Point currentPosition, targetPosition;
	
	// player traits
	private int intelligence, motivation, speed, strength;
	
	public Agent() {
		myID = currentId++;
	}
}
