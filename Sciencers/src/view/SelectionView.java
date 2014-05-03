package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class SelectionView extends JPanel {
	
	private boolean waitingForRectangle = false;
	private boolean waitingForPoint = false;
	private Point firstPoint;
	private Point secondPoint;
	private Point currPoint;
	
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
		// reset point variables
		firstPoint = null;
		waitingForPoint = true;
		
		// wait for two points
		while(waitingForPoint) {
			if(firstPoint != null)
				waitingForPoint = false;
		}
		return new Point(currPoint.x/WorldView.TILE_SIZE, currPoint.y/WorldView.TILE_SIZE);
	}
	
	public Rectangle getRectangle() {
		
		// reset point variables
		firstPoint = null;
		secondPoint = null;
		waitingForRectangle = true;
		
		// wait for two points
		while(waitingForRectangle) {
			if(firstPoint != null && secondPoint != null)
				waitingForRectangle = false;
		}
		

		//set top left point and dimensions using two points
		int x1 = firstPoint.x/WorldView.TILE_SIZE;
		int x2 = secondPoint.x/WorldView.TILE_SIZE;
		int y1 = firstPoint.y/WorldView.TILE_SIZE;
		int y2 = secondPoint.y/WorldView.TILE_SIZE;

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
	
	private class SelectListener extends MouseAdapter implements MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(waitingForRectangle && firstPoint == null) {
				firstPoint = e.getPoint();
			} else if(waitingForRectangle) {
				secondPoint = e.getPoint();
			}
			
			if(waitingForPoint)
				firstPoint = e.getPoint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(waitingForRectangle || waitingForPoint) {
				currPoint = e.getPoint();
				
			}
		}
		
	}

}
