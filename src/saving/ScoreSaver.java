package saving;

import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Class Saver spécialiser pour sauvegarder les scores.
 * 
 * @author Brian Normant
 * @since sprint1
 **/

public class ScoreSaver extends Saver<ScoreDat> {
    /** sous dossier ou sauvegarder les score */
    private static final String DIR = Saver.HOME + "/score";

    /** Constructeur par ce que java refuse que mixer l'hériatage avec static */
    public ScoreSaver() {
    }

    /**
     * Sauvegarde un score dans un fichier du nom name
     * 
     * @param data le score
     * @param name le nom du fichier
     * @return si l'operation à été compléter correctement
     */
    @Override
    public boolean save(ScoreDat data, String name) {
        var b = write(data);
        return Saver.save(b, name, DIR);
    }

    /**
     * Charge tout les scores déja sauvegardé
     * 
     * @return une liste optionnel contenant tous les scores
     */
    @Override
    public Optional<Stream<ScoreDat>> loadAll() {
        Optional<Stream<ByteBuffer>> result = Saver.loadAll(DIR);
        if (result.isPresent()) {
            return Optional.of(result.get().map(b -> this.read(b)));
        } else
            return Optional.empty();
    }

    /**
     * Comment convertir une ScoreDat en binaire: NAME/SCORE EDF
     * 
     * @param dat l'object java
     * @return la representation binaire de cet objet
     **/
    @Override
    public ByteBuffer write(ScoreDat dat) {
        var str = dat.name + "/" + dat.score;
        return ByteBuffer.wrap(Saver.convert(str));
    }

    /** le patern pour matcher le texte */
    private static final Pattern PATTERN = Pattern.compile("(\\D+)(\\/)(\\d+)");

    /**
     * Comment recuperer du binaire et le transformer en ScoreDat
     * 
     * @param raw la representation binaire de l'objet
     * @return l'objet java
     **/
    @Override
    public ScoreDat read(ByteBuffer raw) {
        String str = Saver.convert(raw.array());
        var m = PATTERN.matcher(str);
        if (!m.find()) {
            System.out.println("Failed to match the expression: " + str);
            System.exit(-1);
        }
        return new ScoreDat(m.group(1), Integer.parseInt(m.group(3)));
    }
}
