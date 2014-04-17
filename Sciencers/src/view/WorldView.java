package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.World;

/* I thought it might be nice to have a working view for testing 
 * the map generation - so here is a start for this class
 */

public class WorldView extends JFrame {
	public static JFrame gameWindow;
	public static TextView terrainPanel;
	public static AgentsView agentPanel;
	public static World world;

	static Toolkit tk = Toolkit.getDefaultToolkit();
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_WINDOW_SIZE = 700;
	private final int Y_WINDOW_SIZE = 700;

	private static int moveSpeed = 15;
	private static int panTimerMS = 1;
	private static int xPanelLocation = 0;
	private static int yPanelLocation = 0;

	public static void main(String[] args) {
		gameWindow = new WorldView();
		gameWindow.setVisible(true);
	}

	public WorldView() {
		setupProperties();
		setupModel();
		addComponents();
		registerListeners();
	}

	private void setupProperties() {
		// in the future, we may want to make the window either maximized or
		// scaled to screen size (or both, which would be nice)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sciencers");
		setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		setLayout(null);
		setLocation(50, 50);
	}

	private void setupModel() {
		world = new World();
	}

	private void addComponents() {
		terrainPanel = new TextView(world);
		add(terrainPanel);
		terrainPanel.setLocation(0, 0);
		//TODO: figure out correct panel size
		terrainPanel.setSize(10000, 10000);
		terrainPanel.setOpaque(false);
		terrainPanel.setBackground(new Color(0,0,0,0));
		
		agentPanel = new AgentsView(world);
		add(agentPanel);
		agentPanel.setLocation(0,0);
		agentPanel.setSize(100,100);
		agentPanel.setOpaque(false);
		agentPanel.setBackground(new Color(0,0,0,0));
	}

	private void registerListeners() {
		// These will allow the user to pan around the panel
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
		
		//up and down control
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
		KeyStroke upReleased = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,
				true);
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
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
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
			xPanelLocation-= moveSpeed;
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
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
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
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
			yPanelLocation-= moveSpeed;
			terrainPanel.setLocation(xPanelLocation, yPanelLocation);
			agentPanel.setLocation(xPanelLocation, yPanelLocation);
		}
	};
	static Timer downButtonTimer = new Timer(panTimerMS, downTimerAction);

	public static void downRelease() {
		// right released code here:
		downButtonTimer.stop();
	}

	public void update() {
		// here we should update all relevant panels with world info
		terrainPanel.update(world);
	}
	
	public World getWorld() {
		return world;
	}
}
