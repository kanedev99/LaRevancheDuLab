package interfaces;
/**
Interface representant un objet pouvant avancer dans le temps dans sa scene d'animation.

@author Brian Normant
**/
public interface Tickable {
    /**
    Les actions que l'object fais a chaque mise a jour du temps
    **/
    void tick();
}
