package TheAmazingAtheists.com.github.Button;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Button {

	public Runnable runnable;
	public Point p;
	public Dimension d;
	public Image img;
	public Image hoverImg;
	boolean hovering = false;
	
	public Button(Point p, Dimension d, Runnable runnable, Image img, Image hoverImg){
		this.runnable = runnable;
		this.p = p;
		this.d = d;
		this.img = img;
		this.hoverImg = hoverImg;
	}
	
	public void draw(Graphics g){
		g.drawImage(img,p.x,p.y,d.width,d.height,null);
	}
	
	public void drawHover(Graphics g){
		g.drawImage(hoverImg,p.x,p.y,d.width,d.height,null);
	}
	
	public void setHovering(boolean hovering){
		this.hovering = hovering;
	}
	
	public boolean isHovering(){
		return this.hovering;
	}
	
}
