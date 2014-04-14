package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

// based on A* algorithm described on http://wiki.gamegardens.com/Path_Finding_Tutorial

public class PathFinder {

	private Map<Integer, Coordinate> closeList = new HashMap<Integer, Coordinate>();
	private Queue<Coordinate> openList = new PriorityQueue<Coordinate>();

	public PathFinder(Point currentPosition, Point destination) {
		// TODO Auto-generated constructor stub
	}

	
	// A coordinate class. Used as what would normally be a node in A*
	private class Coordinate {
		// path traveled so far
		List<Point> path = new ArrayList<Point>();

		// Math numbers. f = total cost, g = current path length, h = heuristic distance (best guess).
		private int f = -1;
		int g = -1, h = -1;

		// the parent point
		public Point parent;

		// constructor that makes a super-simple coordinate from a point
		public Coordinate(Point parent) {
			this.parent = parent;
			path.add(parent);
		}
	}

}
