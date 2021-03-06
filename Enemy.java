import java.awt.Color;
import java.awt.Graphics2D;
public class Enemy extends Item {
	public Enemy() {
		super(0, 300, 50, 50);
	}
	public void move(boolean[] keys) {
		dx = 5;
		super.move(new boolean[] {false, false, false, false, false, false, false});
		if (x > 600)
			die();
		if (Math.random() < .01)
			fire(true);
	}
	public void redraw(Graphics2D g) {
		g.setColor(Color.yellow);
		g.fillRect(x, y, width, height);
	}
	public void die() {
		x = 0;

	}
}
