package chimie;

import java.awt.Color;
import java.util.Random;

/**
 * Enemuration de tous les types de molécules, le jeu principal ne simulera pas
 * les réaction entre molécules et molécules mais des réaction plus générique
 * En utilisant les grands types de molécules inorganique on symplifie
 * grandement les réactions possible.
 * Mémorise les couleurs associés à chaque type de molécule
 * Mémorise les noms associés à chaque type de molécule
 * 
 * @author Brian Normant
 **/
public enum MolType {
    ACIDE(Color.RED, new String[] { "HCl" }), // HCl
    BASE(Color.BLUE, new String[] { "KOH" }), // KOH
    COMBUSTIBLE(new Color(255, 102, 0), new String[] { "CH4", "H2" }), // CH4 H2
    COMBURANT(new Color(153, 153, 0), new String[] { "O2", "Cl2" }), // O2
    EAU(Color.CYAN, new String[] { "H2O" }), // H2O
    METAL(Color.GRAY, new String[] { "Cu", "Fe", "Pl", "Co", "Ni" }), // Fe Cu
    OXYDANT(Color.YELLOW, new String[] { "O+", "F+", "Cl+" }), //
    REDUCTEUR(new Color(255, 51, 204), new String[] { "Na-", "Li-" }), //
    SEL(new Color(153, 51, 255), new String[] { "NaCl" }), // NaCl
    NOBLE(new Color(221, 95, 232), new String[] { "He", "Ne" }); // He Ne
    /** couleur associé aux types de molécule*/
    private final Color color;

    /** liste des noms aux types de molécule*/
    private final String[] noms;

    /**
    Contructeur des élements de l'enum
    @param color couleur du type 
    @param noms noms possible pour le type
    */
    MolType(Color color, String[] noms) {

        this.color = color;
        this.noms = noms;
    }

    public Color color() {
        return this.color;
    }

    /**
    Nom aléatoire pour le type de molécule
    */
    public String randNom() {
        return this.noms[new Random().nextInt(noms.length)];
    }
}
