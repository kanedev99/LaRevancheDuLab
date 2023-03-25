package lewis;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import interfaces.CompareFunc;
import java.io.Serializable;

import saving.*;

/**
 * Classe Atome représente un atome simple optionnelement lié à d'autre atome.
 * un atome peut etre lié à d'autres atomes, il est dans ce cas la tete de la
 * sous branche,
 * un atome connais aussi son atome maitre, c'est l'atome qui est lié à celui
 * ci.
 * Dans le cas d'une molécule cyclique: On autorise un atome à être lié par deux
 * atomes differents.
 * Si tel est le cas
 * ... C
 * .../.\
 * ...O.->O1, ici O et C possède un lien sur O1, mais O1 n'a qu'un parent,
 * préférablement l'atome avec le plus d'electron de valence, il est alors
 * marqué comme fin de chaine cyclique, ce qui lui inderdit de remonté à son
 * parent dans un algorithme récursif
 * 
 * @author Brian Normant
 **/
public class Atome implements Iterable<Atome>, Serializable {
    /** ID unique a fin de deboggage */
    private long ID;
    /** Compteur pour les ID */
    private static long nbCreated = 0;
    /** Nombre d'electron de valence libre de l'atome */
    private int valenceLibre;
    /** Nombre d'electron de valence de base de l'atome */
    private final int valenceBase;
    /** Nom de l'atome: ex A C Cl Cu ... */
    private final String name;
    private boolean endCyclic = false;
    /** Atome pointant vers cette atome */
    private Atome pointedBy;
    /** Atome pointé par cet atome */
    protected final List<Link> pointing = new ArrayList<>();

    {
        pointedBy = null;
        ID = nbCreated;
        nbCreated++;
    }

    private List<Point> points;

    public String getSymbol() {
        return name;
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    public void resetPoints() {
        points.clear();
    }

    public List<Point> getPoints() {
        return points;
    }

    /*
     * Constructeur
     * 
     * @param name le nom de l'atome, son symbole atomique
     * 
     * @param nbValence le nombre d'element de valence de l'atome
     */
    public Atome(String name, int nbValence) {
        this.name = name;
        this.valenceBase = nbValence;
        this.points = new ArrayList<>();
    }

    /*
     * Constructeur permettant de faire une copie profonde de l'atome.
     * Normalement les obj sur lequelles seront appelle cette methode seront tous le
     * temps la tete de l'obj,
     * Le constructeur reconstruira alors reccursivement les references.
     * 
     * @param other l'atome a copier
     **/
    public Atome(Atome other) {
        this(other.name, other.valenceBase);
        this.ID = other.ID;
        if (other.isHead()) {
            pointedBy = null;
        }
        if (!other.isTail()) {
            for (var link : other.pointing) {
                this.setPointingTo(
                        new Atome(link.getAtome()),
                        link.type(),
                        link.getReversed());
            }
        }
    }

    /*
     * Ajoute un lien entre cet atome et other.
     * 
     * @param other l'autre atome
     * 
     * @param type le type de lien
     * 
     * @param reversed Est ce que le lien est inversé, voir Link.java
     * 
     * @return false en cas d'echec
     **/
    public boolean setPointingTo(Atome other, LinkType type, boolean reversed) {
        // System.out.println("Now," + this.getName() + "->" + other.getName());
        if (other.pointedBy != null) {
            other.invertPointedBy(other, null);
        }
        if (loopToMyself(this, other)) {
            this.endCyclic = true;
            other.forcePointingTo(this, type, reversed);
        } else {
            pointing.add(new Link(reversed, other, type));
            other.setPointedBy(this);
        }
        return true;
    }

    private void forcePointingTo(Atome other, LinkType type, boolean reversed) {
        pointing.add(new Link(reversed, other, type));
        other.setPointedBy(this);
    }

    /**
    */
    public void setHead() {
        if (this.isHead())
            return;
        this.invertPointedBy(this, null);
        assert this.isHead();
    }

    /**
     * Retire le lien que cet atome a avec other
     * 
     * @param other l'autre atome
     * @return false si echec
     */
    public boolean removeLinkTo(Atome other) {
        for (var oplink : pointing) {
            // On test la reference entre les atomes, c'est pourquoi on utilise == et non
            // .equals()
            if (oplink != null) {
                if (oplink.getAtome() == other) {
                    pointing.remove(oplink);
                    other.pointedBy = null;
                    return true;
                }
            }
        }
        return false;
    }

    public void setPointedBy(Atome other) {
        pointedBy = other;
    }

    public Optional<Atome> getRefToID(final long targetID) {
        for (Atome a : this)
            if (a.ID == targetID)
                return Optional.of(a);
        return Optional.empty();
    }

    public Atome getHead() {
        for (Atome a : this) {
            if (a.isHead())
                return a;
        }
        System.exit(1);
        System.out.print("ERROR failed to find the head");
        return null;
    }

    private static boolean loopToMyself(Atome myself, Atome current) {
        return current == myself || current.pointing.stream().anyMatch(
                link -> {
                    return loopToMyself(myself, link.getAtome());
                });
    }

    /**
     * Inverse le sens de la relation recursive entre cet atome et les autres.
     * c'est a dire
     * C -> O => C <- O
     * Cas simple:
     * P -> T
     * ..-> O
     * T -> P -> O
     * Cas complex:
     * PP -> P -> T
     * ../.... ->
     * ...-> O
     * Cas Cyclique
     * PP -> P -> T -> PP
     * 
     * Cette méthode sera appelé sur la tête de la molécule:
     * nil -> Head -> .. -> nil
     * ou dans le cas d'une molécule cyclique
     * nil -> Head -> .. -> LastCyclic -> nil
     * ...... \-----> .. -> /\
     * 
     * @param origin l'origin de la chaine de changement, permet d'identifier les
     *               cas cycliques.
     * @param last   le dernier atome à voir appeler cette méthode
     * @return false si Echec
     */
    public boolean invertPointedBy(Atome origin, Atome last) {
        Link link = null;
        int linkI = -1;
        // Check if we should end the recursivity:
        if (last != null) {
            // We are not in the first call
            if (isHead()) {
                System.out.println("SIGHead");
                return true;
            } else if (origin == this) {
                System.out.print("SIGCyclic\n");
                this.endCyclic = true;
                return true;
            }
        }
        // Call the method of the (was)parent to do the same process until it reach the
        // head of the subbranche.
        var result = pointedBy.invertPointedBy(origin, this);

        // Get the link of the parent to the current atom;
        // P -> T
        if (!isHead()) {
            System.out.println("[Inversing: " + pointedBy.getName() + "->" + this.getName() + "]");
            link = this.getParentLink();
            linkI = this.getParentIndex();
            assert link != null : "Cannot happen ,the parent mush have a link to this children\n";
        } else {
            return false;
        }
        // Remove the link from the parent to current
        // del P -> C
        pointedBy.pointing.remove(linkI);
        // Add a link from the current to the (was) parent
        // add P <- C
        pointedBy.pointedBy = this;

        // Modifier cet atome pour inclure les changements.
        // C -> P
        this.forcePointingTo(pointedBy, link.type(), !link.getReversed());
        this.pointedBy = last;

        return result;
    }

    public long getID() {
        return this.ID;
    }

    /*
     * Est-ce que cet atome est une queue, c'est a dire qu'il ne pointe vers rien
     * Parent -> This -> nil
     * 
     * @return true si est queue
     */
    public boolean isTail() {
        return pointing.size() == 0;
    }

    /**
     * Est ce que cet atome est un tête, c'est à dire qu'il pas pointé par un autre
     * atome
     * nil -> This -> ...Q'importe
     */
    public boolean isHead() {
        return pointedBy == null;
    }

    /**
     * Récupere le lien du parent vers cet Atome.
     * empty si le lien le parent n'existe pas ou n'a pas de lien vers cet object...
     * 
     * @return le lien du parent vers cet atome
     */
    private Link getParentLink() {
        if (isHead())
            return null;
        for (var opLink : pointedBy.pointing) {
            if (opLink.getAtome() == this) {
                return opLink;
            }
        }
        // If the program ever reach this statement, something has gone wrong
        assert false : "Failed to get the ref to the head.";
        return null;
    }

    /**
     * Récupere l'index du lien du parent vers cet Atome.
     * -1 si le lien le parent n'existe pas ou n'a pas de lien vers cet object...
     * 
     * @return l'index
     */
    private int getParentIndex() {
        if (isHead())
            return -1;
        int i = 0;
        for (var opLink : pointedBy.pointing) {
            if (opLink.getAtome() == this) {
                return i;
            }
            i++;
        }
        // If the program ever reach this statement, something has gone wrong
        assert false : "Failed to get the ref to the head.";
        return -1;
    }

    /**
     * return Total du nombre de lien entre la molécule, incluant
     */
    private int totalLiens() {
        int nb = 0;
        if (pointedBy != null)
            nb++;
        for (var e : pointing)
            nb++;
        return nb;
    }

    @Override
    public String toString() {
        var result = "";
        if (pointedBy != null) {
            result += "P:" + pointedBy.getName() + " -> ";
        }

        result += (endCyclic ? "\033[42mc\033[0m" : "") + (isHead() ? "\033[41mH\033[0m" : "") + this.getName();

        if (totalLiens() != 0) {
            result += "{";
            for (var at : pointing) {
                result += at + ", ";
            }
            result += "}";
        }
        return result;
    }

    /**
     * Permet de tester une comparation 1 a 1 de tout les elements d'une molecule
     * Si la comparaison renvoie 1, c'est a dire echec, renvoie false
     * Si la comparaison renvoie 0 pour tous les elements des molecules renvoie true
     * Si une des molécules est consomé avant l'autre (elle avait moins d'element)
     * renvoie 0
     * 
     * @param a   la tete de la molécule a
     * @param b   la tete de la molécule b
     * @param cmp la méthode pour comparé les atomes
     * @return 0 ou 1
     */
    public static boolean testOneToOne(Atome a, Atome b, CompareFunc<Atome> cmp) {
        Iterator<Atome> itA = a.iterator();
        Iterator<Atome> itB = b.iterator();
        Atome currA, currB;
        while (itA.hasNext() && itB.hasNext()) {
            currA = itA.next();
            currB = itB.next();
            if (!cmp.compare(currA, currB))
                return false;
        }
        if (itA.hasNext() == itB.hasNext()) {
            return true;
        } else {
            return false;
        }
    }

    public String getName() {
        return this.name + this.ID;
    }

    protected String name() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Atome atome) {
            if (this.pointing.size() != atome.pointing.size())
                return false;
            if (this.pointedBy == null)
                if (atome.pointedBy != null)
                    return false;
            if (this.pointedBy != null)
                if (atome.pointedBy == null)
                    return false;
            boolean sameParent;
            if (this.isHead()) {
                sameParent = true;
            } else {
                sameParent = this.pointedBy.name.equals(atome.pointedBy.name);
            }
            if (!sameParent)
                return false;
            if (this.isTail()) {
                // both atome have the same parent and not child.
                return true;
            } else {
                for (int i = 0; i < this.pointing.size(); i++) {
                    if (!this.pointing.get(i).equals(atome.pointing.get(i))) {
                        System.out.print(this.pointing.get(i) + " Is not equal to" + atome.pointing.get(i) + " \n");
                        return false;
                    }
                }
            }
            return true;
        } else
            return false;
    }

    // Implementer Iterable
    @Override
    public Iterator<Atome> iterator() {
        return new AtomeItr(this);
    }

    /**
     * Ajouter quelque molecules simple default dans le dossier ressource.
     */
    static {
        var H2O = new Atome("O", 2);

        H2O.setPointingTo(new Atome("H", 1), LinkType.COVALENT_SIMPLE, false);
        H2O.setPointingTo(new Atome("H", 1), LinkType.COVALENT_SIMPLE, false);

        new MolSaver().saveAsRessource(new MolDat(H2O, "H2O", 1));
    }

}
