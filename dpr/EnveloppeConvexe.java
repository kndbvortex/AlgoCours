package dpr;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Stack;

public class EnveloppeConvexe {

	private LinkedList<Point> EC = new LinkedList<Point>();

	/*
	 * Coté d'un point p par rapport aux 2 premier par calcul du déterminant si
	 * l'angle (p0p1,p0p) est direct retour 1 s'il est indirect retour -1, s'il est
	 * un multiple de pie, retourner 0
	 */

	public int cote(Point p0, Point p1, Point p) {
		double det = (p1.getAbscisse() - p0.getAbscisse()) * (p.getOrdonne() - p0.getOrdonne())
				- (p1.getOrdonne() - p0.getOrdonne()) * (p.getAbscisse() - p0.getAbscisse());
		if (det > 0)
			return 1;
		else if (det == 0d)
			return 0;
		else
			return -1;
	}

	/* Fonction utilisé dans l'aproche brute */

	private void findHull(Point[] points, Point A, Point B) {
		if (points.length == 0) {

		} else if (points.length == 1)
			EC.add(points[0]);
		else {
			Point C = null;
			double distance_max = 0;
			for (Point p : points) {
				double d = p.distance(A, B);
				if (d > distance_max) {
					distance_max = d;
					C = p;
				}
			}
			EC.add(C);
			int cote1 = cote(C, A, B);
			int cote2 = cote(C, B, A);
			LinkedList<Point> S1 = new LinkedList<Point>(), S2 = new LinkedList<Point>();
			for (Point p : points) {
				if (cote(C, A, p) == 0 - cote1)
					S1.add(p);
				else if (cote(C, B, p) == 0 - cote2)
					S2.add(p);
			}
			findHull(S1.toArray(new Point[S1.size()]), A, C);
			findHull(S2.toArray(new Point[S2.size()]), C, B);
		}
	}

	private Point[] quickHull(Point[] points) {
		EC.clear();
		if (points.length < 3) {
			System.out.println("Enveloppe connexe c'est pour 3 points min");
			return null;
		} else {
			Point pointG = points[0], pointD = points[points.length - 1];
			for (Point p : points) {
				if (p.getAbscisse() < pointG.getAbscisse())
					pointG = p;
				else if (p.getAbscisse() > pointD.getAbscisse())
					pointD = p;
			}

			LinkedList<Point> S1 = new LinkedList<Point>(), S2 = new LinkedList<Point>();
			for (Point p : points) {
				if (cote(pointG, pointD, p) == 1)
					S1.add(p);
				else if (cote(pointG, pointD, p) == -1)
					S2.add(p);
			}

			EC.add(pointG);
			findHull(S1.toArray(new Point[S1.size()]), pointG, pointD);
			EC.add(pointD);
			findHull(S2.toArray(new Point[S2.size()]), pointD, pointG);
			return EC.toArray(new Point[EC.size()]);
		}
	}

	public Point[] donnerEnveloppeConnexe(Point[] points) {
		if (points == null || points.length < 3) {
			return null;
		}
		Point[] ec = quickHull(points);
		if (ec.length < 3 || ec == null) {
			return null;
		}
		return ec;

	}
	
	public Point[] donnerEnveloppeConnexeNaif(Point[] points) {
		if (points == null || points.length < 3) {
			return null;
		}
		Point[] ec = Graham(points);
		if (ec.length < 3 || ec == null) {
			return null;
		}
		return ec;

	}


	/*
	 * Methode qui prend en entrée 3 points A, B, C et retourne l'angle formé en A
	 */

	public double donnerAngle(Point A, Point B, Point C) {

		double distance_AB, distance_AC, distance_BC, cosAngle;

		distance_AB = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));

		distance_AC = Math.sqrt((C.x - A.x) * (C.x - A.x) + (C.y - A.y) * (C.y - A.y));

		distance_BC = Math.sqrt((C.x - B.x) * (C.x - B.x) + (C.y - B.y) * (C.y - B.y));

		cosAngle = ((distance_AB * distance_AB) + (distance_AC * distance_AC) - (distance_BC * distance_BC))
				/ (2 * distance_AB * distance_AC);

		return Math.toDegrees(Math.acos(cosAngle));

	}

	public Point[] classerPoints(Point[] points) {

		LinkedList<double[]> liste_IndicePoints_angles = new LinkedList(); // liste de tableau à deux dimensions: dim1 =
																			// indice du tableau de points en entree et
																			// dim2 = angle en ce point la

		Point tableau_de_points_ordonnees[] = new Point[points.length]; // Tableau de retour: il contient les points
																		// classes dans l'ordre croissant d'angle
																		// polaire
		int j = 1;

		Point pivot = new Point(points[0].x, points[0].y);
		// boucle de recuperation du point d'ordonnee minimale
		for (Point p : points) {
			if (p.y < pivot.y || (p.y == pivot.y && p.x < pivot.x)) {
				pivot = p;
			}
		}

		Point aide = new Point(pivot.x + 4, pivot.y); // point cree pour m'aider à recensser les points par angle
														// croissant

		for (int i = 0; i < points.length; i++) {
			if (!points[i].equals(pivot)) {
				double angle = donnerAngle(pivot, aide, points[i]);
				double[] tab = { i, angle };
				liste_IndicePoints_angles.add(tab);
			}
		}

		// Construction du tableau de points dans l'ordre croissant d'angles polaires

		double[][] tableau_de_tableau = liste_IndicePoints_angles
				.toArray(new double[liste_IndicePoints_angles.size()][2]);

		Arrays.sort(tableau_de_tableau, new Comparator<double[]>() {

			@Override
			public int compare(double[] sous_tableau1, double[] sous_tableau2) {
				if (sous_tableau1[1] < sous_tableau2[1]) {
					return -1;

				} else if (sous_tableau1[1] > sous_tableau2[1]) {
					return 1;
				} else {
					return 0;
				}

			}
		});

		tableau_de_points_ordonnees[0] = pivot;
		for (double[] p : tableau_de_tableau) {

			tableau_de_points_ordonnees[j] = points[(int) p[0]];
			j++;
		}

		return tableau_de_points_ordonnees;
	}

	public Point[] Graham(Point[] points) {
		Point point_en_cas_de_points_alignes[] = { new Point(0.0, 0.0) };
		int decision = 2;
		decision = PointsAlignes(points);
		if (decision == 1) {
			return point_en_cas_de_points_alignes;
		}

		Point points_ordonnees[] = new Point[points.length];

		points_ordonnees = classerPoints(points);

		Stack<Point> pile = new Stack();
		pile.push(points_ordonnees[0]);
		pile.push(points_ordonnees[1]);
		pile.push(points_ordonnees[2]);

		for (int i = 3; i < points_ordonnees.length; i++) {

			while (cote(pile.peek(), pile.elementAt(pile.size() - 2), points_ordonnees[i]) >= 0) {

				pile.pop();

			}

			pile.push(points_ordonnees[i]);
		}
		return pile.toArray(new Point[pile.size()]);

	}

	public int PointsAlignes(Point[] points) {
		int aligne = 0, verite = 1;
		for (Point p : points) {
			aligne = 0;
			if (p.x == p.y) {
				aligne = 1;
			} else {
				verite = 0;
			}

		}

		if (verite == 1) {
			return 1;
		}

		else {
			return 0;
		}
	}

}