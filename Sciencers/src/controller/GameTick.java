package controller;

import java.util.List;
import model.Entity;


public class GameTick implements Runnable {
	
	List<Entity> entities;
	int tickTime;

	public GameTick(List<Entity> entities, int tickTime) {
		this.entities = entities;
		this.tickTime = tickTime;
	}
	
	@Override
	public void run() {
		while(true){
			
		}
	}

}
