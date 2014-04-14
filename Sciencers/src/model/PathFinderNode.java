package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial
//and the videos in this tutorial series https://www.youtube.com/watch?v=KNXfSOx4eEE

//Pathfinding Node class. These are nodes for the A* path-finding algorithm.
public class PathFinderNode implements Comparable {

	// Math numbers.
	private int totalCost = 0, movementCost = 0, heuristicCost = 0;

	// important locations
	private PathFinderNode parent = null, north = null, south = null,
			east = null, west = null;
	private Point myCoordinate;
	
	// other important objects
	private PathFinder pathFinder;
	
	public PathFinderNode(Point location, PathFinderNode parent, PathFinder pathFinder) {
		
	}

	private void detectAdjacentNodes() {
		int x = myCoordinate.x;
		int y = myCoordinate.y;

		// get adjacent nodes
		north = pathFinder.getNode(new Point(x, y+1));
		south = pathFinder.getNode(new Point(x, y-1));
		east = pathFinder.getNode(new Point(x+1, y));
		west = pathFinder.getNode(new Point(x-1, y));
	}

	public void updateCosts() {
		movementCost = parent.getMovementCost() + PathFinder.MOVEMENT_COST;
		
		totalCost = movementCost + heuristicCost;
	}
	
	public int seeNewCosts() {
		int newMvmtCost = parent.getMovementCost() + pathFinder.MOVEMENT_COST;
		return newMvmtCost + heuristicCost;
	}
	
	public int getTotalCost() {
		return totalCost;
	}
	
	public int getMovementCost() {
		return movementCost;
	}
	
	public boolean setParent(PathFinderNode newParent) {
		PathFinderNode oldParent = parent;
		parent = newParent;
		
		if(seeNewCosts() < totalCost) {
			updateCosts();
			return true;
		} else {
			parent = oldParent;
			return false;
		}
	}

	@Override
	public int compareTo(Object other) {
		if(other instanceof PathFinderNode) {
			PathFinderNode otherPFN = (PathFinderNode) other;
			if(totalCost > otherPFN.getTotalCost())
				return 1;
			else if(totalCost < otherPFN.getTotalCost())
				return -1;
			else
				return 0;
			
		// PathFinderNodes should always come before other objects
		} else {
			return -2;
		}
	}
}