package controller;

import java.util.ArrayList;
import java.util.Random;

import model.Entity;
import model.World;

public class GameTick extends Thread {

	ArrayList<Entity> entities;
	ArrayList<Entity> deadEntities = new ArrayList<Entity>();
	int tickTime;
	boolean shouldUpdate = true;
	boolean terminate = false;

	Random rand = new Random();
	private final int TRAGIC_EVENT_TIME = 10000;

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
				if (count % 1000 == 0) {
					System.out
							.println("1000 ticks since last count on this thread, "
									+ count + " total");

					// if (count == TRAGIC_EVENT_TIME) {
					if (rand.nextInt(10000) == 5000) {
						World.rogueAttack();
					}
				}
				// Until the game ends, always update the given list of
				// entities
				// every tickTime milliseconds
				try {
					for (Entity e : entities) {
						if (!shouldUpdate) // prevent concurrent modification.
							break; // stops updates when paused, even mid-cycle;

						e.update();

						// if the agent has died, add it to the deadEntities
						// list.
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
				} catch (Exception ex) {
					ex.printStackTrace();
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
