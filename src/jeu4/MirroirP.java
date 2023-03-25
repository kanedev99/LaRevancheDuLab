package jeu4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import interfaces.Collisionnable;
import interfaces.Dessinable;
import interfaces.Jeu;
import interfaces.Rotation;
import interfaces.Tickable;
import utils.Vecteur2D;

/**
 * 
 * @author Kepler Paul-Émile
 * représente un mirroir plan
 */
public class MirroirP implements Collisionnable,Dessinable,Tickable {
    //
    // Les Constantes
    //
    /**  la vitesse du mirroir en metres(m)**/
    private double vitesseM=0.125;
    
    /**la longueur du mirroir**/
    private final double longueurMirroir1=0.51;
    /** la hauteur du mirroir**/
    private final double hauteurMirroir1=0.20;
 /**la longueur du mirroir**/
 private  double longueurMirroir;
 /** la hauteur du mirroir**/
 private double hauteurMirroir;
 
 /** le rectangle représentant le mirroir**/   
 private Rectangle2D rectMiroir;
 /** l'aire du rectangle**/
  private Area aireRect;
  /** le vecteur de mouvement du mirroir**/
  private Vecteur2D vecteurMouvement; 
  /** la forme du recctangle transformé**/
  private Shape rectransfo;
  /** la position en x**/
  private Vecteur2D pos;
  
  /** le vecteur de la normale**/
  private Vecteur2D NormalMi;
  /**la vitesse additionnelle selon la vitesse **/
  private static double vitesseAdd=1;
  
  /**la largeur de l'ecran **/
  private double largeurEcran;
  
  /** la hauteur de l'ecran**/
  private double hauteurEcran;
  /**la ligne reflechissante**/
  private Line2D ligneReflex;
  /**la ligne transformée **/
  private Shape ligneFantome;
  /**le premier point x après sa rotation **/
  private double rx1;
  /**le premier point y après sa rotation **/
  private double ry1;
  /**le deuxime point x après sa rotation **/
  private double rx2;
  /**le le deuxime point y après sa rotation **/
  private double ry2;
  /** le tick**/
  private int tick=0;
  
  private double rotation;
  /** la valeur des pixels par metres**/
  private double pixelsParMetres=1;
  
  public MirroirP(double largeurEcran,double hauteurEcran ,Vecteur2D vecteurMouvement,Vecteur2D pos) {
      this.hauteurEcran=hauteurEcran;
      this.largeurEcran=largeurEcran;
    this.pos=pos;
      
      this.vecteurMouvement=vecteurMouvement;
      System.out.println(vecteurMouvement);
      this.NormalMi=new Vecteur2D(-vecteurMouvement.getY(),vecteurMouvement.getX());
      creerLesProportions();
      this.rotation=Rotation.Rotation(vecteurMouvement);
    //choisirLaRotation();
   
  }
 
    
    
    
    
    
    
    private void creerLesProportions() {
    // TODO Auto-generated method stub
    this.hauteurMirroir= (this.largeurEcran/25);
    this.longueurMirroir=(this.largeurEcran/10);
    //
    rectMiroir=new Rectangle2D.Double(pos.x, pos.y, longueurMirroir1,hauteurMirroir1 );
    ligneReflex=new Line2D.Double(pos.x, pos.y, pos.x+longueurMirroir1, pos.y);
   
    
}







    @Override
    public Area getArea() {
        // TODO Auto-generated method stub
        return aireRect;
    }









    @Override
    public void dessiner(Graphics2D g2d) {
        // TODO Auto-generated method stub
        Graphics2D g2dCopie=(Graphics2D)g2d.create();
        AffineTransform mat=new AffineTransform();
      
        AffineTransform mat2=new AffineTransform();
      
       
        mat.scale(pixelsParMetres, pixelsParMetres);
         
        mat.rotate(rotation, rectMiroir.getCenterX(), rectMiroir.getCenterY());
       
        rectransfo=mat.createTransformedShape(rectMiroir);
        
        g2dCopie.setColor(Color.RED);
        g2dCopie.fill(rectransfo);
        aireRect=new Area(rectransfo);
        g2dCopie.draw(rectransfo);
        
        g2dCopie.setColor(Color.GREEN);
        mat2.scale(pixelsParMetres, pixelsParMetres);
        
        mat2.rotate(rotation,rectMiroir.getCenterX(),rectMiroir.getCenterY());
        
        ligneFantome=mat2.createTransformedShape(ligneReflex);
        g2dCopie.setStroke(new BasicStroke(2));
        g2dCopie.draw(ligneFantome);
       
       g2dCopie.rotate(rotation,rectMiroir.getCenterX(),rectMiroir.getCenterY());
      
       
    }



 //   x1 = (x0 – xc)cos(θ) – (y0 – yc)sin(-θ) + xc(Equation 3)
   // y1 = (x0 – xc)sin(θ) + (y0 – yc)cos(-θ) + yc(Equation 4)
    
    public void lesNouveauxPoints() {
        double xc=rectMiroir.getCenterX();
        double yc=rectMiroir.getCenterY();
        
    }


    @Override
    public void tick() {
        // TODO Auto-generated method stub
       
         if(tick==25) {
           
           vitesseM= vitesseM * (-1);
           tick=0;
          
        }
         
    
       // pos=new Vecteur2D(pos.getX()+vecteurMouvement.getX()*Jeu.deltaT*vitesseAdd,pos.getY()-vecteurMouvement.getY()*Jeu.deltaT*vitesseAdd );
         pos=new Vecteur2D(pos.getX()+vitesseM*Jeu.deltaT*vitesseAdd*vecteurMouvement.getX(),pos.getY()-vitesseM*Jeu.deltaT*vitesseAdd*vecteurMouvement.getY() );
        creerLesProportions();
        tick+=1;
       
        
    }
    
    public void reflexion() {
        
    }
    public void setPixelsParMetres(double pixelsParMetres) {
        
        this.pixelsParMetres = pixelsParMetres;
    }

}
