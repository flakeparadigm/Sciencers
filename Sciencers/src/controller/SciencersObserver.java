package controller;

import model.Entity;
import model.agent.Agent;
import model.agent.AgentReplacement;
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
		if (e instanceof AgentReplacement) {
			worldView.updateAgents();
		} else if (e instanceof Building) {
			worldView.updateBuildings();
		}
	}
}
