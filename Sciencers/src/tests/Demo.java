package tests;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.Agent;
import model.World;
import model.agentCommand.BuildBuildingTask;
import model.agentCommand.HarvestTreeTask;
import model.agentCommand.Task;
import model.agentCommand.TaskList;
import model.building.EBuilding;
import view.Tile;
import view.WorldView;

public class Demo extends JFrame {

	// important things
	// private static World World;
	static Toolkit tk = Toolkit.getDefaultToolkit();

	// Graphical elements
	private static WorldView WorldView;
	private static Demo demoTools;

	// Test Buttons
	private JButton resetWorldButton = new JButton("Reset World");
	private JButton testTerrainUpdateButton = new JButton("Test Terrain Update");
	private JButton testHungerMovementButton = new JButton(
			"Test Hunger Movement");
	private JButton testBuildingConstructionButton = new JButton(
			"Test Building Construction");
	private JButton testTreeHarvestButton = new JButton("Test Tree Harvesting");
	private JButton testSurvivalButton = new JButton("Test Overall Survival");
	private JButton testHungerDeathButton = new JButton("Test Hunger Death");

	// magic numbers
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_WINDOW_SIZE = 250;
	private final int Y_WINDOW_SIZE = 700;

	public static void main(String[] args) {
		WorldView = new WorldView();
		WorldView.setVisible(true);

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
		WorldView.setSize(X_SCREEN_SIZE - X_WINDOW_SIZE - 150, Y_WINDOW_SIZE);
		WorldView.setTitle("Sciencers Demo");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Demo Controls");
		setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		setLayout(new FlowLayout());
		setLocation(X_SCREEN_SIZE - (X_WINDOW_SIZE + 50), 50);
	}

	private void setupModel() {
		// World = WorldView.getWorld();
		// for example: add agent and farm
	}

	private void addComponents() {
		this.add(resetWorldButton);
		this.add(testTerrainUpdateButton);
		this.add(testHungerMovementButton);
		this.add(testBuildingConstructionButton);
		this.add(testTreeHarvestButton);
		this.add(testSurvivalButton);
		this.add(testHungerDeathButton);
	}

	private void registerListeners() {
		resetWorldButton.addActionListener(new demoButtonListener());
		testTerrainUpdateButton.addActionListener(new demoButtonListener());
		testHungerMovementButton.addActionListener(new demoButtonListener());
		testBuildingConstructionButton
				.addActionListener(new demoButtonListener());
		testTreeHarvestButton.addActionListener(new demoButtonListener());
		testSurvivalButton.addActionListener(new demoButtonListener());
		testHungerDeathButton.addActionListener(new demoButtonListener());
	}

	// PUT ALL TEST CASES BELOW.
	private class demoButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == resetWorldButton)
				resetWorld();
			if (event.getSource() == testTerrainUpdateButton)
				testTerrainUpdate();
			if (event.getSource() == testHungerMovementButton)
				testHungerMovement();
			if (event.getSource() == testBuildingConstructionButton)
				testBuildingConstruction();
			if (event.getSource() == testTreeHarvestButton)
				testTreeHarvest();
			if (event.getSource() == testSurvivalButton)
				testSurvival();
			if (event.getSource() == testHungerDeathButton)
				testHungerDeath();
		}

		private void resetWorld() {
			World.reset();
			WorldView.updateAll();
		}
		
		private void testHungerDeath(){
			World.addAgent(9);
			Agent hungryAgent = (Agent) World.agents.get(0);
			hungryAgent.setHunger(5);
		}
		
		private void testSurvival(){
			World.addAgent(9);
			Agent hungryAgent = (Agent) World.agents.get(0);
			hungryAgent.setHunger(200);
		}

		private void testTreeHarvest() {
			World.addAgent(9);
			World.terrain.setTile(Tile.Dirt, 20, 24);
			TaskList.addTask(new HarvestTreeTask((Agent) World.agents.get(0),
					new Point(18, World.terrain.getAltitude(18) - 1),
					World.terrain));
			TaskList.addTask(new HarvestTreeTask((Agent) World.agents.get(0),
					new Point(18, World.terrain.getAltitude(18) - 1),
					World.terrain));
			TaskList.addTask(new HarvestTreeTask((Agent) World.agents.get(0),
					new Point(18, World.terrain.getAltitude(18) - 1),
					World.terrain));
			TaskList.addTask(new HarvestTreeTask((Agent) World.agents.get(0),
					new Point(18, World.terrain.getAltitude(18) - 1),
					World.terrain));
			TaskList.addTask(new HarvestTreeTask((Agent) World.agents.get(0),
					new Point(18, World.terrain.getAltitude(18) - 1),
					World.terrain));
			TaskList.addTask(new HarvestTreeTask((Agent) World.agents.get(0),
					new Point(18, World.terrain.getAltitude(18) - 1),
					World.terrain));
		}

		private void testBuildingConstruction() {
			World.addAgent(9);
			World.terrain.setTile(Tile.Dirt, 20, 24);
			TaskList.addTask(new BuildBuildingTask(EBuilding.FARM, new Point(
					25, 26)));
		}

		private void testHungerMovement() {
			World.addBuilding(EBuilding.FARM, new Point(29, 27));
			World.addAgent(9);
			World.terrain.setTile(Tile.Dirt, 20, 24);
			Agent hungryAgent = (Agent) World.agents.get(0);
			hungryAgent.setHunger(200);
		}

		private void testTerrainUpdate() {
			Runnable r = new Runnable() {
				public void run() {
					try {
						for (int i = 0; i < 100; i += 2) {
							World.terrain.updateTile(Tile.Stone, i, 0);
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
