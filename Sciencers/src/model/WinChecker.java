package model;

import view.WorldView;

public class WinChecker extends Thread {	
	boolean running = true;
	
	public void terminate() {
		running = false;
	}
	
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
				System.out.println("loop");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}