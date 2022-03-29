package prog_dynamique;

import java.util.Arrays;

public class SequenceADN {
	//Incomplet 
	
	public void globalAlignment(String seq1, String seq2, int match, int mismatch, int gap) {
		
		int[][] S = new int[seq1.length() + 1][seq2.length() + 1];
		String[][] B = new String[seq1.length() + 1][seq2.length() + 1];
		int s_mismatch_match = 0, s_indel = 0;
		String indel_dir;
		
		for (int i = 0; i <= seq1.length(); i++) {
			S[i][0] = i * gap;
			B[i][0] = "|";
		}
		
		for (int i = 0; i <= seq2.length(); i++) {
			S[0][i] = i * gap;
			B[0][i] = "-";
		}
		int delta = 0;
		for (int i = 1; i <= seq1.length(); i++) {
			for (int j = 1; j <= seq2.length(); j++) {
				// Cas d'un match
				if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
					delta = match;
					B[i][j] = "\\";
				} 
				// Mismatch
				else {
					delta = mismatch;
				}
				s_mismatch_match = S[i - 1][j - 1] + delta;
				
				// Indel
				if (S[i][j-1] > S[i-1][j]) {
					s_indel = S[i][j - 1] + gap;
					indel_dir = "-";
				}
				else {
					s_indel = S[i - 1][j] + gap;
					indel_dir = "|";
				}
				if(s_mismatch_match > s_indel) {
					S[i][j] = s_mismatch_match;
					B[i][j] = "\\";
				}
				else {
					S[i][j] = s_indel;
					B[i][j] = indel_dir;
				}
			
			}
		}
		
		// Reconstitution
		int i = seq1.length(), j = seq2.length();
		String subseq1 = "", subseq2 = "";
		seq1 = " " + seq1;
		seq2 = " " + seq2;
		while(i != 0 || j > 0) {
			int a=i, b=j;
			if(B[a][b] == "\\") {
				subseq1 = seq1.charAt(i) + subseq1;
				subseq2 = seq2.charAt(j) + subseq2;
				i--;
				j--;
			}
			else if(B[a][b] == "|") {
				subseq1 = seq1.charAt(i) + subseq1;
				subseq2 = "_" + subseq2;
				i--;
			}
			else if (B[a][b] == "-"){
				subseq1 = "_" + subseq1;
				subseq2 = seq2.charAt(j) + subseq2;
				j--;
			}
		}
		System.out.println(subseq1+"\n"+subseq2);
	}

	public void localAlignment(String seq1, String seq2, int match, int mismatch, int gap) {
		int[][] S = new int[seq1.length() + 1][seq2.length() + 1];
		String[][] B = new String[seq1.length() + 1][seq2.length() + 1];
		int s_mismatch_match = 0, s_indel = 0;
		String indel_dir;
		int x = 0, y = 0, max = 0;
		
		for (int i = 0; i <= seq1.length(); i++) {
			S[i][0] = 0;
			B[i][0] = "|";
		}
		
		for (int i = 0; i <= seq2.length(); i++) {
			S[0][i] = 0;
			B[0][i] = "-";
		}
		int delta=0;
		for (int i = 1; i <= seq1.length(); i++) {
			for (int j = 1; j <= seq2.length(); j++) {
				// Cas d'un match
				if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
					delta = match;
					B[i][j] = "\\";
				} 
				// Mismatch
				else {
					delta = mismatch;
				}
				s_mismatch_match = S[i - 1][j - 1] + delta;
				
				// Indel
				if (S[i][j-1] > S[i-1][j]) {
					s_indel = S[i][j - 1] + gap;
					indel_dir = "-";
				}
				else {
					s_indel = S[i - 1][j] + gap;
					indel_dir = "|";
				}
				if(s_mismatch_match > s_indel) {
					S[i][j] = s_mismatch_match;
					B[i][j] = "\\";
				}
				else {
					S[i][j] = s_indel;
					B[i][j] = indel_dir;
				}
				if (S[i][j] < 0) {
					S[i][j] = 0;
				}
				if(S[i][j] > max) {
					max = S[i][j];
					x = i;
					y = j;
				}
			}
		}
		
		// Reconstitution
		int i = x, j = y;
		String subseq1 = "", subseq2 = "";
		seq1 = " " + seq1;
		seq2 = " " + seq2;
		while(S[i][j] != 0) {
			int a=i, b=j;
			if(B[a][b] == "\\") {
				subseq1 = seq1.charAt(i) + subseq1;
				subseq2 = seq2.charAt(j) + subseq2;
				i--;
				j--;
			}
			else if(B[a][b] == "|") {
				subseq1 = seq1.charAt(i) + subseq1;
				subseq2 = "_" + subseq2;
				i--;
			}
			else if (B[a][b] == "-"){
				subseq1 = "_" + subseq1;
				subseq2 = seq2.charAt(j) + subseq2;
				j--;
			}
		}
		System.out.println(Arrays.deepToString(S).replace("]", "]\n"));
		System.out.println(subseq1+"\n"+subseq2);
	}

	public void multipleAlignment(String seq1, String seq2) {

	}
	public static void main(String[] args) {
		SequenceADN S = new SequenceADN();
		S.globalAlignment("CTTG", "ACTG", 4, -4, -4);
		S.localAlignment("GTCAGGT", "CAGTTAG", 2, -1, -1);
	}
}
