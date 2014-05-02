package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.World;

public class TerrainView extends JPanel{

	private HashMap<Tile, Image> textures;
	
	public TerrainView() {		
		textures = new HashMap<Tile, Image>();
		try {
			for(Tile t : Tile.values()) {
				String s = "imgs/" + t + ".png";
				Image i = ImageIO.read(new File(s));
				textures.put(t, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		Tile[][] grid = World.terrain.getTileArray();
		for (int i = 0; i< grid.length; i++){
			for (int j = 0; j< grid[0].length; j++){
				g2.drawImage(textures.get(grid[i][j]), WorldView.TILE_SIZE*(i+1), WorldView.TILE_SIZE*(j+1), null);
//				g2.drawString(Character.toString(grid[i][j].value), 10 + 10*i, 10 + 10*j);
			}
		}
	}

	public void update() {
		repaint();
	}

}