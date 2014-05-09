package model.task;

import java.awt.Point;
import java.util.Random;

import model.World;
import model.agent.Agent;

public class WanderTask implements Task{
	
	private Point position;

	public WanderTask(Point position){
		this.position = position;
	}
	
	@Override
	public void execute() {
		//do nothing!
	}

	@Override
	public Point getPos() {
		Random r = new Random();
		int Low = -4;
		int High = 4;
		int R = r.nextInt(High-Low) + Low;
		//if this code doesn't work, add 1 to the get altitude output
		return new Point(position.x + R, World.terrain.getAltitude(position.x + R) - 1);
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}

}
