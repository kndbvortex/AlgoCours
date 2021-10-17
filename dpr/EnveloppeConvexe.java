package dpr;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class EnveloppeConvexe {

	private LinkedList<Point> EC = new LinkedList<Point>();

	/*
	 * Coté d'un point p par rapport aux 2 premier par calcul du déterminant si
	 * l'angle (p0p1,p0p) est direct retour 1 s'il est indirect retour -1, s'il est
	 * un multiple de pie, retourner 0
	 */

	public int cote(Point p0, Point p1, Point p) {
		if(p0.getAbscisse() > p1.getAbscisse())
			return cote(p1, p0, p);
		if(p0.getAbscisse() == p1.getAbscisse()) {
			if(p0.getOrdonne() > p1.getOrdonne()) {
				return cote(p1, p0, p);
			}
		}
		double a=0d, b=0d, c=0d, val=0d;
		a = (p1.getOrdonne() - p0.getOrdonne());
		b = 0 - (p1.getAbscisse() - p0.getAbscisse());
		c = 0 - (a*p0.getAbscisse() + b*p0.getOrdonne());
		byte dir = -1;
		if(b != 0) {
			if(a/b < 0)
				dir = 1;
		}
		val = a*p.getAbscisse() + b*p.getOrdonne() + c;
		if(val > 0)
			return dir*1;
		else if(val<0)
			return -1*dir;
		else
			return 0;
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
				if (cote(C, A, p) == -cote1)
					S1.add(p);
				else if (cote(C, B, p) == -cote2)
					S2.add(p);
			}
			findHull(S1.toArray(new Point[S1.size()]), A, C);
			findHull(S2.toArray(new Point[S2.size()]), C, B);
		}
	}

	/*
	 * Fusion qui permet de relier les 2 sous enveloppes convexes par détermination
	 * des points de la tangente la plus haute et ceux de la tangente la plus basse
	 */
	private Point[] fusion(Point[] ECG, Point[] ECD) {
		if (ECG == null && ECD == null) {
			return null;
		} else if (ECG == null || ECD == null) {
			if (ECG == null)
				return ECD;
			return ECG;
		} else {
			LinkedList<Point> enveloppe = new LinkedList<Point>();
			Point[] ECGy = Arrays.copyOf(ECG, ECG.length);
			Point[] ECDy = Arrays.copyOf(ECD, ECD.length);

			Arrays.sort(ECG);
			Arrays.sort(ECD);
			Arrays.sort(ECGy, new Comparator<Point>() {
				@Override
				public int compare(Point p, Point p1) {
					if (p.getOrdonne() > p1.getOrdonne())
						return 1;
					else if (p.getOrdonne() < p1.getOrdonne())
						return -1;
					if(p.getAbscisse() > p1.getAbscisse())
						return -1;
					return 1;
				}
			});
			Arrays.sort(ECDy, new Comparator<Point>() {
				@Override
				public int compare(Point p, Point p1) {
					if (p.getOrdonne() > p1.getOrdonne())
						return 1;
					else if (p.getOrdonne() < p1.getOrdonne())
						return -1;
					if(p.getAbscisse() > p1.getAbscisse())
						return 1;
					return -1;
				}
			});

			Point A = ECG[ECG.length - 1], B = ECD[0];
			int idxhg = 0, idxhd = 0;
			for (int i = 0; i < ECGy.length; i++) {
				if (ECGy[i].equals(A)) {
					idxhg = i;
					break;
				}
			}
			for (int i = 0; i < ECDy.length; i++) {
				if (ECDy[i].equals(B)) {
					idxhd = i;
					break;
				}
			}
			int gh = idxhg, dh = idxhd;
			// Point de la tangente Haute
			boolean ok = false;
			while (!ok) {
				ok = false;
				while (dh < ECDy.length-1) {
					if (cote(ECGy[gh], ECDy[dh], ECDy[dh + 1]) <= 0) {
						dh++;
					} else {
						break;
					}
				}
				while (gh < ECGy.length-1) {
					if (cote(ECDy[dh], ECGy[gh], ECGy[gh + 1]) >= 0) {
						gh++;
						ok = true;
					} else {
						break;
					}
				}
			}

			ok = false;
			int gb = idxhg, db = idxhd;
			// Point de la tangente basse
			while (!ok) {
				ok = false;
				while (db > 0) {
					if (cote(ECGy[gb], ECDy[db], ECDy[db - 1]) < 0) {
						db--;
					} else {
						break;
					}
				}
				while (gb > 0) {
					if (cote(ECDy[db], ECGy[gb], ECGy[gb - 1]) <= 0) {
						gb--;
						ok = true;
					} else {
						break;
					}
				}
			}
			/*
			 * Récupération des éléments de l'enveloppe de gauche qui seront dans
			 * l'enveloppe final suivant: Ph point le plus haut, Pb: Point le plus bas, P
			 * point quelconque l'angle PhPb, PhP doit être dans le sens des aiguille d'une
			 * montre
			 */
			for (Point p : ECGy) {
				if (cote(ECGy[gh], ECGy[gb], p) <= 0) {
					enveloppe.add(p);
				}
			}
			/*
			 * Récupération des éléments de l'enveloppe de droite qui seront dans
			 * l'enveloppe final suivant: Ph point le plus haut, Pb: Point le plus bas, P
			 * point quelconque l'angle (PhPb, PhP) doit être dans le sens direct
			 */
			for (int i = ECDy.length - 1; i >= 0; i--) {
				if (cote(ECDy[db], ECDy[dh], ECDy[i]) >=0) {
					enveloppe.add(ECDy[i]);
				}
			}

			return enveloppe.toArray(new Point[enveloppe.size()]);
		}
	}

	private Point[] methodeTangenteDPR(Point[] points) {

		int taille = points.length;
		if (taille < 6) {
			return quickHull(points);
		} else {
			Arrays.sort(points);
			Point[] pointsG = Arrays.copyOfRange(points, 0, Math.floorDiv(taille, 2));
			Point[] pointsD = Arrays.copyOfRange(points, Math.floorDiv(taille, 2), taille);
			Point[] ECG = methodeTangenteDPR(pointsG);
			Point[] ECD = methodeTangenteDPR(pointsD);
			return fusion(ECG, ECD);
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

	public void donnerEnveloppeConnexe(Point[] points) {
//		quickHull(points);
		Arrays.sort(points);
		System.out.println(Arrays.toString(methodeTangenteDPR(points)));

	}
}
