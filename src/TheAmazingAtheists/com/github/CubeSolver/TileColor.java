package TheAmazingAtheists.com.github.CubeSolver;

import java.awt.Color;

public enum TileColor {
	
	white(0,Color.WHITE),
	yellow(3,Color.YELLOW),
	blue(1,Color.BLUE),
	red(2,Color.RED),
	green(4,Color.GREEN),
	orange(5,new Color(255,150,6));
	
	private int i;
	private Color color;
	
	private TileColor(int i, Color color){
		this.i = i;
		this.color = color;
	}
	
	public Integer getInt(){
		return i;
	}
	
	public Color getColor(){
		return color;
	}
	
	public static TileColor get(int i){
		for(TileColor ii : TileColor.values()){
			if(ii.getInt() == i){
				return ii;
			}
		}
		return null;
	}
	
	public TileColor getOpposite(){
		if(getInt() > 2){
			return get(getInt() - 3);
		} else {
			return get(getInt() + 3);
		}
	}
	
	public TileColor getLeft(TileColor top){
		TileColor[] whiteL = {red,blue,orange,green};
		TileColor[] blueL = {red,yellow,orange,white};
		TileColor[] redL = {blue,white,green,yellow};
		TileColor[] L = {};
		if(top.equals(white)){
			L = whiteL;
		} else if(top.equals(blue)){
			L = blueL;
		} else if(top.equals(red)){
			L = redL;
		} else if(top.equals(yellow)){
			L = flip(whiteL);
		} else if(top.equals(green)){
			L = flip(blueL);
		} else if(top.equals(orange)){
			L = flip(redL);
		}
		int i = find(this,L) - 1;
		i = i == -1 ? 3 : i;
		if(i == -2) return null;
		return L[i];
	}
	
	public TileColor getRight(TileColor top){
		return getLeft(top).getOpposite();
	}
	
	private TileColor[] flip(TileColor[] a){
		TileColor[] a1 = new TileColor[a.length];
		for(int i = 0; i < a.length; i++){
			a1[a.length - i - 1] = a[i];
		}
		return a1;
	}
	
	private int find(TileColor c, TileColor[] a){
		for(int i = 0; i < a.length; i++){
			TileColor c1 = a[i];
			if(c.equals(c1)) return i;
		}
		return -1;
	}
	
}
