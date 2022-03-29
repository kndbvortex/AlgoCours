package prog_dynamique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VoyageurCommerce {

	private ArrayList<Integer> baseSolution(double graph[][], int s) {
		ArrayList<Integer> vertex = new ArrayList<Integer>(), considred_vertex = new ArrayList<Integer>();
		for (int i = 0; i < graph.length; i++) {
			if (i != s)
				considred_vertex.add(i);
		}
		int curent_vertex = s, next_vertex = 0;
		for (int i = 0; i < graph.length - 1; i++) {
			double p = Double.POSITIVE_INFINITY;
			for (int v : considred_vertex) {
				if (graph[curent_vertex][v] < p) {
					p = graph[curent_vertex][v];
					next_vertex = v;
				}
			}
			considred_vertex.remove(considred_vertex.indexOf(next_vertex));
			vertex.add(next_vertex);
			curent_vertex = next_vertex;
		}
		return vertex;
	}

	private double coast_cycle(double graph[][], int s, ArrayList<Integer> vertex) {
		double coast = graph[s][vertex.get(0)] + graph[vertex.get(vertex.size() - 1)][s];
		for (int i = 0; i < vertex.size() - 1; i++) {
			coast += graph[vertex.get(i)][vertex.get(i + 1)];
		}
		return coast;
	}

	private ArrayList<ArrayList<Integer>> neighborhood(ArrayList<Integer> vertex) {
		ArrayList<ArrayList<Integer>> neighbors = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < vertex.size() - 1; i++) {
			ArrayList<Integer> n = new ArrayList<Integer>(vertex);
			int tmp = n.get(i);
			n.set(i, n.get(i + 1));
			n.set(i + 1, tmp);
			neighbors.add(n);
		}
		return neighbors;
	}

	public double travllingSalesmanProblem(double graph[][], int s) {
		ArrayList<Integer> vertex = baseSolution(graph, s);
		ArrayList<Integer> current_s = new ArrayList<Integer>(vertex);
		ArrayList<Integer> step_solution = new ArrayList<Integer>();
		
		LinkedList<Double> TL = new LinkedList<Double>();
		double min_path = coast_cycle(graph, s, vertex);
		for (int i = 0; i < Math.log(graph.length); i++) {
			ArrayList<ArrayList<Integer>> neighbors = neighborhood(current_s);
			double sCoast = Double.POSITIVE_INFINITY;
			for (ArrayList<Integer> neighbor : neighbors) {
				double coast = coast_cycle(graph, s, neighbor);
				if (coast < sCoast && !(TL.contains(coast))) {
					sCoast = coast;
					step_solution = neighbor;
				}
			}
			if (TL.size() >= graph.length/2)
				TL.removeFirst();
			TL.add(sCoast);
			current_s = step_solution;
			if (sCoast < min_path) {
				vertex = current_s;
				min_path = sCoast;
			}
		}
		vertex.add(0, s);
		vertex.add(vertex.size(), s);
		//System.out.println("Le cycle est: " + vertex.toString() + " pour un poids de: " + min_path);
		return min_path;
	}

	public static void main(String args[]) {
		VoyageurCommerce v = new VoyageurCommerce();
		ArrayList<Integer> erreur = new ArrayList<Integer>(), time_dif = new ArrayList<Integer>();
		ArrayList<Integer> n_vertex = new ArrayList<Integer>(), naif_ = new ArrayList<Integer>(), tab = new ArrayList<Integer>();
		CFG c = new CFG();
		Random rand = new Random();
		for (int k = 0; k < 100; k++) {
			int n = ThreadLocalRandom.current().nextInt(5, 10);
			n_vertex.add(n);
			double graph[][] = new double[n][n];
//			double graph[][] = { { 0, 10, 15, 20 }, { 10, 0, 35, 25 }, { 15, 35, 0, 30 }, { 20, 25, 30, 0 } };
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					graph[i][j] = rand.nextInt(1000);
				}
			}
			//  System.out.println(Arrays.deepToString(graph).replace("]", "]\n"));
			int s = 0;
			
			long startTime = System.nanoTime();
			double t = v.travllingSalesmanProblem(graph, s);
			long endTime = System.nanoTime();

			long startTime2 = System.nanoTime();
			int naif = c.travllingSalesmanProblem(graph, s);
			long endTime2 = System.nanoTime();
			erreur.add((int)t-naif);
			tab.add((int)t);
			naif_.add(naif);
			time_dif.add((int)((endTime - startTime) - (endTime2 - startTime2)));
			System.out.println(((endTime - startTime) - (endTime2 - startTime2)) + " ; " + "---" + (t - naif) + " pour "+ n+" sommet");
		}
		
		System.out.println("Tabou times: " + tab);
		System.out.println("Naif times: " + naif_);
		System.out.println("Erreur: "+ erreur);
		System.out.println("Différences de temps: " + time_dif);
	}
}
