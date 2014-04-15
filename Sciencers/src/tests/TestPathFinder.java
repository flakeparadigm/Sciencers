package tests;

import java.awt.Point;
import java.util.Stack;

import model.PathFinder;
import model.Terrain;
import view.Tile;

public class TestPathFinder {

	public static void main(String[] args) {
		Terrain theLand = new Terrain(123, 50, 25);
		System.out.println(theLand);
		
		Point startPoint = new Point(0, 0);
		Point endPoint = new Point(25, 0);
		PathFinder pathFinder = new PathFinder(startPoint, endPoint, theLand, Tile.Sky);
		
		Stack<Point> thePath = pathFinder.getPath();
		
		System.out.println(thePath);
		
		System.out.println("");

		theLand.setTile(Tile.Path, startPoint.x, startPoint.y);
		for(Point p : thePath) {
			theLand.setTile(Tile.Path, p.x, p.y);
		}
		System.out.println(theLand);
	}
}
