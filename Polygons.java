import java.util.*;

/**
 * Given a polygon represented by a set of points, find out if a given point
 * falls inside the polygon or not.
 *
 * Solution: Ray tracing. Draw a line through the given point parallel to the x-axis
 * Start traversing this line from the left to the right, counting the number
 * of times it intersects the polygon, before reaching the point under test.
 * If it crosses the polygon an even number of times, our test point is outside.
 */
public class Polygons {

	private static class Point {
		public int x;
		public int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	/**
	 * polygon - the points should always be given clockwise.
	 */
	private static boolean isOnEdgeOfPolygon(List<Point> polygon, Point test) {
		int n = polygon.size();
		int x = test.x;
		int y = test.y;
		for (int i = 0; i < n; i++) {
			int x1 = polygon.get(i).x;
			int y1 = polygon.get(i).y;

			int j = (i+1) % n;
			int x2 = polygon.get(j).x;
			int y2 = polygon.get(j).y;

			int lhs = (y - y1) * (x2 - x1);
			int rhs = (x - x1) * (y2 - y1);

			if (lhs == rhs) {
				// Point lies on the line, now check if it lies on the line
				// segment between the two points.
				System.out.println("isOnEdgeOfPolygon: " + test + polygon.get(i) + polygon.get(j));
				if (x >= Math.min(x1, x2) && x <= Math.max(x1,x2)
					&& y >= Math.min(y1, y2) && y <= Math.max(y1, y2)) {
					System.out.println("True");
					return true;
				}

			}
		}
		return false;
	}

	private static int findLeastX(List<Point> points) {
		int least = Integer.MAX_VALUE;
		for (Point point : points) {
			if (point.x < least) {
				least = point.x;
			}
		}
		return least;
	}

	private static int findGreatestX(List<Point> points) {
		int greatest = Integer.MIN_VALUE;
		for (Point point : points) {
			if (point.x > greatest) {
				greatest = point.x;
			}
		}
		return greatest;	
	}

	private static boolean isInPolygon(List<Point> polygon, Point test) {
		int startX = findLeastX(polygon);
		int endX = findGreatestX(polygon);

		int numCrosses = 0;
		while (startX <= endX && startX < test.x) {
			System.out.println("Testing " + new Point(startX, test.y));
			if (isOnEdgeOfPolygon(polygon, new Point(startX, test.y))) {
				numCrosses++;
			}
			startX += 0.5;
		}

		if (numCrosses % 2 == 1) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		List<Point> input = new ArrayList<>();
		input.add(new Point(-5,1));
		input.add(new Point(2,6));
		input.add(new Point(4,10));
		input.add(new Point(6,1));

		Scanner scanner = new Scanner(System.in);
		for (int i = 0; i < 5; i++) {
			int x = scanner.nextInt();
			int y = scanner.nextInt();

			Point test = new Point(x,y);
			System.out.println("The point " + test + " lies " +
				(isInPolygon(input, test) ? "inside" : "outside") +
				" the polygon.");
		}
	}

}