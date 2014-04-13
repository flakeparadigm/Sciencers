package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.OurObserver;
import model.World;

public class TextView extends JPanel{

	private World world;
	
	public TextView(World world) {
		this.world = world;
		
		// load any necessary media here: (probably not necessary for the text
		// view, but will be used by the game view
		// String baseDir = System.getProperty("user.dir")
		// + System.getProperty("file.separator") + "src"
		// + System.getProperty("file.separator") + "media"
		// + System.getProperty("file.separator");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//display char array for tiles here
		Tile[][] grid = world.getTerrain().getTileArray();
		for (int i = 0; i< grid.length; i++){
			for (int j = 0; j< grid[0].length; j++){
				g2.drawString(Character.toString(grid[i][j].value), 10 + 10*i, 10 + 10*j);
			}
		}
	}

	public void update(World world) {
		this.world = world;
		repaint();
	}

}