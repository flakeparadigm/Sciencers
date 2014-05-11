package model.task;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Random;

import model.Entity;
import model.World;
import model.agent.Agent;
import model.building.Building;
import model.building.EBuilding;
import model.building.Factory;
import model.building.Farm;
import model.building.Lab;
import model.building.Warehouse;

public class WorkNearbyBuildingTask implements Task {

	private Agent sourceAgent;
	private Point currentPosition;
	private EBuilding buildingType;
	private Building targetBuilding;

	public WorkNearbyBuildingTask(Agent sourceAgent,
			EBuilding buildingType, Point currentPosition) {
		this.currentPosition = currentPosition;
		this.buildingType = buildingType;
		this.sourceAgent = sourceAgent;
	}

	@Override
	public void execute() {
		targetBuilding.addWorker(sourceAgent);
	}

	@Override
	public Point getPos() {
		Building closestBuilding = null;
		int shortestDistance = 100;
		for (Entity b : World.buildings) {
			System.out.println("getPos");
			boolean isCorrectType = false;
			if (buildingType == EBuilding.FARM && b instanceof Farm) {
				isCorrectType = true;
			} else if (buildingType == EBuilding.FACTORY
					&& b instanceof Factory) {
				isCorrectType = true;
			} else if (buildingType == EBuilding.LAB && b instanceof Lab) {
				isCorrectType = true;
			} else if (buildingType == EBuilding.WAREHOUSE
					&& b instanceof Warehouse) {
				isCorrectType = true;
			}
			if (isCorrectType) {
				int distance = sourceAgent
						.goHere(new Point2D.Double(currentPosition.x,
								currentPosition.y),
								new Point((int) Math.round(b.getPos().getX()),
										(int) Math.round(b.getPos().getY())))
						.size();
				if (distance < shortestDistance) {
					shortestDistance = distance;
					closestBuilding = (Building) b;
				}
			}
		}

		if (closestBuilding == null) {
			System.out.println("can't find building to work!");
			return currentPosition;
		}

		Random r = new Random();
		int Low = 0;
		int High = closestBuilding.getSize().width;
		int R = r.nextInt(High - Low) + Low;
		targetBuilding = closestBuilding;
		return new Point((int) Math.round(closestBuilding.getPos().getX() + R),
				(int) Math.round(closestBuilding.getPos().getY()));
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}

}
