package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.World;

public class TerrainView extends JPanel {

	private HashMap<Tile, Image> textures;
	private Point viewLocation;
	static Toolkit tk = Toolkit.getDefaultToolkit();
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_TILES = X_SCREEN_SIZE / 16;
	private final int Y_TILES = Y_SCREEN_SIZE / 16;
	private final int BUFFER = 1;

	public TerrainView(Point viewLocation) {
		textures = new HashMap<Tile, Image>();
		try {
			for (Tile t : Tile.values()) {
				String s = "imgs/" + t + ".png";
				Image i = ImageIO.read(new File(s));
				textures.put(t, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.viewLocation = viewLocation;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// display char array for tiles here
		Tile[][] grid = World.terrain.getTileArray();
//		for (int i = 0; i < grid.length; i++) {
//			for (int j = 0; j < grid[0].length; j++) {
//				g2.drawImage(textures.get(grid[i][j]), WorldView.TILE_SIZE
//						* (i + 1), WorldView.TILE_SIZE * (j + 1), null);
//			}
//		}
		for (int i = -BUFFER; i < X_TILES + BUFFER; i++) {
			for (int j = -BUFFER; j < Y_TILES + BUFFER; j++) {
				try {
					g2.drawImage(textures.get(grid[viewLocation.x + i][viewLocation.y + j]), WorldView.TILE_SIZE
					* (viewLocation.x + i + 1), WorldView.TILE_SIZE * (viewLocation.y + j + 1), null);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("out!");
				}
			}
		}
	}

	public void update(Point viewLocation) {
		this.viewLocation = viewLocation;
		repaint();
	}

}