package prog_dynamique;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class LSC {
	private class Node {
		private int i, j;
		private Node left = null, rigth = null, parent = null;

		public Node(int i, int j) {
			this.i = i;
			this.j = j;
		}
		
		public String toString() {
			return "("+this.i+", "+this.j+")";
		}
	}

	/*
	 * calcul de la longueur de la plus longue sous suite commune à deux chaines X =
	 * x1x2…xn Y = y1y2…ym principe: C(i,j) = 0 si i=0 ou j=0 car lcs d'un mot vide
	 * et l'autre vaut 0 C(i,j)=1+C(i-1,j-1) car Xi-1 = Yj-1
	 * C(i,j)=max{C(i-1,j);C(i,j-1)} si Xi-1 != Xj-1
	 */

	private int[][] lcs(String chaine1, String chaine2, String[][] B) {
		int i = 0, j = 0, n = chaine1.length(), m = chaine2.length();
		int[][] C = new int[n + 1][m + 1];

		for (i = 0; i < n; i++) {
			C[i][0] = 0;
		}
		for(j=0; j<m; j++) {
			C[0][j]=0;
		}

		for (i = 1; i < n + 1; i++) {
			// Formule de réccurrence
			for (j = 1; j < m + 1; j++) {

				if (chaine1.charAt(i - 1) == chaine2.charAt(j - 1)) {
					C[i][j] = C[i - 1][j - 1] + 1;
					B[i][j] = "\\";
				}

				else {
					if (C[i - 1][j] > C[i][j - 1]) {
						C[i][j] = C[i - 1][j];
						B[i][j] = "|";
					} else if (C[i - 1][j] < C[i][j - 1]) {
						C[i][j] = C[i][j - 1];
						B[i][j] = "-";
					} else {
						C[i][j] = C[i][j - 1];
						B[i][j] = "-|";
					}
				}

			}
		}

		return C;
	}

	private void constructGraph(Node r, String[][] B) {
		if(r.i != 0 && r.j!=0) {
			if(B[r.i][r.j] == "|") {
				Node l = new Node(r.i-1, r.j);
				l.parent = r;
				r.left= l;
				constructGraph(l, B);
			}
			else if(B[r.i][r.j] == "-") {
				Node l = new Node(r.i, r.j-1);
				l.parent = r;
				r.left = l;
				constructGraph(l, B);
			}
			else if(B[r.i][r.j] == "\\") {
				Node l = new Node(r.i-1, r.j-1);
				r.left = l;
				l.parent = r;
				constructGraph(l, B);
			}
			else {
				Node l = new Node(r.i, r.j-1);
				l.parent = r;
				Node rigth = new Node(r.i-1, r.j);
				rigth.parent = r;
				r.left = l;
				r.rigth = rigth;
				constructGraph(l, B);
				constructGraph(rigth, B);
			}
		}
	}
	
	private LinkedList<String> solution(Node r,String X, String Y, String[][]B) {
		Stack<Node> P = new Stack<LSC.Node>();
		LinkedList<String> mot = new LinkedList<String>(), listeLCS = new LinkedList<String>();
		Node a = r;
		do {
			if(a != null) {
				P.add(a);
				if(B[a.i][a.j] == "\\") {
					mot.addFirst(X.substring(a.i-1, a.i));
				}
				else {
					mot.addFirst("");
				}
				a = a.left;
			}
			else {
				listeLCS.add(String.join("",mot.toArray(new String[mot.size()])));
				a = P.lastElement();
				while((a != r) && (a.parent != r) && (a.parent.rigth == null || a.parent.rigth == a)) {
					a = a.parent;
					mot.removeFirst();
					P.pop();
				}
				
				if((a == r) || (a.parent == r && (a.parent.rigth == a || a.parent.rigth == null))) {
					P.clear();
				}
				else {
					P.pop();
					mot.remove();
					a = a.parent.rigth;
				}
				
			}
		}while(P.size() != 0);
		return new LinkedList<String>(new HashSet<String>(listeLCS));
	}
	
	private LinkedList<String> reconstitution(String X, String Y,String[][] B, int[][] C) {
		LinkedList<String> LCS = new LinkedList<String>();
		int n = B.length-1, m = B[0].length-1;
		Node racine = new Node(n, m);
		constructGraph(racine, B);
		LCS = solution(racine, X, Y, B);
		return LCS;
	}

	/*
	 * Cas d'un élément:
		 * Après avoir rempli une table dont la dernière valeur contient la taille de la
		 * plus longue sous suite commune à nos deux chaine nous venons chercher la plsc
		 * en question en parcourant cette table à partir de la dernière case et en
		 * évoluant ainsi: si case où l'on se trouve a la même valeur que la case du
		 * dessus(ou de gauche) on se déplace alors au dessus(ou à gauche) au cas
		 * contraire on recupère le caractère dans l'une des chaines et on se déplace
		 * suivant la diagonale
		 * 
		 *
	 *Cas de plusieurs:
	 *	Construire un graphe et retourner les étiquettes de chaque branche.
	 */
	public void LpscEtPlsc(String chaine1, String chaine2) {
		// reconstitution d'un élément de LCS
		int n = chaine1.length(), m = chaine2.length();
		int[][] C = new int[n + 1][m + 1];
		String B[][] = new String[n + 1][m + 1];
		String s = "La plsc de "+chaine1 + " et " + chaine2;

		C = lcs(chaine1, chaine2, B);
//		System.out.println(Arrays.deepToString(B).replace("]", "]\n"));
		LinkedList<String> LCS = reconstitution(chaine1, chaine2, B, C);
		
//		System.out.println(Arrays.deepToString(C).replace("]", "]\n"));
		
		if(C[n][m] == 0) {
			System.out.println(s + " est la chaine vide");
		}
		else {
			System.out.println(s+" de taille " + C[n][m] + " est un élément de l'ensemble: \n\t"+ LCS.toString());		
		}
	}

	public static void main(String[] argv) {
		LSC l = new LSC();
		Scanner c = new Scanner(System.in);
		String chaine1 = new String("ABAB");
		String chaine2 = new String("BABA");
		System.out.print("Entrez le premier mot: ");
		chaine1 = c.nextLine();
		System.out.print("Entrez le deuxième mot: ");
		chaine2 = c.nextLine();
		l.LpscEtPlsc(chaine1, chaine2);
		c.close();
	}
}
