package prog_dynamique;

import java.util.Arrays;

public class Parenthesage {

	private int[][] nbreMulPD(int[] d, int[][] indices) {
		int[][] m = new int[d.length - 1][d.length - 1];
		int n = d.length - 1;
		for (int i = 0; i < n; i++) {
			m[i][i] = 0;
		}

		for (int i = 0; i < n - 1; i++) {
			m[i][i + 1] = d[i] * d[i + 1] * d[i + 2];
			indices[i][i + 1] = i;
		}

		for (int s = 2; s < n; s++) {
			for (int i = 0; i <= n - s - 1; i++) {
				int j = i + s;
				int k = i;
				m[i][j] = m[i][k] + m[k + 1][j] + d[i] * d[k + 1] * d[j + 1];
				indices[i][j] = k;
				for (k = i + 1; k < j; k++) {
					int min = m[i][k] + m[k + 1][j] + d[i] * d[k + 1] * d[j + 1];
					if (min < m[i][j]) {
						m[i][j] = min;
						indices[i][j] = k;
					}
				}
			}
		}
		return m;
	}

	private void afficheParenthesage(int i, int j, int[][] indices) {
		if (i == j) {
			System.out.print("M" + (i + 1));
		} else {
			System.out.print("(");
			afficheParenthesage(i, indices[i][j], indices);
			afficheParenthesage(indices[i][j] + 1, j, indices);
			System.out.print(")");
		}
	}

	public void parenthesagePD(int d[]) {
		int[][] indices = new int[d.length - 1][d.length - 1];
		int[][] m = nbreMulPD(d, indices);
		System.out.println("Les dimesions: " + Arrays.toString(d));
		System.out.print("Le parenthésage optimal est: ");
		afficheParenthesage(0, d.length - 2, indices);
		System.out.println("\n\tAvec " + m[0][d.length - 2] + " multiplications de scalaires à faire.");
	}

	public static void main(String[] args) {
		Parenthesage P = new Parenthesage();
//		int[] d = { 2, 3, 6, 2, 7 };
		int[] d = { 30, 35, 15, 5, 10, 20, 25 };
		P.parenthesagePD(d);
	}

}
