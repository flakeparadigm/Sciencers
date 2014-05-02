package model;

public class Research {
	
	private static int research = 0;
	
	public static void change(int amt) {
		research += amt;
	}
	
	public static void set(int amt) {
		research = amt;
	}

	public static int get() {
		return research;
	}
	
}
