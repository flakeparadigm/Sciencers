package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.Entity;
import model.World;

public class BuildingsView extends JPanel{
	private World world;
	
	public BuildingsView(World world){
		this.world = world;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (Entity e : world.getBuildings()) {
			g2.drawString("B", 10 + 10 * e.getPos().x, 10 + 10 * e.getPos().y);
		}
	}
}
