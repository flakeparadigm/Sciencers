package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.WinChecker;
import model.World;
import controller.InfoObserver;
import controller.SciencersObserver;

/* I thought it might be nice to have a working view for testing 
 * the map generation - so here is a start for this class
 */

public class WorldView extends JFrame {
	public static JFrame gameWindow;
	public static World world;

	// panels
	public static TerrainView terrainPanel;
	public static AgentsView agentPanel;
	public static BuildingsView buildingPanel;
	public static InfoPanes infoPanes;
	public static SelectionView selectionPanel;
	public static UpperStatsView upperStatsPanel;

	public static SciencersObserver terrainWatch;
	public static InfoObserver infoWatch;

	// boolean
	public static boolean doCreateWorld;
	// public static boolean loadWorldBool;

	// Magic Numbers
	static Toolkit tk = Toolkit.getDefaultToolkit();
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private static int X_WINDOW_SIZE = 1200;
	private static int Y_WINDOW_SIZE = 700;

	private final static int X_MAP_SIZE = 1500;
	private final static int Y_MAP_SIZE = 600;
	public final static int TILE_SIZE = 16;
	public final static int INFO_PANE_SIZE = 200;
	public final static int X_UPPER_STATS_SIZE = 150;
	public final static int Y_UPPER_STATS_SIZE = 200;

	private static final String SAVE_LOCATION = "world.save";

	private static int moveSpeed = 8;// not 15
	private static int panTimerMS = 1;
	private static int xPanelLocation = -X_MAP_SIZE / 2 * TILE_SIZE;
	private static int yPanelLocation = -30 * TILE_SIZE;

//	public static void main(String[] args) {
//		System.out
//				.println("Wouldn't it be nice to launch from the pretty new menu screen?");
//		gameWindow = new WorldView();
//		gameWindow.setVisible(true);
//	}

	public WorldView() {
		gameWindow = this;
		setupObservers();
		setupProperties();
		setupModel();
		addComponents();
		registerListeners();

		World.startTicks();
		if (doCreateWorld)
			World.giveStarter();
		
		new Thread(new WinChecker()).start();
		System.out.println("WorldView constructed, WinChecker running");

	}

	private void setupObservers() {
		terrainWatch = new SciencersObserver(this);
		infoWatch = new InfoObserver(this);
		System.out.println("Observers set up");
	}

	private void setupProperties() {
		// in the future, we may want to make the window either maximized or
		// scaled to screen size (or both, which would be nice)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowClosingListener());
		setTitle("Sciencers");
		setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		setLayout(null);
		setLocation(50, 50);
		
		System.out.println("Properties set");

		// this.addComponentListener(new ComponentAdapter() {
		// @Override

		// public void componentResized(ComponentEvent e)
		// {
		// infoPanes.setSize(gameWindow.getWidth(), INFO_PANE_SIZE);
		// infoPanes.setLocation(0,gameWindow.getHeight()-INFO_PANE_SIZE-39);
		// infoPanes.repaint();
		// gameWindow.repaint();
		// }
		// });
	}

	private void setupModel() {
		// int loadWorld = JOptionPane.showConfirmDialog(gameWindow,
		// "Would you like to load the previous game?", "Load Game?",
		// JOptionPane.YES_NO_OPTION);
		//
		// if (loadWorld == JOptionPane.YES_OPTION) {
		// doCreateWorld = false;
		// loadSavedWorld();
		// } else {
		// doCreateWorld = true;
		// makeNewWorld();
		// }

		// if (loadWorldBool) {
		// doCreateWorld = false;
		// loadSavedWorld();
		// } else {
		// doCreateWorld = true;
		// makeNewWorld();
		// }

		if (doCreateWorld) {
			makeNewWorld();
		} else {
			loadSavedWorld();
		}
	}

	private void addComponents() {

		upperStatsPanel = new UpperStatsView();
		add(upperStatsPanel);
		upperStatsPanel.setLocation(X_WINDOW_SIZE - X_UPPER_STATS_SIZE,
				Y_WINDOW_SIZE - Y_UPPER_STATS_SIZE);
		upperStatsPanel.setSize(150, 200);
		upperStatsPanel.setOpaque(false);
		upperStatsPanel.setBackground(new Color(0, 0, 0, 0));

		infoPanes = new InfoPanes();
		add(infoPanes);
		infoPanes.setSize(X_WINDOW_SIZE, INFO_PANE_SIZE);
		infoPanes.setLocation(0, Y_WINDOW_SIZE - INFO_PANE_SIZE - 39);

		selectionPanel = new SelectionView();
		add(selectionPanel);
		selectionPanel.setSize(X_MAP_SIZE * TILE_SIZE, Y_MAP_SIZE * TILE_SIZE);
		selectionPanel.setOpaque(false);
		selectionPanel.setBackground(new Color(0, 0, 0, 0));

		agentPanel = new AgentsView();
		add(agentPanel);
		agentPanel.setLocation(xPanelLocation, yPanelLocation);
		agentPanel.setSize(X_MAP_SIZE * TILE_SIZE, Y_MAP_SIZE * TILE_SIZE);
		agentPanel.setOpaque(false);
		agentPanel.setBackground(new Color(0, 0, 0, 0));

		buildingPanel = new BuildingsView();
		add(buildingPanel);
		buildingPanel.setLocation(xPanelLocation, yPanelLocation);
		buildingPanel.setSize(X_MAP_SIZE * TILE_SIZE, Y_MAP_SIZE * TILE_SIZE);
		buildingPanel.setOpaque(false);
		buildingPanel.setBackground(new Color(0, 0, 0, 0));

		terrainPanel = new TerrainView(new Point(-xPanelLocation / TILE_SIZE,
				-yPanelLocation / TILE_SIZE));
		add(terrainPanel);
		terrainPanel.setLocation(xPanelLocation, yPanelLocation);
		// TODO: figure out correct panel size
		terrainPanel.setSize(X_MAP_SIZE * TILE_SIZE, Y_MAP_SIZE * TILE_SIZE);
		terrainPanel.setOpaque(false);
		terrainPanel.setBackground(new Color(0, 0, 0, 0));

	}

	private void registerListeners() {
		// These will allow the user to pan around the panel

		// Left movement with Left Arrow and A keys.
		KeyStroke left = KeyStroke.getKeyStroke("LEFT");
		KeyStroke a = KeyStroke.getKeyStroke("A");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(left,
				"moveLeft");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(a,
				"moveLeft");
		getRootPane().getActionMap().put("moveLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leftPress();
			}
		});
		KeyStroke leftReleased = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,
				true);
		KeyStroke aReleased = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				leftReleased, "releaseLeft");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				aReleased, "releaseLeft");
		getRootPane().getActionMap().put("releaseLeft", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leftRelease();
			}
		});

		// Right movement with Right Arrow and D keys.
		KeyStroke right = KeyStroke.getKeyStroke("RIGHT");
		KeyStroke d = KeyStroke.getKeyStroke("D");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(right,
				"moveRight");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(d,
				"moveRight");
		getRootPane().getActionMap().put("moveRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightPress();
			}
		});
		KeyStroke rightReleased = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0,
				true);
		KeyStroke dReleased = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				rightReleased, "releaseRight");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				dReleased, "releaseRight");
		getRootPane().getActionMap().put("releaseRight", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightRelease();
			}
		});

		// Up movement with Up Arrow and W keys.
		KeyStroke up = KeyStroke.getKeyStroke("UP");
		KeyStroke w = KeyStroke.getKeyStroke("W");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(up,
				"moveUp");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(w,
				"moveUp");
		getRootPane().getActionMap().put("moveUp", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upPress();
			}
		});
		KeyStroke upReleased = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
		KeyStroke wReleased = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				upReleased, "releaseUp");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				wReleased, "releaseUp");
		getRootPane().getActionMap().put("releaseUp", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upRelease();
			}
		});

		// Down movement with Down Arrow and S keys.
		KeyStroke down = KeyStroke.getKeyStroke("DOWN");
		KeyStroke s = KeyStroke.getKeyStroke("S");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(down,
				"moveDown");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(s,
				"moveDown");
		getRootPane().getActionMap().put("moveDown", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downPress();
			}
		});
		KeyStroke downReleased = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,
				true);
		KeyStroke sReleased = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				downReleased, "releaseDown");
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				sReleased, "releaseDown");
		getRootPane().getActionMap().put("releaseDown", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				downRelease();
			}
		});
	}

	public static void leftPress() {
		leftButtonTimer.start();
	}

	static ActionListener leftTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// continuous left pressed code here:
			xPanelLocation += moveSpeed;
			if (xPanelLocation > 0)
				xPanelLocation = 0;
			updateTerrain();
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
			buildingPanel.setLocation(xPanelLocation, yPanelLocation);
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			selectionPanel.setLocation(xPanelLocation, yPanelLocation);
		}
	};
	static Timer leftButtonTimer = new Timer(panTimerMS, leftTimerAction);

	public static void leftRelease() {
		// left released code here:
		leftButtonTimer.stop();
	}

	public static void rightPress() {
		rightButtonTimer.start();
	}

	static ActionListener rightTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// continuous right pressed code here:
			xPanelLocation -= moveSpeed;
			int maxX = -(X_MAP_SIZE * TILE_SIZE - gameWindow.getContentPane()
					.getWidth());
			if (xPanelLocation < maxX)
				xPanelLocation = maxX;
			updateTerrain();
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
			buildingPanel.setLocation(xPanelLocation, yPanelLocation);
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			selectionPanel.setLocation(xPanelLocation, yPanelLocation);
		}
	};
	static Timer rightButtonTimer = new Timer(panTimerMS, rightTimerAction);

	public static void rightRelease() {
		// right released code here:
		rightButtonTimer.stop();
	}

	public static void upPress() {
		upButtonTimer.start();
	}

	static ActionListener upTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// continuous up pressed code here:
			yPanelLocation += moveSpeed;
			if (yPanelLocation > 0)
				yPanelLocation = 0;

			updateTerrain();
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
			buildingPanel.setLocation(xPanelLocation, yPanelLocation);
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			selectionPanel.setLocation(xPanelLocation, yPanelLocation);
		}
	};
	static Timer upButtonTimer = new Timer(panTimerMS, upTimerAction);

	public static void upRelease() {
		// left released code here:
		upButtonTimer.stop();
	}

	public static void downPress() {
		downButtonTimer.start();
	}

	static ActionListener downTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// continuous down pressed code here:
			yPanelLocation -= moveSpeed;
			int maxY = -(Y_MAP_SIZE * TILE_SIZE - gameWindow.getContentPane()
					.getHeight());
			if (yPanelLocation < maxY)
				yPanelLocation = maxY;
			updateTerrain();
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
			buildingPanel.setLocation(xPanelLocation, yPanelLocation);
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			selectionPanel.setLocation(xPanelLocation, yPanelLocation);
		}
	};
	static Timer downButtonTimer = new Timer(panTimerMS, downTimerAction);

	public static void downRelease() {
		// right released code here:
		downButtonTimer.stop();
	}

	// UPDATE METHODS for updating the view panels.
	public void updateAll() {
		// here we should update all relevant panels with world info
		updateTerrain();
		updateAgents();
		updateBuildings();
		updateInfo();
	}

	public static void updateTerrain() {
		terrainPanel.update(new Point(-xPanelLocation / TILE_SIZE,
				-yPanelLocation / TILE_SIZE));
	}

	public void updateAgents() {
		agentPanel.update();
	}

	public void updateBuildings() {
		buildingPanel.update();
	}

	public void updateInfo() {
		infoPanes.performUpdate();
		upperStatsPanel.performUpdate();
	}

	// CREATE, LOAD, AND SAVE WORLDS

	private void makeNewWorld() {
		world = new World(12345, X_MAP_SIZE, Y_MAP_SIZE);
		System.out.println("New world created");
	}

	private void loadSavedWorld() {
		File file = new File(SAVE_LOCATION);
		if (!file.exists()) {
			JOptionPane.showMessageDialog(gameWindow,
					"No save file was found.\nSorry.");
			return;
		}

		try {
			FileInputStream loadFile = new FileInputStream(SAVE_LOCATION);
			ObjectInputStream loadWorld = new ObjectInputStream(loadFile);

			world = (World) loadWorld.readObject();
			world.loadSaveables();

			loadWorld.close();
			loadFile.close();
			System.out.println("Loaded saved world");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(gameWindow,
					"Save file failed to load.\nSorry.");
			makeNewWorld();
		}
	}

	//
	// getWorld method. Should only be used in the Demo tool right now.
	public World getWorld() {
		return world;
	}

	// Save the world on exit!
	private class WindowClosingListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			World.stopTicks();

			int shouldSave = JOptionPane.showConfirmDialog(gameWindow,
					"Would you like to save your game?", "Save Game?",
					JOptionPane.YES_NO_OPTION);
			// If the user wanted to save the game, save it! :)
			if (shouldSave == JOptionPane.YES_OPTION) {
				File file = new File(SAVE_LOCATION);
				if (!file.exists()) {
					try {
						// Try creating the file
						file.createNewFile();
					} catch (IOException ioe) {
						ioe.printStackTrace();
						JOptionPane
								.showMessageDialog(gameWindow,
										"Failed to save. (Could not create save file)\nSorry.");
					}
				}

				try {
					FileOutputStream saveFile = new FileOutputStream(
							SAVE_LOCATION);
					ObjectOutputStream saveWorld = new ObjectOutputStream(
							saveFile);

					world.makeSaveable();
					saveWorld.writeObject(world);
					saveWorld.flush();

					saveWorld.close();
					saveFile.close();

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane
							.showMessageDialog(gameWindow,
									"Failed to save. (Could not write to save file)\nSorry.");
				}
			}
		}
	}

	public static String getSaveLocation() {
		return SAVE_LOCATION;
	}

	public static void win() {
		World.stopTicks();
		JOptionPane
				.showMessageDialog(gameWindow, "You won the game!\nCongrats!");
		System.exit(0);
	}

	public static void lose() {
		World.stopTicks();
		JOptionPane
				.showMessageDialog(gameWindow, "You lost the game!\nThat sucks!");
		System.exit(0);
	}
}
