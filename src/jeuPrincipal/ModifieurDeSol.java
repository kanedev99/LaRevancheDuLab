package jeuPrincipal;
/**
 * classe qui s'occupe de modifier le sol avec des carrés

 * @author Kepler Paul-Émile
 *
 */

import java.awt.geom.Rectangle2D;

public class ModifieurDeSol {

    private Rectangle2D.Double rectModifié;
    private boolean activéPourEnnemis=false;
    private boolean activéPourTank=false;
    private double longueur,hauteur;
    private Double coeffdeFrottementAjouté=0.5;
    
     ModifieurDeSol(double x,double y,double longueur,double hauteur) {
        
    }
     
     
     
}
