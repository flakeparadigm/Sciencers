package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Agent;
import model.Entity;
import model.World;
import model.inventory.Tool;

public class AgentsView extends JPanel{
	private World world;
	private Image placeholder;
	private HashMap<Tool,Image> tools;
	
	public AgentsView(World world){
		this.world = world;
		tools = new HashMap<Tool,Image>();
		
		try {
			placeholder = ImageIO.read(new File("imgs/Agent.png"));
			for(Tool t : Tool.values()) {
				tools.put(t, ImageIO.read(new File("imgs/" + t + ".png")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for (Entity e : World.agents) {
			//this line of code is for testing that agents will show up in the correct location on the TextView
//			g2.drawString("A", WorldView.TILE_SIZE*(e.getPos().x + 1), WorldView.TILE_SIZE*(e.getPos().y + 1));
			
			g2.drawImage(placeholder, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			Agent a = ((Agent) e);
			if(a.workingTool != null) {
				g2.drawImage(tools.get(a.workingTool), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			}
		}
	}

	public void update() {
		repaint();
	}
}
