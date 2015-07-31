package KevKawm.com.github.CubeSolver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
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

	public List<String> actions = new ArrayList<String>();
	
	public List<String> undoneActions = new ArrayList<String>();

	ClassLoader cl = this.getClass().getClassLoader();

	final Display display = this;

	Image background;
	
	public Display(Frame frame) {
		this.frame = frame;

		thread.start();
	}

	@Override
	public void run() {
		background = new ImageIcon(cl.getResource("KevKawm/com/github/Assets/background.png")).getImage();
		
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
					display.undoneActions.clear();
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
		}, new Runnable() {
			@Override
			public void run() {
				cube.redo();
			}
		}, ubImg, ubHoverImg));

		this.addMouseListener(bh);
		this.addMouseMotionListener(bh);

		for(int i = 0; i < 6; i++){
			final TileColor tileColor = TileColor.get(i);
			
			BufferedImage colorImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);
			Graphics graphics = colorImage.getGraphics();
			graphics.setColor(tileColor.getColor());
			graphics.fillRect(0, 0, 19, 19);
			graphics.setColor(Color.BLACK);
			graphics.drawRect(0, 0, 19, 19);
			
			bh.add(new Button(new Point(0,0),new Dimension(40,40), new Runnable(){
				@Override
				public void run(){
					cube.selected = tileColor;
				}
			},colorImage,colorImage));
		}
		
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
		for(int i = 3; i < 9; i++){
			int j = i - 3;
			Button b = bh.buttons.get(i);
			b.p = new Point(frame.getWidth() - 200 + (j % 3) * 50,(int) (frame.getHeight() - 200 + Math.floor(j / 3) * 50));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		for(int x = 0; x <= (int) Math.ceil(frame.getWidth() / 236); x++){
			for(int y = 0; y <= (int) Math.ceil(frame.getHeight() / 236); y++){
				g.drawImage(background,x*236,y*236,null);
			}
		}
		cube.render(g, frame.getSize());
		bh.drawButtons(g);
		g.setColor(Color.BLACK);
		g.drawString("Made by KevKawm", frame.width - 125, frame.height - 45);
		g.setColor(cube.selected.getColor());
		g.fillRect(frame.getWidth() - 150, frame.getHeight() - 250, 39, 39);
		g.setColor(Color.BLACK);
		g.drawRect(frame.getWidth() - 150, frame.getHeight() - 250, 39, 39);
	}

	public String getTurns() {
		String ret = "";
		for (String s : actions) {
			if (s.startsWith("t:")) {
				s = s.substring(2);
				ret += s + ",";
			}
		}
		if (ret.length() > 0) {
			return ret.substring(0, ret.length() - 1);
		} else {
			return "";
		}
	}

	public boolean changedCubie() {
		for (String s : actions) {
			if (s.startsWith("c:"))
				return true;
		}
		return false;
	}

}
