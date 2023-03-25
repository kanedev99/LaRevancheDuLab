package mixMixMix;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import interfaces.Clickable;
import interfaces.Dessinable;
import interfaces.Selectionnable;

public class Atome implements Dessinable, Selectionnable, Clickable {
	/** Nom de l'atome **/
	private String nom;
	/** Couleur de l'atome **/
	private Color couleur;
	/** Le nombre d'atomes **/
	private int nombre;
	/** Position x et y d'un atome **/
	private int x, y;
	/** Le rayon du cercle d'atome **/
	private int rayon;
	/** Un cercle qui represente l'atome **/
	Ellipse2D cercle = new Ellipse2D.Double();
	/** Le rectangle qui contient tous les atomes **/
	Rectangle2D paquet = new Rectangle2D.Double();
	/** Si l'atome est un produit ou reactif **/
	private TypeAtome type;
	/** Le nom de la molécule ***/
	private String molecule;

	/**
	 * Crée un atome avec un symbole, une couleur et un nombre 
	 * @param nom Le symbole de l'atome
	 * @param couleur La couleur de l'atome
	 * @param nombre Le nombre d'atomes voulus
	 */
	public Atome(int dimX, int dimY, int rayon, String nom, Color couleur, int nombre, TypeAtome type) {
		this.x = dimX;
		this.y = dimY;
		this.rayon = rayon;
		this.nom = nom;
		this.couleur = couleur;
		this.nombre = nombre;
		this.type = type;
		creerLaGeometrie();
	}


	private void creerLaGeometrie() {

		// les atomes
		cercle.setFrame(x - rayon, y - rayon, 2 * rayon, 2 * rayon);

		// Le rectangle qui contient tous les atomes
		paquet.setFrame(x - 2 * rayon, y - rayon, 4 * rayon * nombre, 2 * rayon);

	}

	public TypeAtome getType() {
		return type;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public int getNombre() {
		return nombre;
	}

	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	@Override
	public void dessiner(Graphics2D g2d) {

		// On définit la couleur de l'atome
		g2d.setColor(couleur);
		g2d.setStroke(new BasicStroke(3f));

		// On dessine le cercle
		g2d.fill(cercle);

		// On écrit le symbole de l'atome au centre du cercle
		g2d.setColor(Color.BLACK);
		g2d.drawString(nom, x - (g2d.getFontMetrics().stringWidth(nom) / 2), y + (g2d.getFontMetrics().getHeight() / 4));

		g2d.setColor(Color.BLACK);
		g2d.draw(paquet);

	}

	@Override
	public boolean clickOnMe(int x, int y) {
		return cercle.contains(x, y);
	}

	@Override
	public boolean contient(double xPix, double yPix) {
		return cercle.contains(xPix, yPix);
	}
}

