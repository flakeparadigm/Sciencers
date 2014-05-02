package model;

import java.util.LinkedList;
import java.util.Queue;

import controller.InfoObserver;

public class AlertCollection {
	
	private static Queue<Alert> alerts = new LinkedList<Alert>();
	
	public static Alert getAlert() {
		return alerts.peek();
	}
	
	public static void addAlert(String a) {
		alerts.add(new Alert(a));
		InfoObserver.updateObserver();
	}
	
	public static void removeAlert() {
		alerts.remove();
		InfoObserver.updateObserver();
	}
	
	public static Queue<Alert> getAlerts() {
		return new LinkedList<Alert>(alerts); //so that the actual list cannot be modified
	}
}
