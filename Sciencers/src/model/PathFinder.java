package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import view.Tile;

// based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial
// and the videos in this tutorial series https://www.youtube.com/watch?v=KNXfSOx4eEE
public class PathFinder {

	// nodes
	private PathFinderNode startNode, targetNode, checkingNode;
	private HashMap<Point, PathFinderNode> allNodes = new HashMap<Point, PathFinderNode>();

	// tracking lists
	private PriorityQueue<PathFinderNode> openList = new PriorityQueue<PathFinderNode>();
	private List<PathFinderNode> closedList = new ArrayList<PathFinderNode>();
	private Stack<Point> finalPath = new Stack<Point>();

	// other info
	private Terrain terrain;
	private Tile passable;

	// Magic Numbers
	public static final int MOVEMENT_COST = 10;

	public PathFinder(Point startPoint, Point targetPoint, Terrain terrain,
			Tile passable) {
		// store the terain and passable tiles
		this.terrain = terrain;
		this.passable = passable;

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

	public Stack<Point> getPath() {
		return finalPath;
	}

	private void findPath() {
		// will need to probably fix these boolean expressions
		while (checkingNode != targetNode) {
			moveToClosedList(checkingNode);

			makeAdjacentNodes(checkingNode);
			checkingNode.setAdjacentNodes();

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
			
			if(checkingNode == null)
				return;
		}

		while (checkingNode != startNode) {
			finalPath.add(checkingNode.getPoint());
			checkingNode = checkingNode.getParent();
		}
	}

	private void addToOpenList(PathFinderNode node) {
		openList.add(node);
	}

	private void addToClosedList(PathFinderNode node) {
		closedList.add(node);
	}

	private void removeFromOpenList(PathFinderNode node) {
		openList.remove(node);
	}

	private void moveToClosedList(PathFinderNode node) {
		removeFromOpenList(node);
		addToClosedList(node);
	}

	private void makeAdjacentNodes(PathFinderNode checking) {
		// create Nodes for all of the adjacent nodes if they don't exist (are
		// passable)
		Point checkingPoint = checking.getPoint();
		int checkX = (int) checkingPoint.getX();
		int checkY = (int) checkingPoint.getY();

		// north
		Point northPoint = new Point(checkX, checkY + 1);
		Tile northTile = terrain.getTile(checkX, checkY + 1);
		if (northTile == passable && allNodes.get(northPoint) == null) {
			PathFinderNode newNode = new PathFinderNode(northPoint, checking,
					this);
			allNodes.put(northPoint, newNode);
			openList.add(newNode);
		}

		// South
		Point southPoint = new Point(checkX, checkY - 1);
		Tile southTile = terrain.getTile(checkX, checkY - 1);
		if (southTile == passable && allNodes.get(southPoint) == null) {
			PathFinderNode newNode = new PathFinderNode(southPoint, checking,
					this);
			allNodes.put(southPoint, newNode);
			openList.add(newNode);
		}

		// East
		Point eastPoint = new Point(checkX + 1, checkY);
		Tile eastTile = terrain.getTile(checkX + 1, checkY);
		if (eastTile == passable && allNodes.get(eastPoint) == null) {
			PathFinderNode newNode = new PathFinderNode(eastPoint, checking,
					this);
			allNodes.put(eastPoint, newNode);
			openList.add(newNode);
		}

		// West
		Point westPoint = new Point(checkX - 1, checkY);
		Tile westTile = terrain.getTile(checkX - 1, checkY);
		if (westTile == passable && allNodes.get(westPoint) == null) {
			PathFinderNode newNode = new PathFinderNode(westPoint, checking,
					this);
			allNodes.put(westPoint, newNode);
			openList.add(newNode);
		}
	}

	public PathFinderNode getNode(Point p) {
		return allNodes.get(p);
	}

	public PathFinderNode getDestination() {
		return targetNode;
	}

}
