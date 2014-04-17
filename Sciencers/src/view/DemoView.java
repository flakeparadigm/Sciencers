package view;

import javax.swing.JFrame;

import model.World;

public class DemoView extends JFrame {
	public static JFrame demoWindow;
	private static World world;
	
	private final int X_WINDOW_SIZE = 700;
	private final int Y_WINDOW_SIZE = 700;
	
	public static void main(String[] args) {
		demoWindow = new DemoView();
	}
	
	public DemoView() {
		
	}
	
}
