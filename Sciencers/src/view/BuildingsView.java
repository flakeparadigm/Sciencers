package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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
	private Image farm, warehouse, lab, factory;

	public BuildingsView() {
		try {
			farm = ImageIO.read(new File("imgs/Farm.png"));
			warehouse = ImageIO.read(new File("imgs/Warehouse.png"));
			lab = ImageIO.read(new File("imgs/Lab.png"));
			factory = ImageIO.read(new File("imgs/Factory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Entity e : World.buildings) {
			Building b = (Building) e;
			Point pos = b.getPos();
			int height = b.getSize().height;

			if (b.getType() == EBuilding.FARM) {
				g2.drawImage(farm, WorldView.TILE_SIZE * pos.x,
						WorldView.TILE_SIZE * (pos.y - height + 1), null);
				g2.setFont(new Font("Verdana", Font.BOLD, 10));
				g2.setColor(Color.WHITE);
				g2.drawString(
						"FOOD: " + b.getInventory().getAmount(Resource.FOOD),
						WorldView.TILE_SIZE * pos.x, WorldView.TILE_SIZE
								* (pos.y - height));
				g2.drawString(
						"WORKERS: " + b.getNumWorkers(),
						WorldView.TILE_SIZE * pos.x, WorldView.TILE_SIZE
								* (pos.y - height) + 12);
			} else if (b.getType() == EBuilding.WAREHOUSE) {
				g2.drawImage(warehouse, WorldView.TILE_SIZE * pos.x,
						WorldView.TILE_SIZE * (pos.y - height + 1), null);
				g2.setFont(new Font("Verdana", Font.BOLD, 10));
				g2.setColor(Color.WHITE);
				g2.drawString("STORED: " + b.getInventory().getTotal(),
						WorldView.TILE_SIZE * pos.x, WorldView.TILE_SIZE
								* (pos.y - height));
			} else if (b.getType() == EBuilding.LAB) {
				g2.drawImage(lab, WorldView.TILE_SIZE * pos.x,
						WorldView.TILE_SIZE * (pos.y - height + 1), null);
			}else if (b.getType() == EBuilding.FACTORY) {
				g2.drawImage(factory, WorldView.TILE_SIZE * pos.x,
						WorldView.TILE_SIZE * (pos.y - height + 1), null);
				g2.setFont(new Font("Verdana", Font.BOLD, 10));
				g2.setColor(Color.WHITE);
				g2.drawString("STORED: " + b.getInventory().getTotal(),
						WorldView.TILE_SIZE * pos.x, WorldView.TILE_SIZE
								* (pos.y - height));
			}

			// g2.drawString("B", WorldView.TILE_SIZE*(e.getPos().x + 1),
			// WorldView.TILE_SIZE*(e.getPos().y + 1));
		}
	}

	public void update() {
		repaint();
	}
}
