package model.task;

import java.awt.Point;

import view.Tile;
import model.Terrain;
import model.World;
import model.agent.Agent;
import model.agent.EAgent;
import model.inventory.Resource;

public class HarvestTreeTask implements Task {

	private Point location;
	private Terrain terrain;
	private Agent sourceAgent;
	
	private final int WOOD_VALUE = 1;
	private final int FOOD_VALUE = 1;

	public HarvestTreeTask(Agent sourceAgent, Point location, Terrain terrain) {
		this.location = location;
		this.terrain = terrain;
		this.sourceAgent = sourceAgent;
	}
//	
//	public HarvestTreeTask(Point location, Terrain terrain, EAgent aType) {
//		System.out.println("Harvest tree task is being made without explicit sourceAgent, make sure to assign one");
//		this.location = location;
//		this.terrain = terrain;
//	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {
			if (terrain.getTile(location.x, location.y - i).equals(Tile.Wood)) {
				sourceAgent.getInventory().changeAmount(Resource.WOOD, WOOD_VALUE);
				terrain.setTile(
						terrain.getTile(location.x, location.y - 1 - i),
						location.x, location.y - i);
			}
			if (terrain.getTile(location.x, location.y - i).equals(Tile.Leaves)) {
				terrain.setTile(
						terrain.getTile(location.x, location.y - 1 - i),
						location.x, location.y - i);
				if (terrain.getTile(location.x + 1, location.y - i).equals(
						Tile.Leaves)|| terrain.getTile(location.x + 1, location.y - i).equals(
								Tile.Sky)) {
					terrain.setTile(
							terrain.getTile(location.x + 1, location.y - 1 - i),
							location.x + 1, location.y - i);
				}
				if (terrain.getTile(location.x - 1, location.y - i).equals(
						Tile.Leaves) || terrain.getTile(location.x - 1, location.y - i).equals(
								Tile.Sky)) {
					terrain.setTile(
							terrain.getTile(location.x - 1, location.y - 1 - i),
							location.x - 1, location.y - i);
					sourceAgent.getInventory().changeAmount(Resource.FOOD, FOOD_VALUE);
				}
			}
		}

	}

	@Override
	public Point getPos() {
		return location;
	}
	
	public String toString() {
		String pointString = location.x + ", " + location.y;
		
		String taskString = "Harvest tree\n";
		taskString += "Location: " + pointString;
				
		return taskString;
	}

	@Override
	public boolean shouldBeSeen() {
		return true;
	}
	
	public void setAgentSource(Agent sourceAgent) {
		this.sourceAgent = sourceAgent;
	}

}
