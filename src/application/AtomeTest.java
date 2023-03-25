package application;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Optional;

import lewis.*;
import saving.*;

/**
 * Test de la logique de contruction de molecule
 * 
 * @author Brian Normant
 */
public class AtomeTest {
    public static void main(String[] args) {
        List<Atome> atomes = new ArrayList<>();
        System.out.print("\t Test 1: CO2 contruction ideale\n");
        {
            atomes.add(new Atome("C", 4));
            atomes.add(new Atome("O", 2));
            atomes.add(new Atome("O", 2));

            atomes.get(0).setPointingTo(atomes.get(1), LinkType.COVALENT_DOUBLE, false);
            atomes.get(0).setPointingTo(atomes.get(2), LinkType.COVALENT_DOUBLE, false);

            for (Atome a : atomes.get(0))
                System.out.println(a);
            atomes.clear();
        }
        System.out.print("\t Test 2: CO2 reconstruction\n");
        {
            atomes.add(new Atome("C", 4));
            atomes.add(new Atome("O", 2));
            atomes.add(new Atome("O", 2));

            atomes.get(1).setPointingTo(atomes.get(0), LinkType.COVALENT_DOUBLE, false);
            atomes.get(2).setPointingTo(atomes.get(0), LinkType.COVALENT_DOUBLE, false);

            for (Atome atome : atomes)
                System.out.println(atome);
            atomes.clear();
        }
        System.out.print("\t Test 3: C2H6 contruction ideale\n");
        {
            atomes.add(new Atome("C", 4));// 0
            atomes.add(new Atome("C", 4));// 1
            atomes.add(new Atome("H", 1));// 2
            atomes.add(new Atome("H", 1));// 3
            atomes.add(new Atome("H", 1));// 4
            atomes.add(new Atome("H", 1));// 5
            atomes.add(new Atome("H", 1));// 6
            atomes.add(new Atome("H", 1));// 7

            atomes.get(0).setPointingTo(atomes.get(2), LinkType.COVALENT_SIMPLE, false);
            atomes.get(0).setPointingTo(atomes.get(3), LinkType.COVALENT_SIMPLE, false);
            atomes.get(0).setPointingTo(atomes.get(4), LinkType.COVALENT_SIMPLE, false);
            atomes.get(1).setPointingTo(atomes.get(5), LinkType.COVALENT_SIMPLE, false);
            atomes.get(1).setPointingTo(atomes.get(6), LinkType.COVALENT_SIMPLE, false);
            atomes.get(1).setPointingTo(atomes.get(7), LinkType.COVALENT_SIMPLE, false);
            atomes.get(1).setPointingTo(atomes.get(0), LinkType.COVALENT_SIMPLE, false);
            for (Atome a : atomes.get(1))
                System.out.println(a);
            atomes.clear();
        }
        System.out.print("\t Test 4: C2H6 reconstruction\n");
        {
            atomes.add(new Atome("C", 4));// 0
            atomes.add(new Atome("C", 4));// 1
            atomes.add(new Atome("H", 1));// 2
            atomes.add(new Atome("H", 1));// 3
            atomes.add(new Atome("H", 1));// 4
            atomes.add(new Atome("H", 1));// 5
            atomes.add(new Atome("H", 1));// 6
            atomes.add(new Atome("H", 1));// 7

            atomes.get(2).setPointingTo(atomes.get(0), LinkType.COVALENT_SIMPLE, false);
            atomes.get(0).setPointingTo(atomes.get(3), LinkType.COVALENT_SIMPLE, false);
            atomes.get(0).setPointingTo(atomes.get(4), LinkType.COVALENT_SIMPLE, false);
            atomes.get(0).setPointingTo(atomes.get(1), LinkType.COVALENT_SIMPLE, false);
            atomes.get(1).setPointingTo(atomes.get(5), LinkType.COVALENT_SIMPLE, false);
            atomes.get(1).setPointingTo(atomes.get(6), LinkType.COVALENT_SIMPLE, false);
            atomes.get(7).setPointingTo(atomes.get(1), LinkType.COVALENT_SIMPLE, false);
            for (Atome atome : atomes)
                System.out.println(atome);
            atomes.clear();
        }
        System.out.print("\t Test 5: C6H12 Cyclique contruction ideale\n");
        {
            atomes.add(new Atome("N", 3));// 0
            atomes.add(new Atome("N", 3));// 1
            atomes.add(new Atome("N", 3));// 2

            atomes.get(0).setPointingTo(atomes.get(1), LinkType.COVALENT_TRIPLE, false);
            atomes.get(1).setPointingTo(atomes.get(2), LinkType.COVALENT_TRIPLE, false);
            atomes.get(2).setPointingTo(atomes.get(0), LinkType.COVALENT_TRIPLE, false);

            for (Atome a : atomes.get(0))
                System.out.println(a);
            atomes.clear();
        }
        System.out.print("\t Test 6: C6H12 Cyclique reconstruction\n");
        {

        }

        System.out.print("\t Test 7: Egalité H2O\n");
        {
            // assert that H<-O->H == H->O->H

            // Creation H->O->H
            atomes.add(new Atome("O", 2));// 0
            atomes.add(new Atome("H", 1));// 1 Head
            atomes.add(new Atome("H", 1));// 2
            atomes.get(1).setPointingTo(atomes.get(0), LinkType.COVALENT_SIMPLE, true);
            atomes.get(0).setPointingTo(atomes.get(2), LinkType.COVALENT_SIMPLE, false);
            // Creation H<-O->H
            atomes.add(new Atome("O", 2));// 3 //Head
            atomes.add(new Atome("H", 1));// 4
            atomes.add(new Atome("H", 1));// 5
            atomes.get(3).setPointingTo(atomes.get(4), LinkType.COVALENT_SIMPLE, false);
            atomes.get(3).setPointingTo(atomes.get(5), LinkType.COVALENT_SIMPLE, false);

            MolDat mol1 = new MolDat(atomes.get(1));
            MolDat mol2 = new MolDat(atomes.get(3));

            System.out.println("Assert that mol1 == mol2 is true => " + mol1.equals(mol2));

            atomes.clear();
        }

        System.out.print("\t Test 8: Sauvegarde H2O\n");
        {
            atomes.add(new Atome("O", 2));
            atomes.add(new Atome("H", 1));
            atomes.add(new Atome("H", 1));

            atomes.get(0).setPointingTo(atomes.get(1), LinkType.COVALENT_SIMPLE, false);
            atomes.get(0).setPointingTo(atomes.get(2), LinkType.COVALENT_SIMPLE, false);

            MolDat dat = new MolDat(atomes.get(0), "TestH2O");
            new MolSaver().save(dat);

            for (Atome a : atomes.get(0))
                System.out.println(a);
            atomes.clear();
        }
        System.out.print("\t Test 8: Chargement H2O\n");
        {
            Optional<Stream<MolDat>> loaded = new MolSaver().loadAll();
            if (loaded.isEmpty()) {
                System.out.print("ERROR RNFIBRUGLEGHgbjgykvrwjb\n");
            } else {
                loaded.get().forEach(
                        l -> {
                            if (l.getName().equals("TestH2O")) {
                                for (Atome a : l.getHead())
                                    System.out.println(a);
                            }
                        });
            }
        }
    }
}
