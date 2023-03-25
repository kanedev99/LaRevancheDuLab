package saving;

import java.io.*;
import java.nio.*;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URL;

/**
Classe MolSaver, se charge de gerer la sauvegarde des molecules.
Permet aussi de charger toutes les molecules pre construite depuis le dossier ressources

@author Brian Normnat
**/
public class MolSaver extends Saver<MolDat> {
    /** Le dossier ou les molecules sont sauvegarder */
    private static final String DIR = Saver.HOME + "/moléculeLewis";
    private static final String RDIR;
    static {
        URL url = MolSaver.class.getClassLoader().getResource("defaultMol");
        if (url == null) {
            url = MolSaver.class.getClassLoader().getResource("../../../ressources/defaultMol");
            if (url == null) {
                System.err.println("Impossible de trouver le dossier defaultMol dans ./ressource");
            }
        }
        RDIR = url.getPath();
        System.out.println("ressource path is " + RDIR);
    }

    /* Constructeur genereric, comme new Random() **/
    public MolSaver() {
    }

    /**
     * Sauvegarder une molecule
     * 
     * @param data la molecule a sauvegarder
     * @param name le nom a donner au fichier
     * @return si la sauvegarde a reussie
     */
    @Override
    public boolean save(MolDat data, String name) {
        var b = write(data);
        return Saver.save(b, name, DIR);
    }

    /**
     * Sauvegarde une molecule en tant que ressource par default, c'est a dire dans
     * le dossier ressource. Si le nom est le meme, cette molecule supprimera
     * l'original.
     * 
     * @param data la molecule a sauvegarder
     * @param name le nom du fichier
     */
    public void saveAsRessource(MolDat data) {
        var b = write(data);
        Saver.forceSave(b, data.getName(), RDIR);
    }

    /**
     * b
     */
    public boolean save(MolDat data) {
        var b = write(data);
        return Saver.save(b, data.getName(), DIR);
    }

    /**
     * b
     */
    public void forceSave(MolDat data) {
        var b = write(data);
        Saver.forceSave(b, data.getName(), DIR);
    }

    /**
     * Lis touts les fichiers sauvegarde dans le dosssier.
     * 
     * @return une liste optionnel des fichiers
     */
    @Override
    public Optional<Stream<MolDat>> loadAll() {
        Optional<Stream<ByteBuffer>> result = Saver.loadAll(DIR);
        if (result.isPresent()) {
            return Optional.of(result.get().map(b -> this.read(b)));
        } else
            return Optional.empty();
    }

    /**
     * Transforme une MolDat en données binnaires
     * 
     * @param dat la MolDat
     * @return les données binaires équivalentes
     */
    @Override
    public ByteBuffer write(MolDat dat) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(dat);
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("ERROR : Echec dans la sauvegarde de la molécule");
            System.exit(1);
        }
        return null;
    }

    /**
     * Transforme des données binaire en MolDat
     *
     * @param raw les donnés binaires
     * @return
     */
    @Override
    public MolDat read(ByteBuffer raw) {
        Object read = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(raw.array());
                ObjectInputStream in = new ObjectInputStream(bis)) {
            read = in.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("ERROR : Echec dans le chargement de la molécule");
            System.exit(1);
        }
        return (MolDat) read;
    }
}
