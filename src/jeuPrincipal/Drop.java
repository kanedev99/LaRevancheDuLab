package jeuPrincipal;

import java.awt.BasicStroke;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Map;
import java.util.Random;
import java.awt.Color;

import interfaces.*;
import chimie.MolType;
import utils.*;

/**
 * Classe Drop, represente les munitions que vont faire tomber les molécules si
 * elle sont détruite
 * Cette object descent et tourne sur lui Meme;
 * 
 * @author Brian Normant
 */
public class Drop implements Dessinable, Collisionnable, Tickable {
    // ======================= //
    // Constantes
    // ======================= //
    /** Vitesse de chute des drops */
    private final int SPEED = 15;
    /** le nombre de munitions max gagné **/
    private final int MAX_MUN = 5;
    /** geometrie de la munition **/
    private static final Area geom;
    /** Géometrie afficher de l'objet **/
    private static final int SIZE = 30;
    // Créer la géometrie
    //
    // Un pentagone
    //
    static {
        var pointsX = new int[5];
        var pointsY = new int[5];
        for (int i = 0; i < 5; i++) {
            pointsX[i] = (int) (Math.sin(Math.PI * 2 / 5 * i) * SIZE);
            pointsY[i] = (int) (Math.cos(Math.PI * 2 / 5 * i) * SIZE);
        }
        geom = new Area(
                new Polygon(
                        pointsX, pointsY, 5));
    }
    // ======================= //
    // Propriétées
    // ======================= //
    /** Position de l'object **/
    private int x, y;
    /** Rotation de l'object en radians **/
    private double rot;
    /** Direction et vitesse de la rotation **/
    private final double rotD;
    /** Type de la molecule **/
    private MolType type;
    /** nombre de munition obtenu en recuperant ce drop */
    private int nbMunition;

    // ======================= //
    // Constructeurs
    // ======================= //
    /**
     * Contructeur principale, place une molecule dans la munition
     */
    public Drop(int x, int y, MolType type) {
        this.x = x;
        this.y = y;
        this.rot = 0;
        this.rotD = new Random().nextInt(5) - 2.5;
        this.type = type;
        this.nbMunition = new Random().nextInt(MAX_MUN);
    }

    // ======================= //
    // Méthode
    // ======================= //
    /**
     * Dessine l'object sur la scene
     * 
     * @param g2d Le contexte graphique
     */
    @Override
    public void dessiner(Graphics2D g2d) {
        var g = (Graphics2D) g2d.create();
        var gText = (Graphics2D) g2d.create();
       
        g.rotate(rot, this.x + SIZE / 2, this.y + SIZE / 2);
        g.translate(x + SIZE / 2, y + SIZE / 2);
        g.setColor(this.type.color());
        g.fill(geom);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(4));
        g.draw(geom);
        gText.setColor(Color.BLACK);
        // gText.scale(2, 2);
        // gText.translate(SIZE / 2, SIZE / 2);\
        String text = this.type.name().toLowerCase() + "\n" + " x " + this.nbMunition;
        gText.drawString(text, this.x + SIZE / 2 - 10, this.y + SIZE / 2 - 2);
    }

    /**
     * Fait tomber la forme de la munition dans le temps
     */
    public void tick() {

        this.y += SPEED * Jeu.deltaT;
        this.rot += rotD * Math.PI / 75;

    }

    /**
     * Recuper l'Area representant la position de la forme de la munition tombante
     * 
     * @return l'Area
     */
    public Area getArea() {
        var ar = new Area(geom);
        var mat = new AffineTransform();
        // mat.rotate(rot, this.x + SIZE / 2, this.y + SIZE / 2);
        mat.translate(x, y);
        ar.transform(mat);
        return ar;
    }

    /**
     * Determiner si la forme de la munition à touché un tank
     * 
     * @return si la forme de la munition à touché un tank
     */
    public boolean collisionTank(Tank tank) {
        var a = this.getArea();
        a.intersect(tank.getZone());
        return !a.isEmpty();
    }

    public int getMun() {
        return this.nbMunition;
    }

    public MolType type() {
        return this.type;
    }
    // ======================= //
    // Méthode statiques
    // ======================= //

}
