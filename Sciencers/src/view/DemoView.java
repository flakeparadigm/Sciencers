package view;

import javax.swing.JFrame;

import model.World;

public class DemoView extends JFrame {
	public static JFrame demoWindow;
	private static World world;
	
	public static void main(String[] args) {
		demoWindow = new DemoView();
	}
	
	public DemoView() {
		
	}
	
}
