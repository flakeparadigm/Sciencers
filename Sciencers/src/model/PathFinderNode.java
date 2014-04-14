package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial
//and the videos in this tutorial series https://www.youtube.com/watch?v=KNXfSOx4eEE

//Pathfinding Node class. These are nodes for the A* path-finding algorithm.
public class PathFinderNode {
	// path traveled so far
	List<Point> path = new ArrayList<Point>();

	// Math numbers.
	private int totalCost = 0, movementCost = 0, heuristicCost = 0;

	private PathFinderNode parent = null, north = null, south = null,
			east = null, west = null;

	private void detectAdjacentNodes() {

	}

	public void updateCosts() {

	}
}