//Intro to Game Programming Lecture 1
//The following code can be found on my TJ Page here:
//http://www.tjhsst.edu/~2010djohnson/GameTemplate.java
//It's a good idea to start with this code for your first few games.
//Original template by Eugene Paik for TJGames.org

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class GameTemplate extends JPanel {
	//We use these for double buffereing:
	public BufferedImage myImage;
	public Graphics2D buffer;

	//This timer drives your game forward:
	public Timer t = new Timer(30, new Listener());

	final int WINDOWX = 600, WINDOWY = 600;
	//#The dimensions of your game.  Will be called upon later a lot.
	final int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3, SPACE = 4;
	boolean[] keys = new boolean[5];
	int playerx = 0;
	int playery = 0;
	int counter = 0;
	Item i = new Item(300, 0, 100, 100);
	Enemy e = new Enemy();
	Platform p = new Platform(0, 500, 600, 100, Color.red);

	public GameTemplate() {
		myImage = new BufferedImage(WINDOWX, WINDOWY, BufferedImage.TYPE_INT_RGB);

		buffer = (Graphics2D)myImage.getGraphics();

		addKeyListener(new KeyListener());
		setFocusable(true);

		setPreferredSize(new Dimension(WINDOWX, WINDOWY)); //makes the later call to frame.pack() work nicely

		t.start();
	}
	public void roomMath() {
		i.move(keys);
		e.move(keys);
		// does all the actions of the object

		if (i.bullet != null) {
			i.bullet.move();
			if (i.bullet.check(e)) {
				i.bullet = null;
				e.die();
				counter++;
			}
		}
		if (e.bullet != null) {
			e.bullet.move();
			if (e.bullet.check(i)) {
				e.bullet = null;
				System.exit(0);
			}
		}
		p.collide(i);
		p.collide(e);
	}

	public void roomDraw() {
		buffer.setColor(Color.black);
		buffer.fillRect(0, 0, WINDOWX, WINDOWY);
		// redraws the background
		buffer.setColor(Color.yellow);
		buffer.drawString("Score: " + counter, 10, 50);

		if (i.bullet != null) i.bullet.redraw(buffer);
		if (e.bullet != null) e.bullet.redraw(buffer);
		i.redraw(buffer);
		e.redraw(buffer);
		p.redraw(buffer);
		// does all the drawing for all the objects

		repaint(); // displays on the screen
	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//This stuff happens once per frame
			roomMath();
			roomDraw();
		}
	}
	public class KeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				keys[SPACE] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				keys[RIGHT] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (!keys[UP])
					i.jump();
				keys[UP] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				keys[LEFT] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				keys[DOWN] = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(0); //Makes the program quit when the player hits Esc
		}
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				keys[SPACE] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				keys[RIGHT] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				keys[UP] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				keys[LEFT] = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				keys[DOWN] = false;
			}
		}
	}

	public void paintComponent(Graphics g) {
		//displays our stored image to the panel (to the screen)
		g.drawImage(myImage, 0, 0, WINDOWX, WINDOWY, null);
	}
	public static void main(String[] args) {
		//This method sets up everything, including the panel, which will do the rest of the work.

		JFrame frame = new JFrame("Game");
		frame.setLocation(0, 0); //Centers the window onscreen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false); //keeps the window size from changing
		frame.setContentPane(new GameTemplate());
		frame.pack();//Makes the frame the right size so that the panel is displayed at the right size
		frame.setVisible(true);
	}
}
