package jeuPrincipal;

import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.awt.Polygon;

import interfaces.*;
import chimie.MolType;

/**
 * Dessine un projectile représentant chaque tir que le char s'assaut envoie via
 * son cannon Et les tirs des differentes molécules
 * 
 * @author Maysa Zahi
 * @author Kepler Paul-Émile
 */
public class Projectile implements Dessinable, Collisionnable, Tickable {

	// ==========================
	// Constantes
	// ==========================
	/** Le nombre de RAD **/
	private final static int RAD = 5; // pixel
	/****/
	private final static int TRAIL = 10; // pixel
	/** La vitesse du projectile **/
	private final static int SPEED = 50; // pixel/s
	/** la geometrie d'un projectile **/
	private final static Area geom;

	/** Les dégats qu'infligent les projectiles ennemis **/
	private double degats = 2; // en pourcentage
	/**  Determine si projectile ennemi a touché le char d'assaut **/
	private boolean touche = false;

	/** Crée une aire qui définit l'allure du projectile puis
	 * construit la géometrie du projectile at compile time
	 *  
	 * Le projectile doit resemble à ceci 
	 * \/ __ /**\ <- un cercle \__/ \/ <- un triangle
	 **/
	//Maysa Zahi
	static {
		geom = new Area();
		geom.add(new Area(new Ellipse2D.Double(0, 0, RAD, RAD)));
		geom.add(new Area(
				new Polygon(new int[] { 0, RAD, RAD / 2 }, new int[] { RAD / 2, RAD / 2, RAD / 2 + TRAIL }, 3)));
	}

	/** Determine si le projectile monte ou descend **/
	private boolean monteOuDescend;
	/** Position en x et y du projectile **/
	private double x, y;

	/** Le type de la molécule courante **/
	private MolType type;

	/**
	 * Crée un projectile à une certaine position, un type de molécule et 
	 * une indication de si oui ou non il va vers
	 * le haut ou vers le bas
	 * 
	 * @param x          La position en x du projectile
	 * @param y          La position en y du projectile
	 * @param type       Le type de molécule que contient le projectile
	 * @param goingUp    Est-ce que le projectile se déplace vers le haut?
	 */
	// Maysa Zahi
	public Projectile(double x, double y, MolType type, boolean goingUp) {

		this.x = x;
		this.y = y;

		this.type = type;
		this.monteOuDescend = goingUp;
	}

	/**
	 * Méthode permettant au projectile de monter ou descendre selon la propriété
	 * monteOuDescend
	 */
	// Kepler Paul-Émile
	public void tick() {
		this.y += (monteOuDescend ? -1 : 1) * SPEED * Jeu.deltaT;
	}

	/**
	 * Permet d'accéder à l'aire d'une géométrie (ici, celle du projectile)
	 * 
	 * @return a L'aire d'une géométrie ; le projectile
	 */
	//Maysa Zahi
	public Area getArea() {
		var a = new Area(geom);
		var mat = new AffineTransform();
		mat.translate(this.x, this.y);
		a.transform(mat);
		return a;
	}// Fin methode

	/**
	 * Permet de vérifier les collisions entre le projectile et une géométrie
	 * 
	 * @param other Une autre aire qui participe à une collision
	 * @return Est-ce qu'il y a une collision?
	 */
	// Maysa Zahi
	public boolean collision(Area other) {
		var a = this.getArea();
		a.intersect(other);
		return !a.isEmpty();
	}// Fin methode

	/**
	 * Detecte si le tank a été touché par le projectile ennemi
	 * 
	 * @param tank Le char d'assaut
	 * @return Est-ce que le char d'assaut a été touché?
	 */
	// Maysa Zahi
	public boolean estTouche(Tank tank) {
		return collision(tank.getZone());
	}// Fin methode

	/**
	 * Dessine un projectile
	 * 
	 * @param g2d Contexte graphique
	 */
	// Maysa Zahi
	@Override
	public void dessiner(Graphics2D g2d) {
		Graphics2D g2dC = (Graphics2D) g2d.create();

		g2dC.setColor(this.type.color());
		g2dC.translate(this.x, this.y);
		g2dC.scale(2, 2);
		if (!monteOuDescend) {
			g2dC.rotate(Math.PI);
		}
		g2dC.fill(geom);
		g2dC.draw(geom);

		g2dC.dispose();
	}

	/**
	 * Permet l'accès aux dégats que le projectile fait subir au char d'assaut
	 * 
	 * @return degats Les dégats qu'inflige le projectile
	 */
	// Maysa Zahi
	public double getDegats() {
		return this.degats;
	}

	/**
	 * Permet de savoir si le projectile a touché le char d'assaut
	 * 
	 * @return touche Est-ce que le projectile a touché le char d'assaut ?
	 */
	// Maysa Zahi
	public boolean aTouche() {
		return touche;
	}

	/**
	 * Marque le projectile comme ayant touché une cible (char d'assaut)
	 * 
	 * @param touche true si le projectile a touché une cible, false sinon
	 */
	//Maysa Zahi
	public void setTouche(boolean touche) {
		this.touche = touche;
	}

	/**
	 * Méthode qui détermine si le projectile tiré vers le haut (donc par
	 * l'utilisateur) touche
	 * 
	 */
	// Kepler Paul-Émile
	public boolean toucheMole(Molecule mol) {
		return collision(mol.getArea());
	}// Fin methode

	/**
	 * Le getter de la propriété type qui retourne le type du projectile
	 * 
	 * @return le molType attribuée à ce projectile
	 */
	// Kepler Paul-Émile
	public MolType getMolType() {
		return type;
	}// Fin methode

	/**
	 * le getter de la composantes y du projectile
	 * 
	 * @return la composante y du projectile
	 */
	// Kepler Paul-Émile
	public double getY() {
		return this.y;
	}

}
