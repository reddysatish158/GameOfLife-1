package de.hska.iwii.i2.gol;

/**
 * Die eigentliche Spielelogik. Das Spielfeld wird hier nicht als zyklisch
 * geschlossen betrachtet wird.
 * 
 * @author Fabio Ferreira
 */
public class GameOfLifeLogic {
	boolean[][][] population;

	/**
	 * Passes the current start configuration chosen by the user.
	 * 
	 * @param generation
	 *            The start configuration.
	 */
	public void setStartGeneration(boolean[][] generation) {

		population = new boolean[2][generation.length][generation[0].length];

		for (int i = 0; i < generation.length; i++) {
			for (int j = 0; j < generation[i].length; j++) {
				population[0][i][j] = generation[i][j];
			}
		}
	}

	/**
	 * Determines the following generation based upon the rules of Game of Life
	 * and the start configuration.
	 */
	public void nextGeneration() {
		int neighborsAlive = 0;
		for (int i = 1; i < population[0].length - 1; i++) {
			for (int j = 1; j < population[0][i].length - 1; j++) {

				neighborsAlive = countNeighborsAlive(i, j);

				// cell alive
				if (isCellAlive(i, j) == true) {
					// death case (overpopulation or loneliness)
					if (neighborsAlive > 3 || neighborsAlive < 2) {
						population[1][i][j] = false;
					} else { // survive case
						population[1][i][j] = true;
					}
					// cell dead
				} else {
					// revive case
					if (neighborsAlive == 3) {
						population[1][i][j] = true;
					}

				}

			}
		}
		for (int i = 0; i < population[0].length; i++) {
			for (int j = 0; j < population[0][i].length; j++) {
				population[0][i][j] = population[1][i][j];
			}
		}
	}

	/**
	 * The method determines the surrounding living cells for a given coordinate
	 * (x,y). The count will be decremented by one in the case the viewed
	 * coordinate is alive itself.
	 * 
	 * @param x
	 *            x-coordinate of the currently viewed cell.
	 * @param y
	 *            y-coordinate of the currently viewed cell.
	 * @return The count of living cells which are surrounding the currently
	 *         viewed cell. The count will always be larger or equal zero.
	 */
	private int countNeighborsAlive(int x, int y) {
		int count = 0;
		for (int k = x - 1; k <= x + 1; k++) { // check rows -1, 0, +1
			for (int l = y - 1; l <= y + 1; l++) { // check columns -1, 0, +1
				if (isCellAlive(k, l) == true) {
					count++;
				}
			}
		}
		if (isCellAlive(x, y) == true) {
			count--;
		}
		return count;
	}

	/**
	 * Checks if the currently viewed cell of the pre-configuration is alive.
	 * 
	 * @param x
	 *            x-coordinate of the currently viewed cell.
	 * @param y
	 *            y-coordinate of the currently viewed cell.
	 * @return true if cell is alive, false if dead.
	 */
	public boolean isCellAlive(int x, int y) {
		return population[0][x][y];
	}
}