
package jeuPrincipal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;

import chimie.MolType;
import interfaces.*;
import utils.Sprite;

/**
 * La classe tank crée et dessine un char d'assaut capable de tirer des
 * projectiles
 * qui a une vitesse, une accélération et est soumis au frottement du sol
 *
 * Cette classe implementes principalement le visuel et l'animation physique du
 * tank
 * La gestion de la logique du jeu se fera par la classe SceneDanimation, des
 * méthodes statiques permettronts de deleguer les bouts de codes redondants
 * 
 * @author Maysa Zahi
 * @author Kepler Paul-Émile
 */
public class Tank extends Sprite implements Dessinable, Tickable, Collisionnable {

    // ------------------------//
    // Propriété visuel
    // ------------------------//
    /** Couleur du char d'assaut **/
    private final Color couleur = Color.GREEN;
    /** Corps du char d'assaut **/
    private static Rectangle2D.Double corps;
    /** Le canon du char d'assaut **/
    private static Line2D.Double canon;
    /** Largeur du char d'assaut **/
    private static final double LARGEUR = 35;
    /** Hauteur du char d'assaut **/
    private static final double HAUTEUR = 60;
    /** La longeur du tir **/
    private static final double LONGUEUR_CANON = HAUTEUR;
    /** la forme fantome du tank**/
    private Shape corpsFantome;
    private Shape cannonFantome;
    /**
     * La vie initiale du char d'assaut. Elle représente également sa vie maximale
     **/
    private static final int VIE = 10;

    /**
     * Contruit la géometrie du char d'assault
     **/
    // Maysa Zahi
    static {
        corps = new Rectangle2D.Double(0, 0, LARGEUR / 2, HAUTEUR / 2);
        canon = new Line2D.Double(
                LARGEUR / 2, 0,
                LARGEUR / 2, -HAUTEUR / 4);
    }
    // -------------------------------//
    // Propriétées at run time
    // -------------------------------//
    /** Masse du char d'assaut **/
    private double masse = 1;
    /** Le poids du char d'assaut **/
    private double poids = 20;
    /** La position initiale en x du char d'assaut **/
    private static double x;
    /** La position initiale en y du char d'assaut **/
    private static double y;
    /** La vitesse initiale du char d'assaut **/
    private double vX = 0;
    /** le coefficient de frottement **/
    private double coefFrottement = 0.5;
    /** l'acceleration des propulseurs du tank **/
    private int motor = 0;
    /** La force de frottement **/
    private double fFrottement = 0;
    /** La somme des forces qui agissent sur le char d'assaut **/
    private double force = fFrottement - motor;
    /** Force totale appliquée sur le char d'assaut **/
    private double a = force * masse;
    /** changer la couleur du char d'assaut en fonction du moltype **/
    private Color couleurTank = MolType.ACIDE.color();
    /** Petit ajustement de la position du char d'assaut **/
    private double ajustement = -130;
    /** Autre ajustement pour la position de l'image **/
    private int ajust = 4;

    // -----------------------------//
    // Constantes physiques
    // -----------------------------//
    /** Puissance du moteur **/
    private static final int PUISSANCE = 15;
    /** Tolerance utilisée dans les comparaisons réelles avec zero **/
    private static final double EPSILON = 0.5;

    /**
     * Contructeur qui crée une seule fois au lancement du
     * jeuPrincipale un char d'assaut centrer dans la scene
     * 
     * @param x La dimention x de la scene
     * @param y La dimention y de la scene
     */
    // Maysa Zahi
    public Tank(int dimX, int dimY) {
        this.x = dimX / 2 - (LARGEUR);
        this.y = (dimY * 7 / 10 - (HAUTEUR)) - ajustement;

      loadImage("tank.jpg");
    }

    /**
     * Dessine l'intégralité du char s'assaut
     * 
     * @param g2d le contexte graphique
     */
    // Maysa Zahi
    @Override
    public void dessiner(Graphics2D g2d) {
        Graphics2D g2dC = (Graphics2D) g2d.create();

        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.scale(2, 2);
        // Dessin du corps du tank
        g2dC.setColor(couleur);
        corpsFantome = transform.createTransformedShape(corps);
        g2dC.fill(corpsFantome);

        
        // Dessin du canon du tank
        g2dC.setColor(couleurTank);
        g2dC.setStroke(new BasicStroke(7));
        cannonFantome=transform.createTransformedShape(canon);
        g2dC.draw(cannonFantome);
        g2dC.draw(transform.createTransformedShape(new Rectangle2D.Double(0, 0, 15, 15)));

        // Dessin de l'image du char d'assaut
        
        g2dC.drawImage(image, (int) x - (int) (LARGEUR / 2) + ajust, (int) y - (int) HAUTEUR, (int) LARGEUR * 2,
                (int) HAUTEUR * 2, null);


        g2dC.dispose();

    }

    /**
     * Mets le moteur du char d'assaut à 0
     */
    // Maysa Zahi
    public void motorOff() {
        this.motor = 0;
    }// Fin methode

    /**
     * Pousse le tank vers la droite en fonction d'une certaine puissance de moteur
     */
    // Maysa Zahi
    public void goRight() {
        this.motor = PUISSANCE;
    }// Fin methode

    /**
     * Pousse le tank vers la gauche en fonction d'une certaine puissance de moteur
     */
    // Maysa Zahi
    public void goLeft() {
        this.motor = -PUISSANCE;
    }// Fin methode

    /**
     * Calcule la nouvelle vitesse et la nouvelle position du char s'assaut après
     * cette nouvelle intervalle de temps qui correspond à deltaT (le pas) pour le
     * faire déplacer
     */
    // Maysa Zahi
    public void tick() {

        force = (vX < EPSILON && vX > -EPSILON) ? 0 : coefFrottement * -Math.signum(vX) * poids;
        a = (motor + force) / masse;

        /**
         * System.out.println("Acceleration = " + a);
         * System.out.println("moteur = " + motor);
         * System.out.println("force = " + force);
         * System.out.println("position = " + x);
         * System.out.println("vX = " + vX);
         */
        // Algorithme semi-implicite d'euler au premier degree
        if (vX < EPSILON && vX > -EPSILON)
            vX = 0;

        vX += (Jeu.deltaT * a);
        x += Jeu.deltaT * vX;

        // Updater les stats
        Stat.acceleration = a;
        Stat.vitesse = vX;
        Stat.friction = coefFrottement * -Math.signum(vX) * poids;

    }// Fin methode

    /**
     * Verifie si le char d'assaut a touché le mur
     * 
     * @param xMax les murs sont 0 < x < xMax
     * @return Est-ce que le char d'assaut touche les bords ? (oui/non)
     */
    // Maysa Zahi
    public boolean bounce(double xMax) {

        if (x <= 0 || x >= xMax - getLargeur())
            return true;
        else
            return false;
    }// Fin methode

    /**
     * Modifie la position en X initiale du char d'assaut par celle passée en
     * paramètre
     * 
     * @param x La position en X initiale du char d'assaut
     */
    // Maysa Zahi
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Permet l'accès à la position en X initiale du char d'assaut
     * 
     * @return x La position en X initiale du char d'assaut
     */
    // Maysa Zahi
    public double getX() {
        return x;
    }

    /**
     * Permet l'accès à la largeur du char d'assaut
     * 
     * @return LARGEUR La largeur du char d'assaut
     */
    // Maysa Zahi
    public static double getLargeur() {
        return LARGEUR;
    }

    /**
     * Permet l'accès à la vitesse courrante du char d'assaut
     * 
     * @return vX La vitesse courrante du char d'assaut
     */
    // Maysa Zahi
    public double getVx() {
        return vX;
    }

    /**
     * Modifie la vitesse courrante du char d'assaut par celle passée en paramètre
     * 
     * @param vX La vitesse courrante du char d'assaut
     */
    // Maysa Zahi
    public void setVx(double vX) {
        this.vX = vX;
    }

    /**
     * @param munitions
     * @param type
     * @param couleurTir
     * @return le projectile approprié
     */
    // Kepler Paul-Émile
    public Projectile tirer(MolType type) {

        // Projectile(double x, double y,Color color, MolType type, boolean goingUp)

        Projectile tir = new Projectile(
                x + LARGEUR / 2 - 3,
                y - LONGUEUR_CANON,
                type,
                true); // Va vert le haut, puisque le tank tire, on peus partir du principe que ses
        // projectiles se deplaceront toujours vert le haut
        return tir;

    }// Fin methode

    /**
     * Renvoie une aire qui représente la zone occupée par le char d'assaut,
     * y compris son canon.
     *
     * @return area Une aire représentant la zone occupée par le char d'assaut
     */
    // Maysa Zahi
    public Area getZone() {

        Area fantomeCannon=new Area(cannonFantome);
        Area areaCorps = new Area(corpsFantome);
        areaCorps.add(fantomeCannon);
        return areaCorps;
    }// Fin methode

    /**
     * Permet l'accès à la vie maximale du char d'assaut
     * 
     * @return VIE La vie maxiamle du char d'assaut
     */
    // Maysa Zahi
    public double getVieMax() {
        return VIE;
    }

    /**
     * change la couleur du tank
     * 
     * @param couleurTank la couleur du tank
     */
    // Kepler Paul-Émile
    public void setCouleurTank(Color couleurTank) {
        this.couleurTank = couleurTank;
    }

    /**
     * Permet l'accès à la position en y du char d'assaut
     * 
     * @return y La position en y du char d'assaut
     */
    // Maysa Zahi
    public double getPosY() {
        return this.y;
    }

}
