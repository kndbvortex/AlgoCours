package dpr;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class GenerateData {

	// Fonction qui cree le fichier qui contient les resultats du programme
	public static void fichierResultat(Point [] b) {
		
		try {
			File fichierResultat= new File("resultat.txt");
			fichierResultat.createNewFile();
			FileWriter editeurResultat=new FileWriter(fichierResultat);
			for(int i=0;i<b.length;i++) {
				editeurResultat.write(b[i].getAbscisse()+"  "+b[i].getOrdonne()+"\n");
			}
			editeurResultat.close();
		}catch(IOException e){
			System.out.println("Une erreur est survenue");
		      e.printStackTrace();
		}
	}
	
	// Fonction qui genere aleatoirement des donnees dans le fichier
	public static void generateData(File file, int taille) {
		double x,y;
		try {
			file.createNewFile();
			FileWriter editeur=new FileWriter(file);
			for(int i=0;i<taille;i++) {
				x=ThreadLocalRandom.current().nextDouble(1000);
				y=ThreadLocalRandom.current().nextDouble(1000);
				editeur.write(x+"  "+y+"\n");
			}
			editeur.close();
		}catch(IOException e){
			 System.out.println("Une erreur est survenue");
		      e.printStackTrace();
		}
	
	}
	//Fonction qui recupere les donnnes dans le fichier
	public static Point[] recupererData(File fichier) {
	
		ArrayList<Point> tabpt =new ArrayList<Point>();
		Point[] tabDePoint = null;
		
		try {
			
			Scanner liseur=new Scanner(fichier);
			//Parcourir le fichier jusqu'a la fin et stocker les coordonnees dans p
			String line;
			while(liseur.hasNextLine()) {
				line=liseur.nextLine();
				String[] coordonnees=line.split("  "); //divise la ligne de fichier en deux tableaux
				Point p=new Point(Double.parseDouble(coordonnees[0]),Double.parseDouble(coordonnees[1]));
				tabpt.add(p); // Ajoute le point dans la liste
			}
			liseur.close();
			tabDePoint=tabpt.toArray(new Point[0]);
			
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		return tabDePoint;
	}	

	// La fonction principale
	public static void main(String[] args) {
		//Creation du fichier
		Point MesPoints[]=null; 
		Point pointResultats[]=null;
		EnveloppeConvexe EC= new EnveloppeConvexe();
		File fichier=new File("data.txt");
		
		// Fonction qui recupere le temps d'execution
		generateData(fichier, 1000);
		MesPoints=recupererData(fichier);

		long timer1=System.currentTimeMillis();
		pointResultats=EC.donnerEnveloppeConnexe(MesPoints); // apple de la methode Quickull
		long timer2=System.currentTimeMillis();
		
		long timernaifDebut=System.currentTimeMillis();
		pointResultats=EC.donnerEnveloppeConnexeNaif(MesPoints); // apple de la methode Quickull
		long timerNaifFin=System.currentTimeMillis();
		//Affichage des donnees contenues dans notre fichier
		fichierResultat(pointResultats);
		
		//Fonction qui recupre le temps pres excution des fonctions ci dessus
		
		
		
		float temps= timer2 - timer1;
//		System.out.println("Nos donnees dans le fichier\n ");
//		for(int i=0;i<MesPoints.length;i++) {
//			System.out.println("Point "+(i+1)+" : (" +MesPoints[i].getAbscisse()+"  ,  "+MesPoints[i].getOrdonne()+")");
//		}
//		
//		// Affichage des points de notre envellope convexe
//		
//		System.out.println("\n\nLes points de l'envellope convexe :\n ");
//		
//		for(int i=0;i<pointResultats.length;i++) {
//			System.out.println("Point de coordonnes : (" +pointResultats[i].getAbscisse()+"  ,  "+pointResultats[i].getOrdonne()+")");
//		}
		System.out.print("\nLe temps d'execution QuickHull est :"+temps+" ms\n");
		System.out.print("Le temps d'execution de l'algorithme Naif est :" + (timerNaifFin - timernaifDebut) + " ms");

	}
}
