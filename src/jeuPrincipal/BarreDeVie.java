package jeuPrincipal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Classe qui représentera une barre de vie du char d'assaut 
 * @author Maysa Zahi
 *
 */
public class BarreDeVie implements Dessinable {

	/** Le corps de la barre de vie **/
	private Rectangle2D cadre;
	/** La barre de vie **/
	private Rectangle2D barreVariable;
	/** Position x de la barre de vie **/
	private double x;
	/** Position y de la barre de vie **/
	private double y;
	/** Largeur de la barre de vie **/
	private double largeur;
	/** Hauteur de la barre de vie **/
	private double hauteur;
	/** Coefficient selon lequel la barre de vie diminue **/
	private double coefficientReducteur = 1.0;
	/** Poisition en x du pourcentage **/
	private int posX; 
	/** Poisition en y du pourcentage **/
	private  int posY; 
	
	/** Ligne qui détermine à quel moment le tank meurt**/
	private Path2D.Double ligne;	
	/**Le nombre de petit morceaux d'une ligne brisee**/
	private int morceaux = 2;
	/** Valeur de depart a laquelle se dessine le path2D**/
	private int valeurDepart;
	/** L'espace entre les points du path2D **/
	private int pas = 35;
	/** Les cordonnées de la ligne brisée **/
	private int dimScene;
	/** Position en Y de la barre brisée**/
	private int yLigne = 655;

	
	


	/**
	 * Crée une barre de vie avec la position initiale et le montant de vie maximale.
	 * 
	 * @param x La position horizontale de la barre de vie.
	 * @param y La position verticale de la barre de vie.
	 * @param largeur La largeur de la barre de vie
	 * @param hauteur La hauteur de la barre de vie
	 */
	public BarreDeVie(double dimX, double dimY, double largeur, double hauteur) {
		this.x = dimX;
		this.y = dimY;
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.coefficientReducteur = 1.0; //par défaut

		creerLaGeometrie();

	}

	/**
	 * Crée la géométrie de la barre de vie
	 */
	private void creerLaGeometrie() {
		
		//barre de vie

		cadre = new Rectangle2D.Double(x, y, largeur, hauteur);
		barreVariable = new Rectangle2D.Double(x, y, largeur, hauteur);
		
		// ligne millieu 
		ligne = new Path2D.Double();

	}
	
	/**
	 * Permet l'accès à la largeur de la scène 
	 * @param extScene La largeur de la scène
	 */
	public void setDimScene(int extScene) {
		this.dimScene = extScene;
	}


	/**
	 * Dessine la barre de vie du char d'assaut
	 */
	@Override
	public void dessiner(Graphics2D g2d) {


		g2d.setColor(Color.BLACK);
		g2d.draw(cadre);

		g2d.setColor(Color.RED);
		barreVariable.setRect(x, y, largeur * this.coefficientReducteur, hauteur);
		g2d.fill(barreVariable);


		// Dessine le pourcentage centré sur la barre de vie

		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 12));
		String pourcentage = String.format("%.0f%%", this.coefficientReducteur * 100);
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D r = fm.getStringBounds(pourcentage, g2d);

		//centrer le pourcentage 
		posX = (int) (x + (largeur - r.getWidth()) / 2); 
		posY = (int) (y + (hauteur - r.getHeight()) / 2 + fm.getAscent());

		//dessiner le pourcentage
		g2d.drawString(pourcentage, posX, posY);
		
		// Dessiner la ligne 
		
		g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(3));
		
		ligne.moveTo( x , yLigne);  

		for(valeurDepart = 0; valeurDepart <= dimScene; valeurDepart+= 31) {
			ligne.moveTo(valeurDepart, yLigne);
			ligne.lineTo(valeurDepart+pas/morceaux, yLigne);
		}

		g2d.draw(ligne);

	}

	/**
	 * Permet l'accès au coefficient selon lequel la barre du char d'assaut diminue
	 * 
	 * @param d Le coefficient réducteur selon lequelle la barre de vie diminue 
	 * (compris toujours entre 0.0 et 1.0)
	 */
	public void setCoefficientReducteur(double d) {
		this.coefficientReducteur = d;
	}

}

