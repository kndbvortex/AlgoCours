package dpr;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Point points[] = { new Point(0, 0), new Point(0, 4), new Point(-4, 0), new Point(5, 0), new Point(0, -6),
				new Point(1, 0) };
		Point points1[] = { new Point(0, 3), new Point(1, 1), new Point(2, 2), new Point(4, 4), new Point(0, 0),
				new Point(1, 2), new Point(3, 1), new Point(3, 3), new Point(2, 6) };
		
		Point points3[] = { new Point(0, 3), new Point(1, 1), new Point(2, 2) };
		
		Point points4[] = { new Point(0, 3), new Point(1, 1), new Point(2, 2), new Point(4, 4), new Point(0, 0),
				new Point(1, 2), new Point(3, 1), new Point(3, 3), new Point(2, 6),
				new Point(0, -3), new Point(-1, -1), new Point(-2, -2), new Point(-4, -4),
				new Point(-1, -2), new Point(-3, -1), new Point(-3, -3), new Point(-2, -6)
		};

		EnveloppeConvexe ec = new EnveloppeConvexe();
		Point resultsPoints[] = { new Point(0.0, -6.0), new Point(-4.0, 0.0), new Point(0.0, 4.0), new Point(5.0, 0.0)};
		System.out.println(Arrays.compare(ec.donnerEnveloppeConnexe(points), resultsPoints) > 0);
		// Output : (-4, 0), (5, 0), (0, -6), (0, 4)
		Point[] result3 = {new Point(0, 3), new Point(1,1), new Point(2,2)};
		System.out.println(Arrays.compare(ec.donnerEnveloppeConnexe(points3), result3) > 0);
////		(0, 3),(1, 1),(2, 2)
//
		Point result1[] = { new Point(0.0, -6.0), new Point(0, 3), new Point(2, 6), new Point(4, 4), new Point(3, 1)};
//		(0, 0), (0, 3), (2, 6), (4, 4), (3, 1)
		System.out.println(Arrays.compare(ec.donnerEnveloppeConnexe(points1), result1) > 0);

		
		Point result4[] = {   new Point(4, 4),new Point(3, 1), 
				new Point(2, 6), new Point(-4, -4),
				new Point(-3, -1), new Point(-2, -6)
		};
		
		Point p[] = ec.donnerEnveloppeConnexe(points4);
		Arrays.sort(p);
		Arrays.sort(result4);
		
		System.out.println(Arrays.compare(p, result4)>0);

	}

}
