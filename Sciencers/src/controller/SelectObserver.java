package controller;

import view.WorldView;

public class SelectObserver {
	private static WorldView worldView;
	
	public SelectObserver(WorldView worldView) {
		SelectObserver.worldView = worldView;
	}
	
	public static void updateObserver() {
		//worldView.notifySelectObserver();
	}
}
