package TheAmazingAtheists.com.github.Solver;

import TheAmazingAtheists.com.github.CubeSolver.TileColor;

public class CrossSolver implements Solver{

	@SuppressWarnings("unused")
	public String solve(Cube cube) {

		String movements = "";
		for (TileColor c : TileColor.values()) {
			if (!(c.getInt() == 0 || c.getInt() == 3)) {
				int face = getPos(cube, c)[0], x = getPos(cube, c)[1], y = getPos(cube, c)[2];
				if (face != -1) {
					if (face == 3) {
						movements += putPieceInCross(cube, c);
					} else if (Cube.getOthers(getPos(cube, c)).get(0)[0] == 3) {
						movements += putInvertedPieceInCross(cube, c);
					} else if (Cube.getOthers(getPos(cube, c)).get(0)[0] == 0) {
						if(getPos(cube,c)[0] == c.getInt()){
							movements += invertCrossPiece(cube, cube.getSide(TileColor.get(getPos(cube, c)[0])));
						} else {
							cube.turn(TileColor.get(getPos(cube,c)[0]));
							cube.turn(TileColor.get(getPos(cube,c)[0]));
							movements += "," + cube.getSide(TileColor.get(getPos(cube,c)[0])).getName() + "2";
							movements += putInvertedPieceInCross(cube, c);
						}
					} else if (face == 0 && Cube.getOthers(getPos(cube,c)).get(0)[0] != c.getInt()) {
						TileColor f = TileColor.get(Cube.getOthers(getPos(cube, c)).get(0)[0]);
						cube.turn(f);
						cube.turn(f);
						movements += "," + cube.getSide(f).getName() + "2";
						movements += putPieceInCross(cube, c);
					} else {
						if (TileColor.get(Cube.getOthers(getPos(cube, c)).get(0)[0]).equals(c)) {
							int count = 0;
							while(getPos(cube,c)[0] != 0){
								cube.turn(cube.getColor(Cube.getOthers(getPos(cube, c)).get(0)));
								movements += "," + cube.getSide(cube.getColor(Cube.getOthers(getPos(cube, c)).get(0))).getName();
							}
						} else {
							movements += putPieceOnTop(cube, c);
							if (getPos(cube, c)[0] == 3) {
								movements += putPieceInCross(cube, c);
							} else if (Cube.getOthers(getPos(cube, c)).get(0)[0] == 3) {
								movements += putInvertedPieceInCross(cube, c);
							}
						}
					}
				}
			}
		}
		return movements + ",";
	}

	public static int[] getPos(Cube cube, TileColor other) {
		for (int f = 0; f < 6; f++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if ((x == 1 || y == 1) && !(x == 1 && y == 1)) {
						if (cube.getColor(cube.createArray(f, x, y)).equals(TileColor.white)) {
							if (other.getInt() == cube.getColor(Cube.getOthers(cube.createArray(f, x, y)).get(0)).getInt()) {
								return cube.createArray(f, x, y);
							}
						}
					}
				}
			}
		}
		return cube.createArray(-1, -1, -1);
	}

	public static String putPieceInCross(Cube cube, TileColor c) {
		int count = 0;
		String movements = "";
		TileColor otherPiece = cube.getColor(Cube.getOthers(getPos(cube, c)).get(0));
		while (Cube.getOthers(getPos(cube, c)).get(0)[0] != otherPiece.getInt()) {
			cube.turn(Turn.U);
			count++;
		}
		if (count == 1) {
			movements += ",U";
		} else if (count == 2) {
			movements += ",U2";
		} else if (count == 3) {
			movements += ",UI";
		}

		if (getPos(cube, c)[0] != -1) {
			int[] pos = getPos(cube, c);
			cube.turn(TileColor.get(Cube.getOthers(pos).get(0)[0]));
			cube.turn(TileColor.get(Cube.getOthers(pos).get(0)[0]));
			movements += "," + cube.getSide(TileColor.get(Cube.getOthers(pos).get(0)[0])).getName() + "2";
		}
		return movements;
	}

	public static String putInvertedPieceInCross(Cube cube, TileColor c) {
		int count = 0;
		String movements = "";
		while (cube.getColor(Cube.getOthers(getPos(cube, c)).get(0)).getInt() != getPos(cube, c)[0]) {
			cube.turn(Turn.U);
			count++;
		}
		if (count == 1) {
			movements += ",U";
		} else if (count == 2) {
			movements += ",U2";
		} else if (count == 3) {
			movements += ",UI";
		}

		if (getPos(cube, c)[0] != -1) {
			int[] pos = getPos(cube, c);
			cube.turn(TileColor.get(pos[0]));
			cube.turn(TileColor.get(pos[0]));
			movements += "," + cube.getSide(TileColor.get(pos[0])).getName() + "2";
		}
		movements += invertCrossPiece(cube, cube.getSide(TileColor.get(getPos(cube, c)[0])));
		return movements;
	}

	public static String invertCrossPiece(Cube cube, Side side) {
		String a = "";
		TileColor color = cube.getFace(side);
		if (color.equals(TileColor.blue)) {
			a = ",F,DI,L,D";
		} else if (color.equals(TileColor.red)) {
			a = ",R,DI,F,D";
		} else if (color.equals(TileColor.green)) {
			a = ",B,DI,R,D";
		} else if (color.equals(TileColor.orange)) {
			a = ",L,DI,B,D";
		}
		cube.doAlgorithm(a);
		return a;
	}

	public static String putPieceOnTop(Cube cube, TileColor c) {
		String a = "";
		TileColor color = TileColor.get(getPos(cube, c)[0]);
		TileColor other = TileColor.get(Cube.getOthers(getPos(cube, c)).get(0)[0]);
		if (eitherEquals(color, other, TileColor.blue) && eitherEquals(color, other, TileColor.red)) {
			a = ",R,U,RI";
		} else if (eitherEquals(color, other, TileColor.red) && eitherEquals(color, other, TileColor.green)) {
			a = ",B,U,BI";
		} else if (eitherEquals(color, other, TileColor.green) && eitherEquals(color, other, TileColor.orange)) {
			a = ",L,U,LI";
		} else if (eitherEquals(color, other, TileColor.orange) && eitherEquals(color, other, TileColor.blue)) {
			a = ",F,U,FI";
		}
		cube.doAlgorithm(a);
		return a;
	}

	private static boolean eitherEquals(TileColor color0, TileColor color1, TileColor color2) {
		return color0.equals(color2) || color1.equals(color2);
	}

}