package model;

import controller.InfoObserver;

public class Research {

	private static int research = 0;

	public static void change(int amt) {
		research += amt;
		InfoObserver.updateObserver();
	}

	public static void set(int amt) {
		research = amt;
		try {
			InfoObserver.updateObserver();
		} catch (NullPointerException e) {

		}
	}

	public static int get() {
		return research;
	}

}
