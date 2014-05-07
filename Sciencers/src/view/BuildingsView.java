package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Entity;
import model.World;
import model.building.Building;
import model.building.EBuilding;
import model.inventory.Resource;

public class BuildingsView extends JPanel {
	private Image farm;
	private Image warehouse;

	public BuildingsView() {
		try {
			farm = ImageIO.read(new File("imgs/Farm.png"));
			warehouse = ImageIO.read(new File("imgs/Warehouse.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Entity e : World.buildings) {
			if (((Building) e).getType() == EBuilding.FARM) {
				g2.drawImage(farm, (int) (WorldView.TILE_SIZE * (e.getPos()
						.getX() + 1)), (int) (WorldView.TILE_SIZE
						* (e.getPos().getY() + 1) - farm.getHeight(null)) + 16,
						null);
				g2.setFont(new Font("Verdana", Font.BOLD, 10));
				g2.setColor(Color.WHITE);
				g2.drawString("FOOD: " + ((Building)e).getInventory().getAmount(Resource.FOOD), (int) (WorldView.TILE_SIZE * (e.getPos()
						.getX() + 1)), (int) (WorldView.TILE_SIZE
						* (e.getPos().getY() - 1) - farm.getHeight(null)) + 16);
			}
			else if(((Building) e).getType() == EBuilding.WAREHOUSE) {
				g2.drawImage(warehouse, (int) (WorldView.TILE_SIZE * (e.getPos()
						.getX() + 1)), (int) (WorldView.TILE_SIZE
						* (e.getPos().getY() + 1) - warehouse.getHeight(null)) + 16,
						null);
				g2.setFont(new Font("Verdana", Font.BOLD, 10));
				g2.setColor(Color.WHITE);
				g2.drawString("STORED: " + ((Building)e).getInventory().getTotal(), (int) (WorldView.TILE_SIZE * (e.getPos()
						.getX() + 1)), (int) (WorldView.TILE_SIZE
						* (e.getPos().getY() - 1) - warehouse.getHeight(null)) + 16);
			}
			
			
			// g2.drawString("B", WorldView.TILE_SIZE*(e.getPos().x + 1),
			// WorldView.TILE_SIZE*(e.getPos().y + 1));
		}
	}

	public void update() {
		repaint();
	}
}
