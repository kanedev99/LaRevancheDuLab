package lewis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import chimie.Elements;

/**
 * Cette classe cree un panel ou est dessiner le tableau periodique des elements
 * en format traditionnel
 * 
 * @author YohannLaou
 */

public class TableauComplet extends JPanel {

	//Donne le symbole de tous les elements du tableau periodique dans l'ordre du tableau
	private final String[][] elements = { 
			
			{ "H" , ""  , ""	 , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "He" }, //1
			{ "Li", "Be", ""	 , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "B" , "C" , "N" , "O" , "F" , "Ne" }, //2
			{ "Na", "Mg", ""	 , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , ""  , "Al", "Si", "P" , "S" , "Cl", "Ar" }, //3
			{ "K" , "Ca", "Sc"   , "Ti", "V" , "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr" }, //4
			{ "Rb", "Sr", "Y"    , "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I" , "Xe" }, //5
 			{ "Cs", "Ba", "La/Lu", "Hf", "Ta", "W" , "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn" }, //6
 			{ "Fr", "Ra", "Ac/Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og" }, //7
	
	};

	public TableauComplet() {
		setPreferredSize(new Dimension(540, 270));
		setBackground(Color.white);
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Graphics2D g2d = (Graphics2D) g;

	    // Définir la police et la taile pour dessiner le symbole de l'élément
	    Font font = new Font("Arial", Font.BOLD, 14);
	    g2d.setFont(font);

	    // Dessiner les cases pour chaque élément
	    int width = getWidth(); // largeur de la zone dessin
	    int height = getHeight(); // hauteur de la zone dessin
	    int caseWidth = (width-15) / 18; // largeur d'une case
	    int caseHeight = caseWidth; // hauteur d'une case
	    
	    // Centrer le tableau en bas/milieu de la zone de dessin
	    int xStart = (width - (caseWidth * 18)) / 2;
	    int yStart = height - (caseHeight * 7) - 10;

	    // Dessiner les cases
	    for (int row = 0; row < 7; row++) {
	        for (int col = 0; col < 18; col++) {
	            String symbol = elements[row][col];

	            // Dessiner la case avec un fond coloré en fonction de la catégorie de l'élément
	            Color color = ColorCategory(symbol);
	            g2d.setColor(color);
	            g2d.fillRect(xStart + col * caseWidth, yStart + row * caseHeight, caseWidth, caseHeight);

	            // Dessiner le symbole de l'élément dans la case
	            if (!symbol.isEmpty()) {
	                g2d.setColor(Color.BLACK);
	                
	                
	                /**
	                // Dessiner le numéro atomique de l'élément juste au-dessus du symbole
	                Elements numero = Elements.valueOf(symbol);
	                int numAtomique = numero.getNumeroAtomique();
	                String atomicNumberString = String.valueOf(numAtomique);
	                g2d.drawString(atomicNumberString, col * caseWidth + caseWidth / 2, row * caseHeight + 15);
					*/
	                
	                g2d.setStroke(new BasicStroke(1));
	                int widthMot = g.getFontMetrics().stringWidth(symbol);
	                g2d.drawString(symbol, xStart + (col * caseWidth + caseWidth/2) - widthMot/2, yStart + row * caseHeight + caseHeight/2 + 5);
					
	            }
	        }
	    }
	    
	    // Dessiner les numéros de colonnes
	    for (int col = 1; col <= 18; col++) {
	        String colNum = Integer.toString(col);
	        g2d.drawString(colNum, (col - 1) * caseWidth + caseWidth / 2, caseHeight - 5);
	    }

	    // Dessiner la bordure de la case
	    for (int row = 0; row < 7; row++) {
	        for (int col = 0; col < 18; col++) {
	            String symbol = elements[row][col];

	            if (!symbol.isEmpty()) {
	                g2d.setColor(Color.BLACK);
	                g2d.drawRect(xStart + col * caseWidth, yStart + row * caseHeight, caseWidth, caseHeight);
	            }
	        }
	    }
	}

	/**
	 * Retourne une couleur en fonction de la catégorie de l'élément
	 * @param symbol l'element du tableau
	 * @return une couleur selon la categorie de l'element
	 */
	private Color ColorCategory(String symbol) {
		
		if (symbol.isEmpty()) {
	        return Color.WHITE;
	    }
	    
	    String element = symbol.substring(0, Math.min(symbol.length(), 2));
	    switch (element) {
	    
	            case "H":
	            case "C":
	            case "N":
	            case "O":
	            case "P":
	            case "S":
	            case "Se":
	                return new Color(255, 255, 204); // Jaune clair pour les non-métaux
	             
	            case "F":
	            case "Cl":
	            case "Br":
	            case "I":
	            	return new Color(0, 255, 26); // vert clair pour les halogenes
	                
	            case "He":
	            case "Ne":
	            case "Ar":
	            case "Kr":
	            case "Xe":
	            case "Rn":
	                return new Color(204, 255, 255); // Bleu clair pour les gaz nobles
	                
	            case "Li":
	            case "Na":
	            case "K":
	            case "Rb":
	            case "Cs":
	            case "Fr":
	            	return new Color(255, 30, 30); //Rouge pour les alcalins
	            	
	            case "Be":
	            case "Mg":
	            case "Ca":
	            case "Sr":
	            case "Ba":
	            case "Ra":
	                return new Color(255, 204, 204); // Rose pour les alcalino-terreux
	                
	            case "Sc":
	            case "Y":
	            case "La-Lu":
	            case "Ac-Lr":
	            case "Ti":
	            case "Zr":
	            case "Hf":
	            case "Rf":
	            case "V":
	            case "Nb":
	            case "Ta":
	            case "Db":
	            case "Cr":
	            case "Mo":
	            case "W":
	            case "Sg":
	            case "Mn":
	            case "Tc":
	            case "Re":
	            case "Bh":
	            case "Fe":
	            case "Ru":
	            case "Os":
	            case "Hs":
	            case "Co":
	            case "Rh":
	            case "Ir":
	            case "Mt":
	            case "Ni":
	            case "Pd":
	            case "Pt":
	            case "Cu":
	            case "Ag":
	            case "Au":
	            case "Zn":
	            case "Cd":
	            case "Hg":
	            case "Cn":
	            	return new Color(204, 204, 255); // Bleu pour les métaux de transition
	            	
	            case "B":
	            case "Si":
	            case "Ge":	
	            case "As":
	            case "Sb":
	            case "Te":
	            case "At":
	            	return new Color(255, 204, 153); // Beige pour les metalloids
	            	
	            case "Al":
	            case "Ga":
	            case "In":
	            case "Tl":
	            case "Sn":
	            case "Pb":
	            case "Bi":
	            case "Po":
	            	return new Color(174, 174, 174); // Gris clair pour les metaux
	            
	        default:
	            return Color.WHITE;
	    }
    }
}
