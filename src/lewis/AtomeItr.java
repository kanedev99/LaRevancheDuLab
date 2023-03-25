package lewis;

import java.util.Iterator;

/**
 * Classe AtomeItr, permet de gerer un iterateur sur Atome
 * Renvoi tous les atomes lie a un atomes.
 * FIXME probleme mineur, les fin de molecules cycliques seront retourne 2 fois
 * 
 * @author Brian Normant
 */
public class AtomeItr implements Iterator<Atome> {
    private int index = 0;
    private AtomeItr currChild;
    private final Atome me;
    private boolean selfReturned = false;

    public AtomeItr(Atome me) {
        this.me = me;
    }

    @Override
    public boolean hasNext() {
        if (me.isTail()) {
            return !selfReturned;
        } else {
            return !selfReturned || index != me.pointing.size();
        }
    }

    @Override
    public Atome next() {
        if (me.isTail()) { // Tail, so only show itself
            selfReturned = true;
            return me;
        }

        if (currChild != null) {
            if (currChild.hasNext()) {
                return currChild.next();
            } else {
                if (index == 0) {
                    currChild = new AtomeItr(me.pointing.get(index).getAtome());
                    index++;
                    return currChild.next();
                } else if (index == me.pointing.size()) {
                    this.selfReturned = true;
                    return me;
                } else {
                    currChild = new AtomeItr(me.pointing.get(index).getAtome());
                    index++;
                    return currChild.next();
                }
            }
        } else {
            // First time this next() is called, so we get the first child of this node
            currChild = new AtomeItr(me.pointing.get(0).getAtome());
            index++;
            return currChild.next();
        }
    }
}
