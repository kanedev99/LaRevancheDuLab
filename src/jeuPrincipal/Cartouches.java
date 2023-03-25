package jeuPrincipal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chimie.MolType;
import interfaces.Dessinable;
import interfaces.Selectionnable;

/**
 * classe qui montre les types de cartouche et le nombre de munitions
 * 
 * @author Kepler Paul-Émile
 *
 */
public class Cartouches {
    /**
     * le rectangle qui montre les munitions et le rectangle qui représente le
     * numéro de clavier à presser pour utiliser une munition de ce type
     **/
    private Rectangle2D.Double rect, rectCopie, rectNo;
    /** le nombre de segments horizontale égale de l'écran **/
    private final int SEPARATION = 10;
    /** la largeur de la scene qui appelle cette classe **/
    private int largeurEcran;
    /** la hauteur de la fenetre qui appelle cette classe **/
    private int hauteurEcran;
    /** la position du grand rectangle **/
    private int positionRecty;
    /** la postion du petit rectangle **/
    private int positionRectNoX;
    /** la larguer du bloc **/
    private int largeurBloc;
    /** tableau du nom de chaque munitions **/
    private final String[] nom = { "ACIDE", "BASE", "COMBUSTIBLE", "COMBURANT", "EAU", "METAL", "REDUCTEUR", "OXYDANT",
            "SEL" };
    private List<Carte> nosCartes = new ArrayList<Carte>();
    private MolType[] type = MolType.values();

    /**
     * Constructeur qui intialise la largeur et l'hauteur de l'écran et crée les
     * proportions des formes
     * 
     * @param largeurEcran la composante x de la fenetre
     * @param hauteurEcran la composante y de la fenetre
     */
    public Cartouches(int largeurEcran, int hauteurEcran) {
        this.largeurEcran = largeurEcran;
        this.hauteurEcran = hauteurEcran;
        creerLesProportions();
    }

    /**
     * methode qui crée les proportions des formes représentant les cartouches
     */
    public void creerLesProportions() {
        largeurBloc = (int) (largeurEcran / SEPARATION);
        positionRecty = (int) (hauteurEcran - largeurBloc);
        positionRectNoX = largeurBloc / 5;
        rect = new Rectangle2D.Double(0, positionRecty, largeurBloc, largeurBloc);
        rectNo = new Rectangle2D.Double(largeurBloc - positionRectNoX, positionRecty, positionRectNoX, positionRectNoX);
        // the new shit
        for (int i = 0; i < SEPARATION - 1; i++) {
            nosCartes.add(new Carte(largeurBloc, positionRecty, positionRectNoX, type[i]));

        }

    }

    /**
     * la methode qui dessine les affiche pour les cartouches
     * 
     * @param g2d       le contexte graphique
     * @param munitions le hashmap contenant le nombre de munitions pour chaque type
     */
    public void dessiner(Graphics2D g2d, Map<MolType, Integer> munitions) {
        Graphics2D g2dCopie = (Graphics2D) g2d.create();

        MolType[] type = MolType.values();

        g2dCopie.setFont(new Font("Arial", Font.BOLD, 10));
        nosCartes.forEach(carte -> {
            carte.dessiner(g2dCopie);
            g2dCopie.setColor(Color.BLACK);
            g2dCopie.drawString(munitions.get(carte.getType()) + " ", 0, positionRecty + 10);

        }

        );

        for (int i = 0; i < munitions.size() - 1; i++) {

            g2dCopie.drawString(munitions.get(type[i]) + " ", 0, positionRecty + 10);
            g2dCopie.translate(largeurBloc, 0);
        }

    }

    /**
     *
     * @param xPix
     * @param yPix
     * @return
     */
    public Carte reconnaitreClick(double xPix, double yPix) {
        Carte carteTouche = null;
        List<Carte> cartel = new ArrayList<Carte>();
        nosCartes.forEach(carte -> {
            if (carte.contient(xPix, yPix)) {

                cartel.add(carte);
            }
        });
        if (!cartel.isEmpty()) {
            carteTouche = cartel.get(0);
            
        }
        return carteTouche;

    }// Fin methode

}
