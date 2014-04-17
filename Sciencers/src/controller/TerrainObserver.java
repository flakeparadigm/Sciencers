package controller;

import model.Terrain;
import view.WorldView;

public class TerrainObserver {
	private static WorldView worldView;
	
	public TerrainObserver(WorldView worldView) {
		TerrainObserver.worldView = worldView;
	}
	
	public static void updateObserver() {
		worldView.updateTerrain();
	}
}
