package view;

import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;

import model.OurObserver;
import model.World;

/* I thought it might be nice to have a working view for testing 
 * the map generation - so here is a start for this class
 */

public class WorldView extends JFrame implements OurObserver{
	public static JFrame gameWindow;
	public static TextView panel;
	public static World world;

	static Toolkit tk = Toolkit.getDefaultToolkit();
	private final int xScreenSize = ((int) tk.getScreenSize().getWidth());
	private final int yScreenSize = ((int) tk.getScreenSize().getHeight());
	private final int xWindowSize = 700;
	private final int yWindowSize = 700;
	
	public static void main(String[] args){
		gameWindow = new WorldView();
		gameWindow.setVisible(true);
	}
	
	public WorldView(){
		setupProperties();
		setupModel();
		addComponents();
		registerListeners();
	}

	private void setupProperties() {
		//in the future, we may want to make the window either maximized or scaled to screen size (or both, which would be nice)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Animation Example");
		setSize(xWindowSize, yWindowSize);
		setLocation(50, 50);
	}

	private void setupModel() {
		world = new World();
	}

	private void addComponents() {
		panel = new TextView(world);
		add(panel);
	}

	private void registerListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// here we should update all relevant panels with world info
		panel.update(world);
	}
}
