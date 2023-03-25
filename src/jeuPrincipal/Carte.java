package jeuPrincipal;
/**
 * classe qui représente une carte (ce qui rend le travaille de selectionnable bien plus
 * facile) 
 * @author Kepler Paul-Émile
 *
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;
import interfaces.Selectionnable;
import chimie.MolType;
public class Carte implements Dessinable, Selectionnable {
   /** les rectangles qui seront placés sur la fenetre**/
 private Rectangle2D.Double rect,rectNo,rectTrait;
 /** les formes des rectangles**/
 private Shape carreFantome,carreNoFantome;
 /** les mesures des rectangles**/
 private int   largeurBloc,positionRecty,positionRectNoX;
 /** l'aire de la carte**/
 private Area rectAire;
 /** le type de la carte**/
 private MolType typeCarte;
 /** si il ya présence de trait**/
 private boolean trait=false;
 
 //constantes
 /**l'ajustement pour la position x du rectNo**/
 private final int AJUSTEMENT_POSX_RECT_NOX=5;
 /**l'ajustement pour la position Y du rect**/
 private final int AJUSTEMENT_POSY_RECT=9;
 
 /**
  * construit une carte avec les mesures appropriés
  * et son molType qui sera éventuellement utilisé pour
  * @param largeurBloc
  * @param positionRecty
  * @param positionRectNoX
  * @param typeCarte
  */
 public Carte(int  largeurBloc,int positionRecty,int positionRectNoX,MolType typeCarte) {
     this.largeurBloc=largeurBloc;
     this.positionRecty=positionRecty;
     this.positionRectNoX=positionRectNoX;
     this.typeCarte=typeCarte;
     creerLaGeometrie();
    
     
     
 }
 
 
 

 
 /**
  * Créer la géometrie
  */
private void creerLaGeometrie() {
    // TODO Auto-generated method stub
    rect = new Rectangle2D.Double(0, positionRecty, largeurBloc, largeurBloc);
    rectNo = new Rectangle2D.Double(largeurBloc - positionRectNoX, positionRecty, positionRectNoX, positionRectNoX);
    
}






private Area getAire() {
    
    return rectAire;
    
}
@Override
public boolean contient(double xPix, double yPix) {
    // TODO Auto-generated method stub
   
    return (carreFantome.contains(xPix, yPix)||carreFantome.contains(xPix, yPix));
}


@Override
public void dessiner(Graphics2D g2d) {
    // TODO Auto-generated method stub
    Graphics2D g2dCopie=(Graphics2D) g2d.create();
   
    
    AffineTransform mat = new AffineTransform();

    mat.translate(0+((largeurBloc)*getType().ordinal()),0);
    g2dCopie.setColor(typeCarte.color());
    
    carreFantome = mat.createTransformedShape(rect);
    mat.getTranslateX();
    
    g2dCopie.fill(carreFantome);
    g2dCopie.draw(carreFantome);
    
   
    g2dCopie.setStroke(new BasicStroke(2));
    g2dCopie.setColor(Color.BLACK);
    carreNoFantome = mat.createTransformedShape(rectNo);
    g2dCopie.draw(carreNoFantome);
    
    g2dCopie.translate(0+(largeurBloc)*getType().ordinal(),0);
    g2dCopie.drawString(typeCarte.name(), 0, (int) (positionRecty + largeurBloc));
    g2dCopie.drawString("" + (typeCarte.ordinal() + 1), largeurBloc - positionRectNoX+ AJUSTEMENT_POSX_RECT_NOX, positionRecty + AJUSTEMENT_POSY_RECT);
    
}








/**
 * Getter de la propriété typeCarte
 * @return le MolType de la carte
 */
public MolType getType() {
    // TODO Auto-generated method stub
    
    return typeCarte;
}

@Override
    public String toString() {
        // TODO Auto-generated method stub
    
        return " "+getType();
    }

public void setTrait(boolean trait) {
    this.trait = trait;
}
 
}
