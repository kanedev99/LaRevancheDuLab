package chimie;

import java.util.Map;

/**
 * Enumére le nom des molécules composées 
 * @author Maysa Zahi
 *
 */
public class MolData {
	private String name;
	private Map<String, Integer> composition;

	/**
	 * Crée une liste de molécules contenant des noms de molécules et leur composition atomique.
	 * @param name Le nom de la molécule
	 * @param composition La composition atomique de chaque molécule
	 */
	public MolData(String name, Map<String, Integer> composition) {
		this.name = name;
		this.composition = composition;
	}

	public String getName() {
		return name;
	}

	public Map<String, Integer> getComposition() {
		return composition;
	}
}
