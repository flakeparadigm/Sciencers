package model.task;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

import model.Entity;
import model.agent.Agent;
import model.building.Building;

public class RogueTask implements Task {

	private boolean killer;
	private Agent predator;
	private Entity prey;

	public RogueTask(Agent predator, Agent prey) {
		this.predator = predator;
		this.prey = (Entity) prey;
		killer = true;
	}

	public RogueTask(Agent predator, Building prey) {
		this.predator = predator;
		this.prey = (Entity) prey;
		killer = false;
	}

	@Override
	public void execute() {
		if(killer) {
			Random rand = new Random();
			predator.attack((Agent) prey, rand.nextInt(5));
			
		}
	}

	@Override
	public Point getPos() {
		Point point;
		if (prey.getPos() instanceof Point2D) {
			point = new Point((int)prey.getPos().getX(), (int)prey.getPos().getY());
		} else {
			point = (Point) prey.getPos();
		}
		return point;
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}

}
