package KevKawm.com.github.CubeSolver;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public int width = 800;
	public int height = 600;
	
	ClassLoader cl = this.getClass().getClassLoader();
	
	public static void main(String[] args) {
		new Frame();
	}

	public Frame(){
		new JFrame();
		
		Display display = new Display(this);
		this.add(display);
		
		this.setSize(width,height);
		this.setTitle("Cube Solver");
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(cl.getResource("KevKawm/com/github/Assets/Icon.png")).getImage());
	}
	
}
