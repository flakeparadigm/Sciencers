package model;

public class Research {
	
	private static int research = 0;
	
	public static void change(int amt) {
		research += amt;
	}

	public static String get() {
		return "" + research;
	}
	
}
