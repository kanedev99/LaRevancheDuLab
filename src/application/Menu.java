package application;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import interfaces.Jeu;

/**
 * Cette classe cree le panel principal de l'application
 * correspondant au menu du jeu
 * 
 * 
 * @author Kepler Paul-Émile
 * @author Maysa Zahi
 * @author YohannLaou
 */

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnJouer;
	private JButton btnMiniJeux;
	private JButton btnCredits;
	private JButton btnQuitter;
	private JLabel lblDifficulte;
	private JButton btnScores;
	private JLabel lblNewLabel;
	private JComboBox<String> comboBoxDifficulty;

	/** Image représentant l'image de fond du menu **/
	private Image fond = null;

	// ajouter le support pour lancer des evenements de type PropertyChange
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * voici la methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	//YohannLaou
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}// Fin methode

	public Menu() {

		mettreFond(); // mets le fond

		lblNewLabel = new JLabel("La revanche du laboratoire");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 34));
		lblNewLabel.setBounds(193, 52, 456, 41);
		add(lblNewLabel);

		btnJouer = new JButton("Commencer une partie\r\n");
		btnJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAppOpenGame();
			}
		});
		btnJouer.setBounds(187, 137, 141, 23);
		add(btnJouer);

		btnMiniJeux = new JButton("\u00C9dition : Le laboratoire  peut attendre");
		btnMiniJeux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherMinis();
			}
		});
		btnMiniJeux.setBounds(187, 171, 213, 23);
		add(btnMiniJeux);

		btnCredits = new JButton("Credits");
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"pas disponible mais c'est seulement kepler qui travaille sur le projet(et brian i guess....)");
			}
		});
		btnCredits.setBounds(187, 286, 67, 23);
		add(btnCredits);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuitter.setBounds(372, 361, 67, 23);
		add(btnQuitter);

		lblDifficulte = new JLabel("Difficult\u00E9:");
		lblDifficulte.setBounds(363, 141, 46, 14);
		add(lblDifficulte);

		comboBoxDifficulty = new JComboBox<String>();
		comboBoxDifficulty.setBounds(419, 138, 75, 20);
		comboBoxDifficulty.setMaximumRowCount(3);
		comboBoxDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				implemDiff();
			}
		});
		comboBoxDifficulty.setModel(new DefaultComboBoxModel<String>(new String[] { "Facile", "Moyen", "Difficile" }));
		add(comboBoxDifficulty);

		btnScores = new JButton("Historique");
		btnScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherScore();
			}
		});
		btnScores.setBounds(187, 205, 81, 23);
		add(btnScores);

	} // fin constructeur

	/**
	 * Permet la gestion de l'URL du fond d'image
	 * 
	 * Aussi de vérifie que le fichier image existe dans le projet
	 */
	// Maysa Zahi
	public void mettreFond() {

		URL urlFond = getClass().getClassLoader().getResource("all.jpg"); // le fond du menu
		if (urlFond == null) {
			JOptionPane.showMessageDialog(null, "Fichier welcome.jpg introuvable");
			System.exit(0);
		}

		try {
			fond = ImageIO.read(urlFond);
		} catch (IOException e) {
			System.out.println("Erreur pendant la lecture du fichier d'image");
		}
	}

	/**
	 * Dessine le fond d'image du Menu
	 * 
	 * @param g Le contexte graphique
	 */
	// Maysa Zahi
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(fond, 0, 0, 895, 713, null);
	}

	/**
	 * affiche l'interface des scores
	 */
	// YohannLaou
	public void afficherScore() {
		pcs.firePropertyChange("passerVersScore", 0, -1);
	}// Fin methode

	/**
	 * affiche l'interface des mini-jeux
	 */
	// YohannLaou
	public void afficherMinis() {
		pcs.firePropertyChange("passerVersMinis", 0, -1);
	}// Fin methode

	/**
	 * ferme l'application principale et ouvre le jeu
	 */
	// YohannLaou
	private void closeAppOpenGame() {
		pcs.firePropertyChange("passerVersJeu", 0, -1);
	}// Fin methode

	/**
	 * implemente la difficulte du jeu dans la classe Jeu
	 */
	// YohannLaou
	private void implemDiff() {
		Jeu.setDifficulty((String) comboBoxDifficulty.getSelectedItem());
	}// Fin Methode
}
