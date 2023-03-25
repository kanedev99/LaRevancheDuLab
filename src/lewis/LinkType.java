package lewis;

/**
 * Type de liens entre 2 molécule.
 *
 * @author Brian Normant
 */
public enum LinkType {
    COVALENT_SIMPLE(1),
    COVALENT_DOUBLE(2),
    COVALENT_TRIPLE(3),
    IONIQUE(1),
    COORDINANCE(1);

    /**
     * Le nombre d'electron de valence demandé pour faire ce type de lien
     **/
    private int valence;

    private LinkType(int valence) {
        this.valence = valence;
    }

    public int valence() {
        return this.valence;
    }
}
