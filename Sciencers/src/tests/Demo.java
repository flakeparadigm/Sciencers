package tests;

import javax.swing.JFrame;
import java.awt.Toolkit;
import model.World;
import view.WorldView;

public class Demo extends JFrame {
	
	// important things
	private static World world;
	static Toolkit tk = Toolkit.getDefaultToolkit();
	
	// Graphical elements
	private static WorldView worldView;
	private static Demo demoTools;
	
	// magic numbers
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_WINDOW_SIZE = 200;
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
	}

	private void setupProperties() {
		worldView.setSize(X_SCREEN_SIZE - X_WINDOW_SIZE - 150, Y_WINDOW_SIZE);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Demo Controls");
		setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		setLayout(null);
		setLocation(X_SCREEN_SIZE - (X_WINDOW_SIZE + 50), 50);
	}

	private void setupModel() {
		world = worldView.getWorld();
	}

	private void addComponents() {
		
	}

}
