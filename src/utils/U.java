package utils;

import java.awt.Color;
import java.util.HashMap;
import java.awt.geom.Area;

import chimie.MolType;

/**
 * Classe d'utilitaire simple.
 * Si une methode semble tres abstraite l'implementer ici
 * 
 * @author Brian Normant
 **/
public class U {

    private final static HashMap<MolType, Color> TYPE_COLORS;
    static {
        TYPE_COLORS = new HashMap<>();
        TYPE_COLORS.put(MolType.ACIDE, Color.GREEN);
        TYPE_COLORS.put(MolType.BASE, Color.BLUE);
        TYPE_COLORS.put(MolType.COMBUSTIBLE, new Color(86, 83, 82));
        TYPE_COLORS.put(MolType.COMBURANT, new Color(64, 232, 232));
        TYPE_COLORS.put(MolType.EAU, new Color(32, 39, 232));
        TYPE_COLORS.put(MolType.METAL, new Color(68, 70, 61));
        TYPE_COLORS.put(MolType.REDUCTEUR, Color.YELLOW);
        TYPE_COLORS.put(MolType.OXYDANT, new Color(228, 13, 72));
        TYPE_COLORS.put(MolType.SEL, new Color(210, 198, 201));
        TYPE_COLORS.put(MolType.NOBLE, new Color(221, 95, 232));
    }

    public static Color toColor(MolType t) {
        return TYPE_COLORS.get(t);
    }

    public static boolean collision(Area a, Area b) {
        a.intersect(b);
        return !a.isEmpty();
    }

}
