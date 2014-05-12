package view;

import java.awt.Dimension;
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

import model.Entity;
import model.World;
import model.agent.AgentFactory;
import model.agent.EAgent;
import model.building.BuildingFactory;
import model.task.BuildBuildingTask;
import model.task.GoMineAreaTask;
import model.task.HarvestTreeTask;
import model.task.TaskList;

public class SelectionView extends JPanel {
	private SelectionView myView;

	// waiting booleans
	private boolean waitingForAgentPoint = false;
	private boolean waitingForBuildingPoint = false;
	private boolean waitingForMiningArea = false;
	
	// temporary info storage
	private String newClass;
	private Point firstPoint = null;
	private Point currPoint = null;

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

		if (currPoint != null)
			if (firstPoint == null) {
				g2.drawImage(selectionImg, currPoint.x * WorldView.TILE_SIZE,
						currPoint.y * WorldView.TILE_SIZE, WorldView.TILE_SIZE,
						WorldView.TILE_SIZE, null);
			} else {
				paintBox(g2, firstPoint, currPoint);
			}
	}

	private void paintBox(Graphics2D g2, Point startPoint, Point endPoint) {

		Rectangle r = makeRectangle(startPoint, endPoint);
		for (int x = 0; x <= r.width; x++) {
			for (int y = 0; y <= r.height; y++) {
				g2.drawImage(selectionImg, (r.x + x) * WorldView.TILE_SIZE,
						(r.y + y) * WorldView.TILE_SIZE, WorldView.TILE_SIZE,
						WorldView.TILE_SIZE, null);
			}
		}

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

			if (waitingForAgentPoint) {
				waitingForAgentPoint = false;

				Entity agent = AgentFactory.makeAgent(newClass, scaledPoint);

				World.agentsTick.pauseTick();
				World.addAgent(agent);
				World.agentsTick.resumeTick();
			} else if (waitingForBuildingPoint) {
				waitingForBuildingPoint = false;

				Entity bldg = BuildingFactory.makeBuilding(newClass,
						scaledPoint);
				Point location = (Point)bldg.getPos();
				Point upperLeft = new Point(location.x, location.y - (bldg.getSize().height-1));
				TaskList.addTask(new GoMineAreaTask(new Rectangle(upperLeft, new Dimension(bldg.getSize().width-1, bldg.getSize().height-1))), EAgent.MINER);
//				TaskList.addTask(new HarvestTreeTask(upperLeft, null, null));
				TaskList.addTask(new BuildBuildingTask(bldg), EAgent.MINER);
			} else if (waitingForMiningArea) {
				if (firstPoint == null) {
					firstPoint = scaledPoint;

					myView.repaint();
					return;
				} else {
					waitingForMiningArea = false;
					Rectangle r = makeRectangle(firstPoint, scaledPoint);
					TaskList.addTask(new GoMineAreaTask(r), EAgent.MINER);

					firstPoint = null;
				}
			}

			currPoint = null;
			myView.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (waitingForAgentPoint || waitingForBuildingPoint
					|| waitingForMiningArea) {
				currPoint = new Point(e.getPoint().x / WorldView.TILE_SIZE,
						e.getPoint().y / WorldView.TILE_SIZE);
				myView.repaint();
			}
		}

	}

	
	// Different types of selections.
	public void addAgent(String selectedAgent) {
		newClass = selectedAgent;
		waitingForAgentPoint = true;
	}

	public void addBuilding(String selectedBuilding) {
		newClass = selectedBuilding;
		waitingForBuildingPoint = true;
	}

	public void mineArea() {
		firstPoint = null;
		waitingForMiningArea = true;
	}

}
