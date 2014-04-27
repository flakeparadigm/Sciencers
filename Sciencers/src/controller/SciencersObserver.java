package controller;

import model.Agent;
import model.AgentReplacement;
import model.Entity;
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
		if(e instanceof AgentReplacement)
			worldView.updateAgents();
		else if(e instanceof Building)
			worldView.updateBuildings();
	}
}
