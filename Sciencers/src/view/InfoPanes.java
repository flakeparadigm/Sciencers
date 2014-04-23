package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.World;
import model.agentCommand.BuildBuildingTask;
import model.agentCommand.Task;
import model.building.EBuilding;

public class InfoPanes extends JPanel {
	
	World world;

	DefaultListModel<String> taskListModel = new DefaultListModel<String>();
	JList<String> taskList = new JList<String>(taskListModel);
	JScrollPane notificationScroller, tasksScroller;
	
	// Magic Numbers
	private final int INFO_PANE_SIZE = 200;
	private final int TASK_BOX_WIDTH = 150;
	private final int TASK_BOX_HEIGHT = 60;
	
	public InfoPanes(World world){
		this.world = world;
		this.setBackground(new Color(0,0,0,180)); 
		this.setLayout(null);
		
		tasksScroller = new JScrollPane();
		tasksScroller.setViewportView(new TaskPane());
		tasksScroller.setOpaque(false);
		tasksScroller.setBorder(null);
		this.add(tasksScroller);
		tasksScroller.setSize(TASK_BOX_WIDTH + 30, INFO_PANE_SIZE);

		tasksScroller.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
	        @Override
	        public void adjustmentValueChanged(final AdjustmentEvent e) {
	            tasksScroller.repaint();
	        }
	    });
		
		this.add(new JLabel("Hi"));

		this.add(new JLabel("Hi"));
		
	}

	public void update() {
		repaint();
	}
	
	private class TaskPane extends JPanel {
		
		public TaskPane() {
			this.setLayout(new FlowLayout());
			this.setBackground(new Color(0,0,0,0));
			
			for(int i = 0; i < 10; i++) {
				TaskBox newTask = new TaskBox(new BuildBuildingTask(EBuilding.FARM, new Point(5,2*i)));
				this.add(newTask);
			}
			this.setPreferredSize(new Dimension(150, 65 * this.getComponentCount() + 5));
		}
		
		@Override
		public void repaint() {
			super.repaint();
			this.setPreferredSize(new Dimension(TASK_BOX_WIDTH, (TASK_BOX_HEIGHT+5) * this.getComponentCount() + 5));
		}
		
	}
	
	private class TaskBox extends JPanel {
		private Task task;
		
		public TaskBox(Task t) {
			task = t;
			this.setBackground(new Color(255,255,255,100));
			this.setSize(new Dimension(TASK_BOX_WIDTH,TASK_BOX_HEIGHT));
			this.setPreferredSize(new Dimension(TASK_BOX_WIDTH,TASK_BOX_HEIGHT));
			
		}
		
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(Color.WHITE);
			
			int x = 5, y = 0;
		    for (String line : task.toString().split("\n"))
		        g2.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}
	
}
