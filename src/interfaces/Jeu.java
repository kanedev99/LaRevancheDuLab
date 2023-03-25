package interfaces;

import javax.swing.JFrame;

import application.ApplicationPrincipale6;

import java.lang.Runnable;

/**
Classe abstraite representant un jeu, Cette classe met en commun des propriétés pour toutes les sous-fenêtres de notre application
@author Brian Normant
**/
public abstract class Jeu extends JFrame implements Runnable {
   /** variable qui permet de savoir si le jeu vient du menu**/
   protected boolean vientDuMenu;
   /** la vie/les indices de chaque jeu**/
   protected int vie;
   /** déterminer si le programme est en cours d'animation**/
   protected boolean enCoursDAnimation = false;
   /** déterminer si le programme est n'est pas en cours d'animation**/
   protected boolean enPause = false;
   /** temps du sleep**/
   public static final int SLEEP = 10;
   /** Delta T utilisé**/
   public static final double deltaT = 0.09;
   /** Difficulte du jeu **/
   public static String difficulte = "";
   /** facteur de difficulte dependant de difficulte **/
   public static double facteur = 1;
   
   public static double FACTEUR_REDIM = ApplicationPrincipale6.REDIM ? 0.5 : 1;
   
   public static void setDifficulty(String newDifficulte) {
	   difficulte = newDifficulte;
   }//Fin methode
   
   public static String getDifficulty() {
	   return difficulte;
   }//Fin Methode
   
   public abstract void start();
   
   /**
    * Implemente un facteur de difficulte en fonction de la difficulte selectionnee dans le menu
    */
   //YohannLaou
   public static void calculFacteur() {
   	switch(difficulte) {
   	  case "Moyen":
   		  facteur = 1.18;
   		  break;
   	  case "Difficile":
   		  facteur = 1.40;
   		  break;  
   	  default:
   	    facteur = 1;
   	}
   }//Fin methode
   
   public static double getFacteur() {
	   return facteur;
   }//Fin Methode
   
}
