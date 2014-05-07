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
			} else if (secondPoint == null) {
				paintBox(g2, firstPoint, currPoint);
			} else {
				paintBox(g2, firstPoint, secondPoint);
			}
		} else if (waitingForPoint) {
			g2.drawImage(selectionImg, currPoint.x * WorldView.TILE_SIZE,
					currPoint.y * WorldView.TILE_SIZE, WorldView.TILE_SIZE,
					WorldView.TILE_SIZE, null);
		}
	}

	private void paintBox(Graphics2D g2, Point startPoint, Point endPoint) {
		
		Rectangle r = makeRectangle(startPoint, endPoint);
		for(int x = 0; x <= r.width; x++) {
			for(int y = 0; y <= r.height; y++) {
				g2.drawImage(selectionImg, (r.x+x) * WorldView.TILE_SIZE, (r.y+y)
						* WorldView.TILE_SIZE, WorldView.TILE_SIZE,
						WorldView.TILE_SIZE, null);
			}
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
		
		return new Point(firstPoint.x, firstPoint.y);
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
		
		// make a rectangle using those
		return makeRectangle(firstPoint, secondPoint);
	}
	
	// creates a rectangle, where the location is the top-left point.
	private Rectangle makeRectangle(Point p1, Point p2) {
		int x1 = p1.x;
		int x2 = p2.x;
		int y1 = p1.y;
		int y2 = p2.y;

		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);

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
			Point scaledPoint = new Point(
					(e.getPoint().x / WorldView.TILE_SIZE), e.getPoint().y
							/ WorldView.TILE_SIZE);

			System.out.println("Point clicked at tile: " + scaledPoint.x + ", "
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
