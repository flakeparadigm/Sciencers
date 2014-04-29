package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Research;
import model.World;
import model.building.EBuilding;
import model.task.BuildBuildingTask;
import model.task.Task;

public class InfoPanes extends JPanel {

	private DefaultListModel<String> taskListModel = new DefaultListModel<String>();
	private JList<String> taskList = new JList<String>(taskListModel);
	private JScrollPane tasksScroller, alertsScroller;
	private JPanel statsPane, playerButtonsPane;

	private final int INFO_PANE_SIZE = 200;
	// Panes
	private final int STATS_PANE_WIDTH = 90;
	private final int PLAYER_BUTTONS_WIDTH = 150;
	// Boxes
	private final int TASK_BOX_WIDTH = 150;
	private final int TASK_BOX_HEIGHT = 60;
	private final int ALERTS_BOX_WIDTH = 150;
	private final int ALERTS_BOX_HEIGHT = 60;

	public InfoPanes(World world) {
		this.setBackground(new Color(0, 0, 0, 180));
		this.setLayout(null);

		int xTemp = 0; // keeps track of assignment position for each new panel
		
		playerButtonsPane = new PlayerButtonsPane();
		playerButtonsPane.setBorder(null);
		playerButtonsPane.setOpaque(false);
		this.add(playerButtonsPane);
		playerButtonsPane.setSize(PLAYER_BUTTONS_WIDTH + 30, PLAYER_BUTTONS_WIDTH);
		playerButtonsPane.setLocation(xTemp, 0);

		xTemp += playerButtonsPane.getWidth() + 10;

		// Task Pane
		tasksScroller = new JScrollPane();
		tasksScroller.setViewportView(new TaskPane());
		tasksScroller.setOpaque(false);
		tasksScroller.setBorder(null);
		this.add(tasksScroller);
		tasksScroller.setSize(TASK_BOX_WIDTH + 30, INFO_PANE_SIZE);
		tasksScroller.setLocation(xTemp, 0);

		tasksScroller.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(final AdjustmentEvent e) {
						tasksScroller.repaint();
					}
				});

		xTemp += tasksScroller.getWidth() + 10;

		// Stats Pane
		statsPane = new StatsPane();
		statsPane.setBorder(null);
		statsPane.setOpaque(false);
		this.add(statsPane);
		statsPane.setSize(STATS_PANE_WIDTH + 30, INFO_PANE_SIZE);
		statsPane.setLocation(xTemp, 0);

		xTemp += statsPane.getWidth() + 10;

		// Alerts Pane
		alertsScroller = new JScrollPane();
		alertsScroller.setViewportView(new AlertsPane());
		alertsScroller.setOpaque(false);
		alertsScroller.setBorder(null);
		this.add(alertsScroller);
		alertsScroller.setSize(ALERTS_BOX_WIDTH + 30, INFO_PANE_SIZE);
		alertsScroller.setLocation(xTemp, 0);
		alertsScroller.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(final AdjustmentEvent e) {
						alertsScroller.repaint();
					}
				});

		// make sure to increment xTemp before each new panel

		// this.add(new JLabel("Hi"));
		//
		// this.add(new JLabel("Hi"));

	}

	public void update() {
//		repaint();
//		statsPane.repaint();
		tasksScroller.getViewport().update(this.getGraphics());
		statsPane.update(this.getGraphics());
		alertsScroller.getViewport().update(this.getGraphics());
	}
	
	private class PlayerButtonsPane extends JPanel {
		
//		private JButton buildButton;
		private JButton hireButton;
		private JComboBox<String> buildMenu;
		
		public PlayerButtonsPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));
			setUpButtons();
		}
		
		private void setUpButtons() {
			
//			ArrayList<String> buildingTypes = new ArrayList<String>();
			

			//Create the combo box, select item at index 4.
			//Indices start at 0, so 4 specifies the pig.
			buildMenu = new JComboBox<String>();
			for(EBuilding b : EBuilding.values()) {
				buildMenu.addItem(b.name()); //TODO make this proper case instead of all caps
			}
			
			buildMenu.setSelectedIndex(4);
			buildMenu.addActionListener(new MenuListener());
			
			add(buildMenu);
			
//			buildButton = new JButton("Build Something");
//			add(buildButton);
			
			hireButton = new JButton("Hire Somebody");
			add(hireButton);
		}
		
		private class MenuListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Building a building through the InfoPane is not yet implemented.");
			}
		}
		
		private class PlayerButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
//				if(arg0.getSource() == buildButton) {
//					JOptionPane.showOptionDialog(null, "Choose a building. Make it awesome.", "Build a building", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Farm", "Storeroom", "Lab"}, "Farm");
//				}
			}


			
		}
	}

	private class TaskPane extends JPanel {

		public TaskPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));

			//bogus tasks just for testing
			for (int i = 0; i < 10; i++) {
				TaskBox newTask = new TaskBox(new BuildBuildingTask(
						EBuilding.FARM, new Point(5, 2 * i)));
				this.add(newTask);
			}
			this.setPreferredSize(new Dimension(TASK_BOX_WIDTH,
					(TASK_BOX_HEIGHT + 5) * this.getComponentCount() + 5));
		}

		@Override
		public void repaint() {
			super.repaint();
			this.setPreferredSize(new Dimension(TASK_BOX_WIDTH,
					(TASK_BOX_HEIGHT + 5) * this.getComponentCount() + 5));
		}

	}

	private class TaskBox extends JPanel {
		private Task task;

		public TaskBox(Task t) {
			task = t;
			this.setBackground(new Color(255, 255, 255, 100));
			this.setSize(new Dimension(TASK_BOX_WIDTH, TASK_BOX_HEIGHT));
			this.setPreferredSize(new Dimension(TASK_BOX_WIDTH, TASK_BOX_HEIGHT));

		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(Color.WHITE);

			int x = 5, y = 0;
			for (String line : task.toString().split("\n"))
				g2.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}

	private class StatsPane extends JPanel {
		public StatsPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));

			JLabel lAgents = new JLabel();
			lAgents.setText("Agents: " + World.agents.size());
			lAgents.setForeground(Color.white);
			this.add(lAgents);

			JLabel lResearch = new JLabel();
			lResearch.setText("Research: " + Research.get());
			lResearch.setForeground(Color.white);
			this.add(lResearch);

			JLabel lBuildings = new JLabel();
			lBuildings.setText("Buildings: " + World.buildings.size());
			lBuildings.setForeground(Color.white);
			this.add(lBuildings);

			// this.setPreferredSize(new Dimension(150, 65 *
			// this.getComponentCount() + 5));
		}

		@Override
		public void repaint() {
			super.repaint();
			for (Component j : this.getComponents()) {
				j.repaint();
			}
			// this.setPreferredSize(new Dimension(TASK_BOX_WIDTH,
			// (TASK_BOX_HEIGHT+5) * this.getComponentCount() + 5));

		}
	}

	private class AlertsPane extends JPanel {
		public AlertsPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));
			this.setPreferredSize(new Dimension(ALERTS_BOX_WIDTH,
					(ALERTS_BOX_HEIGHT + 5) * this.getComponentCount() + 5));

			// Bogus alerts for testing
			AlertsBox a = new AlertsBox("We require more pylons!");
			this.add(a);
			AlertsBox b = new AlertsBox("All ur base r belong to us");
			this.add(b);
			AlertsBox c = new AlertsBox("I'm making a note here\nHuge success!");
			this.add(c);
			AlertsBox d = new AlertsBox("Now STOP!\n\nHammertime.");
			this.add(d);

		}

		@Override
		public void repaint() {
			super.repaint();
			this.setPreferredSize(new Dimension(ALERTS_BOX_WIDTH,
					(ALERTS_BOX_HEIGHT + 5) * this.getComponentCount() + 5));
		}
	}

	private class AlertsBox extends JPanel {

		private String alert;

		public AlertsBox(String a) {
			alert = a;
			this.setBackground(new Color(255, 255, 255, 100));
			this.setSize(new Dimension(ALERTS_BOX_WIDTH, ALERTS_BOX_HEIGHT));
			this.setPreferredSize(new Dimension(ALERTS_BOX_WIDTH, ALERTS_BOX_HEIGHT));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(Color.WHITE);

			int x = 5, y = 0;
			for (String line : alert.split("\n"))
				g2.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}

}
