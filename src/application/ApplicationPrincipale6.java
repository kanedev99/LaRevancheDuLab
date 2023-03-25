package application;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import heiss.JeuHeiss;
import jeu4.Jeu4;
import jeuPrincipal.JeuPrincipal;
import lewis.JeuLewis;
import mixMixMix.JeuMix;

/**
 * 
 * @author YohannLaou
 *
 */
public class ApplicationPrincipale6 extends JFrame {
	public static boolean REDIM = false;
	static ApplicationPrincipale6 frame = new ApplicationPrincipale6();

	/**
	 * Lancer l'application
	 */
	public static void main(String[] args) {
		for (var arg : args)
			System.out.println(arg);
		REDIM = args.length != 0;
		System.out.println(
				REDIM ? "L'application démarre en taille redimentionnée" : "L'application démarre en taille réelle");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ApplicationPrincipale6();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creation de la fenetre
	 */
	public ApplicationPrincipale6() {

		setTitle("La revanche du laboratoire");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 895, 713); // dimensions de la fenêtre

		// on cree les deux panels aternatifs
		Menu menu = new Menu();
		Scores score = new Scores();
		MiniJeux minis = new MiniJeux();

		// On cree les fenetres des mini-Jeux
		JeuPrincipal jeuPrinc = new JeuPrincipal();
		JeuLewis lewis = new JeuLewis();
		JeuHeiss heiss = new JeuHeiss();
		JeuMix mix = new JeuMix();
		Jeu4 mirroir = new Jeu4();

		// on décide lequel on verra en premier
		menu.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(menu);
		menu.setLayout(null);

		// on installe les écouteurs pour recevoir les directives de rendre l'un ou
		// l'autre des panels visibles!
		menu.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("passerVersScore")) {
					menu.setVisible(false);
					score.setVisible(true);
					setContentPane(score);
				}

				if (evt.getPropertyName().equals("passerVersMinis")) {
					menu.setVisible(false);
					minis.setVisible(true);
					setContentPane(minis);
				}

				if (evt.getPropertyName().equals("passerVersJeu")) {
					jeuPrinc.setVisible(true);
					frame.dispose();
				}

				score.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("passerVersMenu")) {
							menu.setVisible(true);
							score.setVisible(false);
							setContentPane(menu);
						}
					}
				});

				minis.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("passerVersMenu")) {
							menu.setVisible(true);
							score.setVisible(false);
							setContentPane(menu);
						}
						if (evt.getPropertyName().equals("Lewis")) {
							frame.setVisible(false);
							lewis.setVisible(true);
						}
						if (evt.getPropertyName().equals("Heiss")) {
							frame.setVisible(false);
							heiss.setVisible(true);
						}
						if (evt.getPropertyName().equals("Mix")) {
							frame.setVisible(false);
							mix.setVisible(true);
						}
						if (evt.getPropertyName().equals("Mirroir")) {
							frame.setVisible(false);
							mirroir.setVisible(true);
						}

					}
				});

			}
		});

	}

}
