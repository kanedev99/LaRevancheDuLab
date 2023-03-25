package jeu4;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import interfaces.Jeu;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Jeu4 extends Jeu {

    private JPanel contentPane;
   
    // ajouter le support pour lancer des evenements de type PropertyChange
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private SceneDanimation4 sceneDanimation4;
    private JButton btnDemarrer;
    private JButton btnArreter;

    /**
     * voici la methode qui permettra de s'ajouter en tant qu'ecouteur
     */
    //YohannLaou
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }// Fin methode

    /**
     * Creation de la fenetre
     */
    public Jeu4() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1165, 882);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        sceneDanimation4 = new SceneDanimation4();
        sceneDanimation4.setBounds(10, 27, 1000, 907);
        contentPane.add(sceneDanimation4);
        
        btnDemarrer = new JButton("Demarrer");
        btnDemarrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        btnDemarrer.setBounds(1007, 41, 134, 116);
        contentPane.add(btnDemarrer);
        
        btnArreter = new JButton("Arreter");
        btnArreter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        btnArreter.setBounds(1020, 190, 121, 116);
        contentPane.add(btnArreter);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(enCoursDAnimation) {
        sceneDanimation4.loop();
        
        try {
            Thread.sleep(SLEEP);
        } catch (Exception ignored) {
        }
        }
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        if (!enCoursDAnimation) {

            enCoursDAnimation = true;
            Thread t = new Thread(this);
            t.start();

        }
    }
    public void stop() {
        enCoursDAnimation=false;
    }
}

