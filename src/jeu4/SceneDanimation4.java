package jeu4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import utils.Vecteur2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class SceneDanimation4 extends JPanel {

    private double largeurDuComposantEnMetres = 10;
    private double hauteurDuComposantEnMetres;
    private double pixelsParMetre;
    
    private CercleOrientation cercleO;
    private MirroirP miroir;
    private Rayon rayon;
    private boolean surCercle=false;
    private boolean premiereCreation=true;
    private List<MirroirP>mirroirs=new ArrayList<MirroirP>();
    /**
     * Create the panel.
     */
    public SceneDanimation4() {
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(surCercle) {
                    cercleO.changeVecteur(e.getX(), e.getY());
                    rayon.nouvelleDirection(new Vecteur2D(e.getX(),e.getY()));
                    surCercle=cercleO.contient(e.getX(), e.getY());
                }
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                gererOrientation(e);
            }

            private void gererOrientation(MouseEvent e) {
                // TODO Auto-generated method stub
                
                if(cercleO.contient(e.getX(), e.getY()));
                {
                    surCercle=true;
                    rayon.nouvelleDirection(new Vecteur2D(e.getX(),e.getY()));
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                surCercle=false;
            }
        });
       
       

        setBackground(Color.white);
    }
    
    
    
    /**
     * Permet de dessiner une scene
     * 
     * @param g Contexte graphique
     */
    // Kepler Paul-Émile
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        if(premiereCreation) {
            
        cercleO=new CercleOrientation(getWidth(),getHeight());
        rayon=new Rayon(getWidth(),getHeight());
        miroir=new MirroirP(getWidth(),getHeight(),new Vecteur2D(1,-1),new Vecteur2D(2.5,1));
          //miroir=new MirroirP(getWidth(),getHeight(),new Vecteur2D(0,20),new Vecteur2D(getWidth()/2,getHeight()/2));
          
        pixelsParMetre = getWidth()/largeurDuComposantEnMetres;
        
        hauteurDuComposantEnMetres = getHeight()/pixelsParMetre;
        
        premiereCreation=false;
        
     
        }
        miroir.setPixelsParMetres(pixelsParMetre);
        
         rayon.dessiner(g2d);   
        cercleO.dessiner(g2d);
        miroir.dessiner(g2d);
    }

    
    
    /**
     * la boucle d'animation
     */
    public void loop() {
        
        miroir.tick();
        repaint();
        
    }
}
