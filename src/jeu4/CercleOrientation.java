package jeu4;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import interfaces.Dessinable;
import interfaces.Selectionnable;
import utils.Vecteur2D;

/**
 * classe qui représente l'orientation du rayon
 * @author Kepler Paul-Émile
 *
 */
public class CercleOrientation implements Dessinable,Selectionnable {


private Ellipse2D  cercleOrien, cercleEfface;
/** le diametre du cercle**/
private double diametreCercle;
private double diametreCercleE;
/**la largeur de l'ecran sur lequel le demie cercle est réalisé**/
private double largeurEcran;
/** la hauteur de l'ecran sur lequel le demie cercle est réalisé **/
private double hauteurEcran;
/** les positions en x et y du cercle**/
private double posX, posY;
/** **/
private double posCercleEX, posCercleEY;
private final double PROP_DIAMETRE_PETIT_CERCLE=0.75;

private Vecteur2D positionClique;

/** l'aire du cercle transformé**/
private Area aireCercle;

/**
 * Constructeur de la classe
 * @param largeurEcran la largeur de l'ecran
 * @param hauteurEcran la hauteur de l'ecran
 */
public CercleOrientation(double largeurEcran, double hauteurEcran) {
    this.largeurEcran=largeurEcran;
    this.hauteurEcran=hauteurEcran;
    creerLesProportions();
    
}
/**
 * Methode qui créer les proportions du cercle
 */
private void creerLesProportions() {
    // TODO Auto-generated method stub
      this.diametreCercle=(hauteurEcran/5);
  
    this.posX=(largeurEcran/2)-(diametreCercle/2);
    this.posY=(-diametreCercle/2);
    cercleOrien=new Ellipse2D.Double(posX, posY,  diametreCercle, diametreCercle);
    
    this.diametreCercleE=PROP_DIAMETRE_PETIT_CERCLE*diametreCercle;
    
    this.posCercleEX=(largeurEcran/2)-(diametreCercleE/2);
    this.posCercleEY=-(diametreCercleE/2);
    cercleEfface=new Ellipse2D.Double(posCercleEX, posCercleEY, diametreCercleE, diametreCercleE);
    
    positionClique=new Vecteur2D(0,0);
    

   
    
}

@Override
public void dessiner(Graphics2D g2d) {
    // TODO Auto-generated method stub
    Graphics2D g2dCopie=(Graphics2D) g2d.create();
    aireCercle=new Area(cercleOrien);
    aireCercle.subtract(new Area(cercleEfface));
 
    g2dCopie.setColor(Color.BLUE);
   
    g2dCopie.fill(aireCercle);
    g2dCopie.draw(aireCercle);
    
}
@Override
public boolean contient(double xPix, double yPix) {
    // TODO Auto-generated method stub
    
    
    return aireCercle.contains(xPix, yPix);
}
public void changeVecteur(double x,double y) {
    
    setPositionClique(new Vecteur2D(x,y));
}


  public void setPositionClique(Vecteur2D positionClique) {
    this.positionClique = positionClique;
   
}
  public Vecteur2D getPositionClique() {
    return positionClique;
}
}
