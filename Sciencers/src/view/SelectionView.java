package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SelectionView extends JPanel {
	private SelectionView myView;
	private boolean waitingForRectangle = false;
	private boolean waitingForPoint = false;
	private Point firstPoint;
	private Point secondPoint;
	private Point currPoint;

	private Image selectionImg;

	public SelectionView() {
		myView = this;
		try {
			selectionImg = ImageIO.read(new File("imgs/Selection.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		SelectListener sl = new SelectListener();
		this.addMouseListener(sl);
		this.addMouseMotionListener(sl);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (waitingForRectangle) {
			if (firstPoint == null) {
				g2.drawImage(selectionImg, currPoint.x * WorldView.TILE_SIZE,
						currPoint.y * WorldView.TILE_SIZE, WorldView.TILE_SIZE,
						WorldView.TILE_SIZE, null);
			} else if(secondPoint == null) {
				paintBox(g2, firstPoint, currPoint);
			} else {
				paintBox(g2,firstPoint,secondPoint);
			}
		} else if(waitingForPoint) {
			g2.drawImage(selectionImg, currPoint.x * WorldView.TILE_SIZE,
					currPoint.y * WorldView.TILE_SIZE, WorldView.TILE_SIZE,
					WorldView.TILE_SIZE, null);
		}
	}
	
	private void paintBox(Graphics2D g2, Point startPoiont, Point endPoint) {
		int currX = startPoiont.x;
		int currY = startPoiont.y;
		
		 while(currX != (endPoint.x+1)) {
			 while(currY != (endPoint.y+1)) {
				 
				// draw the image!
				g2.drawImage(selectionImg, currX * WorldView.TILE_SIZE,
						currY * WorldView.TILE_SIZE, WorldView.TILE_SIZE,
						WorldView.TILE_SIZE, null);
				
				// increment the y point and do it again!
				if(currY < (currPoint.y+1))
					currY++;
				else
					currY--;
				
			}
			
			// reset currY
			currY = startPoiont.y;
			
			// increment the x point and do it again!
			if(currX < (endPoint.x+1))
				currX++;
			else
				currX--;
			
		}
	}

	public Point getPoint() {
		// reset point variables
		firstPoint = null;
		waitingForPoint = true;

		// wait for two points
		while (waitingForPoint) {
			if (firstPoint != null) {
				waitingForPoint = false;
			}
			System.out.print("\0");
		}
		return firstPoint;
	}

	public Rectangle getRectangle() {

		// reset point variables
		firstPoint = null;
		secondPoint = null;
		waitingForRectangle = true;

		// wait for two points
		while (waitingForRectangle) {
			if (firstPoint != null && secondPoint != null)
				waitingForRectangle = false;
			System.out.print("\0");
		}

		// set top left point and dimensions using two points
		int x1 = firstPoint.x;
		int x2 = secondPoint.x;
		int y1 = firstPoint.y;
		int y2 = secondPoint.y;

		int x = Math.min(x1, x2);
		int y = Math.max(y1, y2);

		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);

		// make a rectangle using those
		return new Rectangle(x, y, dx, dy);
	}

	public void update() {
		repaint();
	}

	private class SelectListener extends MouseAdapter implements
			MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			Point scaledPoint = new Point(e.getPoint().x / WorldView.TILE_SIZE,
					e.getPoint().y / WorldView.TILE_SIZE);

			System.out.println("Point clicked at pixel: " + scaledPoint.x
					+ ", " + scaledPoint.y + " | Tile: " + scaledPoint.x + ", "
					+ scaledPoint.y);

			if (waitingForRectangle && firstPoint == null) {
				firstPoint = scaledPoint;
			} else if (waitingForRectangle) {
				secondPoint = scaledPoint;
				waitingForRectangle = false;
			} else if (waitingForPoint) {
				firstPoint = scaledPoint;
				waitingForPoint = false;
			}
			
			myView.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (waitingForRectangle || waitingForPoint) {
				currPoint = new Point(e.getPoint().x / WorldView.TILE_SIZE,
						e.getPoint().y / WorldView.TILE_SIZE);
				myView.repaint();
			}
		}

	}

}
