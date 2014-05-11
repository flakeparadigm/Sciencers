package model.task;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Stack;

import view.Tile;
import model.World;
import model.agent.Agent;
import model.inventory.Resource;

public class GatherResources implements Task {

	private final int SEARCH_RANGE = 40;
	private final int SEARCH_DEPTH = 30;
	private Resource resourceType;
	private Agent agentSource;
	private int agentX;
	private int agentY;
	int bestPointX = 0;
	int bestPointY = 0;

	public GatherResources(Resource resourceType) {
		this.resourceType = resourceType;
	}

	public void setAgentSource(Agent agentSource) {
		this.agentSource = agentSource;
		agentX = agentSource.getCurrentX(agentSource.currentPosition);
		agentY = agentSource.getCurrentY(agentSource.currentPosition);
	}

	@Override
	public void execute() {
		agentSource.priorityResource = resourceType;
		System.out.println("ON IT!!!!!!!!!!!!!!!");
		agentSource.tasks.add(new ChangeTileTask(agentSource, new Point(bestPointX, bestPointY), Tile.Sky));
	}

	@Override
	public Point getPos() {
		// if ground resource, find nearest. If not, go for tree
		Tile searchForThis = Tile.Dirt;
		double shortestDistance = -1;

		
		if (resourceType == Resource.STONE) {
			searchForThis = Tile.Stone;
		} else if (resourceType == Resource.URANIUM) {
			searchForThis = Tile.Uranium;
		} else if (resourceType == Resource.IRON) {
			searchForThis = Tile.Iron;
		}

		if (resourceType == Resource.WOOD || resourceType == Resource.FOOD) {
			agentSource.tasks.add(new HarvestTreeTask(agentSource, new Point(
					agentX, agentY), World.terrain));
		} else {
			// search for ground resource:
			for (int i = -1; i < 2; i++) {
				for (int j = 1; j < SEARCH_RANGE; j++) {
					for (int k = 1; k < SEARCH_DEPTH; k++) {
//						System.out.println((i * j) + agentX);
//						System.out.println(k + agentY);
						if (World.terrain.getTile((i * j) + agentX, k + agentY) == searchForThis) {
//							System.out.println("Current Pos:" + agentX + ", "
//									+ agentY);
//							System.out.println("Found resource at "
//									+ ((i * j) + agentX) + ", " + (k + agentY));
							double distance = Math.pow(agentX - ((i * j) + agentX),2) + Math.pow((agentY - (k + agentY)), 2);
//							System.out.println(distance);
							if (distance < shortestDistance || shortestDistance == -1){
								shortestDistance = distance;
								System.out.println(distance);
								bestPointX = (i * j) + agentX;
								bestPointY = k + agentY;
							}
						}
					}
				}
			}
		}
		System.out.println("DONE finding");
		return new Point(bestPointX, World.terrain.getAltitude(bestPointX) - 1);
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}

}
