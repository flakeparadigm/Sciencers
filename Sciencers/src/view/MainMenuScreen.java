package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenuScreen extends JFrame {
	
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_WINDOW_SIZE = 800;
	private final int Y_WINDOW_SIZE = 600;
	
	public static void main(String[] args) {
		
		MainMenuScreen window = new MainMenuScreen();
		window.setVisible(true);
		
	}
	
	public MainMenuScreen() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		this.setTitle("Sciencers Demo");
		this.setLayout(new FlowLayout());
		this.setLocation(X_SCREEN_SIZE/2 - X_WINDOW_SIZE/2, Y_SCREEN_SIZE/2 - Y_WINDOW_SIZE/2);
	
		MainMenuPanel panel = new MainMenuPanel();
		panel.setLocation(0, 0);
		panel.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		panel.setLayout(new GridLayout(1, 1));
		panel.setVisible(true);
		
		this.add(panel);
	
//		panel.repaint();
	}
}

class MainMenuPanel extends JPanel {
	
	private Image image;
	
	public MainMenuPanel() {
		
		try {
			String s = "imgs/LogoCheezitMountain.jpg";
			image = ImageIO.read(new File(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, null);
	}
	
	//might be unnecessary
	public void update() {
		repaint();
	}
}
