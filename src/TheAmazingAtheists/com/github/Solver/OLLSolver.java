package TheAmazingAtheists.com.github.Solver;

import java.util.List;

import TheAmazingAtheists.com.github.Algorithms.OLL;
import TheAmazingAtheists.com.github.CubeSolver.TileColor;

public class OLLSolver implements Solver, OLL {

	@Override
	public String solve(Cube cube) {
		for (int j = 1; j < OLL.length; j++) {
			String alg = OLL[j];
			for (int i = 0; i < 4; i++) {
				Cube cubeClone = cube.clone();
				cubeClone.doAlgorithm(alg);
				boolean correct = true;
				for (List<TileColor> list : cubeClone.faces.get(3)) {
					for (TileColor c : list) {
						if (c.getInt() != 3) {
							correct = false;
						}
					}
				}
				if(correct){
					cube.doAlgorithm(alg);
					return alg;
				}
				alg = Cube.rotateAlgorithm(alg);
			}
		}
		return "";
	}

}
