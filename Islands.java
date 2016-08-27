import java.util.*;

public class Islands {

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
			for (int j = 0; j < n; j++) {
				// We're going through this matrix row by row.
				if (matrix[i][j] == 1) {
					// When we see a 1, it could be the first 1 of it's island that we're
					// seeing.
					boolean isIndependent = true;
					if (i > 0 && matrix[i-1][j] == 1) {
						// But if it has a 1 above it, then we def have seen that 1 in
						// a previous pass; and hence have counted that 1 towrds an island
						isIndependent = false;
					} 
					if (j > 0 && matrix[i][j-1] == 1) {
						// But if it has a 1 to the left of it, then we def have seen that 1 in
						// a previous pass. and hence have counted that 1 towrds an island
						isIndependent = false;
					}
					if (isIndependent) {
						num++;
					}
				}
			}
		}

		System.out.println(num);

	}
}