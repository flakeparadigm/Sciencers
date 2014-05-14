package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Entity;
import model.World;
import model.agent.Agent;
import model.agent.EAgent;
import model.inventory.Inventory;
import model.inventory.Resource;
import model.task.GatherResources;
import model.task.TaskList;

public class UpperStatsView extends JPanel {
	ArrayList<Entity> agents;
	ArrayList<Inventory> inventories;
	private HashMap<Tile, Image> textures;

	private JButton fireButton1;
	private JButton fireButton2;
	private JButton fireButton3;
	private JButton fireButton4;
	private JButton fireButton5;
	private JButton fireButton6;
	private JButton fireButton7;

	private final int BUTTON_OFFSET = 100;
	// button width is 80
	private final int HUNGER_OFFSET = 0;
	private final int INV_OFFSET = 210;
	private final int TYPE_OFFSET = 180;

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

		addComponents();
	}

	private void addComponents() {
		this.setLayout(null);

		fireButton1 = new JButton("Fire Me!");
		fireButton2 = new JButton("Fire Me!");
		fireButton3 = new JButton("Fire Me!");
		fireButton4 = new JButton("Fire Me!");
		fireButton5 = new JButton("Fire Me!");
		fireButton6 = new JButton("Fire Me!");
		fireButton7 = new JButton("Fire Me!");

		fireButton1.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton1.setSize(80, 15);
		fireButton1.setLocation(BUTTON_OFFSET, 40);
		fireButton1.addActionListener(new ButtonListener());
		this.add(fireButton1);

		fireButton2.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton2.setSize(80, 15);
		fireButton2.setLocation(BUTTON_OFFSET, 40 + WorldView.TILE_SIZE);
		fireButton2.addActionListener(new ButtonListener());
		this.add(fireButton2);

		fireButton3.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton3.setSize(80, 15);
		fireButton3.setLocation(BUTTON_OFFSET, 40 + (2 * WorldView.TILE_SIZE));
		fireButton3.addActionListener(new ButtonListener());
		this.add(fireButton3);

		fireButton4.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton4.setSize(80, 15);
		fireButton4.setLocation(BUTTON_OFFSET, 40 + (3 * WorldView.TILE_SIZE));
		fireButton4.addActionListener(new ButtonListener());
		this.add(fireButton4);

		fireButton5.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton5.setSize(80, 15);
		fireButton5.setLocation(BUTTON_OFFSET, 40 + (4 * WorldView.TILE_SIZE));
		fireButton5.addActionListener(new ButtonListener());
		this.add(fireButton5);

		fireButton6.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton6.setSize(80, 15);
		fireButton6.setLocation(BUTTON_OFFSET, 40 + (5 * WorldView.TILE_SIZE));
		fireButton6.addActionListener(new ButtonListener());
		this.add(fireButton6);

		fireButton7.setFont(new Font("Verdana", Font.PLAIN, 10));
		fireButton7.setSize(80, 15);
		fireButton7.setLocation(BUTTON_OFFSET, 40 + (6 * WorldView.TILE_SIZE));
		fireButton7.addActionListener(new ButtonListener());
		this.add(fireButton7);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// g2.drawString("Inventories:", 0, 10);
		for (int i = 0; i < agents.size(); i++) {
			ArrayList agentInv = changeToList(inventories.get(i));
			for (int j = 0; j < agentInv.size(); j++) {
				g2.drawImage(textures.get(agentInv.get(j)), INV_OFFSET + j
						* WorldView.TILE_SIZE, i * WorldView.TILE_SIZE + 40,
						null);
			}
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Verdana", Font.BOLD, 20));
			g2.drawString(((Agent) agents.get(i)).getType().getName()
					.substring(0, 1), TYPE_OFFSET, i * WorldView.TILE_SIZE + 56);
			g2.drawString("" + ((Agent) agents.get(i)).getHunger(), HUNGER_OFFSET, i * WorldView.TILE_SIZE + 56);
			
			//headings:
			g2.setFont(new Font("Verdana", Font.PLAIN, 15));
			g2.drawString("Hunger", HUNGER_OFFSET, 35);

			
			
			setCorrectButtonsVisible();
		}
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == fireButton1) {
				((Agent) agents.get(0)).setDead();
				System.out.println("1");
			}
			if (e.getSource() == fireButton2) {
				((Agent) agents.get(1)).setDead();
				System.out.println("2");
			}
			if (e.getSource() == fireButton3) {
				((Agent) agents.get(2)).setDead();

			}
			if (e.getSource() == fireButton4) {
				((Agent) agents.get(3)).setDead();

			}
			if (e.getSource() == fireButton5) {
				((Agent) agents.get(4)).setDead();

			}
			if (e.getSource() == fireButton6) {
				((Agent) agents.get(5)).setDead();

			}
			if (e.getSource() == fireButton7) {
				((Agent) agents.get(6)).setDead();

			}
		}
	}

	private void setCorrectButtonsVisible() {
		// yes, this could be done better.
		if (agents.size() > 0 && ((Agent)agents.get(0)).getType() != EAgent.ROGUE) { 
			fireButton1.setVisible(true);
		} else {
			fireButton1.setVisible(false);
		}
		
		if (agents.size() > 1 && ((Agent)agents.get(1)).getType() != EAgent.ROGUE) { 
			fireButton2.setVisible(true);
		} else {
			fireButton2.setVisible(false);
		}
		
		if (agents.size() > 2 && ((Agent)agents.get(2)).getType() != EAgent.ROGUE) { 
			fireButton3.setVisible(true);
		} else {
			fireButton3.setVisible(false);
		}
		
		if (agents.size() > 3 && ((Agent)agents.get(3)).getType() != EAgent.ROGUE) { 
			fireButton4.setVisible(true);
		} else {
			fireButton4.setVisible(false);
		}
		
		if (agents.size() > 4 && ((Agent)agents.get(4)).getType() != EAgent.ROGUE) { 
			fireButton5.setVisible(true);
		} else {
			fireButton5.setVisible(false);
		}
		
		if (agents.size() > 5 && ((Agent)agents.get(5)).getType() != EAgent.ROGUE) { 
			fireButton6.setVisible(true);
		} else {
			fireButton6.setVisible(false);
		}
		
		if (agents.size() > 6 && ((Agent)agents.get(6)).getType() != EAgent.ROGUE) { 
			fireButton7.setVisible(true);
		} else {
			fireButton7.setVisible(false);
		}
	}

	private ArrayList<Tile> changeToList(Inventory inventory) {
		ArrayList<Tile> agentStuff = new ArrayList<Tile>();
		for (Resource r : Resource.values()) {
			int quantity = inventory.getAmount(r);
			for (int i = 0; i < quantity; i++) {
				if (r.equals(Resource.FOOD)) {
					agentStuff.add(Tile.Leaves);
				} else if (r.equals(Resource.WOOD)) {
					agentStuff.add(Tile.Wood);
				} else if (r.equals(Resource.IRON)) {
					agentStuff.add(Tile.Iron);
				} else if (r.equals(Resource.URANIUM)) {
					agentStuff.add(Tile.Uranium);
				}
			}
		}
		return agentStuff;
	}

	public void performUpdate() {
		// if (!agents.equals(null)) {
		// agents = World.agents;
		// }
		agents = World.agents;
		for (Entity e : agents) {
			inventories.add(((Agent) e).getInventory());
		}

		for (int i = 0; i < agents.size(); i++) {
			inventories.add(i, ((Agent) agents.get(i)).getInventory());
		}

		repaint();
	}
	
	private class FireButton extends JButton {
		private Agent agent;
		
		FireButton(Agent a) {
			super("Fire Me!");
			agent = a;
		}
		
		public Agent firedAgent() {
			return agent;
		}
	}

}
