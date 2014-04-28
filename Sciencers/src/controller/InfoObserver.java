package controller;

import view.WorldView;

public class InfoObserver {
	private static WorldView worldView;
	
	public InfoObserver(WorldView worldView) {
		InfoObserver.worldView = worldView;
	}
	
	public static void updateObserver() {
		worldView.updateInfo();
	}
	
}
