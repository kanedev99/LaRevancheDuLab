package lewis;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.ApplicationPrincipale6;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JRadioButtonMenuItem;
/**
 * Cette classe cree la fenetre du mini-jeu Lewis
 * et contient des zones de dessin
 * 
 * @author YohannLaou
 */

public class JeuLewis extends JFrame {

	private JPanel contentPane;
	private JButton btnTableau;
	
	//permet d'alterner entre les tableaux
	private boolean tableauTrad = true;
	private TableauComplet tabComplet;
	private TableauSimple tabSimple;
	
	//active ou desactive le mode developpeur
	private boolean devMode = false;
	
	private JButton btnSave;
	private JButton btnLoad;
	private JButton btnCheck;
	
	//Positions du bouton valider (depend de devMode)
	int xX, xY, yX, yY;
	
	// ajouter le support pour lancer des evenements de type PropertyChange
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	/**
	 * voici la methode qui permettra de s'ajouter en tant qu'ecouteur
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}// Fin methode

	/**
	 * Creation de la fenetre
	 */
	public JeuLewis() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1297, 714);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabComplet = new TableauComplet();
		tabComplet.setBounds(622, 89, 649, 323);
		contentPane.add(tabComplet);
		
		tabSimple = new TableauSimple();
		tabSimple.setBounds(622, 89, 649, 323);
		contentPane.add(tabSimple);
		
		btnTableau = new JButton("Tableau traditionnel");
		btnTableau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toggleTableau();
			}
		});
		btnTableau.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnTableau.setBounds(824, 33, 262, 45);
		contentPane.add(btnTableau);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(621, 423, 649, 241);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDescription = new JLabel("Description de l'atome choisi");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblDescription.setBounds(200, 11, 247, 45);
		panel.add(lblDescription);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1281, 22);
		contentPane.add(menuBar);
		
		JMenu mnAccueil = new JMenu("Accueil");
		menuBar.add(mnAccueil);
		
		JMenuItem mntmRetourMenu = new JMenuItem("Retour au menu");
		mntmRetourMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            var frameMenu = new ApplicationPrincipale6();
                            frameMenu.setVisible(true);
                            setVisible(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
			}
		});
		
		mnAccueil.add(mntmRetourMenu);
		
		JMenuItem mntmLeave = new JMenuItem("Quitter le jeu");
		mntmLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnAccueil.add(mntmLeave);
		
		JMenu mnSettings = new JMenu("Parametre");
		menuBar.add(mnSettings);
		
		JRadioButtonMenuItem rdbtnmntmDevMode = new JRadioButtonMenuItem("Dev. Mode");
		rdbtnmntmDevMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeDevMode();
			}
		});
		mnSettings.add(rdbtnmntmDevMode);
		
		btnSave = new JButton("Sauvegarder la molecule");
		btnSave.setBounds(10, 566, 178, 52);
		btnSave.setVisible(false);
		contentPane.add(btnSave);
		
		btnLoad = new JButton("Charger une molecule");
		btnLoad.setBounds(198, 566, 178, 52);
		btnLoad.setVisible(false);
		contentPane.add(btnLoad);
		
		xX = 244;
		xY = 566;
		yX = 129;
		yY = 52;
		
		btnCheck = new JButton("Valider");
		btnCheck.setBounds(xX, xY, yX, yY);
		contentPane.add(btnCheck);
		
		dessinMol dessinMol = new dessinMol();
		dessinMol.setBackground(new Color(255, 255, 255));
		dessinMol.setBounds(10, 33, 602, 522);
		contentPane.add(dessinMol);
		dessinMol.setLayout(null);
	}
	
	/**
	 * change le tableau periodique pour l'autre
	 */
	private void toggleTableau() {
		if (tableauTrad) {
			btnTableau.setText("Tableau simplifié");
			tabComplet.setVisible(false);
			tabSimple.setVisible(true);
			tableauTrad = !tableauTrad;
		} else {
			btnTableau.setText("Tableau traditionnel");
			tabComplet.setVisible(true);
			tabSimple.setVisible(false);
			tableauTrad = !tableauTrad;
		}
	}// Fin methode
	
	/**
	 * Active/Desactive le mode develeppeur
	 */
	private void activeDevMode() {
		if (!devMode) {
			btnSave.setVisible(true);
			btnLoad.setVisible(true);
			btnCheck.setBounds(416, 566, 129, 52);
			devMode = !devMode;
			//System.out.println("DevMode active");
		} else {
			btnSave.setVisible(false);
			btnLoad.setVisible(false);
			devMode = !devMode;
			btnCheck.setBounds(xX, xY, yX, yY);
			//System.out.println("DevMode Desactive");
		}
		
	}//Fin Methode
}
