package utils;

/**
 * Classe Pair permet de pairer des couleurs et des MolTypes Les Pairs sont
 * finals et de taille fixes
 * 
 * @author Brian Normant
 */
public class Pair<K, E> {
	private final K x;
	private final E y;

	/**
	 * Constructeur Les deux elements de la pair peuvent etre de type different
	 * 
	 * @param x La premiere valeur
	 * @param y La deuxieme valeur
	 **/
	public Pair(K x, E y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter de la premiere valeur
	 * 
	 * @return la deuxieme valeur
	 **/
	public K getX() {
		return this.x;
	}

	/**
	 * Getter de la deuxieme valeur
	 * 
	 * @return la deuxieme valeur
	 **/
	public E getY() {
		return this.y;
	}

}
