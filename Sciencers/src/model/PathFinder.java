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
	
	// Magic Numbers
	public static final int MOVEMENT_COST = 10;
	
	public PathFinder(Point startPoint, Point targetPoint, Terrain terrain , Tile passable) {
		// store the terain
		this.terrain = terrain;
		
		// make the starting and ending nodes, add them to our tracking map
		startNode = new PathFinderNode(startPoint, null, this);
		allNodes.put(startPoint, startNode);
		targetNode = new PathFinderNode(targetPoint, null, this);
		allNodes.put(targetPoint, targetNode);
		
		// start with the startNode as the first node to check.
		checkingNode = startNode;
	}
	
	public Stack<Point> getPath() {
		return finalPath;
	}
	
	private void findPath() {
		// will need to probably fix these boolean expressions
		while(checkingNode != targetNode){
			
		}
		
		while(checkingNode != startNode){
			finalPath.add(checkingNode.getPoint());
		}
	}
	
	private void determineNodeValues() {
		
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
	
	public PathFinderNode getNode(Point p) {
		return allNodes.get(p);
	}
	
	public PathFinderNode getDestination() {
		return targetNode;
	}
	
}
