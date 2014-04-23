package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import view.Tile;

/*
 * A path-finder based on the A* search algorithm. Creating the object calculates the path,
 * calling getPath() returns a Stack of Points that contains all of the points along the
 * path from the starting point to the destination.
 */
public class PathFinder {

	// nodes
	private PathFinderNode startNode, targetNode, checkingNode;
	private HashMap<Point, PathFinderNode> allNodes = new HashMap<Point, PathFinderNode>();
	private Point targetPoint;

	// tracking lists
	private PriorityQueue<PathFinderNode> openList = new PriorityQueue<PathFinderNode>();
	private List<PathFinderNode> closedList = new ArrayList<PathFinderNode>();
	private Stack<Point> finalPath = new Stack<Point>();

	// other info
	private Terrain terrain;
	private Tile passable;

	// Magic Numbers
	public static final int MOVEMENT_COST = 10;

	// sets up all of the information then calls findPath()
	public PathFinder(Point startPoint, Point targetPoint, Terrain terrain,	Tile passable) {
		// store the terain and passable tiles
		this.terrain = terrain;
		this.passable = passable;
		this.targetPoint = targetPoint;

		// make the starting and ending nodes, add them to our tracking map
		startNode = new PathFinderNode(startPoint, null, this);
		allNodes.put(startPoint, startNode);
		targetNode = new PathFinderNode(targetPoint, null, this);
		allNodes.put(targetPoint, targetNode);

		// start with the startNode as the first node to check.
		checkingNode = startNode;

		// find the path!
		findPath();
	}

	// Actually finds the path by iterating through the nodes
	private void findPath() {
		// Continue to check the node with the lowest cost until we have
		// reached our destination
		while (checkingNode != targetNode) {
			// stick the current item on the closed list, we've tested it.
			moveToClosedList(checkingNode);
			// create the adjacent nodes if they don't already exist
			makeAdjacentNodes(checkingNode);
			checkingNode.setAdjacentNodes();

			// set the parents of the adjacent nodes. (Will only change if
			// the path through the current node is faster)
			if (checkingNode.north != null)
				checkingNode.north.setParent(checkingNode);

			if (checkingNode.south != null)
				checkingNode.south.setParent(checkingNode);

			if (checkingNode.east != null)
				checkingNode.east.setParent(checkingNode);

			if (checkingNode.west != null)
				checkingNode.west.setParent(checkingNode);

			// get next node. If one of the adjacent nodes is the end-node, then
			// set that as the current node, otherwise move on.
			boolean isNorthTarget = (checkingNode.north == targetNode);
			boolean isSouthTarget = (checkingNode.south == targetNode);
			boolean isEastTarget = (checkingNode.east == targetNode);
			boolean isWestTarget = (checkingNode.west == targetNode);

			if (isNorthTarget || isSouthTarget || isEastTarget || isWestTarget) {
				targetNode.forceParent(checkingNode);
				checkingNode = targetNode;
			} else {
				checkingNode = openList.poll();
			}

			// if there are no more nodes to check, then we can assume there is
			// no valid path. returning now will leave the path stack empty.
			if (checkingNode == null)
				return;
		}

		// generate the path stack by starting at the destination.
		while (checkingNode != startNode) {
			finalPath.add(checkingNode.getPoint());
			checkingNode = checkingNode.getParent();
		}
	}

	// add an unchecked node to the open list
	private void addToOpenList(PathFinderNode node) {
		openList.add(node);
	}

	// add a checked node to the closed list
	private void addToClosedList(PathFinderNode node) {
		closedList.add(node);
	}

	// remove a checked node from the open list
	private void removeFromOpenList(PathFinderNode node) {
		openList.remove(node);
	}

	// remove a checked item from the open list and add it to the closed list.
	private void moveToClosedList(PathFinderNode node) {
		removeFromOpenList(node);
		addToClosedList(node);
	}

	/*
	 * Create nodes for all of the adjacent nodes if they don't exist. This
	 * only adds nodes that have passable tiles in their location on the
	 * given terrain.
	 */
	private void makeAdjacentNodes(PathFinderNode checking) {
		Point checkingPoint = checking.getPoint();
		int checkX = (int) checkingPoint.getX();
		int checkY = (int) checkingPoint.getY();
		int maxX = terrain.getTileArray().length - 1;
		int maxY = (terrain.getTileArray())[0].length - 1;

		if ((checkX > maxX) || (checkY > maxY))
			return;

		// north
		if (checkY != 0) {
			Point northPoint = new Point(checkX, checkY - 1);
			makeNode(northPoint, checking);
		}

		// South
		if (checkY < maxY) {
			Point southPoint = new Point(checkX, checkY + 1);
			makeNode(southPoint, checking);
		}

		// East
		if (checkX < maxX) {
			Point eastPoint = new Point(checkX + 1, checkY);
			makeNode(eastPoint, checking);
		}

		// West
		if (checkX != 0) {
			Point westPoint = new Point(checkX - 1, checkY);
			makeNode(westPoint, checking);
		}
	}
	private void makeNode(Point newPoint, PathFinderNode parent) {
		if (isPassable(newPoint) && allNodes.get(newPoint) == null) {
			PathFinderNode newNode = new PathFinderNode(newPoint, parent, this);
			allNodes.put(newPoint, newNode);
			addToOpenList(newNode);
		}
	}
	
	private boolean isPassable(Point pt) {
		Tile myTile = terrain.getTile(pt.x, pt.y);
		boolean isPassable = myTile == passable;
		
		if(!isPassable)
			return false;
		
		try {
			if(terrain.getTile(pt.x, pt.y+1) != passable)
				return true;
		} catch(ArrayIndexOutOfBoundsException e) {
			return true;
		}
		

		try {
			if((terrain.getTile(pt.x-1, pt.y+1) != passable) || (terrain.getTile(pt.x+1, pt.y+1) != passable))
				return true;
		} catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
		
		return false;
	}

	// get methods
	public Point getDestination() {
		return targetPoint;
	}
	public PathFinderNode getNode(Point p) {
		return allNodes.get(p);
	}
	public Stack<Point> getPath() {
		return finalPath;
	}

}
