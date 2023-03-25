package jeuPrincipal;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import saving.*;

import application.ApplicationPrincipale6;
import chimie.MolType;
import chimie.ReactionDat;
import interfaces.Jeu;
import chimie.MolType;

/**
 * 
 * @author YohannLaou
 * @author Kepler Paul-Émile
 */
public class JeuPrincipal extends Jeu {

    private JPanel contentPane;

    private SceneDanimation sceneDanimation;
    private JButton btnRun;
    private JButton btnStop;
    private JButton btnAvancerPas;
    private JButton btnRecommencerPartie;
   
    private JLabel lblAcide = new JLabel("Acides:");
    private JLabel lblBase = new JLabel("Bases: ");
    private JLabel lblCombustible = new JLabel("combustibles: ");
    private JLabel lblComburant = new JLabel("comburants: ");
    private JLabel lblEau = new JLabel("eau: ");
    private JLabel lblMetal = new JLabel("Metaux: ");
    private JLabel lblReducteur = new JLabel("Reducteurs: ");
    private JLabel lblOxydant = new JLabel("Oxydants: ");
    private JLabel lblSel = new JLabel("Sel: ");
    private JLabel lblTotal = new JLabel("Total: ");
    private JLabel lblTirMiss = new JLabel("Tirs manques: ");
    private JLabel lblTempsBonus = new JLabel("Temps restant du bonus:");

    private JLabel lblAccel = new JLabel("Accelereration: ");
    private JLabel lblFriction = new JLabel("friction: ");
    private JLabel lblAffliction = new JLabel("Vitesse: ");

    /**
     * Démmarage du miniJeu, Ceci doit initialiser les propriété par défault
     * Cela démare aussi le thread
     */
    //YohannLaou
    @Override
    public void start() {
        if (!enCoursDAnimation) {

            enCoursDAnimation = true;
            Thread t = new Thread(this);
            t.start();

        }
    }// Fin methode

    /**
     * Arrete l'animation en cours
     */
  //YohannLaou
    public void stop() {
        enCoursDAnimation = false;
    }// Fin methode

    /**
     * Demarre l'animation
     */
  //YohannLaou
    public void boutonRun() {
        start();
        btnRun.setEnabled(false);
        btnStop.setEnabled(true);
        btnAvancerPas.setEnabled(false);
        sceneDanimation.requestFocusInWindow();
    }// Fin methode

    /**
     * Pause l'animation
     */
    //YohannLaou
    public void boutonPause() {
        stop();
        btnStop.setEnabled(false);
        btnAvancerPas.setEnabled(true);
        btnRun.setEnabled(true);
    }// Fin methode

    /**
     * prochain tick de simulation avec la scene etant en pause
     */
    //YohannLaou
    public void boutonNext() {
        sceneDanimation.loop();
    }// Fin methode

    /**
     * Arrete l'animation, reinitialise la scene, le score et la vague
     */
    //YohannLaou	
    public void boutonReset() {
        stop();
        btnStop.setEnabled(false);
        btnAvancerPas.setEnabled(true);
        btnRun.setEnabled(true);
        sceneDanimation.reset();
        repaint();
    }// Fin methode

    /**
     * Demarre le thread et initialise les sorties
     **/
    //YohannLaou
    @Override
    public void run() {
        while (enCoursDAnimation) {
            sceneDanimation.loop();
            // Updater les labels de la Scène d'animation
            lblAcide.setText("Acide: " + Stat.getT(MolType.ACIDE));
            lblBase.setText("Base: " + Stat.getT(MolType.BASE));
            lblCombustible.setText("Combustible: " + Stat.getT(MolType.COMBUSTIBLE));
            lblComburant.setText("Comburant: " + Stat.getT(MolType.COMBURANT));
            lblEau.setText("Eau: " + Stat.getT(MolType.EAU));
            lblMetal.setText("Metal: " + Stat.getT(MolType.METAL));
            lblReducteur.setText("Reducteur: " + Stat.getT(MolType.REDUCTEUR));
            lblOxydant.setText("Oxydant: " + Stat.getT(MolType.OXYDANT));
            lblSel.setText("Sel: " + Stat.getT(MolType.SEL));
            lblTotal.setText("Total: " + Stat.nbTypeNeutrTotal());

            lblTirMiss.setText("Tirs Manquées: " + Stat.tirManquees);
            lblTempsBonus.setText("TempsBonus: " + Stat.tempsBonus);

            lblAccel.setText("Acceleration: " + Stat.acceleration);
            lblFriction.setText("Friction: " + Stat.friction);
            lblAffliction.setText("Vitesse: " + Stat.vitesse);

            if (Stat.lost) {
                String nom = JOptionPane.showInputDialog(this,
                        "Bravo, vous avez obtenu un score de " + Stat.score + ". Mais la partie est fini." + "\nEntrer votre nom");
                if (nom != null) {
                    ScoreDat score = new ScoreDat(nom, Stat.score);
                    new ScoreSaver().save(score, nom);
                }
                sceneDanimation.reset();
                Stat.reset();
                
                var frameMenu = new ApplicationPrincipale6();
                frameMenu.setVisible(true);
                setVisible(false);
                
            }

            try {
                Thread.sleep(SLEEP);
            } catch (Exception ignored) {
            }
        }
    }// Fin methode

    /**
     * Creation de la fenetre
     */
    public JeuPrincipal() {

        setResizable(false);
        setTitle("La revanche du laboratoire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1350, 1150);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnRun = new JButton("Demarrer");
        btnRun.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boutonRun();
            }
        });
        sceneDanimation = new SceneDanimation();
        sceneDanimation.setBounds(10, 49, 1000, 1001);
        contentPane.add(sceneDanimation);

        btnRun.setBounds(1020, 49, 144, 130);
        contentPane.add(btnRun);

        btnStop = new JButton("Pause");
        btnStop.setEnabled(false);
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boutonPause();
            }
        });
        btnStop.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnStop.setBounds(1180, 49, 144, 130);
        contentPane.add(btnStop);

        btnAvancerPas = new JButton("Next pas");
        btnAvancerPas.setEnabled(false);
        btnAvancerPas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boutonNext();
            }
        });
        btnAvancerPas.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnAvancerPas.setBounds(1020, 190, 144, 130);
        contentPane.add(btnAvancerPas);

        btnRecommencerPartie = new JButton("Recommencer");
        btnRecommencerPartie.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boutonReset();
            }
        });
        btnRecommencerPartie.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnRecommencerPartie.setBounds(1180, 190, 144, 130);
        contentPane.add(btnRecommencerPartie);

        JPanel PanelSortieJeu = new JPanel();
        PanelSortieJeu.setBackground(new Color(192, 192, 192));
        PanelSortieJeu.setBounds(1020, 331, 304, 424);
        contentPane.add(PanelSortieJeu);
        PanelSortieJeu.setLayout(null);

        JLabel lblNeutralisation = new JLabel("Nombres de molecules neutralisees");
        lblNeutralisation.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNeutralisation.setBounds(10, 11, 250, 30);
        PanelSortieJeu.add(lblNeutralisation);

        lblAcide.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAcide.setBounds(52, 56, 100, 17);
        PanelSortieJeu.add(lblAcide);

        lblBase.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblBase.setBounds(52, 84, 100, 17);
        PanelSortieJeu.add(lblBase);

        lblCombustible.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblCombustible.setBounds(52, 112, 100, 17);
        PanelSortieJeu.add(lblCombustible);

        lblComburant.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblComburant.setBounds(52, 140, 100, 17);
        PanelSortieJeu.add(lblComburant);

        lblEau.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEau.setBounds(52, 168, 100, 17);
        PanelSortieJeu.add(lblEau);

        lblMetal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblMetal.setBounds(52, 196, 100, 17);
        PanelSortieJeu.add(lblMetal);

        lblReducteur.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblReducteur.setBounds(52, 224, 100, 17);
        PanelSortieJeu.add(lblReducteur);

        lblOxydant.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblOxydant.setBounds(52, 252, 100, 17);
        PanelSortieJeu.add(lblOxydant);

        lblSel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSel.setBounds(52, 280, 100, 17);
        PanelSortieJeu.add(lblSel);

        lblTirMiss.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTirMiss.setBounds(10, 350, 124, 17);
        PanelSortieJeu.add(lblTirMiss);

        lblTempsBonus.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTempsBonus.setBounds(10, 378, 260, 35);
        PanelSortieJeu.add(lblTempsBonus);
        
        lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTotal.setBounds(52, 308, 100, 17);
        PanelSortieJeu.add(lblTotal);

        JPanel panelSortiePhysique = new JPanel();
        panelSortiePhysique.setBackground(new Color(192, 192, 192));
        panelSortiePhysique.setBounds(1020, 766, 304, 284);
        contentPane.add(panelSortiePhysique);
        panelSortiePhysique.setLayout(null);

        JPanel panelVecteurVItesse = new JPanel();
        panelVecteurVItesse.setBounds(47, 30, 213, 99);
        panelSortiePhysique.add(panelVecteurVItesse);

        lblAccel.setBounds(28, 155, 126, 14);
        panelSortiePhysique.add(lblAccel);

        JLabel lblForces = new JLabel("Forces");
        lblForces.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblForces.setBounds(28, 180, 67, 27);
        panelSortiePhysique.add(lblForces);

        lblFriction.setBounds(28, 219, 79, 14);
        panelSortiePhysique.add(lblFriction);

        lblAffliction.setBounds(28, 234, 79, 14);
        panelSortiePhysique.add(lblAffliction);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1334, 38);
        contentPane.add(menuBar);

        JMenu mnAccueil = new JMenu("Accueil");
        menuBar.add(mnAccueil);

        JMenuItem mntmMenu = new JMenuItem("Retour au menu");
        mntmMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            stop();
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
        mnAccueil.add(mntmMenu);

        JMenuItem mntmLeave = new JMenuItem("Quitter le jeu");
        mntmLeave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JSeparator separator = new JSeparator();
        mnAccueil.add(separator);
        mnAccueil.add(mntmLeave);

        JMenu mnSettings = new JMenu("Parametres");
        menuBar.add(mnSettings);

        JMenu mnInstructions = new JMenu("Prof");
        menuBar.add(mnInstructions);

        JMenuItem mntnProf = new JMenuItem("Pour le prof");
        mntnProf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               affichePourProf();
            }

           
        });
        mnInstructions.add(mntnProf);
        
        JMenu mnInfosProf = new JMenu("Manuel instructions");
        menuBar.add(mnInfosProf);
        
        JMenuItem mntmControles = new JMenuItem("Les Controles");
        mntmControles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionControles();
            }

           
        });
        mnInfosProf.add(mntmControles);
        
        JMenuItem mntmLesAffinites = new JMenuItem("Affinités");
        mntmLesAffinites.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               montrerLesAffinites();
             
            }

            
        });
        mnInfosProf.add(mntmLesAffinites);
    }
    
    /**
     * montre les controles du joueur
     */
    //Kepler Paul-Émile
    private void actionControles() {
        // TODO Auto-generated method stub
        JOptionPane.showMessageDialog(null,"Le mouvement: flèche droite pour bouger à droite et flèche gauche pur bouger à gauche  "+""
                + "\n tirer: barre espace"+"\n la triche: P " );
    }
    /**
     * montrer les affinites 
     */
    //Kepler Paul-Émile
    private void montrerLesAffinites() {
        // TODO Auto-generated method stub
        JOptionPane.showMessageDialog(null,"voici les affinités de destruction: "+"\n Acide et Base"+
                "\n combustible et eau"+"\n oxydant et reducteur"+"\n comburant et eau "+"\n eau et sel"+"\n metal et oxydant"
                +"\n noble et tout(pas implimenté au sprint 3)");
    }
    /**
     * montre un message au professeur
     */
    //Kepler Paul-Émile
    private void affichePourProf() {
        // TODO Auto-generated method stub
        JOptionPane.showMessageDialog(null,"En touchant la lettre p vous activez des drops à chaque élimination" );
    }
}
