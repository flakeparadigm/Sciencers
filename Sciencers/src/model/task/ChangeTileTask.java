package model.task;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import view.Tile;
import model.World;
import model.agent.AgentReplacement;
import model.inventory.Resource;

public class ChangeTileTask implements Task {

	private Point position;
	private AgentReplacement sourceAgent;
	private ArrayList<Tile> passableTiles;
	private Tile tileType;
	
	public ChangeTileTask(AgentReplacement sourceAgent, Point position, Tile tileType) { 
		//i think the player should select a rectangle to be mined
		this.position = position;
		this.sourceAgent = sourceAgent;
		passableTiles = new ArrayList<Tile>();
		passableTiles.add(Tile.Sky);
		passableTiles.add(Tile.Wood);
		passableTiles.add(Tile.Leaves);
		passableTiles.add(Tile.Ladder);
		this.tileType = tileType;
	}
	
	@Override
	public void execute() {
		World.terrain.setTile(tileType, position.x, position.y);
		if (World.terrain.getTile(position.x, position.y).equals(Tile.Stone)){
			sourceAgent.getInventory().changeAmount(Resource.STONE, 1);
		} else if (World.terrain.getTile(position.x, position.y).equals(Tile.Iron)){
			sourceAgent.getInventory().changeAmount(Resource.IRON, 1);
		} else if (World.terrain.getTile(position.x, position.y).equals(Tile.Uranium)){
			sourceAgent.getInventory().changeAmount(Resource.URANIUM, 1);
		} else if (World.terrain.getTile(position.x, position.y).equals(Tile.Wood)){
			sourceAgent.getInventory().changeAmount(Resource.WOOD, 1);
		} 
		
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
		return new Point((int)sourceAgent.getPos().getX(), (int) sourceAgent.getPos().getY());
	}
	
	public Point getTileLocation(){
		return position;
	}
	
	public void changeTileType(Tile tileType){
		this.tileType = tileType;
	}
}
