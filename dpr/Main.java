package dpr;

public class Main {

	public static void main(String[] args) {
		Point points[] = { new Point(0, 0), new Point(0, 4), new Point(-4, 0), new Point(5, 0), new Point(0, -6),
				new Point(1, 0) };
		// Output : (-4, 0), (5, 0), (0, -6), (0, 4)
//		Point points[] = { new Point(0, 3), new Point(1, 1), new Point(2, 2)};
//		(0, 3),(1, 1),(2, 2)
		EnveloppeConvexe ec = new EnveloppeConvexe();
		ec.donnerEnveloppeConnexe(points);

	}

}
