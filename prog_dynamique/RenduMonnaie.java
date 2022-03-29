package prog_dynamique;

import java.util.Arrays;
import java.util.Scanner;

public class RenduMonnaie {
	// Incomplet

	private int[][] nbrePiece(int M, int[] pieces, String[][] B) {
		int[][] c = new int[pieces.length][M + 1];
		for (int i = 0; i < pieces.length; i++) {
			c[i][0] = 0;
		}
		for (int i = 0; i <= M; i++) {
			c[0][i] = i / pieces[0];
		}
		for (int i = 1; i < pieces.length; i++) {
			for (int j = 1; j <= M; j++) {
				c[i][j] = c[i - 1][j];
				B[i][j] = "|";
				if (pieces[i] <= j) {
					if (c[i][j - pieces[i]] + 1 < c[i - 1][j]) {
						c[i][j] = c[i][j - pieces[i]] + 1;
						B[i][j] = "-";
					}
				}
			}
		}
		return c;
	}

	public void rendreMonnaie(int M) {
		int[] pieces = { 1, 4, 6 };
		String[][] B = new String[pieces.length][M + 1];
		int[][] c = nbrePiece(M, pieces, B);
		System.out.println(Arrays.deepToString(c).replace("]", "]\n"));
	}

	public static void main(String[] args) {
		Scanner a = new Scanner(System.in);
		System.out.print("Entrez le montant: ");
		int M = a.nextInt();
		RenduMonnaie R = new RenduMonnaie();
		R.rendreMonnaie(M);
		a.close();
	}

}
