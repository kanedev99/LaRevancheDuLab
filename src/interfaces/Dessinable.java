package interfaces;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Interface qui definit la methode qu'un objet doit implementer pour pouvoir etre dessine
 * 
 * @author Caroline Houle
 *
 */
public interface Dessinable {
	

	/**
	 * Permet de dessiner des objets 
	 * @param g2d Le contexte graphique
	 */
	
	public void dessiner(Graphics2D g2d);
}
