package dpr;

public class Vecteur extends Point{
	private  Point origine;
	private Point extremite;
	
	public Vecteur(Point o, Point e) {
		super(e.getAbscisse()-o.getAbscisse(), e.getOrdonne()-o.getOrdonne());
		this.origine = o;
		this.extremite =e;
	}
	
	public double det(Vecteur v2) {
		return x*v2.y - y*v2.x;
	}
}
