package view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TextView extends JPanel {

	public TextView() {
		// load any necessary media here: (probably not necessary for the text
		// view, but will be used by the game view
		// String baseDir = System.getProperty("user.dir")
		// + System.getProperty("file.separator") + "src"
		// + System.getProperty("file.separator") + "media"
		// + System.getProperty("file.separator");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//display char array for tiles here
	}

}