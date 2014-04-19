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

public class AgentsView extends JPanel{
	private World world;
	private Image placeholder;
	
	public AgentsView(World world){
		this.world = world;
		
		try {
			placeholder = ImageIO.read(new File("imgs/Agent.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (Entity e : world.getAgents()) {
			//this line of code is for testing that agents will show up in the correct location on the TextView
			g2.drawImage(placeholder, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
//			g2.drawString("A", WorldView.TILE_SIZE*(e.getPos().x + 1), WorldView.TILE_SIZE*(e.getPos().y + 1));
		}
	}

	public void update() {
		repaint();
	}
}
