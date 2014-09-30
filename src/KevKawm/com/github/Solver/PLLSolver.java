package KevKawm.com.github.Solver;

import KevKawm.com.github.Algorithms.PLL;
import KevKawm.com.github.CubeSolver.TileColor;

public class PLLSolver implements Solver, PLL {

	@Override
	public String solve(Cube cube) {
		boolean Break = false;
		String algo = "";
		for (int j = 0; j < PLL.length; j++) {
			String alg = PLL[j];
			for (int i = 0; i < 4; i++) {
				Cube cubeClone = cube.clone();
				cubeClone.doAlgorithm(alg);
				if (isAlmostSolved(cubeClone)) {
					cube.doAlgorithm(alg);
					algo = alg;
					Break = true;
					break;
				}
				alg = Cube.rotateAlgorithm(alg);
			}
			if (Break) break;
		}
		for (int i = 0; !isSolved(cube) && i < 4; i++) {
			cube.turn(Turn.U);
			algo += ",U";
		}
		return algo;
	}

	private static boolean isAlmostSolved(Cube cube) {
		for (int i = 1; i < 6; i = i == 2 ? i + 2 : i + 1) {
			if (i == 1) {
				if (!((cube.getList(TileColor.get(i)).get(0).get(0) == cube.getList(TileColor.get(i)).get(1).get(0)) && (cube.getList(TileColor.get(i)).get(2).get(0) == cube.getList(TileColor.get(i)).get(1).get(0))))
					return false;
			} else if (i == 2) {
				if (!((cube.getList(TileColor.get(i)).get(2).get(0) == cube.getList(TileColor.get(i)).get(2).get(1)) && (cube.getList(TileColor.get(i)).get(2).get(2) == cube.getList(TileColor.get(i)).get(2).get(1))))
					return false;
			} else if (i == 4) {
				if (!((cube.getList(TileColor.get(i)).get(0).get(2) == cube.getList(TileColor.get(i)).get(1).get(2)) && (cube.getList(TileColor.get(i)).get(2).get(2) == cube.getList(TileColor.get(i)).get(1).get(2))))
					return false;
			} else if (i == 5) {
				if (!((cube.getList(TileColor.get(i)).get(0).get(0) == cube.getList(TileColor.get(i)).get(0).get(1)) && (cube.getList(TileColor.get(i)).get(0).get(2) == cube.getList(TileColor.get(i)).get(0).get(1))))
					return false;
			}
		}
		return true;
	}

	public static boolean isSolved(Cube cube) {
		for (int i = 1; i < 6; i = i == 2 ? i + 2 : i + 1) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					if (cube.getList(TileColor.get(i)).get(j).get(k) != new Cube(TileColor.blue, TileColor.yellow).getList(TileColor.get(i)).get(j).get(k))
						return false;
				}
			}
		}
		return true;
	}
}