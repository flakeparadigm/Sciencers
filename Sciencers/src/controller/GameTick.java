package controller;

import java.util.ArrayList;
import model.Entity;

public class GameTick extends Thread {

	ArrayList<Entity> entities;
	int tickTime;
	boolean shouldUpdate = true;
	boolean terminate = false;

	public GameTick(ArrayList<Entity> entities, int tickTime) {
		this.entities = entities;
		this.tickTime = tickTime;
	}

	public void terminate() {
		terminate = true;
	}

	public void pauseTick() {
		shouldUpdate = false;
	}
	public void resumeTick() {
		shouldUpdate = true;
	}

	@Override
	public void run() {
		for (int i = 0;; i++) {
			while (shouldUpdate) {
				// Until the game ends, always update the given list of
				// entities
				// every tickTime milliseconds
				for (Entity e : entities) {
					if(!shouldUpdate)				// prevent concurrent modification.
						break;						// stops updates when paused, even mid-cycle;
					
					e.update();
				}

				if (entities.size() > 0) {
					SciencersObserver.updateObserver(entities.get(0));
				}

				try {
					Thread.sleep(tickTime);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			try {
				Thread.sleep(tickTime);

			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			if(terminate)
				return;
		}
	}
}
