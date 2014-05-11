package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BackgroundView extends JPanel {

	private BufferedImage background;
	private JButton imageLabel;
	private Point viewLocation;

	public BackgroundView(Point viewLocation) {
		this.viewLocation = viewLocation;
		try {
			background = ImageIO.read(new File("imgs/panorama.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imageLabel = new JButton(new ImageIcon(background));
		setLayout(null);
		setVisible(true);
		add(imageLabel);
		imageLabel.setSize(10000, 800);
		imageLabel.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

	}

	public void update(Point viewLocation) {
		this.viewLocation = viewLocation;
		repaint();
		imageLabel.setLocation(
				(int) (WorldView.TILE_SIZE * (viewLocation.x / 1.5)), 0);
	}
}
