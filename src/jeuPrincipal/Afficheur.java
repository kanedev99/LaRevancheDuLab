package jeuPrincipal;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Classe qui représente un afficheur de score et de vague d'ennemis.
 * 
 * @author Maysa Zahi
 */
public class Afficheur implements Dessinable {

    /** Marge intérieure de l'afficheur **/
    private static final int PADDING = 10;
    /** Largeur de l'afficheur **/
    private static final int LARGEUR = 200;
    /** Hauteur de l'afficheur **/
    private static final int HAUTEUR = 25;

    /** Coordonée en x qui indique la position en x de l'afficheur **/
    private double x;
    /** Coordonée en y qui indique la position en y de l'afficheur **/
    private double y;
    /** Le corps de l'afficheur **/
    private static Rectangle2D.Double panneau;
    /** Petit ajustement **/
    private int ajustement = 16;
    /** Largeur de la chaine de caractère **/
    private int vagueWidth;

    /**
     * Contruit la géometrie de l'afficheur
     **/
    static {
        panneau = new Rectangle2D.Double(0, 0, LARGEUR, HAUTEUR);
    }

    /**
     * Contructeur qui crée une seule fois au lancement du
     * jeuPrincipale un panneau d'affichage
     * 
     * @param x La dimention x du panneau
     * @param y La dimention y du panneau
     **/
    public Afficheur(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * Dessine l'intégralité du panneau (afficheur)
     * 
     * @param g2d le contexte graphique
     */
    @Override
    public void dessiner(Graphics2D g2d) {

        // Dessin du panneau
        g2d.setColor(Color.BLUE);
        g2d.translate(x, y);
        g2d.fill(panneau);

        // Dessin du score
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        g2d.drawString("Score : " + Stat.score, 5, (int) y + ajustement);

        // Dessin de la vague d'ennemis
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));

        String vagueLabel = "Vague : " + Stat.vague + " sur " + Stat.totalVague;
        FontMetrics fm = g2d.getFontMetrics();
        vagueWidth = fm.stringWidth(vagueLabel);
        g2d.drawString(vagueLabel, LARGEUR - PADDING - vagueWidth, (int) y + ajustement);

    }

}
