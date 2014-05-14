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

	private WorldView gameWindow;

	public static void main(String[] args) {

		MainMenuScreen window = new MainMenuScreen();
		window.setVisible(true);

	}

	public MainMenuScreen() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(X_WINDOW_SIZE, Y_WINDOW_SIZE);
		this.setTitle("Sciencers");
		this.setLayout(new FlowLayout());
		this.setLocation(X_SCREEN_SIZE / 2 - X_WINDOW_SIZE / 2, Y_SCREEN_SIZE
				/ 2 - Y_WINDOW_SIZE / 2);

		CheezitPanel cheezitLogo = new CheezitPanel();
		// cheezitLogo.setLocation(0, 0);
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

		// panel.repaint();
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

		// might be unnecessary
		public void update() {
			repaint();
		}
	}

	class OptionsPanel extends JPanel {

		private JButton loadGameButton, newGameButton, howToPlayButton;

		public OptionsPanel() {

			OButtonListener obl = new OButtonListener();
			loadGameButton = new JButton("Load Saved Game");
			loadGameButton.addActionListener(obl);
			this.add(loadGameButton);

			howToPlayButton = new JButton("How To Play");
			howToPlayButton.addActionListener(obl);
			this.add(howToPlayButton);

			newGameButton = new JButton("New Game");
			newGameButton.addActionListener(obl);
			this.add(newGameButton);

		}

		private class OButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean newWorld;

				if (arg0.getSource() == loadGameButton) {
					newWorld = false;
					gameWindow = new WorldView(newWorld);
					gameWindow.setVisible(true);
					System.out
							.println("Setup procedure completed. Game is now happy, and indeed quite playable.");

					hideThis();
				} else if (arg0.getSource() == newGameButton) {
					newWorld = true;
					gameWindow = new WorldView(newWorld);
					gameWindow.setVisible(true);
					System.out
							.println("Setup procedure completed. Game is now happy, and indeed quite playable.");

					hideThis();
				} else if (arg0.getSource() == howToPlayButton) {
					JOptionPane
							.showMessageDialog(
									null,
									"Sciencers is a game of intense strategy, poorly designed economies and a general sense of happiness.\n\nYour goal as benevolent leader of the smiley people is to gain a surplus of 500 research \nthrough the use of Sciencers. Sciencers will provide research when able to work at a lab.\n\nLabs will gain you research points.\nFarms will allow workers to feed your smileys.\nWorkshops will allow miners to arm your minions, \n\nIn order to build anything, you will need to give your miners directions to build structures for you,\nbut beware, not all workers enjoy doing work! As leader of the smileys, you will have to be watchful in your dealings\nto make sure your minions complete your tasks and do so in a timely manner.\n\nTime is indeed of the essence! Rogue agents, hunger, and blood loss are all ways your smileys might die.\nFatigue will cause them to grow hungry faster.\n\nIf you find yourself in need of more smileys, you may use research points to hire new ones, but remember your goal! SCIENCE!!!!!! ",
									"How To Play",
									JOptionPane.INFORMATION_MESSAGE);
				} else {
					System.out.println("Main menu screen buttons are derped");
					return;
				}

				// closeThis();
			}

		}
	}
}
