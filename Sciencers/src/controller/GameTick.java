package controller;

import java.util.ArrayList;
import model.Entity;


public class GameTick extends Thread {
	
	ArrayList<Entity> entities;
	int tickTime;

	public GameTick(ArrayList<Entity> entities, int tickTime) {
		this.entities = entities;
		this.tickTime = tickTime;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				
			for(Entity e : entities)
				e.update();
				Thread.sleep(tickTime);
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
