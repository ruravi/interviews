import java.util.*;

public class Islands {

	private enum State {
		IN,
		OUT,
		ALREADYSEEN,
	}

	/**
	 * Input format:
	 *  3 			// m num rows in matrix
	 *	5			// n num columns in matrix
	 *	0 0 1 1 0	
	 *	1 0 1 0 0
	 *	0 0 1 0 0
	 * Question: Find the number of islands of 1 in the given matrix.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int m = scanner.nextInt();
		int n = scanner.nextInt();

		int[][] matrix = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = scanner.nextInt();
			}
		}

		int num = 0;
		for (int i = 0; i < m; i++) {
			// For each row, we begin fresh.
			State state = State.OUT;
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 1) {
					switch (state) {
						case OUT:
							// This is the first 1 in this row.
							state = State.IN;
							num++; // Optimistically count it as an island unless proven otherwise.
							if (i > 0 && matrix[i-1][j] == 1) {
								state = State.ALREADYSEEN;
								num--;
							}
							break;
						case IN:
							// If this 1 is connected to a previously seen 1,
							// then we need to decrement the count, since we would have
							// incremented it when we saw the first 1 on this line.
							if (i > 0 && matrix[i-1][j] == 1) {
								state = State.ALREADYSEEN;
								num--;
							}
							break;
					}
				} else {
					state = State.OUT;
				}
			}
		}

		System.out.println(num);

	}
}