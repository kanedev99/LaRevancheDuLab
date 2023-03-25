package jeuPrincipal;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Optional;

import chimie.MolType;
import interfaces.*;
import utils.*;
import application.ApplicationPrincipale6;

/**
 * La ;
 * import utils.Pairclasse Molecule crée et dessine une molecule qui peut tirer
 * des projectiles
 * Elle represente l'espace physique de la molécule dans la scene ainsi
 * que ses propriété chimiques.
 * la position serat décrite avec un vecteur dont l'origine est placé en haut a
 * gauche de la scene
 * Cette classe contient aussi des méthodes statique pour gerer un ensemble de
 * molécules afin decharger la classe SceneDanimation
 * 
 * @author Brian Normant
 */
public class Molecule implements Collisionnable, Dessinable {
    /** Liste des positions ou il y a déja une molécule */
    private static List<Pair<Integer, Integer>> usedPos = new ArrayList<>();

    /** Taille d'une molécule */
    private static final int RAYON = 45; // En pixel
    /** Combien de pixel doit séparer chaque molécule **/
    private static final int ECART_MOLECULES = 20;
    private static final int SEP_SIZE = RAYON * 2 + 20;
    /** Géométrie d'une molécule. */
    private static final Ellipse2D.Double geom = new Ellipse2D.Double(
            RAYON / 2.0, RAYON / 2.0,
            RAYON, RAYON);

    /** Positions x, y de la molécule **/
    private int x, y;
    /** type chimique de la molécule */
    private final MolType typeMolecule;
    /**le nom qui apparait sur la molécule**/
    private String nom;
    /** La composition de la molécule**/
    Map<String, Integer> composition = new HashMap<>();
    

    /**
     * Contructeur privée de Molécule. Puisqu'on ne veut pas faire apparaitre deux
     * molécules au même endroit. il faudra utiliser la méthode newMolécule pour
     * obtenir une nouvelle molécule
     * Le Type de la Molécules est choisie au hasard
     * 
     * @param x position x
     * @param y position y
     */
    private Molecule(int x, int y) {
        this.x = x;
        this.y = y;
        this.typeMolecule = MolType.values()[(new Random()).nextInt(MolType.values().length - 1)];
        this.nom= this.typeMolecule.randNom();
    }

	/**
     * Créateur de molécule, essai de placer une molécule à la position (x y)
     * revoie un empty si une molécule existe déja à cette endroit
     * 
     * @param x la position X
     * @param y la position Y
     * @return optionellement une molécule
     */
    public static Optional<Molecule> newMolecule(int x, int y) {
        if (usedPos.stream().anyMatch(
                pos -> pos.getX() == x && pos.getY() == y || x < 0 || x > SceneDanimation.DIMX)) {
            return Optional.empty();
        }
        usedPos.add(new Pair<Integer, Integer>(x, y));
        return Optional.of(new Molecule(x, y));
    }//

    /**
     * Dessine la molécule
     * 
     * @param g2d le contexte graphique
     */
    @Override
    public void dessiner(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(this.typeMolecule.color());
        g2d.translate(
                this.x,
                this.y);
        g2d.fill(geom);
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.BLACK);
        g2d.drawString(nom, 0, 0);
        g2d.draw(geom);

    } //

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }

    /**
     * Avancer vers la droite si true, avancer vers la gauche false
     * @ param isRight vers la droite
     */
    public void move(boolean isRight) {
        this.x += (isRight ? 1 : -1) * ECART_MOLECULES;
    }

    /**
     * renvoi l'aire occupé par la molécule
     * 
     * @return l'aire de la molécule
     */
    public Area getArea() {
        var a = new Area(geom);
        var t = new AffineTransform();
        t.translate(this.x, this.y);
        a.transform(t);
        return a;
    }//

    /**
     * La molécule descent d'un niveau et s'approche du joueur
     **/
    public void descend() {
        this.y += RAYON;
    }//

    /**
     * La molécule tire un nouveau projectile
     * 
     * @return le projectile tirer par la molécule
     */
    public Projectile tirer() {
        return new Projectile(
                this.x + RAYON,
                this.y + RAYON + RAYON / 2,
                this.typeMolecule,
                false);
    }//

    /**
     * s
     * Créer plusieurs molécules dont les positions sont entre Xmax et Ymax
     * La création des molécules suivra un pattern de plusieurs pyramide:
     * 
     * ---| *****
     * | *** ***
     * -| * - *
     * |
     * |
     * | /\
     * 
     * @param nbPyr le nombre de molécule a creer
     * @param Xmax  la borne x superieur
     * @param Ymax  la borne y superieur
     * @return une List de molécule
     */
    public static List<Molecule> makeMolecules(int nbPyr, int xMax, int yMax) {
        ArrayList<Molecule> result = new ArrayList<>();
        for (int i = 0; i < nbPyr; i++) {
            Pyr pyr;
            if (!ApplicationPrincipale6.REDIM) {
                pyr = new Pyr(
                        SEP_SIZE * (new Random().nextInt(xMax / SEP_SIZE - 1)),
                        SEP_SIZE * (new Random().nextInt(yMax / SEP_SIZE)),
                        (int) (Math.round(2 * Jeu.getFacteur())));// (new Random().nextInt(1,
                                                                  // 3)*(int)(Math.round((Jeu.getFacteur())))));
            } else {
                pyr = new Pyr(
                        SEP_SIZE * (new Random().nextInt(xMax / SEP_SIZE)),
                        SEP_SIZE * (new Random().nextInt(yMax / SEP_SIZE)),
                        (int) (Math.round(2 * Jeu.getFacteur())));// (new Random().nextInt(
                                                                  // 3)*(int)(Math.round((Jeu.getFacteur())))));

            }
            var molPyr = new Molecule[0];
            molPyr = pyr.genMols(molPyr);
            for (var mol : molPyr) {
                if (mol != null)
                    result.add(mol);
            }
        }

        return result;
    }//

    /**
     * Verifie si un groupe de molécule doit rebondir contre un mur
     * 
     * @param mols les molecules a checker
     * @param Xmax les murs sont 0 < x < Xmax
     * @return Est ce que les molécules ont touché le mur
     */
    public static boolean doBounce(List<Molecule> mols, int Xmax) {
        return mols.stream().anyMatch(
                mol -> mol.getPosX() < 0 || mol.getPosX() > Xmax - RAYON * 2);
    }//

    /**
     * Verifier si les molécules ont atteinte le joueur.
     * Méthode de sous traitance
     * 
     * @param y position du joeur
     * @return si la molécule la plus basse est au niveau du joueur
     */
    public static boolean lowestLowerThan(List<Molecule> mols, int y) {
        // int lowest = 5000;
        // for (var mol : mols) {
        // lowest = (mol.y < lowest) ? mol.y : lowest;
        // }
        // System.out.println("lowest = " + lowest);
        return mols.stream().anyMatch(
                mol -> mol.getPosY() >= y);
    }//

    /**
     * le getter de la propriété typeMolecule
     * 
     * @return le MolType de cette molécule
     */
    public MolType getMolType() {
        return typeMolecule;
    }//

    /**
     * Nettoye les positions déja utilisees
     */
    public static void clean() {
        usedPos.clear();
    }//

    /**
     * méthode qui renvoie le drop avec le Moltype de la molécule et sa position
     * 
     * @return le Drop avec le MolType de la molécule et sa position
     */
    public Drop genDrop() {
        return new Drop(this.x, this.y, this.typeMolecule);
    }//
    
    
    /**
     * Classe locale qui permet de créer des pyramides pour l'apparition des
     * molécules
     * 
     * @author Brian Normant
     */
    private static class Pyr {
        /** Position de la molécule en tête de la pyramide **/
        private int x, y;
        /** Dans quel rangé de la pyramide est le generateur actuellement **/
        private int depth;
        /** le nombre de rangé de la pyramide **/
        private final int maxDepth;

        /** l'index de la prochaine place du tableau vide **/
        // Il y a probablement une manière de le determiné avec depth et maxdepth mais
        // flemme
        private int index;

        Pyr(int x, int y, int maxDepth) {
            this.x = x;
            this.y = y;
            this.depth = 1;
            this.maxDepth = maxDepth;
        }

        /**
         * Méthode récursive pour generé une liste molécule.
         * * 1 | 1
         * *** 3 | 4
         * ***** 5 | 9
         * ******* 7 (2n+1) | 16
         * ********* 9 | 25 (n*n)
         * 
         * @param prev la pyramide généré precedement taille de 0 si appel de la méthode
         * @return un tableau contenant les molécules placé au bon endroit.
         */
        Molecule[] genMols(Molecule[] prev) {
            if (prev.length == 0) {
                prev = new Molecule[maxDepth * maxDepth];
                index = 0;
            }
            // Remplir la ligne actuelle de la pyramide
            // Centre
            var temp = newMolecule(
                    this.x,
                    this.y - (this.depth - 1) * SEP_SIZE);
            if (!temp.isEmpty()) {
                prev[index] = temp.get();
                index++;
            }
            for (int i = 1; i < depth; i++) {
                temp = newMolecule( // Droite
                        this.x + i * SEP_SIZE,
                        this.y - (this.depth - 1) * SEP_SIZE);
                if (!temp.isEmpty()) {
                    prev[index] = temp.get();
                    index++;
                }
                temp = newMolecule( // Gauche
                        this.x + i * -SEP_SIZE,
                        this.y - (this.depth - 1) * SEP_SIZE);
                if (!temp.isEmpty()) {
                    prev[index] = temp.get();
                    index++;
                }
            }
            if (depth == maxDepth)
                return prev;
            depth++;
            return this.genMols(prev);
        }//

    }

}
