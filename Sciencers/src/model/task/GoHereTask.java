package model.task;

import java.awt.Point;

public class GoHereTask implements Task{

	private Point point;
	
	public GoHereTask(Point point){
		this.point = point;
	}
	
	@Override
	public void execute() {
		//Nothing! That's how it should be for this task
	}

	@Override
	public Point getPos() {
		return point;
	}

	@Override
	public boolean shouldBeSeen() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
