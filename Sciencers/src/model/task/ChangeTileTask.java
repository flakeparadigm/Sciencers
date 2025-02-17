package model.task;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.Tile;
import model.World;
import model.agent.Agent;
import model.inventory.Resource;

public class ChangeTileTask implements Task {

	private Point position;
	private Agent sourceAgent;
	private ArrayList<Tile> passableTiles;
	private Tile tileType;
	
	public ChangeTileTask(Agent sourceAgent, Point position, Tile tileType) { 
		//i think the player should select a rectangle to be mined
		this.position = position;
		this.sourceAgent = sourceAgent;
//		passableTiles = new ArrayList<Tile>();
//		passableTiles.add(Tile.Sky);
//		passableTiles.add(Tile.Wood);
//		passableTiles.add(Tile.Leaves);
//		passableTiles.add(Tile.Ladder);
//		passableTiles.add(Tile.BackgroundDirt);
		this.passableTiles = World.terrain.passableTiles;
		this.tileType = tileType;
	}
	
	@Override
	public void execute() {
		if (World.terrain.getTile(position.x, position.y).equals(Tile.Stone)){
			sourceAgent.getInventory().changeAmount(Resource.STONE, 1);
		} else if (World.terrain.getTile(position.x, position.y).equals(Tile.Iron)){
			System.out.println("Added Iron");
			sourceAgent.getInventory().changeAmount(Resource.IRON, 1);
		} else if (World.terrain.getTile(position.x, position.y).equals(Tile.Uranium)){
			sourceAgent.getInventory().changeAmount(Resource.URANIUM, 1);
			System.out.println("Got uranium");
		} else if (World.terrain.getTile(position.x, position.y).equals(Tile.Wood)){
			sourceAgent.getInventory().changeAmount(Resource.WOOD, 1);
			System.out.println("GotWood");
		} 
		
		World.terrain.setTile(tileType, position.x, position.y);

		if (tileType.equals(Tile.Ladder)){
			sourceAgent.getInventory().changeAmount(Resource.WOOD, -1);
		}
	}

	@Override
	public Point getPos() {
		if (passableTiles.contains(World.terrain.getTile(position.x - 1, position.y))){
			return new Point(position.x - 1, position.y);
		} else if (passableTiles.contains(World.terrain.getTile(position.x + 1, position.y))){
			return new Point(position.x + 1, position.y);
		} else if (passableTiles.contains(World.terrain.getTile(position.x, position.y - 1))){
			return new Point(position.x, position.y - 1);
		}

		//default return in case of bad location
		return new Point(position.x, position.y - 1);
	}
	
	public Point getTileLocation(){
		return position;
	}
	
	public void changeTileType(Tile tileType){
		this.tileType = tileType;
	}

	@Override
	public boolean shouldBeSeen() {
		return false;
	}
}
