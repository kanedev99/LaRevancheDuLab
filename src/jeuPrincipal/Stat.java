package jeuPrincipal;

import java.util.HashMap;
import java.util.stream.Collectors;
import chimie.*;

/**
 * Classe Statique Stat, retient les statistque associé a la partie courante.
 * Permet de communiquer plus efficacement entre les differentes classe de
 * jeuPrincipal
 * 
 * @author Brian Normant
 */

public class Stat {
    public static HashMap<MolType, Integer> nbTypeNeutr = new HashMap<>();
    public static int tirManquees = 0;
    public static int tempsBonus = 0;

    public static double acceleration = 0;
    public static double vitesse = 0;
    public static double friction = 0;
    public static double affliction = 0;
    /** Le score courant du joueur **/
    public static int score = 0;
    public static int totalVague = 0;
    public static int vague = 0;

    public static boolean lost = false;

    /**
     * Calcul le total de molécule neutralisé
     * 
     * @return nombre total de molécule neutralisé
     */
    public static int nbTypeNeutrTotal() {
        return nbTypeNeutr
                .values()
                .stream()
                .collect(Collectors.summingInt(x -> x));
    }

    /**
     * Incremente de 1 le type de molécule tuée
     * 
     * @param type le type
     */
    public static void incType(MolType type) {
        nbTypeNeutr.put(
                type,
                nbTypeNeutr.get(type) + 1);
    }

    public static int getT(MolType type) {
        return nbTypeNeutr.get(type);
    }

    /**
     * Remets toutes les valeur à zero
     */
    public static void reset() {
        for (MolType t : MolType.values())
            nbTypeNeutr.put(t, 0);
        tirManquees = 0;
        tempsBonus = 0;
        acceleration = 0;
        vitesse = 0;
        friction = 0;
        affliction = 0;
        score = 0;
        totalVague = 0;
        vague = 0;
        Stat.lost = false;
    }

    static {
        for (MolType t : MolType.values())
            nbTypeNeutr.put(t, 0);
    }
}
