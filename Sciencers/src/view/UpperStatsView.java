package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Entity;
import model.World;
import model.agent.AgentReplacement;
import model.inventory.Inventory;
import model.inventory.Resource;

public class UpperStatsView extends JPanel {
	ArrayList<Entity> agents;
	ArrayList<Inventory> inventories;
	private HashMap<Tile, Image> textures;

	public UpperStatsView() {
		agents = new ArrayList<Entity>();
		inventories = new ArrayList<Inventory>();
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
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.drawString("Inventories:", 0, 10);
		for (int i = 0; i < agents.size(); i++) {
			ArrayList agentInv = changeToList(inventories.get(i));
//			System.out.println(agentInv.get(0));
			for (int j = 0; j < agentInv.size(); j++) {
				g2.drawImage(textures.get(agentInv.get(j)), j * 16, i * 16 + 40,
						null);
			}
//			g2.drawString("", x, y);
		}
	}

	private ArrayList<Tile> changeToList(Inventory inventory) {
		ArrayList<Tile> agentStuff = new ArrayList<Tile>();
		for (Resource r : Resource.values()) {
			int quantity = inventory.getAmount(r);
			for (int i = 0; i < quantity; i++) {
				if (r.equals(Resource.FOOD)){
					agentStuff.add(Tile.Leaves);
				} else if (r.equals(Resource.WOOD)){
					agentStuff.add(Tile.Wood);
				} else if (r.equals(Resource.IRON)){
					agentStuff.add(Tile.Iron);
				} else if (r.equals(Resource.URANIUM)){
					agentStuff.add(Tile.Uranium);
				}
			}
		}
		return agentStuff;
	}

	public void performUpdate() {
//		if (!agents.equals(null)) {
//			agents = World.agents;
//		}
		agents = World.agents;
		for (Entity e : agents) {
			inventories.add(((AgentReplacement) e).getInventory());
		}

		repaint();
	}

}
