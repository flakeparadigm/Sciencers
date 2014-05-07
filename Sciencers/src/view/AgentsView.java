package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Entity;
import model.World;
import model.agent.AgentReplacement;
import model.agent.FarmerAgent;
import model.agent.MinerAgent;
import model.agent.SciencerAgent;
import model.inventory.Tool;

public class AgentsView extends JPanel{
	private Image placeholder;
	
	private Image farmer;
	private Image sciencer;
	private Image miner;
	
	private HashMap<Tool,Image> tools;
	
	public AgentsView(){
		tools = new HashMap<Tool,Image>();
		
		try {
			placeholder = ImageIO.read(new File("imgs/Agent.png"));
			
			farmer = ImageIO.read(new File("imgs/FarmerAgent16.png"));
			sciencer = ImageIO.read(new File("imgs/SciencerAgent16.png"));
			miner = ImageIO.read(new File("imgs/MinerAgent16.png"));
			
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
			
//			g2.drawImage(placeholder, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			
			if(e instanceof FarmerAgent) {
				g2.drawImage(farmer, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			} else if(e instanceof SciencerAgent) {
				g2.drawImage(sciencer, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			} else if(e instanceof MinerAgent) {
				g2.drawImage(miner, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			} else {
				g2.drawImage(placeholder, (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
				System.out.println("AgentsView is drawing a generic agent. Why does this exist?");
			}
			
			AgentReplacement a = ((AgentReplacement) e);
			if(a.workingTool != null) {
				g2.drawImage(tools.get(a.workingTool), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getX() + 1)), (int) (((double)WorldView.TILE_SIZE)*(e.getPos().getY() + 1)), null);
			}
		}
	}

	public void update() {
		repaint();
	}
}
