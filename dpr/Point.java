package dpr;

public class Point implements Comparable<Point>{
	protected double x;
	protected double y;
	
	public Point() {
		x = 0d;
		y = 0d;
	}
	
	public Point(double abs, double ord) {
		x = abs;
		y = ord;
	}
	
	public double distance(Point p) {
		return Math.sqrt((x*x + p.x*p.x) + (y*y + p.y*p.y));
	}
	
	public double distance(Point p, Point p1) {
		Point vect1 = new Point(x-p.x, y-p.y);
		Point vect2 = new Point(x-p1.x, y-p1.y);
		double det = Math.abs(vect1.x*vect2.y - vect2.x*vect1.y);
		return det / p.distance(p1);
	}
	
	public double getAbscisse() {return x; }
	public double getOrdonne() {return y; }
	
	@Override
	public String toString() {
		return "Point("+ x + ", " + y + ")";
	}
	
	public boolean equals(Point p) {
		return p.x==x && p.y==y;
	}
	
	public int compareTo(Point p) {
        if(p.x > x)
        	return -1;
        else if(p.x < x)
        	return 1;
        if(p.y > y)
        	return -1;
        return 1;
    }
}
