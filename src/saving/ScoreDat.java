package saving;

import java.lang.Integer;

/**
 * DataClass representant un score, il contient un nom et un score Cette classe
 * est immutable Character iterdit pour le nom: /
 * 
 * @author Brian Normant
 * @since sprint1
 */
public class ScoreDat implements Comparable<ScoreDat> {
    /** Nom du joueur */
    public final String name;
    /** Score du joueur */
    public final int score;

    /**
     * Contructeur de score, Le score doit être positif Le nom peut contenir tous
     * les caractère sauf /
     * 
     * @param name  le nom du joueur
     * @param score le score du joueur
     */
    public ScoreDat(String name, int score) {
        if (name.contains("/")) {
            System.out.println("Le caractère \'/\' est interdit dans les noms des scores");
            System.exit(-2);
        }
        this.name = name;

        if (score < 0) {
            System.out.println("Le score ne peut pas être négatif");
            System.exit(-2);
        }
        this.score = score;
    }

    /**
     * Comparable demande cette méthode
     * Comparer le score de deux joueurs
     * 
     * @param s autre score
     * @return 0 si egaux, 1 Si superieur 0 si inferieur
     */
    public int compareTo(ScoreDat s) {
        return Integer.valueOf(this.score).compareTo(s.score);
    }

    /**
     * ToString, renvoie une reprentation lisible du contenu de l'object
     * 
     * @return chaine de caractere representant le score
     */
    @Override
    public String toString() {
        String string;
        string = ". " + name + "     " + score;
        return string;
    }
}
