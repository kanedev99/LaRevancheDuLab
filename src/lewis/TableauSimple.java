package lewis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Cette classe cree un panel ou est dessiner le tableau periodique des elements
 * simplifié arbitrairement
 * 
 * @author YohannLaou
 *
 */
public class TableauSimple extends JPanel {

	//Donne le symbole de tous les elements du tableau periodique dans l'ordre du tableau
	private final String[][] elements = { 
			
			{ "H" , ""  , ""  , ""  , ""  , ""  , ""  , "He" }, //1
			{ "Li", "Be", "B" , "C" , "N" , "O" , "F" , "Ne" }, //2
			{ "Na", "Mg", "Al", "Si", "P" , "S" , "Cl", "Ar" }, //3
			{ "K" , "Ca", "Ga", "Ge", "As", "Se", "Br", "Kr" }, //4
			{ "Rb", "Sr", "In", "Sn", "Sb", "Te", "I" , "Xe" }, //5
 			{ "Cs", "Ba", "Tl", "Pb", "Bi", "Po", "At", "Rn" }, //6
	
	};

	public TableauSimple() {
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
		int caseHeight = height / 8; // hauteur d'une case
		int caseWidth = caseHeight; // largeur d'une case
	    
	    
	    // Centrer le tableau en bas/milieu de la zone de dessin
	    int xStart = (width - (caseWidth * 8)) / 2;
	    int yStart = height - (caseHeight * 6) - 10;

		// Dessiner les cases
		for (int row = 0; row < 6; row++) {
		   for (int col = 0; col < 8; col++) {
		       String symbol = elements[row][col];

		       // Dessiner la case avec un fond coloré en fonction de la catégorie de l'élément
		       Color color = ColorCategory(symbol);
		       g2d.setColor(color);
		       g2d.fillRect(xStart + col * caseWidth, yStart + row * caseHeight, caseWidth, caseHeight);
		            
		       // Dessiner le symbole de l'élément dans la case
		       if (!symbol.isEmpty()) {
		              g2d.setColor(Color.BLACK);
	                	int widthMot = g.getFontMetrics().stringWidth(symbol);
	                	g2d.drawString(symbol, xStart + (col * caseWidth + caseWidth/2) - widthMot/2, yStart + row * caseHeight + caseHeight/2 + 5);
		                
		            }
		        }
		    }
		
			// Dessiner les numéros de colonnes
	    	for (int col = 1; col <= 8; col++) {
	    		String colNum = Integer.toString(col);
	    		g2d.drawString(colNum, xStart + (col - 1) * caseWidth + caseWidth / 2, yStart - caseHeight);
	    	}
		    
			// Dessiner la bordure de la case
		    for (int row = 0; row < 6; row++) {
		        for (int col = 0; col < 8; col++) {
		            String symbol = elements[row][col];

		            if (!symbol.isEmpty()) {
			            g2d.setColor(Color.BLACK);
			            g2d.drawRect(xStart + col * caseWidth, yStart + row * caseHeight, caseWidth, caseHeight);
		            }
		        }
		    }
		    
	}

	// Retourne une couleur en fonction de la catégorie de l'élément
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
	            	return new Color(255, 30, 30); //Rouge pour les alcalins
	            	
	            case "Be":
	            case "Mg":
	            case "Ca":
	            case "Sr":
	            case "Ba":
	                return new Color(255, 204, 204); // Rose pour les alcalino-terreux
	                
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
    }//Fin Methode
}
