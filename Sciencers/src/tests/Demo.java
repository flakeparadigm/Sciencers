package tests;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.Tile;
import model.Agent;
import model.World;

public class Demo extends JFrame {
	
	// important things
	public static JFrame demoWindow;
	private static World world;
	
	// graphical components
	private DemoPanel demoPanel;

	// magic numbers
	private final int X_WINDOW_SIZE = 700;
	private final int Y_WINDOW_SIZE = 700;

	public static void main(String[] args) {
		demoWindow = new Demo();
	}

	public Demo() {
		setupProperties();
		setupModel();
		addComponents();
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
		world = new World(456789, 50, 50);
	}

	private void addComponents() {
		demoPanel = new DemoPanel(world);
		add(demoPanel);
		
		// terrainPanel = new TextView(world);
		// add(terrainPanel);
		// terrainPanel.setLocation(0, 0);
		// //TODO: figure out correct panel size
		// terrainPanel.setSize(10000, 10000);
		// terrainPanel.setOpaque(false);
		// terrainPanel.setBackground(new Color(0,0,0,0));
		//
		// agentPanel = new AgentsView(world);
		// add(agentPanel);
		// agentPanel.setLocation(0,0);
		// agentPanel.setSize(100,100);
		// agentPanel.setOpaque(false);
		// agentPanel.setBackground(new Color(0,0,0,0));
	}

	private class DemoPanel extends JPanel {
		private World world;
		
		public DemoPanel(World world) {
			this.world = world;
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			// display char array for tiles here
			Tile[][] grid = world.getTerrain().getTileArray();
			ArrayList<Agent> agents = world.getAgents();
			
			for(Agent a : agents) {
				Point aPos = a.getPos();
				grid[aPos.x][aPos.y] = Tile.Agent;
				if(aPos.y != 0)
					grid[aPos.x][aPos.y-1] = Tile.Agent;
			}
			
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					g2.drawString(Character.toString(grid[i][j].value), 10 + 10 * i, 10 + 10 * j);
				}
			}
		}
	}

}
