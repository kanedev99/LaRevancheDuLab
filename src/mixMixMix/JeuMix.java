package mixMixMix;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JButton;

/**
 * 
 * @author YohannLaou
 *
 */

public class JeuMix extends JFrame {

	private JPanel contentPane;

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
	public JeuMix() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 951, 721);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu ntbMenu = new JMenu("Menu");
		menuBar.add(ntbMenu);
		
		JMenuItem mntmAide = new JMenuItem("Aide");
		ntbMenu.add(mntmAide);
		
		JMenu mnInstruction = new JMenu("Instruction");
		menuBar.add(mnInstruction);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Scene scene = new Scene();
		scene.setBounds(10, 11, 915, 563);
		contentPane.add(scene);
		
		JButton btnValider = new JButton("Valider");
		btnValider.setBounds(707, 585, 133, 45);
		contentPane.add(btnValider);
		
		JButton btnEffacer = new JButton("Effacer");
		btnEffacer.setBounds(480, 585, 133, 45);
		contentPane.add(btnEffacer);
	}
}

