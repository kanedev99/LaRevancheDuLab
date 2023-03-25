package interfaces;

import java.awt.geom.Area ;

/**
Interface representant un object pouvant retrer en collision avec un autre.
Doit pouvoir renvoiyer une Area Correspondant a sa geomtrie dans l'espace en Pixel de
la zone d'animation
@author Brian Normant
**/
public interface Collisionnable {
    /**
    recupere la geometrie de l'objet afin de tester si celle ci entre en contact avec une autre.
    @return la geometrie de l'object
    **/
    public Area getArea();
}
