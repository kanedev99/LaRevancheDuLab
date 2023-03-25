package mixMixMix;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import application.ApplicationPrincipale6;
import chimie.MolData;
import interfaces.Jeu;
import jeuPrincipal.Molecule;

/**
 * Cette classe represente la scene dans laquelle on dessine
 * un paquet d'atomes qu'on pourra déplacer vers le bon endroit de l'équation
 * chimique. C'est cette classe qui s'occuperat de
 * la logique et de l'interaction entre les éléments 
 * 
 * Cette classe gere la logique, c'est donc elle qui possède les informations
 * liés a l'etats du jeu: Atomes, réactifs, produits, etc
 * 
 * @author Maysa Zahi
 **/
public class Scene extends JPanel {
	private static final long serialVersionUID = 1L;
	final static int DIMX = ApplicationPrincipale6.REDIM ? 789 : 1000, DIMY = ApplicationPrincipale6.REDIM ? 482 : 950;

	private List<Molecule> molecules;
	private Molecule currentMolecule;
	private String molecule; // nom de la molécule à former
	private boolean gameOver; // indique si le jeu est terminé
	private boolean victory; // indique si le joueur a gagné
	private Font font; // police de caractères à utiliser pour afficher le message de fin de jeu
	
	
	private List<MolData> molDataList = Arrays.asList(
	        new MolData("H2O", Map.of("H", 2, "O", 1)),
	        new MolData("CO2", Map.of("C", 1, "O", 2)),
	        new MolData("CH4", Map.of("C", 1, "H", 4))
	        // Ajouter d'autres molécules (a venir)
	);

	/**
	 * Constructeur de la Scene
	 */
	// Maysa Zahi
	public Scene() {

		initialiser();
		// dessin des atomes et de l'equation

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gg(e); //vérifie si le joueur a reussi la 1e partie du jeu ou pas
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//

			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//
			}

		});
		setBackground(Color.white);
	}

	
	public void initialiser() {
		// Initialisation de la liste de molécules
		molecules = new ArrayList<>();

	}
	
	/**
	 * Génère des noms de molécules aliatoires selon la liste de molécules
	 */
	public String genererNomMoleculeAleatoire() {
	    int randIndex = new Random().nextInt(molDataList.size());
	    return molDataList.get(randIndex).getName();
	}


	public void gg(KeyEvent e) {

		if (!gameOver) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				// vérifier si le joueur a placé les atomes correctement
				if (verifierEquation()) {
					victory = true;
				} else {
					victory = false;
				}
				gameOver = true;
				repaint();
				break;
			case KeyEvent.VK_ESCAPE:
				// recommencer le jeu
				genererMoleculeAleatoire();
				gameOver = false;
				victory = false;
				repaint();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Vérifie si l'équation validée est fausse ou vraie
	 * @return Est-ce que l'équation est fausse ou vraie?
	 */
	private boolean verifierEquation() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Génère des molécules aliatoires
	 */
	private void genererMoleculeAleatoire() {
		// TODO Auto-generated method stub

	}


	/**
	 * Permet de dessiner une scene
	 * 
	 * @param g Contexte graphique
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.scale(Jeu.FACTEUR_REDIM, Jeu.FACTEUR_REDIM);


	}// fin paintComponent
}

