package tests;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.World;
import model.agent.Agent;
import model.agent.EAgent;
import model.building.BuildingFactory;
import model.building.EBuilding;
import model.task.BuildBuildingTask;
import model.task.ChangeTileTask;
import model.task.GoMineAreaTask;
import model.task.HarvestTreeTask;
import model.task.TaskList;
import view.Tile;
import view.WorldView;

public class Demo extends JFrame {

	// important things
	// private static World World;
	static Toolkit tk = Toolkit.getDefaultToolkit();

	// Graphical elements
	private static WorldView worldView;
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
	private JButton testGatherAndStoreButton = new JButton("Gather and Store Food");
	private JButton testMineTile = new JButton("Mine Tiles");
	private JButton testMineArea = new JButton("Mine Area");
	
	private JButton testSimMineAreaButton = new JButton("Mine Area w/ sim");

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
//		setupModel();
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

//	private void setupModel() {
//		// World = WorldView.getWorld();
//		// for example: add agent and farm
//	}

	private void addComponents() {
		this.add(resetWorldButton);
		this.add(testTerrainUpdateButton);
		this.add(testHungerMovementButton);
		this.add(testBuildingConstructionButton);
		this.add(testTreeHarvestButton);
		this.add(testSurvivalButton);
		this.add(testHungerDeathButton);
		this.add(testGatherAndStoreButton);
		this.add(testMineTile);
		this.add(testMineArea);
		this.add(testSimMineAreaButton);
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
		testGatherAndStoreButton.addActionListener(new demoButtonListener());
		testMineTile.addActionListener(new demoButtonListener());
		testMineArea.addActionListener(new demoButtonListener());
		testSimMineAreaButton.addActionListener(new demoButtonListener());
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
			if (event.getSource() == testGatherAndStoreButton)
				testGatherAndStore();
			if (event.getSource() == testMineTile)
				testMineTile();
			if (event.getSource() == testMineArea)
				testMineArea();
			if (event.getSource() == testSimMineAreaButton)
				testSimMineArea();
		}

		private void resetWorld() {
			World.reset();
			worldView.updateAll();
		}
		
		private void testMineArea(){
			World.addAgent(EAgent.MINER, 9);
			TaskList.addTask(new GoMineAreaTask(new Point(16, 28), new Point(25, 30)), EAgent.MINER);
		}
		
		private void testHungerDeath(){
			World.terrain.setTile(Tile.Dirt, 38, 31);
			World.terrain.setTile(Tile.Dirt, 38, 30);
			World.terrain.setTile(Tile.Dirt, 38, 29);
			World.terrain.setTile(Tile.Dirt, 38, 28);
			World.terrain.setTile(Tile.Dirt, 38, 27);
			World.terrain.setTile(Tile.Dirt, 37, 27);
			World.terrain.setTile(Tile.Dirt, 35, 27);
			World.terrain.setTile(Tile.Dirt, 34, 27);
			World.terrain.setTile(Tile.Dirt, 33, 27);
			
			World.addAgent(EAgent.FARMER, 36);

			Agent hungryAgent = (Agent) World.agents.get(0);
			hungryAgent.setHunger(5);
		}
		
		private void testMineTile(){
			World.addAgent(EAgent.MINER, 9);
			World.addAgent(EAgent.MINER, 15);
			World.addAgent(EAgent.MINER, 14);
			Agent miningAgent = (Agent) World.agents.get(0);
//			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(10, 21), Tile.Sky), EAgent.MINER);
//			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(10, 22), Tile.Sky), EAgent.MINER);
//			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(10, 23), Tile.Sky), EAgent.MINER);
			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(10, 30), Tile.Sky), EAgent.MINER);
			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(6, 50), Tile.Sky), EAgent.MINER);
			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(10, 21), Tile.Sky), EAgent.MINER);
			TaskList.addTask(new ChangeTileTask(miningAgent, new Point(10, 27), Tile.Sky), EAgent.MINER);
		}
		
		private void testGatherAndStore(){
			World.addAgent(EAgent.FARMER, 9);
			World.terrain.setTile(Tile.Dirt, 20, 24);
			TaskList.addTask(new BuildBuildingTask(EBuilding.FARM, new Point(25, 26)));
			Agent gatherAgent = (Agent) World.agents.get(0);
			System.out.println("Unfinished");
//			gatherAgent.setToGather();
		}
		
		private void testSurvival(){
			World.addAgent(EAgent.FARMER, 9);
			Agent hungryAgent = (Agent) World.agents.get(0);
			hungryAgent.setHunger(200);
			
		}

		private void testTreeHarvest() {
			World.addAgent(EAgent.FARMER, 9);
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
			World.addAgent(EAgent.FARMER, 9);
			World.terrain.setTile(Tile.Dirt, 20, 24);
//			TaskList.addTask(new BuildBuildingTask(EBuilding.FARM, new Point(
//					25, 26)), EAgent.GENERIC);
			TaskList.addTask(new BuildBuildingTask(BuildingFactory.makeBuilding("Farm", new Point(25, 26))), EAgent.GENERIC);
		}

		private void testHungerMovement() {
//			World.addBuilding(EBuilding.FARM, new Point(29, 27));
			World.addBuilding(BuildingFactory.makeBuilding("Farm", new Point(29, 27)));
			World.addAgent(EAgent.FARMER, 17);
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
		
		private void testSimMineArea() {
			TaskList.addTask(new GoMineAreaTask(new Point(10, 50), new Point(20, 60)), EAgent.MINER);
		}

	}
}
