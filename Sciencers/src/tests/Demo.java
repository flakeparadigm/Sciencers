package tests;

import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.World;
import view.WorldView;

public class Demo extends JFrame {
	
	// important things
	private static World world;
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	// Graphical elements
	private static WorldView worldView;
	private static Demo demoTools;
	
	// Test Buttons
	private JButton testTerrainUpdateButton = new JButton("Test Terrain Update");
	
	// magic numbers
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_WINDOW_SIZE = 250;
	private final int Y_WINDOW_SIZE = 700;

	public static void main(String[] args) {
		worldView = new WorldView();
		worldView.setVisible(true);
		
		demoTools = new Demo();
		demoTools.setVisible(true);
	}

	public Demo() {
		setupProperties();
		setupModel();
		addComponents();
		registerListeners();
	}

	private void setupProperties() {
		worldView.setSize(X_SCREEN_SIZE - X_WINDOW_SIZE - 150, Y_WINDOW_SIZE);
		worldView.setTitle("Sciencers Demo");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Demo Controls");
		setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		setLayout(null);
		setLocation(X_SCREEN_SIZE - (X_WINDOW_SIZE + 50), 50);
	}

	private void setupModel() {
		world = worldView.getWorld();
		//for example: add agent and farm
		world.addAgent(11);
		world.addFarm(27, 16);
	}

	private void addComponents() {
		
	}
	
	private void registerListeners() {
		
	}
	
	// PUT ALL TEST CASES BELOW
	private void testTerrainUpdate() {
		
	}

}
