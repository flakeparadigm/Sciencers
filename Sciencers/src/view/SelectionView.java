package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class SelectionView extends JPanel {
	
	public SelectionView() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
	}

	public void update() {
		repaint();
	}
}
