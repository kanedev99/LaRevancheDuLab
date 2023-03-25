package chimie;

import java.util.Optional;

/**
 * La classe ReactionDat est une simple dataclass répresentant une réaction
 * chimique.
 * Toute les réactions chimiques suivront ce pattern:
 * R1 + R2 => P1
 * ou bien
 * R1 + R2 => P1 + P2
 * 
 * @author Brian Normant
 * @since sprint1
 **/
public class ReactionDat {
    /**réactif */
    private final MolType R1, R2;
    /**produits optionnel */
    private final Optional<MolType> P1, P2;

    /**
     * Contructeur pour faire une reaction de type R1 + R2 => P1 + P2
     * @param R1 Réactif 1
     * @param R2 Réactif 2
     * @param P1 Produit 1
     * @param P2 Produit 2
     **/
    public ReactionDat(MolType R1, MolType R2, MolType P1, MolType P2) {
        this.R1 = R1;
        this.R2 = R2;
        this.P1 = Optional.of(P1);
        this.P2 = Optional.of(P2);
    }

    /**
     * Contructeur pour faire une reaction de type R1 + R2 => P1
     * @param R1 Réactif 1
     * @param R2 Réactif 2
     * @param P1 Produit 1
     **/
    public ReactionDat(MolType R1, MolType R2, MolType P1) {
        this.R1 = R1;
        this.R2 = R2;
        this.P1 = Optional.of(P1);
        this.P2 = Optional.empty();
    }

    /**
     * Constructeur pour faire une réaction intermédiare, qui ne contient que les réactifs
     * @param R1 Réactif 1
     * @param R2 Réactif 2
     **/
    public ReactionDat(MolType R1, MolType R2) {
        this.R1 = R1;
        this.R2 = R2;
        this.P1 = Optional.empty();
        this.P2 = Optional.empty();
    }

    /**
     * @return Si la réaction n'a qu'un produit
     **/
    public boolean singleProduct() {
        return P2.isEmpty();
    }

    public MolType getP1() {
        return P1.get();
    }

    public MolType getP2() {
        return P2.get();
    }

    /**
     * Comparaison d'une réaction, compare uniquement les réactifs sans prendre en compte l'ordre
     * @param o L'oject avec lequel comparé cet objet
     * @return Si les 2 objets sont considérer égaux
     **/
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof ReactionDat other) {
            return this.R1 == other.R1 &&
                    this.R2 == other.R2 ||
                    this.R1 == other.R2 &&
                            this.R2 == other.R1; // &&
            // this.P1 == other.P1 &&
            // this.P2.equals(other.P2);
        } else
            return false;
    }//Fin methode
}
