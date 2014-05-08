package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainMenuScreen extends JFrame {
	
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private final int X_SCREEN_SIZE = ((int) tk.getScreenSize().getWidth());
	private final int Y_SCREEN_SIZE = ((int) tk.getScreenSize().getHeight());
	private final int X_WINDOW_SIZE = 1000;
	private final int Y_WINDOW_SIZE = 600;
	
	public static void main(String[] args) {
		
		MainMenuScreen window = new MainMenuScreen();
		window.setVisible(true);
		
	}
	
	public MainMenuScreen() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		this.setTitle("Sciencers");
		this.setLayout(new FlowLayout());
		this.setLocation(X_SCREEN_SIZE/2 - X_WINDOW_SIZE/2, Y_SCREEN_SIZE/2 - Y_WINDOW_SIZE/2);
	
		CheezitPanel cheezitLogo = new CheezitPanel();
//		cheezitLogo.setLocation(0, 0);
		cheezitLogo.setSize(X_WINDOW_SIZE, 450);
		cheezitLogo.setPreferredSize(new Dimension(X_WINDOW_SIZE, 450));
		cheezitLogo.setLayout(new FlowLayout());
		cheezitLogo.setVisible(true);
		this.add(cheezitLogo);
		
		OptionsPanel options = new OptionsPanel();
		options.setSize(X_WINDOW_SIZE, 150);
		options.setPreferredSize(new Dimension(X_WINDOW_SIZE, 150));
		options.setLayout(new FlowLayout());
		options.setVisible(true);
		this.add(options);
		
//		panel.repaint();
	}
	
	public void closeThis() {
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	
	private void hideThis() {
		this.setVisible(false);
	}
	
	class CheezitPanel extends JPanel {
		
		private Image image;
		
		public CheezitPanel() {
			
			try {
				String s = "imgs/LogoCheezitMountain.jpg";
				image = ImageIO.read(new File(s));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image, 0, 0, null);
		}
		
		//might be unnecessary
		public void update() {
			repaint();
		}
	}
	
	class OptionsPanel extends JPanel {
		
		private JButton loadGameButton, newGameButton;
		
		public OptionsPanel() {		
			
			OButtonListener obl = new OButtonListener();
			loadGameButton = new JButton("Load Saved Game");
			loadGameButton.addActionListener(obl);
			this.add(loadGameButton);
			newGameButton = new JButton("New Game");
			newGameButton.addActionListener(obl);
			this.add(newGameButton);
			
		}
		
		private class OButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == loadGameButton) {
					
					File file = new File(WorldView.getSaveLocation());
					if (!file.exists()) {
						JOptionPane.showMessageDialog(WorldView.gameWindow, "No save file was found.\nSorry.");
						return;
					}
					WorldView.doCreateWorld = false;
				}
				else if(arg0.getSource() == newGameButton) {
					WorldView.doCreateWorld = true;
				}
				else {
					System.out.println("Main menu screen buttons are derped");
					return;
				}
				WorldView.gameWindow = new WorldView();
				WorldView.gameWindow.setVisible(true);
				System.out.println("Setup procedure completed. Game is now happy, and indeed quite playable.");

				hideThis();
//				closeThis();
			}
			
		}
	}
}
