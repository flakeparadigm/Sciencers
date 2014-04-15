package model;

import java.awt.Point;

//based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial
//and the videos in this tutorial series https://www.youtube.com/watch?v=KNXfSOx4eEE

//Pathfinding Node class. These are nodes for the A* path-finding algorithm.
public class PathFinderNode implements Comparable<PathFinderNode> {

	// Math numbers.
	private int totalCost = 0, movementCost = 0, heuristicCost = 0;

	// important locations
	private PathFinderNode parent = null;
	public PathFinderNode north = null, south = null, east = null, west = null;
	private Point myCoordinate;
	private Point target;

	// other important objects
	private PathFinder pathFinder;

	public PathFinderNode(Point location, PathFinderNode parent,
			PathFinder pathFinder) {
		// set my location
		myCoordinate = location;
		// store the parent node
		this.parent = parent;
		// store the PathFinder
		this.pathFinder = pathFinder;

		// set the target
		target = pathFinder.getDestination();

		// calculate the (initial) values
		calcHeuristic();
		updateCosts();
		setAdjacentNodes();

		System.out.println("New node created for " + myCoordinate);
	}

	public void updateCosts() {
		if(parent != null)
			movementCost = parent.getMovementCost() + PathFinder.MOVEMENT_COST;

		totalCost = movementCost + heuristicCost;
	}

	public int seeNewCosts() {
		int newMvmtCost = parent.getMovementCost() + PathFinder.MOVEMENT_COST;
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

		if (seeNewCosts() < totalCost) {
			updateCosts();
			return true;
		} else {
			parent = oldParent;
			return false;
		}
	}

	public PathFinderNode getParent() {
		return parent;
	}

	public void setAdjacentNodes() {
		int x = myCoordinate.x;
		int y = myCoordinate.y;

		// get adjacent nodes
		north = pathFinder.getNode(new Point(x, y + 1));
		south = pathFinder.getNode(new Point(x, y - 1));
		east = pathFinder.getNode(new Point(x + 1, y));
		west = pathFinder.getNode(new Point(x - 1, y));
	}

	@Override
	public int compareTo(PathFinderNode other) {
		PathFinderNode otherPFN = (PathFinderNode) other;
		if (totalCost > otherPFN.getTotalCost())
			return 1;
		else if (totalCost < otherPFN.getTotalCost())
			return -1;
		else
			return 0;
	}

	public Point getPoint() {
		return myCoordinate;
	}

	private void calcHeuristic() {
		int heuristicCalc = (int) Math.pow(target.getX() - myCoordinate.getX(),
				2);
		heuristicCalc += (int) Math.pow(target.getY() - myCoordinate.getY(), 2);

		heuristicCost = (int) Math.sqrt(heuristicCalc);
	}

	public void forceParent(PathFinderNode newParent) {
		parent = newParent;
		updateCosts();
	}

}