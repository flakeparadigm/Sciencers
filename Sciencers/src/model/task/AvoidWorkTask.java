package model.task;

import java.awt.Point;
import java.util.Random;

import model.World;
import model.agent.Agent;

public class AvoidWorkTask implements Task{
	
	private Agent sourceAgent;
	private Point position;
	

	public AvoidWorkTask(Agent sourceAgent, Point position){
		this.sourceAgent = sourceAgent;
		this.position = position;
	}
	
	@Override
	public void execute() {
		//do nothing!
	}

	@Override
	public Point getPos() {
		Random r = new Random();
		int Low = 2;
		int High = 8;
		int R = r.nextInt(High-Low) + Low;
		//if this code doesn't work, add 1 to the get altitude output
		return new Point(position.x + R, World.terrain.getAltitude(position.x + R));
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}

}
