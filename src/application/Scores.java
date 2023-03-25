package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JPanel;

import saving.ScoreDat;
import saving.ScoreSaver;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;

/**
 * 
 * @author YohannLaou
 *
 */

public class Scores extends JPanel {

	private final int nbLines = 8; //nombre de lignes dans le conteneur
	private final int nbColumns = 2; //nombre de colonnes dans le conteneur
	
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
	public Scores() {
		setLayout(null);
		
		JPanel panelScore = new JPanel();
		panelScore.setBackground(new Color(255, 255, 255));
		panelScore.setBounds(135, 104, 628, 446);
		add(panelScore);
		panelScore.setLayout(null);
		
		Optional<Stream<ScoreDat>> loading = new ScoreSaver().loadAll();
		
		Stream<ScoreDat> score = loading.get().sorted(Collections.reverseOrder());
		ArrayList<ScoreDat> listScore = streamToArrayList(score);
			
		panelScore.setLayout(new GridLayout(0, nbColumns)); // 0 rows, 2 columns
			
		List<JLabel> left = new ArrayList<>();
		List<JLabel> right = new ArrayList<>();
			
	    for (int i = 0; i < nbLines*nbColumns; i++) {
	        String content = (i+1) + ". Aucun Joueur     0";
	        if (i < listScore.size()) {
	        	content = (i+1) + listScore.get(i).toString();
	    	} 
	        if (i < nbLines) {
	        	left.add(new JLabel(content));
	        } else {
	        	right.add(new JLabel(content));
	        }
	            
	    }//Fin for
	    
	    for (int i = 0; i < nbLines; i++) {
	    	panelScore.add(left.get(i));
	    	panelScore.add(right.get(i));
	    }//Fin for
	            
	        
	        
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pcs.firePropertyChange("passerVersMenu", null, -1);
			}
		});
		btnRetour.setBounds(10, 630, 128, 37);
		add(btnRetour);
		
		JLabel lblHistory = new JLabel("Tableau des scores");
		lblHistory.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblHistory.setBounds(344, 11, 265, 55);
		add(lblHistory);
		
	}
	
	/**
	 * methode permettant de convertir un Stream vers un arraylist
	 * @param <T>
	 * @param stream
	 * @return arrayList une liste de type arrayList
	 */
	public static <T>ArrayList<T> streamToArrayList (Stream<T> stream){
  
        List<T>list = stream.collect(Collectors.toList());
        ArrayList<T>arrayList = new ArrayList<T>(list);
        return arrayList;
    }//Fin methode
}
