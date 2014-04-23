package controller;

import java.util.ArrayList;
import model.Entity;


public class GameTick extends Thread {
	
	ArrayList<Entity> entities;
	int tickTime;
	boolean shouldUpdate = true;

	public GameTick(ArrayList<Entity> entities, int tickTime) {
		this.entities = entities;
		this.tickTime = tickTime;
	}
	
	public void terminate() {
		shouldUpdate = false;
	}
	
	@Override
	public void run() {
		while(shouldUpdate){
			try {
			
				// Until the game ends, always update the given list of entities
				// every tickTime milliseconds
				for(Entity e : entities)
					e.update();
				
				if(entities.size() > 0){
					SciencersObserver.updateObserver(entities.get(0));
				}
				
				Thread.sleep(tickTime);
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
