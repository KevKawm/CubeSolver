package KevKawm.com.github.CubeSolver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import KevKawm.com.github.Button.Button;
import KevKawm.com.github.Button.ButtonHandler;
import KevKawm.com.github.Solver.Cube;

public class Display extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	public Frame frame;

	Thread thread = new Thread(this);

	public Cube cube;

	ButtonHandler bh;

	//public List<String> cubieChanges = new ArrayList<String>(), moves = new ArrayList<String>();
	public List<String> actions = new ArrayList<String>();
	
	ClassLoader cl = this.getClass().getClassLoader();

	final Display display = this;

	public Display(Frame frame) {
		this.frame = frame;

		thread.start();
	}

	@Override
	public void run() {
		cube = new Cube(TileColor.blue, TileColor.yellow, this);

		this.addMouseListener(cube);

		bh = new ButtonHandler(this);

		Image bImg = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Solve_Button.png")).getImage();
		Image bHoverImg = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Hover_Solve_Button.png")).getImage();

		bh.add(new Button(new Point(0, 0), new Dimension(100, 50), new Runnable() {
			@Override
			public void run() {
				if (!cube.isSolved()) {
					cube.solve();
				}
			}
		}, bImg, bHoverImg));

		Image rbImg = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Reset_Button.png")).getImage();
		Image rbHoverImg = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Hover_Reset_Button.png")).getImage();

		bh.add(new Button(new Point(0, 0), new Dimension(100, 50), new Runnable() {
			@Override
			public void run() {
				if (!cube.isSolved()) {
					display.removeMouseListener(cube);
					cube = new Cube(TileColor.blue, TileColor.yellow, display);
					display.addMouseListener(cube);
					display.actions.clear();
				}
			}
		}, rbImg, rbHoverImg));

		Image ubImg = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Undo_Button.png")).getImage();
		Image ubHoverImg = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Hover_Undo_Button.png")).getImage();

		bh.add(new Button(new Point(0, 0), new Dimension(100, 50), new Runnable() {
			@Override
			public void run() {
				cube.undo();
			}
		}, ubImg, ubHoverImg));

		this.addMouseListener(bh);
		this.addMouseMotionListener(bh);

		moveButtons();

		while (true) {
			repaint();
			if (frame.getWidth() != frame.width || frame.getHeight() != frame.height) {
				moveButtons();
				frame.width = frame.getWidth();
				frame.height = frame.getHeight();
			}
		}
	}

	private void moveButtons() {
		bh.buttons.get(0).p = new Point((frame.getWidth() / 5 * 3), 50);
		bh.buttons.get(0).d = new Dimension(frame.getWidth() / 5, frame.getWidth() / 10);
		bh.buttons.get(1).d = new Dimension(frame.getWidth() / 5, frame.getWidth() / 10);
		bh.buttons.get(1).p = new Point(((int) (frame.getWidth() / 5 * 0.2)), 50);
		bh.buttons.get(2).d = new Dimension(frame.getWidth() / 5, frame.getWidth() / 10);
		bh.buttons.get(2).p = new Point(((int) (frame.getWidth() / 5 * 0.2)), frame.getHeight() - 100 - bh.buttons.get(2).d.height);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		cube.render(g, frame.getSize());
		bh.drawButtons(g);
		g.setColor(Color.BLACK);
		g.drawString("Made by KevKawm", frame.width - 125, frame.height - 45);
	}

	public String getTurns(){
		String ret = "";
		for(String s : actions){
			if(s.startsWith("t:")){
				s = s.substring(2);
				ret+= s + ",";
			}
		}
		return ret.substring(0,ret.length() - 1);
	}
	
	public boolean changedCubie(){
		for(String s : actions){
			if(s.startsWith("c:")) return true;
		}
		return false;
	}
	
}
