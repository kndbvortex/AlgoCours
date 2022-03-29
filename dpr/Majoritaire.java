package dpr;

import java.util.Arrays;

public class Majoritaire {
	public int[] major(int[] tab, int d, int f) {
		int n = f - d + 1;
		if (n < 4) {
			if (n == 1) {
				int[] p = { tab[d], 1 };
				return p;
			}
			if (n == 2) {
				if (tab[f] == tab[d]) {
					int[] p = { tab[d], 2 };
					return p;
				}
				return null;
			}
			if (n == 3) {
				Arrays.sort(tab);
				if (tab[d + 1] == tab[d] || tab[d + 1] == tab[f]) {
					int a = 0;
					for (int e : tab) {
						if (e == tab[d + 1])
							a++;
					}
					int[] p = { tab[d + 1], a };
					return p;
				}
			}
		} else {
			int mil = 0;
			if (n % 2 == 1)
				mil = (f + d + 1) / 2;
			else
				mil = (f + d) / 2;

			int[] gauche = major(tab, 0, mil);
			int[] droite = major(tab, mil + 1, n - 1);
			if (gauche != null) {
				if (droite == null) {
					if (gauche[1] > n / 2) {
						return gauche;
					}
					return null;
				} else {
					for (int e : gauche) {
						if (e == droite[0])
							droite[1]++;
					}
					for (int e : droite) {
						if (e == gauche[0])
							gauche[1]++;
					}
					if (gauche[1] > n / 2)
						return gauche;
					else if (droite[1] > n / 2)
						return droite;
					else
						return null;
				}
			} else {
				if (droite != null) {
					if (droite[1] > n / 2) {
						return droite;
					}
				}
				return null;
			}
		}
		return null;

	}

//	private int get(int position, int[] BD) {
//		int[] tb = Arrays.copyOf(BD, BD.length);
//		Arrays.sort(tb);
//		return tb[position - 1];
//	}
//	
//	public int middleVecteur(int[] BD1, int[] BD2) {
//		int p = 2, n = BD1.length, k = 0;
//		int pos1 = n/2, pos2 = n/2, m1= get(pos1, BD1), m2 = get(pos2, BD2);
//		while(k < Math.log(n)/Math.log(2)) {
//			k++;
//			p *= 2;
//			if(m1<m2) {
//				pos1 += n/p;
//				pos2 -= n/p;
//			}
//			else {
//				pos1 -= n/p;
//				pos2 += n/p;
//			}
//			m1 = get(pos1, BD1);
//			m2 = get(pos2, BD2);
//		}
//		if(m1 < m2)
//			return m2;
//		else
//			return m1;
//	}

	public void suiteConsecutiveMaximale(int[] T) {
		int[] S = new int[T.length];
		S[0] = T[0];
		char[] P = new char[T.length];
		P[0] = '-';
		for(int i=1; i < T.length; i++) {
			if(S[i-1]+T[i]> T[i]) {
				P[i] = 'T';
				S[i] = S[i-1]+T[i];
			}
			else {
				P[i] = '-';
				S[i] = T[i];
			}
		}
		int m = S[0], index_max = 0, index_debut=0;
		String sous_seq = "";
		
		for(int i=1; i<T.length; i++) {
			if(S[i] > m) {
				index_max = i;
				m = S[i];
			}
		}
		
		index_debut = index_max;
		while(P[index_debut] != '-')
			index_debut--;
		
		for(int i=index_debut; i<=index_max; i++) {
			sous_seq += "" + T[i] + ", ";
		}
		System.out.println("La somme maximale est : " + m + " des éléments: [" + sous_seq + "]");
	}

	public static void main(String[] args) {
		Majoritaire M = new Majoritaire();
//		int[] bd1 = {1,3,7,9,90,13,34,78};
//		int[] bd2 = {2,4,6,8,77,17,48,87};
		int[] a = { 1, 1, 1, 0, 1, 0, 3, 4, 1, 1 };
		int T[] = { 5, 15, -30, 10, -5, 40, 10};
		System.out.println(Arrays.toString(M.major(a, 0, a.length - 1)));
//		System.out.println(M.middleVecteur(bd1, bd2));
		M.suiteConsecutiveMaximale(T);
	}

}
