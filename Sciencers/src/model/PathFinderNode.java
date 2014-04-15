package model;

import java.awt.Point;

//based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial
//and the videos in this tutorial series https://www.youtube.com/watch?v=KNXfSOx4eEE

//Pathfinding Node class. These are nodes for the A* path-finding algorithm.
public class PathFinderNode implements Comparable<PathFinderNode> {

	// Math numbers.
	private int totalCost = 0, movementCost = 2147483647, heuristicCost = 0;

	// important locations
	private PathFinderNode parent = null;
	public PathFinderNode north = null, south = null, east = null, west = null;
	private Point myCoordinate;
	private Point target;

	// other important objects
	private PathFinder pathFinder;

	public PathFinderNode(Point location, PathFinderNode parent, PathFinder pathFinder) {
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
	}

	/*
	 * This updates the movement and total costs. The Heuristic cost will not
	 * change because a point on the map shouldn't move.
	 * 
	 * Movement Cost: This is the distance from the starting point. This is
	 * 		the number of blocks traveled from the starting point, times 10
	 * 
	 * Total Cost: The movement cost plus the Heuristic Cost
	 */
	public void updateCosts() {
		if(parent != null)
			movementCost = parent.getMovementCost() + PathFinder.MOVEMENT_COST;
		else
			movementCost = 0;

		totalCost = movementCost + heuristicCost;
	}

	/*
	 * Get a preview of the cost with the new parent 
	 */
	public int seeNewCosts() {
		int newMvmtCost = parent.getMovementCost() + PathFinder.MOVEMENT_COST;
		return newMvmtCost + heuristicCost;
	}

	/*
	 * Set the parent of this node by checking to see first if the path
	 * from the starting point, to this node is lower. If it new path is
	 * cheaper, then keep the parent. If the new path is more expensive,
	 * then toss it out.
	 */
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

	/*
	 * Force a new parent on the node. This is only really used for the
	 * destination once it's reached, allowing the path to be traced
	 * back by starting on the end.
	 */
	public void forceParent(PathFinderNode newParent) {
		parent = newParent;
		updateCosts();
	}

	public PathFinderNode getParent() {
		return parent;
	}

	/*
	 * pulls the the nodes that would be adjacent out of the map of all
	 * of the nodes created so far in this path finder
	 */
	public void setAdjacentNodes() {
		int x = myCoordinate.x;
		int y = myCoordinate.y;

		// get adjacent nodes
		north = pathFinder.getNode(new Point(x, y - 1));
		south = pathFinder.getNode(new Point(x, y + 1));
		east = pathFinder.getNode(new Point(x + 1, y));
		west = pathFinder.getNode(new Point(x - 1, y));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * This overrides compareTo in comparable so that the object can be
	 * sorted in a priority queue
	 */
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

	/*
	 * Calculate the heuristic cost. Only gets called once, but it's neater to
	 * have it in its own method.
	 */
	private void calcHeuristic() {
		int heuristicCalc = (int) Math.pow(target.getX() - myCoordinate.getX(),
				2);
		heuristicCalc += (int) Math.pow(target.getY() - myCoordinate.getY(), 2);

		heuristicCost = (int) Math.sqrt(heuristicCalc);
	}
	
	// Get methods
	public int getMovementCost() {
		return movementCost;
	}
	public Point getPoint() {
		return myCoordinate;
	}
	public int getTotalCost() {
		return totalCost;
	}

}