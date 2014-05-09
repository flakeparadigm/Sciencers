package controller;

import java.util.ArrayList;

import model.AlertCollection;
import model.Entity;

public class GameTick extends Thread {

	ArrayList<Entity> entities;
	ArrayList<Entity> deadEntities = new ArrayList<Entity>();
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
		 int count = 0;
		for (int i = 0;; i++) {
			while (shouldUpdate) {
				count++;
				if(count % 100 == 0) {
					System.out.println("100 ticks since last count on this thread, " + count + " total");
				}
				// Until the game ends, always update the given list of
				// entities
				// every tickTime milliseconds
				for (Entity e : entities) {
					if (!shouldUpdate) // prevent concurrent modification.
						break; // stops updates when paused, even mid-cycle;
					
					try{
						e.update();
					} catch(Exception ex) {
						ex.printStackTrace();
					}

					// if the agent has died, add it to the deadEntities list.
					if (e.isDead()) {
						deadEntities.add(e);
					}
				}

				// Clean up dead entities and clear the DE array list
				for (Entity d : deadEntities) {
					entities.remove(d);
				}
				deadEntities.clear();

				// If there are any entities, update the view
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

			if (terminate)
				return;
		}
	}
}
