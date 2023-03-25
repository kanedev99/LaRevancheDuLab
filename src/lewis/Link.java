package lewis;

import lewis.Atome;
import java.io.Serializable;

/**
 * Lien entre 2 atome, contient l'atome qui est pointé, et le type du lien.
 * 
 * @author Brian Normant
 **/
public class Link implements Serializable {
    /**
     * Est ce que la polarité du lien suit la recursivité des molécule?
     * cad:
     * C -> O sachant que le code peut lié C a O ou l'inverse, le followLink
     * represente si on suit le sens de la fleche
     * O <- C
     **/
    private boolean reversed;
    private LinkType type;
    private Atome atome;

    /**
     * Constructeur d'un lien,
     */
    public Link(boolean reversed, Atome atome, LinkType type) {
        this.reversed = reversed;
        this.atome = atome;
        this.type = type;
    }

    @Override
    public String toString() {
        var result = "";
        result += "(" + this.type.name() + ")";
        result += "->" + atome.getName();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Link link) {
            return this.type.equals(link.type) &&
                    this.atome.name().equals(link.atome.name()) &&
                    this.reversed == link.reversed;
        } else {
            return false;
        }
    }

    public Atome getAtome() {
        return atome;
    }

    public LinkType type() {
        return type;
    }

    public boolean getReversed() {
        return reversed;
    }
}
