package tests;

/*
 * This test is set to consider Dirt to be an open tile. I figured this was easiest
 * since the current state of the terrain gen is able to put things that can be
 * considered obstacles into the area.
 */

import java.awt.Point;
import java.util.Stack;

import model.PathFinder;
import model.Terrain;
import view.Tile;

public class TestPathFinder {

	public static void main(String[] args) {
		// create the terrain and print it to be compared with
		// a terrain that has a path cut out of it.
		Terrain theLand = new Terrain(123, 50, 25);
		System.out.println(theLand);
		
		// create the start and end points, find the path
		Point startPoint = new Point(25, 24);
		Point endPoint = new Point(34, 23);
		PathFinder pathFinder = new PathFinder(startPoint, endPoint, theLand, Tile.Dirt);
		Stack<Point> thePath = pathFinder.getPath();
		
		// print out the stack of points. This is all of the points
		// traveled through to get to the destination. Including the
		// destination, excluding the starting point.
		System.out.println(thePath);
		
		System.out.println("");

		// mark the path on the map and see if we had success!
		theLand.setTile(Tile.Path, startPoint.x, startPoint.y);
		for(Point p : thePath) {
			theLand.setTile(Tile.Path, p.x, p.y);
		}
		System.out.println(theLand);
	}
}
