package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class SelectionView extends JPanel {
	
	private Point currPoint;
	private Point prevPoint;
	
	public SelectionView() {
		SelectListener sl= new SelectListener();
		this.addMouseListener(sl);
		this.addMouseMotionListener(sl);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	}
	
	public Point getPoint() {
		return currPoint;
	}
	
	public Rectangle getRectangle() {
		//set top left point and dimensions using two points
		int x1 = currPoint.x;
		int x2 = prevPoint.x;
		int y1 = currPoint.y;
		int y2 = prevPoint.y;
		
		int x = Math.min(x1, x2);
		int y = Math.max(y1, y2);
		
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		//make a rectangle using those
		return new Rectangle(x, y, dx, dy);
	}

	public void update() {
		repaint();
	}
	
	private class SelectListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			prevPoint = currPoint;
			currPoint = e.getPoint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseDragged(MouseEvent arg0) {}

		@Override
		public void mouseMoved(MouseEvent arg0) {}
		
	}

}
