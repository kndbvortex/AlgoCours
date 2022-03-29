package prog_dynamique;

public class SacDos {
	// Incomplet

	/*
	 * v : ensemble des valeurs
	 * w : ensemble des poids des objets
	 * W : poids du sac
	 */
	private String valueurMaxSac(int W, int[] v, int[] w){
		int[][] V = new int[v.length+1][W+1];
		String[][] B = new String[v.length+1][W+1];
		for(int i=0; i<=v.length; i++) {
			V[i][0] = 0;
		}
		for(int i=0; i<=W; i++) {
			V[0][i] = 0;
		}
		for(int i=1; i<= v.length; i++) {
			for(int j=1; j <= W; j++) {
				V[i][j] = V[i-1][j];
				B[i][j] = "|";
				if(j >= w[i-1]) {
					if(V[i-1][j-w[i-1]]+v[i-1] > V[i-1][j]) {
						V[i][j] = V[i-1][j-w[i-1]]+v[i-1];
						B[i][j] = "\\";
					}
				}
			}
		}
		
		int i = v.length, j = W;
		String sac = "pour une valeur de "+V[i][j];
		while(i != 0 && j!= 0) {
			if(B[i][j] == "\\") {
				sac = "objet" +i+"(w="+w[i-1]+", v="+v[i-1]+") " + sac; 
				i--;
				j -= w[i];
			}
			else {
				i--;
			}
		}
		return sac;
	}
	
	public void contenuSac() {
		int[] v = {1, 6, 18, 22, 28}, w= {1, 2, 5, 6, 7};
		int W = 11;
		System.out.println(valueurMaxSac(W, v, w));
	}
	
	public static void main(String[] args) {
		SacDos sac = new SacDos();
		sac.contenuSac();
	}

}
