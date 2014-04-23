package model.agentCommand;

import java.awt.Point;

import view.Tile;
import model.Terrain;

public class HarvestTreeTask implements Task {

	private Point location;
	private Terrain terrain;

	public HarvestTreeTask(Point location, Terrain terrain) {
		this.location = location;
		this.terrain = terrain;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 20; i++) {
			if (terrain.getTile(location.x, location.y - i).equals(
					Tile.Wood)) {
				System.out.println("Harvesting");
				terrain.setTile(terrain.getTile(location.x, location.y - 1 - i), location.x, location.y - i);
			}
			if (terrain.getTile(location.x, location.y - i).equals(
							Tile.Leaves)){
				terrain.setTile(terrain.getTile(location.x, location.y - 1 - i), location.x, location.y - i);
				terrain.setTile(terrain.getTile(location.x + 1, location.y - 1 - i), location.x + 1, location.y - i);
				terrain.setTile(terrain.getTile(location.x - 1, location.y - 1 - i), location.x - 1, location.y - i);
			}
		}
	}

	@Override
	public Point getPos() {
		return location;
	}

}
