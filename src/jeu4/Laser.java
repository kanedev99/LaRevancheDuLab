package jeu4;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

import interfaces.Collisionnable;
import interfaces.Dessinable;
import utils.Vecteur2D;

public class Laser implements Dessinable,Collisionnable {
/** le vecteur representant l'orientation**/
    private Vecteur2D vecOri;
    
 /** le point de depart**/
    private Vecteur2D posDepart;
/** le point d'arrivee**/    
    private Vecteur2D posFinal;
    /** la ligne représentant le rayon**/
    private Line2D.Double ligne;
    
@Override
public void dessiner(Graphics2D g2d) {
    // TODO Auto-generated method stub

}

@Override
public Area getArea() {
    // TODO Auto-generated method stub
    return null;
}
}
