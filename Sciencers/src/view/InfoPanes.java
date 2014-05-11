package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Entity;
import model.Research;
import model.World;
import model.agent.AgentFactory;
import model.agent.Agent;
import model.agent.EAgent;
import model.agent.FarmerAgent;
import model.agent.MinerAgent;
import model.agent.SciencerAgent;
import model.building.BuildingFactory;
import model.building.EBuilding;
import model.inventory.Resource;
import model.task.BuildBuildingTask;
import model.task.GatherResources;
import model.task.GoMineAreaTask;
import model.task.Task;
import model.task.TaskList;

public class InfoPanes extends JPanel {

	private DefaultListModel<String> taskListModel = new DefaultListModel<String>();
	private JList<String> taskList = new JList<String>(taskListModel);
	
	private PlayerButtonsPane playerButtonsPane;
	private TaskPane genericTaskPane, farmerTaskPane, minerTaskPane, sciencerTaskPane;
	private StatsPane statsPane;
	private AlertsPane alertsPane;
	
	private JScrollPane genericTaskScroller, farmerTaskScroller, sciencerTaskScroller, minerTaskScroller, alertsScroller;

	private final int INFO_PANE_SIZE = 200;
	// Panes
	private final int STATS_PANE_WIDTH = 90;
	private final int PLAYER_BUTTONS_WIDTH = 150;
	// Boxes
	private final int TASK_BOX_WIDTH = 150;
	private final int TASK_BOX_HEIGHT = 60;
	private final int ALERTS_BOX_WIDTH = 150;
	private final int ALERTS_BOX_HEIGHT = 60;

	public InfoPanes() {
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
		alertsPane = new AlertsPane();
		alertsScroller.setViewportView(alertsPane);
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
		
		xTemp += alertsScroller.getWidth() + 10;

		// Generic Task Pane
		genericTaskScroller = new JScrollPane();
		genericTaskPane = new TaskPane(EAgent.GENERIC);
		genericTaskScroller.setViewportView(genericTaskPane);
		genericTaskScroller.setOpaque(false);
		genericTaskScroller.setBorder(null);
		this.add(genericTaskScroller);
		genericTaskScroller.setSize(TASK_BOX_WIDTH + 30, INFO_PANE_SIZE);
		genericTaskScroller.setLocation(xTemp, 0);

		genericTaskScroller.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(final AdjustmentEvent e) {
						genericTaskScroller.repaint();
					}
				});

		xTemp += genericTaskScroller.getWidth() + 10;
		
		farmerTaskScroller = new JScrollPane();
		farmerTaskPane = new TaskPane(EAgent.FARMER);
		farmerTaskScroller.setViewportView(farmerTaskPane);
		farmerTaskScroller.setOpaque(false);
		farmerTaskScroller.setBorder(null);
		this.add(farmerTaskScroller);
		farmerTaskScroller.setSize(TASK_BOX_WIDTH + 30, INFO_PANE_SIZE);
		farmerTaskScroller.setLocation(xTemp, 0);

		farmerTaskScroller.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(final AdjustmentEvent e) {
						farmerTaskScroller.repaint();
					}
				});
		
		xTemp += farmerTaskScroller.getWidth() + 10;
		
		minerTaskScroller = new JScrollPane();
		minerTaskPane = new TaskPane(EAgent.MINER);
		minerTaskScroller.setViewportView(minerTaskPane);
		minerTaskScroller.setOpaque(false);
		minerTaskScroller.setBorder(null);
		this.add(minerTaskScroller);
		minerTaskScroller.setSize(TASK_BOX_WIDTH + 30, INFO_PANE_SIZE);
		minerTaskScroller.setLocation(xTemp, 0);

		minerTaskScroller.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(final AdjustmentEvent e) {
						minerTaskScroller.repaint();
					}
				});

		xTemp += minerTaskScroller.getWidth() + 10;
		
		sciencerTaskScroller = new JScrollPane();
		sciencerTaskPane = new TaskPane(EAgent.SCIENCER);
		sciencerTaskScroller.setViewportView(sciencerTaskPane);
		sciencerTaskScroller.setOpaque(false);
		sciencerTaskScroller.setBorder(null);
		this.add(sciencerTaskScroller);
		sciencerTaskScroller.setSize(TASK_BOX_WIDTH + 30, INFO_PANE_SIZE);
		sciencerTaskScroller.setLocation(xTemp, 0);

		sciencerTaskScroller.getVerticalScrollBar().addAdjustmentListener(
				new AdjustmentListener() {
					@Override
					public void adjustmentValueChanged(final AdjustmentEvent e) {
						sciencerTaskScroller.repaint();
					}
				});
		
		// make sure to increment xTemp before each new panel
	}

	public void performUpdate() {
		genericTaskPane.performUpdate();
		farmerTaskPane.performUpdate();
		minerTaskPane.performUpdate();
		sciencerTaskPane.performUpdate();
		statsPane.performUpdate();
		alertsPane.performUpdate();
	}
	
	private class PlayerButtonsPane extends JPanel {
	
		private JComboBox<String> buildMenu;
		private JButton buildButton;
		private JComboBox<String> hireMenu;
		private JButton hireButton;
		private JButton mineButton;
		private JButton gatherButton;
		
		private String selectedBuilding = "Select Building";
		private String selectedAgent;
		
		public PlayerButtonsPane() {
			this.setLayout(new GridLayout(6, 1));
			this.setBackground(new Color(0, 0, 0, 0));
			setUpButtons();
		}
		
		private void setUpButtons() {

			buildMenu = new JComboBox<String>();
			buildMenu.addItem("Select Building");
			for(EBuilding b : EBuilding.values()) {
				buildMenu.addItem(b.getName()); //TODO make this proper case instead of all caps
			}
			buildMenu.addActionListener(new MenuListener());
			add(buildMenu);
			
			buildButton = new JButton("GO");
			buildButton.addActionListener(new ButtonListener());
			add(buildButton);
			
			hireMenu = new JComboBox<String>();
			hireMenu.addItem("Select Dood");
			for(EAgent a : EAgent.values()) {
				if(a != EAgent.GENERIC)
					hireMenu.addItem(a.getName()); //TODO make pretty
			}
			hireMenu.addActionListener(new MenuListener());
			add(hireMenu);
			
			hireButton = new JButton("GO");
			hireButton.addActionListener(new ButtonListener());
			add(hireButton);
			
			mineButton = new JButton("Go mining!");
			mineButton.addActionListener(new ButtonListener());
			add(mineButton);
			
			gatherButton = new JButton("Gather");
			gatherButton.addActionListener(new ButtonListener());
			add(gatherButton);
			
		}
		
		private class MenuListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == buildMenu) {
					selectedBuilding = (String)buildMenu.getSelectedItem();
				}
				else if(e.getSource() == hireMenu) {
					selectedAgent = (String)hireMenu.getSelectedItem();
				}
				else System.out.println("MenuListener registered a click from a menu that doesn't exist!");
			}
		}
		
		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == buildButton) {
					if(selectedBuilding.equals("Select Building"))
						return;					
					
					WorldView.selectionPanel.addBuilding(selectedBuilding);
					return;
				}
				else if(e.getSource() == hireButton) {
					if(selectedAgent.equals("Select Dood") || selectedAgent.equals("Generic"))//TODO
						return;
					
					WorldView.selectionPanel.addAgent(selectedAgent);
					return;
				}
				else if(e.getSource() == mineButton) {			
					WorldView.selectionPanel.mineArea();
					return;
				} if (e.getSource() == gatherButton) {
					TaskList.addTask(new GatherResources(Resource.IRON), EAgent.MINER);
				}
				else System.out.println("ButtonListener registered a click from a button that doesn't exist!");
			}
		}
	}

	private class TaskPane extends JPanel {
		
		private EAgent a;

		public TaskPane(EAgent a) {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));
			
			this.a = a;
			
			JLabel nameLabel = new JLabel(a.getName() + " Tasks");
			nameLabel.setForeground(Color.white);
			this.add(nameLabel);
			
			setupBoxes();
			this.setPreferredSize(new Dimension(TASK_BOX_WIDTH,
					(TASK_BOX_HEIGHT + 5) * this.getComponentCount() + 5));
		}

		public void performUpdate() {
			setupBoxes();
		}
		
		public void setupBoxes() {
			for(Task t : TaskList.getList(a)) {
				if(t.shouldBeSeen())
					this.add(new TaskBox(t));
			}
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
		
		JLabel lAgents, lResearch, lBuildings;
		public StatsPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));
			
			lAgents = new JLabel();
			lResearch = new JLabel();
			lBuildings = new JLabel();
			
			performUpdate();
			
			lAgents.setForeground(Color.white);
			this.add(lAgents);
			
			lResearch.setForeground(Color.white);
			this.add(lResearch);
			
			lBuildings.setForeground(Color.white);
			this.add(lBuildings);
		}
		
		public void performUpdate() {
			lAgents.setText("Agents: " + World.agents.size());
			lResearch.setText("Research: " + Research.get());
			lBuildings.setText("Buildings: " + World.buildings.size());
			repaint();
		}
	}

	private class AlertsPane extends JPanel {
		public AlertsPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0, 0, 0, 0));
			
			performUpdate();

			this.setPreferredSize(new Dimension(ALERTS_BOX_WIDTH,
					(ALERTS_BOX_HEIGHT + 5) * this.getComponentCount() + 5));
		}

		public void performUpdate() {
			
//			for(Alert a : AlertCollection.getAlerts()) {
//				this.add(new AlertsBox(a.toString()));
//			}
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
