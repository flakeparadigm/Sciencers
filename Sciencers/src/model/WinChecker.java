package model;

import view.WorldView;

public class WinChecker extends Thread {	
	boolean running = true;
	
	public void terminate() {
		running = false;
	}
	
	@Override
	public void run() {
		while(running) {
			if(World.playerScience >= World.WINNING_SCIENCE) {
				WorldView.win();
			}
			if(World.agents.size() == 0) {
				WorldView.lose();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}