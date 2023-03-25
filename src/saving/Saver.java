package saving;

import java.nio.ByteBuffer;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.nio.charset.StandardCharsets;

/**
 * La classe Saver est une classe squelette permettant d'unifier le systeme de
 * sauvegarde de cette application This class handle file creater by child
 * classes and put them in the appropriate folder The file can be of any type
 * and will be saved together with a different name. Cette classe verifie que le
 * nom choisi est disponible Cette méthode est entierement statique puisque
 * qu'instancier un object de type saver est inutile, 2 saver ne peuvent pas
 * exisiter Tous les textes sont encodées et décoder en UTF8
 * 
 * @author Brian Normant
 * @since sprint1
 **/
public abstract class Saver<T> {
    /** dossier home de revanchedulab */
    protected static final String HOME = System.getProperty("user.home") + "/RevancheDuLab";

    /**
     * Méthode de la classe fille permettant de sauvegarder un object
     * 
     * @param data the object to save
     * @param name the name of the file
     * @return Si l'object a bien été sauvegarder
     **/
    public abstract boolean save(T data, String name);

    /**
     * Méthode de la classe fille permettant de charger tous les objects ayant été
     * sauvegardé
     * 
     * @return Les object ayant été precedament sauvegarder
     **/
    public abstract Optional<Stream<T>> loadAll();

    /**
     * Méthode qui transformer des données Runtime en donné binaire qui pourront
     * plus tard etre converti
     * 
     * @param data les données à convertir
     * @return les donnés binnaire correspondante
     **/
    public abstract ByteBuffer write(T data);

    /**
     * Méthode inverse de write, elles doivent être inplementé tel que data ==
     * read(write(data)) et raw == write(read(raw))
     * 
     * @param raw les données binaires à convertir
     * @return un object utilisable par java representant ces données
     **/
    public abstract T read(ByteBuffer raw);

    /**
     * Cette methode utilise un buffer contenant les données à sauvegarder (binaires
     * ou textes) et les écrits dans un file avec le nom nom
     * 
     * @params content Buffer des données à sauvegarder
     * @params name Nom du fichier sur lequel sauvegarder
     * @return si les données ont bien été sauvegardé Brian Normant
     **/
    protected static boolean save(ByteBuffer content, String name, String DIR) {
        if (allFilesNames(DIR).isPresent()) // If a file of the same name is already here, skip the save
            if (allFilesNames(DIR).get().anyMatch(f -> f.equals(DIR + "/" + name)))
                return false;
        return Saver.forceSave(content, name, DIR);
    }

    /**
     * Sauvegarde et réecrit par dessus les données si le fichier existe
     * 
     * @params content Buffer des données à sauvegarder
     * @params name Nom du fichier sur lequel sauvegarder
     * @return si les données ont bien été sauvegardé
     **/
    protected static boolean forceSave(ByteBuffer content, String name, String DIR) {
        var f = new File(DIR + "/" + name);
        try {
            f.createNewFile();
            System.out.println("Création du fichier: " + f.getPath());
        } catch (IOException e) {
            System.out.println("Le programme n'a pas put creer un fichier pour sauvegarder");
            System.exit(-1);
        }

        // Now write the content of the buffer into the create file
        FileOutputStream fileStream = null;
        try {
            fileStream = new FileOutputStream(f);
            fileStream.write(content.array());
            // fileStream.write('\u001a');
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.exists();
    }

    /**
     * read every file in the current directory and return them
     * 
     * @return a Stream of ByteBuffer containing the raw data of the files
     **/
    protected static Optional<Stream<ByteBuffer>> loadAll(String DIR) {
        var files = allFilesNames(DIR);
        if (files.isPresent()) {
            List<ByteBuffer> result = new ArrayList<>();
            files.get().forEach(f -> {
                var file = new File(f);
                ByteBuffer data = ByteBuffer.allocate(1024 * 1024 * 5); // Max size of a file is 5 Mo
                try {
                    FileInputStream fileStream = new FileInputStream(file);
                    int b;
                    do {
                        b = fileStream.read();
                        data.put((byte) b);
                    } while (b != -1);
                    fileStream.close();
                } catch (IOException e) {
                    System.out.println("Failed to read the file");
                    System.exit(-1);
                }
                if (data.position() != 0) // the buffer have some data
                    result.add(data);
            });
            return Optional.of(result.stream());
        } else
            return Optional.empty();
    }

    /**
     * Méthode Utilitaire pour lister l'ensemble des fichiers presents dans un
     * dossier Cette méthode se charge aussi de créer les fichers et dossiers s'ils
     * sont absent Ps: Les sous dossiers sont des fichiers Brian Normant
     * 
     * @return un Stream contenant les noms de tous les fichiers dans ledit dossier
     **/
    private static Optional<Stream<String>> allFilesNames(String DIR) {
        var home = new File(HOME);
        home.mkdir();

        Optional<Stream<String>> result = Optional.empty();
        var folder = new File(DIR);
        if (!folder.exists()) {
            folder.mkdir();
        }
        File[] ss = folder.listFiles();
        if (ss != null) {
            result = Optional.of(Arrays.stream(ss).map(f -> f.getPath() // Give something like C:/grgt/egfeg/fxecf.txt
                                                                        // or /home/frge/egfe/tes.txt
            ));
        }
        return result;
    }

    /**
     * Methode Wrapper pour convertir une chaine de caractère en octets Brian
     * Normant
     * 
     * @param s La chaine de caractère
     * @return un tableau d'octect reprentant les données encodé de la chaine en
     *         binnaire
     **/
    public static byte[] convert(String s) {
        byte[] result = null; // la valeur par default n'a pas d'importance puisque le programme quitte
                              // lorsque la chaine ne peut être convertie
        try {
            result = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Le systeme ne supporte pas cet encodage");
            System.exit(-1);
        }
        return result;
    }

    /**
     * Méthode inverse de convert, converti des octeds en chaine de caractère. Brian
     * Normant
     * 
     * @param raw les octets
     * @return la chaine de caractère
     **/
    public static String convert(byte[] raw) {
        return new String(raw, StandardCharsets.UTF_8);
    }

}
