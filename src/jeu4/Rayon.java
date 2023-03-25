package jeu4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import interfaces.Collisionnable;
import interfaces.Dessinable;
import interfaces.Rotation;
import utils.Vecteur2D;

/**
 * la classe representant le rayon 
 * @author Kepler Paul-Émile
 *
 */
public class Rayon implements Collisionnable, Dessinable {

    /** le rectangle **/
    private Rectangle2D rect;
    /** la longueur du rectangle représentant le laser**/
    private  double longueurRect;
    /**la largeur du rectangle représentant le laser **/
    private  double largueurRect;
    
    /****/
    private double largeurEcran;
    /****/
    private double hauteurEcran;
    /** la position en x du rectangle representatn le laser**/
    private double posX;
    /** la position en y du rectangle representatn le laser**/
    private double posY;
    
    /** le nombre de pixels par metres**/
    private double pixelsParMetres=1;
    
    /** la rotation du laser **/
    private double rotation=0;
    
    /** le vecteur d'orientation**/
     private Vecteur2D vecDirec;
     
     /** le point d'origine**/
     private Vecteur2D vecOrigine;
     
     /** le point ou le rayon pointe**/
     private Vecteur2D pointArrivé;
     
     /**la position ou le rayon est réfléchie et donc est terminer**/
     private Vecteur2D posFinal;
     
     /** la ligne représentant le laser**/
     private Path2D ligne;
     
     public Rayon(double largeurEcran,double hauteurEcran) {
         
      
       this.largeurEcran=largeurEcran;
       this.hauteurEcran=hauteurEcran;
       
         creerLaGeometrie();
        // choisirLaRotation();
         
     }
     
     
     /**
      * créer la géometrie
      */
     private void creerLaGeometrie() {
        // TODO Auto-generated method stub
         this.largueurRect=(largeurEcran/100);
         this.longueurRect=(hauteurEcran/10);
         
         this.posX=(largeurEcran/2)-(largueurRect/2);
         this.posY=0;
                 
        rect=new Rectangle2D.Double(posX, posY, largueurRect , longueurRect);
        this.vecOrigine=new Vecteur2D(largeurEcran/2,0+longueurRect/2);
        //Le point d'arrivé de base
        this.pointArrivé=new Vecteur2D(vecOrigine.getX(),vecOrigine.getY());
    }



   

    @Override
    public void dessiner(Graphics2D g2d) {
        // TODO Auto-generated method stub
        Graphics2D g2dCopie=(Graphics2D)g2d.create();
        AffineTransform mat=new AffineTransform();
        g2dCopie.rotate(this.rotation, rect.getCenterX(), vecOrigine.getY());
        g2dCopie.setColor(Color.PINK);
        g2dCopie.fill(rect);
        g2dCopie.draw(rect);
    }

    @Override
    public Area getArea() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
    
    public void setPixelsParMetres(double pixelsParMetres) {
        this.pixelsParMetres = pixelsParMetres;
    }
    
    /**
     * change la valeur du point d'arrivé
     * @param pointArrivé le vecteur representant le point d'arrive
     */
    public void setPointArrivé(Vecteur2D pointArrivé) {
        this.pointArrivé = pointArrivé;
    }
    
    /***
     * 
     */
    public void nouvelleDirection(Vecteur2D point) {
      
       this.vecDirec=point.soustrait(vecOrigine);
       System.out.println(vecDirec);
       this.rotation=Rotation.Rotation(this.vecDirec);
    }
}
