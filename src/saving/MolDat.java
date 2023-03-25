package saving;

import java.io.*;
import java.util.ArrayList;
import lewis.*;

/**
 * Cette class represente les données d'une molécule, elle contient donc tous ses atomes ainsi  
 * que les liens entre eux.
 * @author Brian Normant
 **/
public class MolDat implements Serializable {
    /**
     * Tête de l'atome, puisque les atomes sont des structures referentielle, la
     * tête à acces au reference de tous les atomes
     */
    private Atome head;
    /** Nom de la molécule, "" vide si c'est une molecule destiner a etre comparé */
    private String name;
    /**
     * La difficulte a laquelle est associe cette molecule
     * -1 pour test
     * 1 pour facile
     * 2 pour moyen
     * 3 pour difficile
     */
    private int diff;

    public String getName() {
        return this.name;
    }

    public Atome getHead() {
        return this.head;
    }

    public int getDiff() {
        return this.diff;
    }

    /*
     * Constructeur
     * 
     * @param head la tete de l'atome
     */
    public MolDat(Atome head) {
        this.head = head;
    }

    /*
     * Constructeur
     * 
     * @param head la tete de l'atome
     * 
     * @param name le nom de la molécule.
     */
    public MolDat(Atome head, String name) {
        this.head = head;
        this.name = name;
    }

    /*
     * Constructeur complet
     * 
     * @param head la tete de l'atome
     * 
     * @param name le nom de la molécule.
     * 
     * @param diff la difficulte a laquelle est associe cette molecule
     */
    public MolDat(Atome head, String name, int diff) {
        this.head = head;
        this.name = name;
        this.diff = diff;
    }

    /*
     * equals, compare l'egalite entre 2 atomes.
     * 
     * @param o l'autre object
     * 
     * @return si o et this sont egaux
     **/
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof MolDat moldat) {
            // For each individual atome in moldat.head
            // Make a clone of moldat
            // make this at in the clone the head
            // Compare 1 by 1 every atome you can reach by following this.head and
            // clone.head
            for (Atome at : moldat.head) {
                Atome clone = new Atome(moldat.head);
                Atome futureHead;
                var temp = clone.getRefToID(at.getID());
                assert !temp.isEmpty() : "ERROR, Failed to find HEAD in equals of MolDat\n";
                futureHead = temp.get();
                futureHead.setHead();
                System.out.print("\033[4m Comparing this \033[0m\n");
                for (Atome a : this.head)
                    System.out.println("\t" + a);
                System.out.print("\033[4m To this \033[0m\n");
                for (Atome a : futureHead)
                    System.out.println("\t" + a);
                if (Atome.testOneToOne(this.head, futureHead, (a, b) -> a.equals(b)))
                    return true;
                System.out.print("One more iteration\n");
            }
        }
        return false;
    }
}
