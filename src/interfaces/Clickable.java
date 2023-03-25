package interfaces;

/**
Interface clickable, oblige un elements dessinable a pouvoir être cliquer.
@author Brian Normant
**/
public interface Clickable {
    /**
    methode verifiant si la souris est dans la zone de l'elements.
    @param x positions x de la souris
    @param y positions y de la souris
    @return true si la souris a clickée sur l'élement
    **/
    boolean clickOnMe(int x, int y);
}
