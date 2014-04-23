package view;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.World;

public class InfoPanes extends JPanel {
	
	World world;

	DefaultListModel<String> taskListModel = new DefaultListModel<String>();
	JList<String> taskList = new JList<String>(taskListModel);
	JScrollPane notificationScoller, tasksScroller;
	
	public InfoPanes(World world){
		this.world = world;
		this.setBackground(new Color(0,0,0,180)); 
	}
	
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D g2 = (Graphics2D) g;
//		
//		
//	}

	public void update() {
		repaint();
	}
	
	private class TaskBox extends JPanel {
		
	}
	
}
