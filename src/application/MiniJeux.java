package application;

import javax.swing.JPanel;

//import heiss.JeuHeiss;
import jeu4.Jeu4;
//import lewis.JeuLewis;
//import mixMixMix.JeuMix;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.awt.event.ActionEvent;

/**
 * 
 * @author YohannLaou
 *
 */

public class MiniJeux extends JPanel {
	
	
	// ajouter le support pour lancer des evenements de type PropertyChange
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	/**
	 * voici la methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}//Fin methode

	/**
	 * Creation de l'interface
	 */
	public MiniJeux() {
		
		/**Mini-Jeux**/
	//	JeuLewis frameLewis = new JeuLewis();
	//	JeuHeiss frameHeiss = new JeuHeiss();
	//	JeuMix frameMix = new JeuMix();
		Jeu4 frameMirroir = new Jeu4();
		
		setLayout(null);
		
		JLabel lblMiniJeux = new JLabel("Mini-Jeux");
		lblMiniJeux.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblMiniJeux.setBounds(306, 96, 239, 71);
		add(lblMiniJeux);
		
		JButton btnLewis = new JButton("Lewis");
		btnLewis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherLewis();
			}
		});
		btnLewis.setBounds(162, 241, 89, 23);
		add(btnLewis);
		
		JButton btnHeiss = new JButton("Heiss");
		btnHeiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherHeiss();
			}
		});
		btnHeiss.setBounds(589, 241, 89, 23);
		add(btnHeiss);
		
		JButton btnStochio = new JButton("Stochiometrie");
		btnStochio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherMix();
			}
		});
		btnStochio.setBounds(162, 311, 89, 23);
		add(btnStochio);
		
		JButton btnMiroir = new JButton("Miroir");
		btnMiroir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherMirror();
			}
		});
		btnMiroir.setBounds(589, 311, 89, 23);
		add(btnMiroir);
		
		JButton btnRetour = new JButton("retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange("passerVersMenu", null, -1);
			}
		});
		btnRetour.setBounds(391, 392, 89, 23);
		add(btnRetour);
	}
	
	/**
	 * Ferme la fenetre de l'application et ouvre le mini-jeu Lewis
	 */
	public void afficherLewis() {
		pcs.firePropertyChange("Lewis", 0, -1);
	}// Fin methode
	
	/**
	 * Ferme la fenetre de l'application et ouvre le mini-jeu Heiss
	 */
	public void afficherHeiss() {
		pcs.firePropertyChange("Heiss", 0, -1);
	}// Fin methode
	
	/**
	 * Ferme la fenetre de l'application et ouvre le mini-jeu MixMixMix
	 */
	public void afficherMix() {
		pcs.firePropertyChange("Mix", 0, -1);
	}// Fin methode
	
	/**
	 * Ferme la fenetre de l'application et ouvre le mini-jeu Mirroir
	 */
	public void afficherMirror() {
		pcs.firePropertyChange("Mirroir", 0, -1);
	}// Fin methode
}
