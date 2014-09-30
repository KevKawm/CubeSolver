package TheAmazingAtheists.com.github.Solver;

public enum Side {
	
	front(0,"F"),
	back(1,"B"),
	up(2,"U"),
	down(3,"D"),
	left(4,"L"),
	right(5,"R");
	
	int i = 0;
	String name = "";
	
	Side(int i, String name){
		this.i = i;
		this.name = name;
	}
	
	public int getInt(){
		return i;
	}
	
	public String getName(){
		return name;
	}
	
}
