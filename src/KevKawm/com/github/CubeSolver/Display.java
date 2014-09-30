package KevKawm.com.github.CubeSolver;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import KevKawm.com.github.Button.Button;
import KevKawm.com.github.Button.ButtonHandler;
import KevKawm.com.github.Solver.Cube;

public class Display extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	public Frame frame;

	Thread thread = new Thread(this);

	Cube cube;

	ButtonHandler bh;

	public String turns = "";

	public boolean changedCubie = false;

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

		Image bImg = new ImageIcon(cl.getResource("TheAmazingAtheists/com/github/Assets/Solve_Button.png")).getImage();
		Image bHoverImg = new ImageIcon(cl.getResource("TheAmazingAtheists/com/github/Assets/Hover_Solve_Button.png")).getImage();

		bh.add(new Button(new Point(300, 100), new Dimension(100, 50), new Runnable() {
			@Override
			public void run() {
				if (!cube.isSolved()) {
					try {
						if (cube.isPossible()) {
							Cube cubeClone = cube.clone();
							String out = "The solving algorithm is: ";
							String[] array = {};
							if (changedCubie) {
								array = cube.solve().split(",");
							} else {
								String str = cube.getSolve();
								String[] array1 = str.split(",");
								String[] array2 = Cube.compact(turns).split(",");
								if (array1.length < array2.length) {
									array = array1;
									cube.doAlgorithm(str);
								} else {
									String str2 = Cube.invertAlgorithm(Cube.compact(turns));
									array = str2.split(",");
									cube.doAlgorithm(str2);
									turns = "";
									changedCubie = false;
								}
							}
							if (array.length > 10) {
								for (int i = 0; i < array.length; i++) {
									if (i % (int) Math.pow(array.length, 1 / 1.5D) == 0) {
										out += "\n";
									}
									out += array[i] + ",";
								}
							} else {
								for (int i = 0; i < array.length; i++) {
									out += array[i] + ",";
								}
							}
							if (cube.isSolved()) {
								JOptionPane.showMessageDialog(null, "Make sure you are holding the cube with\n BLUE as face and YELLOW as top", "Solve", 1);
								JOptionPane.showMessageDialog(null, out.endsWith(",") ? out.substring(0, out.length() - 1) : out, "Solve", 1);
							} else {
								display.removeMouseListener(cube);
								cube = cubeClone.clone();
								display.addMouseListener(cube);
								JOptionPane.showMessageDialog(null, "An error occured when solving.\nMake sure everything is entered correctly", "Error", 0);
							}
						} else {
							JOptionPane.showMessageDialog(null, "An error occured when solving.\nMake sure everything is entered correctly", "Error", 0);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "An error occured when solving.\nMake sure everything is entered correctly", "Error", 0);
					}
				}
			}
		}, bImg, bHoverImg));

		this.addMouseListener(bh);
		this.addMouseMotionListener(bh);

		while (true) {
			repaint();
			moveButtons();
		}
	}

	private void moveButtons() {
		for (Button b : bh.buttons) {
			b.p = new Point((frame.getWidth() / 5 * 3), 50);
			b.d = new Dimension(frame.getWidth() / 5, frame.getWidth() / 10);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		cube.render(g, frame.getSize());
		bh.drawButtons(g);
	}

}
