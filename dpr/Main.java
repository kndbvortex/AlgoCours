package dpr;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		
		Point ensemblepoint1[] = { 
				new Point(0, 0), new Point(0, 4), new Point(-4, 0), 
				new Point(5, 0), new Point(0, -6),new Point(1, 0)};
		
		Point ensemblepoints2[] = { 
				new Point(0, 3), new Point(1, 1), new Point(2, 2), new Point(4, 4), 
				new Point(0, 0),new Point(1, 2), new Point(3, 1), new Point(3, 3), 
				new Point(2, 6) };
		
		Point ensemblepoints3[] = { new Point(0, 0), new Point(3, 0), new Point(2, 1), new Point(2,2) };
		 
		Point[] ensemblePoints5 = { new Point(0.0, -6.0), new Point(0, 3)};

		
		
		Point enveloppeConnexePoint1[] = { 
				new Point(0.0, -6.0), new Point(-4.0, 0.0), 
				new Point(0.0, 4.0), new Point(5.0, 0.0)};
		
		Point[] enveloppeConnexePoint3 = {new Point(0, 3), new Point(1,1), new Point(2,2)};
		
		Point[] enveloppeConnexePoint2 = { 
				new Point(0.0, -6.0), new Point(0, 3), new Point(2, 6), 
				new Point(4, 4), new Point(3, 1)};
		
		
		Point[] enveloppeConnexePoint5 = null;
		
		Point[] enveloppeConnexePoint = {new Point(0, 0), new Point(1,2), new Point(2,2)};
		
		EnveloppeConvexe ec = new EnveloppeConvexe();
		
		System.out.println("Cas 1 points : "+ Arrays.toString(enveloppeConnexePoint));
		System.out.println("\t Enveloppe convexe: " +Arrays.toString(ec.donnerEnveloppeConnexe(enveloppeConnexePoint))+ "\n");
		
		System.out.println("Cas 2 points : "+ Arrays.toString(ensemblepoints2));
		System.out.println("\t Enveloppe convexe: " + Arrays.toString(ec.donnerEnveloppeConnexe(ensemblepoints2))+ "\n");
		
		System.out.println("Cas 3 points : "+ Arrays.toString(ensemblepoints3));
		System.out.println("\t Enveloppe convexe: " +Arrays.toString(ec.donnerEnveloppeConnexe(ensemblepoints3))+ "\n");
		
		System.out.println("Cas 4 points : "+ Arrays.toString(ensemblepoints3));
		System.out.println("\t Enveloppe convexe: " +Arrays.toString(ec.donnerEnveloppeConnexe(ensemblepoints3))+ "\n");
		
		System.out.println("Cas 5 points : "+ Arrays.toString(ensemblePoints5));
		System.out.println("\t Enveloppe convexe: " +Arrays.toString(ec.donnerEnveloppeConnexe(ensemblePoints5))+ "\n");
	}

}
