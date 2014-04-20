package tests;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Agent;
import model.World;
import view.Tile;
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
	private JButton testHungerMovementButton = new JButton(
			"Test Hunger Movement");

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
		setLayout(new FlowLayout());
		setLocation(X_SCREEN_SIZE - (X_WINDOW_SIZE + 50), 50);
	}

	private void setupModel() {
		world = worldView.getWorld();
		// for example: add agent and farm
	}

	private void addComponents() {
		this.add(testTerrainUpdateButton);
		this.add(testHungerMovementButton);
	}

	private void registerListeners() {
		testTerrainUpdateButton.addActionListener(new demoButtonListener());
		testHungerMovementButton.addActionListener(new demoButtonListener());
	}

	// PUT ALL TEST CASES BELOW.
	private class demoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == testTerrainUpdateButton)
				testTerrainUpdate();
			if (event.getSource() == testHungerMovementButton)
				testHungerMovement();

		}

		private void testHungerMovement() {
			world.addFarm(29, 17);
			world.addAgent(9);
			world.addAgent(18);
			world.getTerrain().setTile(Tile.Dirt, 20, 14);
			Agent hungryAgent = (Agent) world.getAgents().get(0);
			hungryAgent.setHunger(200);
		}

		private void testTerrainUpdate() {
			JOptionPane
					.showMessageDialog(worldView,
							"This demo should replace 50 blocks along the top of the map with Stone (X)");
			Runnable r = new Runnable() {
				public void run() {
					try {
						for (int i = 0; i < 100; i += 2) {
							world.getTerrain().updateTile(Tile.Stone, i, 0);
							Thread.sleep(100);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			new Thread(r).start();
		}

	}
}
