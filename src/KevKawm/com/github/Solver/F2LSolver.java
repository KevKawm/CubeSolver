package KevKawm.com.github.Solver;

import java.util.List;

import KevKawm.com.github.Algorithms.F2L;
import KevKawm.com.github.CubeSolver.TileColor;

public class F2LSolver implements Solver, F2L {

	TileColor[][] pairs = { { TileColor.blue, TileColor.red }, { TileColor.red, TileColor.green }, { TileColor.green, TileColor.orange }, { TileColor.orange, TileColor.blue } };

	@Override
	public String solve(Cube cube){
		String movements = "";
		for (int f = 1; f < 6; f = f == 2 ? f + 2 : f + 1) {
			TileColor c1 = TileColor.get(f);
			if (c1.getInt() != 0 && c1.getInt() != 3) {
				TileColor c2 = c1.getInt() == 2 ? TileColor.get(4) : c1.getInt() == 5 ? TileColor.get(1) : TileColor.get(c1.getInt() + 1);
				String cornerTop = putCornerOnTop(cube, c1, c2);
				String edgeTop = putEdgeOnTop(cube, c1, c2);
				String algorithm = findAlgorithm(cube, c1, c2);
				movements += cornerTop;
				movements += edgeTop;
				movements += algorithm;
			}
		}
		return movements;
	}

	public String findAlgorithm(Cube cube, TileColor c1, TileColor c2) {
		int rotation = 4 - (c1.getInt() < 3 ? c1.getInt() - 1 : c1.getInt() - 2);
		String top = "";
		for (int k = 0; k < 4; k++) {
			for (int i = 1; i < F2L.length; i++) {
				String alg = F2L[i];
				Cube cubeClone = cube.clone();
				for (int j = 0; j < rotation; j++)
					alg = Cube.rotateAlgorithm(alg);
				cubeClone.doAlgorithm(alg);
				if (isCorrect(cubeClone, c1, c2)) {
					cube.doAlgorithm(alg);
					return top + alg;
				}
			}
			top += "U,";
			cube.turn(Turn.U);
		}
		return "";
	}

	public boolean containsSide(int i, int[]... a) {
		for (int[] l : a) {
			if (l[0] == i)
				return true;
		}
		return false;
	}

	public String putEdgeOnTop(Cube cube, TileColor c1, TileColor c2) {
		String movements = "";
		boolean edgeCorrect = true;
		int[] edge = findEdge(cube, c1, c2);
		int[] otherEdge = Cube.getOthers(findEdge(cube, c1, c2)).get(0);
		if (otherEdge[0] != 3 && findEdge(cube, c1, c2)[0] != 3) {
			if ((findEdge(cube, c1, c2)[0] != c1.getInt() && findEdge(cube, c1, c2)[0] != c2.getInt()) || (otherEdge[0] != c1.getInt() && otherEdge[0] != c2.getInt())) {
				edgeCorrect = false;
			}
		}
		if (!edgeCorrect) {
			String alg = F2L[2];
			int l = 0;
			if (eitherEquals(edge[0], otherEdge[0], 2) && eitherEquals(edge[0], otherEdge[0], 4)) {
				l = 1;
			} else if (eitherEquals(edge[0], otherEdge[0], 4) && eitherEquals(edge[0], otherEdge[0], 5)) {
				l = 2;
			} else if (eitherEquals(edge[0], otherEdge[0], 5) && eitherEquals(edge[0], otherEdge[0], 1)) {
				l = 3;
			}
			for (int i = 0; i < 4 - l; i++) {
				alg = Cube.rotateAlgorithm(alg);
			}
			movements += alg;
			cube.doAlgorithm(alg);
		}
		return movements;
	}

	public int[] findEdge(Cube cube, TileColor c1, TileColor c2) {
		for (int f = 0; f < 6; f++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if ((x == 1 || y == 1) && !(x == 1 && y == 1)) {
						if (cube.getColor(cube.createArray(f, x, y)).equals(c1) && cube.getColor(Cube.getOthers(cube.createArray(f, x, y)).get(0)).equals(c2)) {
							return cube.createArray(f, x, y);
						}
					}
				}
			}
		}
		return cube.createArray(-1, -1, -1);
	}

	public int[] findCorner(Cube cube, TileColor c1, TileColor c2, TileColor c3) {
		for (int f = 0; f < 6; f++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (!(x == 1 || y == 1)) {
						List<int[]> others = Cube.getOthers(cube.createArray(f, x, y));
						TileColor nc2 = cube.getColor(others.get(0));
						TileColor nc3 = cube.getColor(others.get(1));
						if (cube.getColor(cube.createArray(f, x, y)).equals(c1) && ((nc2.equals(c2) && nc3.equals(c3)) || (nc2.equals(c3) && nc3.equals(c2)))) {
							return cube.createArray(f, x, y);
						}
					}
				}
			}
		}
		return cube.createArray(-1, -1, -1);
	}

	public boolean isCorrect(Cube cube, TileColor c1, TileColor c2) {
		return isEdgeCorrect(cube, c1, c2) && isCornerCorrect(cube, c1, c2);
	}

	public boolean isEdgeCorrect(Cube cube, TileColor c1, TileColor c2) {
		int[] edge = findEdge(cube, c1, c2);
		return (edge[0] == c1.getInt() && Cube.getOthers(edge).get(0)[0] == c2.getInt());
	}

	public boolean isCornerCorrect(Cube cube, TileColor c1, TileColor c2) {
		int[] corner = findCorner(cube, TileColor.white, c1, c2);
		return (corner[0] == 0 && ((Cube.getOthers(corner).get(0)[0] == c1.getInt() && Cube.getOthers(corner).get(1)[0] == c2.getInt()) || (Cube.getOthers(corner).get(0)[0] == c2.getInt() && Cube.getOthers(corner).get(1)[0] == c1.getInt())));
	}

	public String putCornerOnTop(Cube cube, TileColor c1, TileColor c2) {
		String movements = "";
		int[] corner = findCorner(cube, TileColor.white, c1, c2);
		List<int[]> cornerCubies = Cube.getOthers(corner);
		cornerCubies.add(corner);
		boolean cornerCorrect = true;
		TileColor cornerSide = c1;
		for (int[] c : cornerCubies) {
			if (c[0] == 0) {
				List<int[]> cL = Cube.getOthers(c);
				if (cL.get(0)[0] != c1.getInt() && cL.get(1)[0] != c2.getInt()) {
					cornerCorrect = false;
					cornerSide = TileColor.get(cL.get(0)[0]);
					break;
				}
			}
		}
		if (!cornerCorrect) {
			String alg = "R,UI,RI,";
			int l = 0;
			if (cornerSide == TileColor.blue && (eitherEquals(c1.getInt(), cube.getColor(1, 1, 0).getInt(), cube.getOtherColor(1, 1, 0).getInt()) || (eitherEquals(c2.getInt(), cube.getColor(1, 1, 0).getInt(), cube.getOtherColor(1, 1, 0).getInt())))) {
				alg = "U," + alg;
			} else if (cornerSide == TileColor.red && (eitherEquals(c1.getInt(), cube.getColor(2, 2, 1).getInt(), cube.getOtherColor(2, 2, 1).getInt()) || (eitherEquals(c2.getInt(), cube.getColor(2, 2, 1).getInt(), cube.getOtherColor(2, 2, 1).getInt())))) {
				alg = "U," + alg;
			} else if (cornerSide == TileColor.green && (eitherEquals(c1.getInt(), cube.getColor(4, 1, 2).getInt(), cube.getOtherColor(4, 1, 2).getInt()) || (eitherEquals(c2.getInt(), cube.getColor(4, 1, 2).getInt(), cube.getOtherColor(4, 1, 2).getInt())))) {
				alg = "U," + alg;
			} else if (cornerSide == TileColor.orange && (eitherEquals(c1.getInt(), cube.getColor(5, 0, 1).getInt(), cube.getOtherColor(5, 0, 1).getInt()) || (eitherEquals(c2.getInt(), cube.getColor(5, 0, 1).getInt(), cube.getOtherColor(5, 0, 1).getInt())))) {
				alg = "U," + alg;
			}
			if (cornerSide == TileColor.red) {
				l = 1;
			} else if (cornerSide == TileColor.green) {
				l = 2;
			} else if (cornerSide == TileColor.orange) {
				l = 3;
			}
			for (int i = 0; i < 4 - l; i++) {
				alg = Cube.rotateAlgorithm(alg);
			}
			movements += alg;
			cube.doAlgorithm(alg);
			corner = findCorner(cube, TileColor.white, c1, c2);
		}
		return movements;
	}

	private static boolean eitherEquals(int i0, int i1, int i2) {
		return i0 == i2 || i1 == i2;
	}

}
