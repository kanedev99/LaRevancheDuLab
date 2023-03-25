package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

import interfaces.Collisionnable;
import interfaces.Dessinable;

/**
 * Classe abstraite représentant un sprite, c'est-à-dire un élément graphique
 * avec une image, une taille et une position. Cette classe permet de
 * 
 * @author Maysa Zahi
 */
public abstract class Sprite implements Dessinable, Collisionnable {
    /** L'image utilisée pour dessiner le sprite. */
    protected Image image;
    /** La position en X du sprite. */
    protected int x;
    /** La position en Y du coin supérieur gauche du sprite. */
    protected int y;
    /** La largeur du sprite. */
    protected int largeur;
    /** La hauteur du sprite. */
    protected int hauteur;

    /** La rotation du sprite en degrés. */
    protected double rotation;

    /**
     * Dessine le sprite.
     * 
     * @param g2d Le contexte graphique sur lequel est dessiné le sprite.
     */
    public void dessiner(Graphics2D g2d) {

        AffineTransform oldTransform = g2d.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(rotation, largeur / 2, hauteur / 2);
        g2d.transform(transform);
        g2d.drawImage(image, 0, 0, (int) largeur, (int) hauteur, null);
        g2d.setTransform(oldTransform);
    }

    /**
     * Retourne un objet Rectangle représentant la zone occupée par ce sprite
     * 
     * @return un objet Rectangle représentant la zone occupée par ce sprite
     */
    public Rectangle getRectangle() {
        return new Rectangle((int) x, (int) y, (int) largeur, (int) hauteur);
    }

    /**
     * Vérifie si ce sprite entre en collision avec un autre objet Collisionnable.
     * 
     * @param other l'autre objet Collisionnable à tester
     * @return true si ce sprite entre en collision avec l'autre objet
     *         Collisionnable, false sinon
     */
    public boolean collision(Collisionnable other) {
        Area thisArea = new Area(getRectangle());
        Area otherArea = other.getArea();
        thisArea.intersect(otherArea);
        return !thisArea.isEmpty();
    }// Fin methode

    /**
     * Retourne une aire représentant la zone occupée par ce sprite, après
     * transformation par sa position et sa rotation.
     * 
     * @return une aire représentant la zone occupée par ce sprite, après
     *         transformation par sa position et sa rotation
     */
    public Area getArea() {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(rotation, largeur / 2, hauteur / 2);
        return new Area(transform.createTransformedShape(getRectangle()));
    }// Fin methode

    /**
     * Charge l'image à partir d'un fichier et la stocke tout en vérifiant si le
     * fichier existe ou non
     */
    public void loadImage(String fileName) {
        try {
            URL url = getClass().getClassLoader().getResource(fileName);
            if (url == null) {
                url = getClass().getClassLoader().getResource("../../../ressources/" + fileName);
                if (url == null) {
                    System.err.println("Impossible de charger l'image : " + fileName);
                    return;
                }
            }
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Fin methode

    /**
     * Permet de charger une image animée comme un GIF
     * 
     * @param fileName Le nom du fichier image
     */
    public void loadGif(String fileName) {
        try {
            URL url = getClass().getClassLoader().getResource(fileName);
            if (url == null) {
                System.err.println("Impossible de charger l'image : " + fileName);
                return;
            }

            ImageInputStream inputStream = ImageIO.createImageInputStream(url.openStream());
            ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
            reader.setInput(inputStream);

            int numFrames = reader.getNumImages(true);
            Image[] frames = new Image[numFrames];

            for (int i = 0; i < numFrames; i++) {
                frames[i] = reader.read(i);
            }

            image = frames[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Fin methode
}
