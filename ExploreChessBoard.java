import java.util.*;

/**
 * Knights's tour: https://en.wikipedia.org/wiki/Knight%27s_tour
 * Write a program to see if a knight's tour exists for a board of a given size.
 */
class ExploreChessBoard {

	private static class Point {
		public int row;
		public int col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public String toString() {
			return "(" + row + "," + col + ")";
		}
	}

	private static void explore(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				boolean[][] visited = new boolean[n][n];
				visited[i][j] = true;
				if (exploreRecursive(i, j, n, visited, 1)) {
					System.out.println("Solution exists from: " + i + "," + j);
				}
			}
		}
	}

	private static boolean exploreRecursive(int i, int j, int n, boolean[][] visited, int numVisited) {
		// Base case.
		if (numVisited == n * n) {
			return true;
		}

		List<Point> validMoves = getValidMoves(i, j, n, visited);
		for (Point move : validMoves) {
			// Visit each reachable point as long as it hasn't been visited before.
			visited[move.row][move.col] = true;
			if (exploreRecursive(move.row, move.col, n, visited, numVisited + 1)) {
				return true;
			}
			// Nope the above exploration didn't work.
			// Be sure to mark this point as not visited for other tries starting from here.
			visited[move.row][move.col] = false;
		}
		return false;
	}

	private static List<Point> getValidMoves(int row, int col, int n, boolean[][] visited) {
		ArrayList<Point> list = new ArrayList<>();
		// Down right
		int nextRow = row + 2;
		int nextCol = col + 1;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		nextRow = row + 1;
		nextCol = col + 2;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		// Down left
		nextRow = row + 2;
		nextCol = col - 1;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		nextRow = row + 1;
		nextCol = col - 2;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		// Up left
		nextRow = row - 2;
		nextCol = col - 1;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		nextRow = row - 1;
		nextCol = col - 2;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		// Up right
		nextRow = row - 2;
		nextCol = col + 1;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		nextRow = row - 1;
		nextCol = col + 2;
		if (isPointValid(nextRow, nextCol, n, visited)) {
			list.add(new Point(nextRow, nextCol));
		}
		return list;
	}

	private static boolean isPointValid(int row, int col, int n, boolean[][] visited) {
		return row >= 0 && row < n && col >= 0 && col < n && !visited[row][col];
	}

	public static void main(String[] args) {
		explore(8);
	}
	
}