package lewis;

// import java.awt.Graphics;
// import java.awt.Graphics2D;

// import javax.swing.JPanel;

// /**
//  * Cette classe cree un panel de dessin pour le mini-jeu Lewis
//  *
//  * @author YohannLaou
//  */
// public class dessinMol extends JPanel {

// 	/**
// 	 * Creation de l'interface
// 	 */
// 	public dessinMol() {

// 	}
	
// 	public void paintComponent(Graphics g) {
// 		super.paintComponent(g);

// 		Graphics2D g2d = (Graphics2D) g;

		    
// 	}

// }

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class dessinMol extends JPanel implements MouseListener {
    
    private Map<String, Element> elements;
    private List<Point> points;
    private List<Bond> bonds;
    private Element currentElement;
    private int currentPoints;
    private int maxPoints = 8;
    private boolean canValidate;
    
    public dessinMol() {
        this.elements = new HashMap<>();
        for (Element element : Elements.elements) {
            this.elements.put(element.getSymbol(), element);
        }
        this.points = new ArrayList<>();
        this.bonds = new ArrayList<>();
        this.canValidate = false;
        this.addMouseListener(this);
    }
    
    public void addElement(String symbol) {
        Element element = this.elements.get(symbol);
        if (element != null) {
            int x = (int) (Math.random() * (getWidth() - 40)) + 20;
            int y = (int) (Math.random() * (getHeight() - 40)) + 20;
            this.currentElement = element;
            this.currentPoints = 0;
            this.points.clear();
            this.bonds.clear();
            this.canValidate = false;
            repaint();
        }
    }
    
    public void addPoint() {
        if (this.currentElement != null && this.currentPoints < this.maxPoints) {
            int x = (int) (Math.random() * 30) - 15;
            int y = (int) (Math.random() * 30) - 15;
            this.points.add(new Point(this.currentElement.getX() + x, this.currentElement.getY() + y));
            this.currentPoints++;
            if (this.currentPoints == this.maxPoints) {
                this.canValidate = true;
            }
            repaint();
        }
    }
    
    public void validate() {
        if (this.canValidate) {
            boolean valid = true;
            String[] molecules = ApplicationPrincipale6.getMolecule().split("(?=[A-Z])");
            Map<String, Integer> expectedElements = new HashMap<>();
            for (String molecule : molecules) {
                String symbol = molecule.substring(0, 1);
                int count = 1;
                if (molecule.length() > 1) {
                    count = Integer.parseInt(molecule.substring(1));
                }
                expectedElements.put(symbol, count);
            }
            Map<String, Integer> actualElements = new HashMap<>();
            for (Point point : this.points) {
                String symbol = getSymbolForPoint(point);
                if (symbol != null) {
                    Integer count = actualElements.get(symbol);
                    if (count == null) {
                        count = 0;
                    }
                    count++;
                    actualElements.put(symbol, count);
                } else {
                    valid = false;
                    break;
                }
            }
            if (valid && expectedElements.equals(actualElements)) {
                ApplicationPrincipale6.nextQuestion();
            } else {
                this.points.clear();
                this.bonds.clear();
            }
            this.canValidate = false;
            this.currentElement = null;
            this.currentPoints = 0;
            repaint();
        }
    }
    
    public void addBond(Point p1, Point p2) {
        String symbol1 = getSymbolForPoint(p1);
        String symbol2 = getSymbolForPoint(p2);
        
        // Check if both points contain an element symbol and the symbols are not the same
        if (symbol1 != null && symbol2 != null && !symbol1.equals(symbol2)) {
            // Check if there is already a bond between the two points
            for (Bond bond : this.bonds) {
                if ((bond.getPoint1().equals(p1) && bond.getPoint2().equals(p2)) || 
                    (bond.getPoint1().equals(p2) && bond.getPoint2().equals(p1))) {
                    // Bond already exists, do not add a new one
                    return;
                }
            }
            
            // Add the new bond
            int order = 1; // default bond order
            double distance = p1.distance(p2);
            if (distance <= Element.SINGLE_BOND_DISTANCE) {
                order = 1;
            } else if (distance <= Element.DOUBLE_BOND_DISTANCE) {
                order = 2;
            } else if (distance <= Element.TRIPLE_BOND_DISTANCE) {
                order = 3;
            } else {
                // Bond is too long, do not add it
                return;
            }
            Bond bond = new Bond(p1, p2, order);
            this.bonds.add(bond);
            repaint();
        }
    }
