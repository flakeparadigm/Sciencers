package controller;

import model.Entity;
import model.agent.Agent;
import model.building.Building;
import view.WorldView;

public class SciencersObserver {
	private static WorldView worldView;

	public SciencersObserver(WorldView worldView) {
		SciencersObserver.worldView = worldView;
	}

	public static void updateObserver() {
		worldView.updateTerrain();
	}

	public static void updateObserver(Entity e) {
		if (e instanceof Agent) {
			worldView.updateAgents();
		} else if (e instanceof Building) {
			worldView.updateBuildings();
		}
	}
}
