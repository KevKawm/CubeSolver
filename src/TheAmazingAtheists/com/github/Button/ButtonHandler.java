package TheAmazingAtheists.com.github.Button;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import TheAmazingAtheists.com.github.CubeSolver.Display;

public class ButtonHandler implements MouseListener, MouseMotionListener {

	public List<Button> buttons = new ArrayList<Button>();

	Display display;
	
	public ButtonHandler(Display display){
		this.display = display;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for (Button b : buttons) {
			if (e.getPoint().x > b.p.x && e.getPoint().x < b.p.x + b.d.width && e.getPoint().y > b.p.y && e.getPoint().y < b.p.y + b.d.height) {
				b.runnable.run();
			}
		}
	}

	public void add(Button b) {
		buttons.add(b);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public void drawButtons(Graphics g) {
		for (Button b : buttons) {
			if(b.isHovering()){
				b.drawHover(g);
			} else {
				b.draw(g);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseMoved(MouseEvent e) {
		for (Button b : buttons) {
			if (e.getPoint().x > b.p.x && e.getPoint().x < b.p.x + b.d.width && e.getPoint().y > b.p.y && e.getPoint().y < b.p.y + b.d.height) {
				b.setHovering(true);
				display.frame.setCursor(Cursor.HAND_CURSOR);
			} else if(b.isHovering()){
				b.setHovering(false);
				display.frame.setCursor(Cursor.DEFAULT_CURSOR);
			}
		}
	}

}
