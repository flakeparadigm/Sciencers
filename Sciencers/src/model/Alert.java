package model;

public class Alert {
	
	private String alert;
	private long birth;
	
	public Alert(String a) {
		alert = a;
		birth = System.currentTimeMillis();
	}
	
	public int getAge() {
		return (int) (System.currentTimeMillis() - birth);
	}
	
	public String toString() {
		return alert;
	}
}
