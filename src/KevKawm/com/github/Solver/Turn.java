package KevKawm.com.github.Solver;

public enum Turn {

	F(Side.front,1),
	FI(Side.front,3),
	F2(Side.front,2),
	B(Side.back,1),
	BI(Side.back,3),
	B2(Side.back,2),
	L(Side.left,1),
	LI(Side.left,3),
	L2(Side.left,2),
	R(Side.right,1),
	RI(Side.right,3),
	R2(Side.right,2),
	U(Side.up,1),
	UI(Side.up,3),
	U2(Side.up,2),
	D(Side.down,1),
	DI(Side.down,3),
	D2(Side.down,2);
	
	private Side side;
	private int rotations;
	
	Turn(Side side, int rotations){
		this.side = side;
		this.rotations = rotations;
	}
	
	public Side getSide(){
		return this.side;
	}
	
	public int getRotations(){
		return this.rotations;
	}
	
}
