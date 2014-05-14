package model;

import model.agent.FarmerAgent;
import model.agent.MinerAgent;
import model.agent.SciencerAgent;
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
			if(!agentAlive()) {
				WorldView.lose();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean agentAlive() {
		for (Entity a: World.agents){
			if (a instanceof MinerAgent || a instanceof SciencerAgent || a instanceof FarmerAgent){
				return true;
			}
		}
		return false;
	}
}