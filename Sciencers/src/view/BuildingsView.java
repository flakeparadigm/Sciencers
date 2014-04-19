package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Entity;
import model.World;

public class BuildingsView extends JPanel{
	private World world;
	private Image placeholder;
	
	public BuildingsView(World world){
		this.world = world;
		
		try {
			placeholder = ImageIO.read(new File("imgs/Building.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (Entity e : world.getBuildings()) {
			g2.drawImage(placeholder, (int) (WorldView.TILE_SIZE*(e.getPos().getX()+1)), (int) (WorldView.TILE_SIZE*(e.getPos().getY()+1)), null);
//			g2.drawString("B", WorldView.TILE_SIZE*(e.getPos().x + 1), WorldView.TILE_SIZE*(e.getPos().y + 1));
		}
	}

	public void update() {
		repaint();
	}
}
