package interfaces;

/**
 * Interface qui definit la methode qu'un objet doit implementer pour pouvoir
 * etre selectionne
 * 
 * @author Caroline Houle
 *
 */

public interface Selectionnable {

	/**
	 * Retourne vrai si le point passe en parametre fait partie de l'objet
	 * dessinable sur lequel cette methode sera appelee
	 * 
	 * 
	 * @param xPix Coordonnee en x du point (exprimee en pixels)
	 * @param yPix Coordonnee en y du point (exprimee en pixels)
	 */
	public boolean contient(double xPix, double yPix);

}
