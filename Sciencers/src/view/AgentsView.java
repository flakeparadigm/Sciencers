package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.World;

public class AgentsView extends JPanel{
	private World world;
	
	public AgentsView(World world){
		this.world = world;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (int i = 0; i<world.getAgents().size(); i++){
			//this line of code is for testing that agents will show up in the correct location on the TextView
			g2.drawString("A", 10 + 10 * world.getAgents().get(i).getPos().x, 10 + 10 * world.getAgents().get(i).getPos().y);
		}
		
		for (int i = 0; i<world.getBuildings().size(); i++){
			g2.drawString("B", 10 + 10 * world.getBuildings().get(i).getPos().x, 10 + 10 * world.getBuildings().get(i).getPos().y);
		}
	}
}
