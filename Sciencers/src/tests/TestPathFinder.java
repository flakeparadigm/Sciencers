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
		
		Point startPoint = new Point(25, 49);
		Point endPoint = new Point(34, 48);
		PathFinder pathFinder = new PathFinder(startPoint, endPoint, theLand, Tile.Dirt);
		
		Stack<Point> thePath = pathFinder.getPath();
		
		System.out.println(thePath);
		
		System.out.println("");

		theLand.setTile(Tile.Path, 25, 24);
		theLand.setTile(Tile.Path, 34, 23);
		System.out.println(theLand);
	}
}
