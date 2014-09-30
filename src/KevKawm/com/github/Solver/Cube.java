package KevKawm.com.github.Solver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import KevKawm.com.github.CubeSolver.Display;
import KevKawm.com.github.CubeSolver.TileColor;

public class Cube implements MouseListener{

	public List<List<List<TileColor>>> faces = new ArrayList<List<List<TileColor>>>();

	public TileColor frontFace;
	public TileColor topFace;

	int squareSize;
	Dimension frameSize;

	Display display;

	public Cube(TileColor frontFace, TileColor topFace, Display display) {
		this.frontFace = frontFace;
		this.topFace = topFace;
		for (int i = 0; i < TileColor.values().length; i++) {
			List<List<TileColor>> l = new ArrayList<List<TileColor>>();
			for (int j = 0; j < 3; j++) {
				List<TileColor> ll = new ArrayList<TileColor>();
				for (int k = 0; k < 3; k++) {
					ll.add(TileColor.get(i));
				}
				l.add(ll);
			}
			faces.add(i, l);
		}
		this.display = display;
	}

	public Cube(TileColor frontFace, TileColor topFace) {
		this.frontFace = frontFace;
		this.topFace = topFace;
		for (int i = 0; i < TileColor.values().length; i++) {
			List<List<TileColor>> l = new ArrayList<List<TileColor>>();
			for (int j = 0; j < 3; j++) {
				List<TileColor> ll = new ArrayList<TileColor>();
				for (int k = 0; k < 3; k++) {
					ll.add(TileColor.get(i));
				}
				l.add(ll);
			}
			faces.add(i, l);
		}
	}

	public TileColor getFace(Side side) {
		if (side.equals(Side.front))
			return frontFace;
		else if (side.equals(Side.back))
			return frontFace.getOpposite();
		else if (side.equals(Side.up))
			return topFace;
		else if (side.equals(Side.down))
			return topFace.getOpposite();
		else if (side.equals(Side.left))
			return frontFace.getLeft(topFace);
		else if (side.equals(Side.right))
			return frontFace.getRight(topFace);
		return null;
	}

	public List<List<TileColor>> getList(TileColor color) {
		return faces.get(color.getInt());
	}

	public void turn(Turn turn) {
		List<List<TileColor>> list = getList(getFace(turn.getSide()));
		for (int i = 0; i < turn.getRotations(); i++) {
			rotateList(list);
			rotateEdges(getFace(turn.getSide()));
		}
	}

	public void turn(TileColor color) {
		List<List<TileColor>> list = getList(color);
		rotateList(list);
		rotateEdges(color);
	}

	public Side getSide(TileColor color) {
		for (Side s : Side.values()) {
			if (getFace(s).equals(color)) {
				return s;
			}
		}
		return null;
	}

	public void rotateList(List<List<TileColor>> list) {
		// Rotate corners
		TileColor topLeft = list.get(0).get(0);
		list.get(0).set(0, list.get(0).get(2));
		list.get(0).set(2, list.get(2).get(2));
		list.get(2).set(2, list.get(2).get(0));
		list.get(2).set(0, topLeft);

		// Rotate edges
		TileColor left = list.get(0).get(1);
		list.get(0).set(1, list.get(1).get(2));
		list.get(1).set(2, list.get(2).get(1));
		list.get(2).set(1, list.get(1).get(0));
		list.get(1).set(0, left);
	}

	public void rotateEdges(TileColor face) {
		List<int[]> corners = new ArrayList<int[]>();
		List<int[]> edges = new ArrayList<int[]>();
		if (face.equals(TileColor.white)) {
			// corners
			corners.add(createArray(1, 0, 2));
			corners.add(createArray(1, 2, 2));
			corners.add(createArray(2, 0, 0));
			corners.add(createArray(2, 0, 2));
			corners.add(createArray(4, 2, 0));
			corners.add(createArray(4, 0, 0));
			corners.add(createArray(5, 2, 2));
			corners.add(createArray(5, 2, 0));
			// edges
			edges.add(bottomEdge(TileColor.blue));
			edges.add(leftEdge(TileColor.red));
			edges.add(topEdge(TileColor.green));
			edges.add(rightEdge(TileColor.orange));
		} else if (face.equals(TileColor.blue)) {
			// corners
			corners.add(createArray(3, 2, 0));
			corners.add(createArray(3, 0, 0));
			corners.add(createArray(2, 2, 0));
			corners.add(createArray(2, 0, 0));
			corners.add(createArray(0, 2, 0));
			corners.add(createArray(0, 0, 0));
			corners.add(createArray(5, 2, 0));
			corners.add(createArray(5, 0, 0));
			// edges
			edges.add(topEdge(TileColor.yellow));
			edges.add(topEdge(TileColor.red));
			edges.add(topEdge(TileColor.white));
			edges.add(topEdge(TileColor.orange));
		} else if (face.equals(TileColor.red)) {
			// corners
			corners.add(createArray(1, 2, 2));
			corners.add(createArray(1, 2, 0));
			corners.add(createArray(3, 0, 0));
			corners.add(createArray(3, 0, 2));
			corners.add(createArray(4, 2, 2));
			corners.add(createArray(4, 2, 0));
			corners.add(createArray(0, 2, 2));
			corners.add(createArray(0, 2, 0));
			// edges
			edges.add(rightEdge(TileColor.blue));
			edges.add(leftEdge(TileColor.yellow));
			edges.add(rightEdge(TileColor.green));
			edges.add(rightEdge(TileColor.white));
		} else if (face.equals(TileColor.yellow)) {
			// corners
			corners.add(createArray(1, 2, 0));
			corners.add(createArray(1, 0, 0));
			corners.add(createArray(5, 0, 0));
			corners.add(createArray(5, 0, 2));
			corners.add(createArray(4, 0, 2));
			corners.add(createArray(4, 2, 2));
			corners.add(createArray(2, 2, 2));
			corners.add(createArray(2, 2, 0));
			// edges
			edges.add(topEdge(TileColor.blue));
			edges.add(leftEdge(TileColor.orange));
			edges.add(bottomEdge(TileColor.green));
			edges.add(rightEdge(TileColor.red));
		} else if (face.equals(TileColor.green)) {
			// corners
			corners.add(createArray(0, 0, 2));
			corners.add(createArray(0, 2, 2));
			corners.add(createArray(2, 0, 2));
			corners.add(createArray(2, 2, 2));
			corners.add(createArray(3, 0, 2));
			corners.add(createArray(3, 2, 2));
			corners.add(createArray(5, 0, 2));
			corners.add(createArray(5, 2, 2));
			// edges
			edges.add(bottomEdge(TileColor.white));
			edges.add(bottomEdge(TileColor.red));
			edges.add(bottomEdge(TileColor.yellow));
			edges.add(bottomEdge(TileColor.orange));
		} else if (face.equals(TileColor.orange)) {
			// corners
			corners.add(createArray(1, 0, 0));
			corners.add(createArray(1, 0, 2));
			corners.add(createArray(0, 0, 0));
			corners.add(createArray(0, 0, 2));
			corners.add(createArray(4, 0, 0));
			corners.add(createArray(4, 0, 2));
			corners.add(createArray(3, 2, 2));
			corners.add(createArray(3, 2, 0));
			// edges
			edges.add(leftEdge(TileColor.blue));
			edges.add(leftEdge(TileColor.white));
			edges.add(leftEdge(TileColor.green));
			edges.add(rightEdge(TileColor.yellow));
		}
		// set corners
		TileColor corner1 = getColor(corners.get(corners.size() - 2));
		TileColor corner2 = getColor(corners.get(corners.size() - 1));
		for (int i = corners.size() - 1; i > 1; i--) {
			setPiece(corners.get(i), getColor(corners.get(i - 2)));
		}
		setPiece(corners.get(0), corner1);
		setPiece(corners.get(1), corner2);
		// set edges
		TileColor edge = getColor(edges.get(edges.size() - 1));
		for (int i = edges.size() - 1; i > 0; i--) {
			setPiece(edges.get(i), getColor(edges.get(i - 1)));
		}
		setPiece(edges.get(0), edge);
	}

	public int[] createArray(int... i) {
		return i;
	}

	public void setPiece(int[] i, TileColor color) {
		faces.get(i[0]).get(i[1]).set(i[2], color);
	}

	public TileColor getColor(int... i) {
		return faces.get(i[0]).get(i[1]).get(i[2]);
	}

	public TileColor getOtherColor(int... i) {
		return this.getColor(Cube.getOthers(i).get(0));
	}

	private int[] bottomEdge(TileColor face) {
		int i = face.getInt();
		int[] ret = { i, 1, 2 };
		return ret;
	}

	private int[] leftEdge(TileColor face) {
		int i = face.getInt();
		int[] ret = { i, 0, 1 };
		return ret;
	}

	private int[] topEdge(TileColor face) {
		int i = face.getInt();
		int[] ret = { i, 1, 0 };
		return ret;
	}

	private int[] rightEdge(TileColor face) {
		int i = face.getInt();
		int[] ret = { i, 2, 1 };
		return ret;
	}

	public void render(Graphics g, Dimension frameSize) {
		g.setColor(Color.black);
		this.squareSize = (frameSize.width / 6);
		this.frameSize = frameSize;
		Point offset = new Point(150, -25);
		renderSide(g, getList(TileColor.orange), new Point(offset.x, frameSize.height / 2 - squareSize / 2 + offset.y), squareSize);
		renderSide(g, getList(TileColor.white), new Point(offset.x + squareSize, frameSize.height / 2 - squareSize / 2 + offset.y), squareSize);
		renderSide(g, getList(TileColor.red), new Point(offset.x + squareSize * 2, frameSize.height / 2 - squareSize / 2 + offset.y), squareSize);
		renderSide(g, getList(TileColor.yellow), new Point(offset.x + squareSize * 3, frameSize.height / 2 - squareSize / 2 + offset.y), squareSize);
		renderSide(g, getList(TileColor.blue), new Point(offset.x + squareSize, frameSize.height / 2 - squareSize / 2 - squareSize + offset.y), squareSize);
		renderSide(g, getList(TileColor.green), new Point(offset.x + squareSize, frameSize.height / 2 - squareSize / 2 + squareSize + offset.y), squareSize);
	}

	public void renderSide(Graphics g, List<List<TileColor>> face, Point p, int squareSize) {
		int cubieSize = squareSize / 3;
		g.drawRect(p.x, p.y, squareSize, squareSize);
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				g.setColor(Color.black);
				g.drawRect(p.x + cubieSize * x, p.y + cubieSize * y, cubieSize + 1, cubieSize + 1);
				g.setColor(face.get(x).get(y).getColor());
				g.fillRect(p.x + 1 + cubieSize * x, p.y + 1 + cubieSize * y, cubieSize, cubieSize);
			}
		}
	}

	public Point getSideLocation(TileColor face) {
		Point offset = new Point(150, -25);
		if (face.equals(TileColor.orange))
			return new Point(offset.x, frameSize.height / 2 - squareSize / 2 + offset.y);
		else if (face.equals(TileColor.white))
			return new Point(offset.x + squareSize, frameSize.height / 2 - squareSize / 2 + offset.y);
		else if (face.equals(TileColor.red))
			return new Point(offset.x + squareSize * 2, frameSize.height / 2 - squareSize / 2 + offset.y);
		else if (face.equals(TileColor.yellow))
			return new Point(offset.x + squareSize * 3, frameSize.height / 2 - squareSize / 2 + offset.y);
		else if (face.equals(TileColor.blue))
			return new Point(offset.x + squareSize, frameSize.height / 2 - squareSize / 2 - squareSize + offset.y);
		else if (face.equals(TileColor.green))
			return new Point(offset.x + squareSize, frameSize.height / 2 - squareSize / 2 + squareSize + offset.y);
		return null;
	}

	public int[] getCubie(Point p) {
		int cubieSize = squareSize / 3;
		for (int f = 0; f < 6; f++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					Point l = getSideLocation(TileColor.get(f));
					if (p.x >= l.x + 1 + cubieSize * x && p.x <= (l.x + 1 + cubieSize * x) + cubieSize && p.y >= l.y + 1 + cubieSize * y && p.y <= (l.y + 1 + cubieSize * y) + cubieSize) {
						return createArray(f, x, y);
					}
				}
			}
		}
		return null;
	}

	public static boolean isCorner(int[] i) {
		return !isEdge(i);
	}

	public static boolean isEdge(int[] i) {
		return (i[1] == 1 || i[2] == 1);
	}

	public void doAlgorithm(String s) {
		if (s.startsWith(","))
			s = s.substring(1);
		if (s.endsWith(","))
			s = s.substring(0, s.length() - 1);
		String[] sA = s.split(",");
		for (String sL : sA) {
			if (sL.length() > 0)
				turn(Turn.valueOf(sL));
		}
	}

	public static List<int[]> getOthers(int[] i) {
		if (i[0] != -1) {
			int[][][] edges = { { { 0, 1, 0 }, { 1, 1, 2 } }, { { 0, 2, 1 }, { 2, 0, 1 } }, { { 0, 1, 2 }, { 4, 1, 0 } }, { { 0, 0, 1 }, { 5, 2, 1 } }, { { 3, 1, 0 }, { 1, 1, 0 } }, { { 3, 2, 1 }, { 5, 0, 1 } }, { { 3, 1, 2 }, { 4, 1, 2 } }, { { 3, 0, 1 }, { 2, 2, 1 } }, { { 1, 0, 1 }, { 5, 1, 0 } }, { { 1, 2, 1 }, { 2, 1, 0 } }, { { 4, 0, 1 }, { 5, 1, 2 } }, { { 4, 2, 1 }, { 2, 1, 2 } } };
			int[][][] corners = { { { 0, 0, 0 }, { 5, 2, 0 }, { 1, 0, 2 } }, { { 0, 2, 0 }, { 1, 2, 2 }, { 2, 0, 0 } }, { { 0, 2, 2 }, { 2, 0, 2 }, { 4, 2, 0 } }, { { 0, 0, 2 }, { 4, 0, 0 }, { 5, 2, 2 } }, { { 3, 0, 0 }, { 2, 2, 0 }, { 1, 2, 0 } }, { { 3, 2, 0 }, { 1, 0, 0 }, { 5, 0, 0 } }, { { 3, 2, 2 }, { 4, 0, 2 }, { 5, 0, 2 } }, { { 3, 0, 2 }, { 4, 2, 2 }, { 2, 2, 2 } } };
			int[][][] a = edges;
			if (isCorner(i)) {
				a = corners;
			}
			Point p = null;
			boolean found = false;
			for (int k = 0; k < a.length; k++) {
				int[][] i0 = a[k];
				for (int ii = 0; ii < i0.length; ii++) {
					boolean same = true;
					int[] i1 = i0[ii];
					for (int j = 0; j < i1.length; j++) {
						if (i1[j] != i[j]) {
							same = false;
							break;
						}
					}
					found = same;
					if (same) {
						p = new Point(k, ii);
						break;
					}
				}
				if (found)
					break;
			}
			List<int[]> ret = new ArrayList<int[]>();
			for (int j = 0; j < a[p.x].length; j++) {
				if (j != p.y)
					ret.add(a[p.x][j]);
			}
			return ret;
		}
		return null;
	}

	public void changeColor(int[] i, int b) {
		if (b == 1) {
			int c = getColor(i).getInt() + 1;
			if (c > 5)
				c = 0;
			setPiece(i, TileColor.get(c));
		} else if (b == 2) {
			int c = getColor(i).getInt() + 3;
			if (c > 5)
				c -= 6;
			setPiece(i, TileColor.get(c));
		} else if (b == 3) {
			int c = getColor(i).getInt() - 1;
			if (c < 0)
				c = 5;
			setPiece(i, TileColor.get(c));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		int[] cubie = getCubie(p);
		if (cubie != null) {
			if (cubie[1] == 1 && cubie[2] == 1) {
				if (e.getButton() == 1) {
					display.turns += "," + this.getSide(getColor(cubie)).getName();
					turn(getColor(cubie));
				} else if (e.getButton() == 2) {
					display.turns += "," + this.getSide(getColor(cubie)).getName() + "2";
					for (int i = 0; i < 2; i++) {
						turn(getColor(cubie));
					}
				} else if (e.getButton() == 3) {
					display.turns += "," + this.getSide(getColor(cubie)).getName() + "I";
					for (int i = 0; i < 3; i++) {
						turn(getColor(cubie));
					}
				}
				if (display.turns.startsWith(",")) {
					display.turns = display.turns.substring(1);
				}
			} else {
				display.changedCubie = true;
				changeColor(cubie, e.getButton());
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public static String compact(String movements) {
		String strB4 = movements;
		if (movements.startsWith(","))
			movements = movements.substring(1);
		if (movements.endsWith(","))
			movements = movements.substring(0, movements.length() - 1);
		while (!compactList(movements).equals(strB4)) {
			strB4 = movements;
			movements = compactList(movements);
		}
		return movements;
	}

	public static String compactList(String movements) {
		String[] array = movements.split(",");
		List<String> list = new ArrayList<String>();
		for (String i : array) {
			list.add(i);
		}
		for (int i = 0; i < list.size() - 1; i++) {
			char turn1 = list.get(i).charAt(0);
			char kind1 = ' ';
			if (list.get(i).length() != 1) {
				kind1 = list.get(i).charAt(1);
			}
			char turn2 = list.get(i + 1).charAt(0);
			char kind2 = ' ';
			if (list.get(i + 1).length() != 1) {
				kind2 = list.get(i + 1).charAt(1);
			}

			if (turn1 == turn2) {
				if (kind1 == kind2) {
					if (kind1 == '2') {
						list.remove(i);
						list.remove(i);
						i--;
					} else if (kind1 == ' ' || kind1 == 'I') {
						list.remove(i);
						list.set(i, turn1 + "2");
					}
				} else if ((kind1 == ' ' && kind2 == 'I') || (kind1 == 'I' && kind2 == ' ')) {
					list.remove(i);
					list.remove(i);
					i--;
				} else if ((kind1 == '2' && kind2 == ' ') || (kind1 == ' ' && kind2 == '2')) {
					list.remove(i);
					list.set(i, turn1 + "I");
				} else if ((kind1 == '2' && kind2 == 'I') || (kind1 == 'I' && kind2 == '2')) {
					list.remove(i);
					list.set(i, Character.toString(turn1));
				}
			}
		}
		movements = "";
		for (String i : list) {
			movements += i + ",";
		}
		movements = movements.substring(0, movements.length() - 1);

		return movements;
	}

	public static String rotateAlgorithm(String alg) {
		List<Integer> FPos = new ArrayList<Integer>();
		for (int i = 0; i < alg.length(); i++) {
			if (alg.charAt(i) == 'F') {
				FPos.add(i);
			}
		}
		alg = alg.replace('R', 'F');
		alg = alg.replace('B', 'R');
		alg = alg.replace('L', 'B');
		for (int i : FPos) {
			alg = alg.substring(0, i) + "L" + alg.substring(i + 1, alg.length());
		}
		return alg + (alg.endsWith(",") ? "" : ",");
	}

	public Cube clone() {
		Cube ret = new Cube(this.frontFace, this.topFace, display);
		for (int f = 0; f < 6; f++) {
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					ret.faces.get(f).get(x).set(y, this.faces.get(f).get(x).get(y));
				}
			}
		}
		return ret;
	}

	public static String invertAlgorithm(String algo) {
		String[] array = algo.split(",");
		List<String> list = new ArrayList<String>();
		for (String i : array) {
			list.add(i);
		}
		algo = "";
		for (int i = list.size() - 1; i >= 0; i--) {
			char turn = list.get(i).charAt(0);
			char kind = ' ';
			if (list.get(i).length() != 1) {
				kind = list.get(i).charAt(1);
			}
			if (kind == ' ') {
				algo += turn + "I,";
			} else if (kind == 'I') {
				algo += turn + ",";
			} else {
				algo += list.get(i) + ",";
			}
		}
		if (algo.endsWith(",")) {
			algo = algo.substring(0, algo.length() - 1);
		}
		return algo;
	}

	public boolean isSolved(){
		for(int f = 0; f < 6; f++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					if(!getColor(f,x,y).equals(TileColor.get(f))) return false;
				}
			}
		}
		return true;
	}
	
	public String getSolve() {
		String ret = "";
		List<Solver> solvers = new ArrayList<Solver>();
		solvers.add(new CrossSolver());
		solvers.add(new F2LSolver());
		solvers.add(new OLLSolver());
		solvers.add(new PLLSolver());
		Cube cubeCopy = this.clone();
		for (Solver s : solvers) {
			String a = s.solve(cubeCopy);
			ret += a;
		}
		ret = ret.replace(",,", ",");
		return compact(ret);
	}

	public String solve() {
		display.turns = "";
		display.changedCubie = false;
		String ret = getSolve();
		this.doAlgorithm(getSolve());
		return ret;
	}
	
	public boolean isPossible(){
		int[] colors = {0,0,0,0,0,0};
		for(int f = 0; f < 6; f++){
			for(int x = 0; x < 3; x++){
				for(int y = 0; y < 3; y++){
					colors[getColor(f,x,y).getInt()]++;
				}
			}
		}
		for(int i = 0; i < 5; i++){
			if(colors[i] != colors[i+1]){
				return false;
			}
		}
		return true;
	}
}