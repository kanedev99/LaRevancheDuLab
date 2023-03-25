package mixMixMix;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.Dessinable;

public class Equation implements Dessinable {

	/** Les atomes qui sont les réactifs **/
	private List<Atome> reactifs;
	/** Les atomes qui sont les produits **/
	private List<Atome> produits;
	/** La position x et y de l'équation **/
	private int x, y;
	/** La largeur et hauteur des cases **/
	private int caseWidth = 50;
	private int caseHeight = 50;

	/** Les chiffres à afficher devant chaque case **/
	private List<Integer> reactifChiffres;
	private List<Integer> produitChiffres;

	/**
	 * Crée une nouvelle équation
	 * 
	 * @param x la position x de l'équation
	 * @param y la position y de l'équation
	 */
	public Equation(int x, int y) {
		this.x = x;
		this.y = y;
		this.reactifs = new ArrayList<>();
		this.produits = new ArrayList<>();

		// Mets à jour les chiffres
		this.reactifChiffres = new ArrayList<>();
		this.produitChiffres = new ArrayList<>();
	}

	public void ajouterAtome(Atome atome) {
		if (atome.getType() == TypeAtome.REACTIF) {
			reactifs.add(atome);
			reactifChiffres.add(0);
		} else if (atome.getType() == TypeAtome.PRODUIT) {
			produits.add(atome);
			produitChiffres.add(0);
		}
	}

	public void setReactifChiffre(int index, int chiffre) {
		if (index >= 0 && index < reactifChiffres.size()) {
			reactifChiffres.set(index, chiffre);
		}
	}

	public void setProduitChiffre(int index, int chiffre) {
		if (index >= 0 && index < produitChiffres.size()) {
			produitChiffres.set(index, chiffre);
		}
	}

	@Override
	public void dessiner(Graphics2D g2d) {

		// Dessine les cases pour les réactifs
		int reactifX = x;
		for (Atome reactif : reactifs) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(reactifX, y, caseWidth, caseHeight);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(reactifX, y, caseWidth, caseHeight);
			reactifX += caseWidth + 5;
		}

		// Dessine les symboles "+" entre les réactifs et les produits
		g2d.drawString("+", reactifX, y + (caseHeight / 2));
		reactifX += caseWidth + 10;
		g2d.drawString("=", reactifX, y + (caseHeight / 2));
		reactifX += caseWidth + 10;

		// Dessine les cases pour les produits
		for (Atome produit : produits) {
			g2d.setColor(Color.WHITE);
			g2d.fillRect(reactifX, y, caseWidth, caseHeight);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(reactifX, y, caseWidth, caseHeight);
			reactifX += caseWidth + 5;
		}

		// Dessine les chiffres devant les cases pour les produits
		int produitX = reactifX;
		for (int i = 0; i < produits.size(); i++) {
			int chiffre = produitChiffres.get(i);
			if (chiffre > 0) {
				g2d.drawString(Integer.toString(chiffre), produitX - 10, y + (caseHeight / 2) + 5);
			}
			produitX += caseWidth + 5;
		}

	}


}