package jeuPrincipal;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import application.ApplicationPrincipale6;
import chimie.MolType;
import chimie.ReactionChecker;
import interfaces.Jeu;
import utils.*;

/**
 * La classe jeuPrincipal.Scene d'animation represente la scene dans laquelle le
 * tanks et le molécules se deplaceront. C'est cette classe qui s'occuperat de
 * la logique et de l'interaction entre tanks molecules et projectiles.
 * 
 * Cette classe gere la logique, c'est donc elle qui possède les informations
 * liés a l'etats du jeu: * Munitions, Vie...
 * 
 * @author Brian Normant
 * @author Maysa Zahi
 * @author Kepler Paul-Émile
 * @author YohannLaou
 **/
public class SceneDanimation extends JPanel {
    private static final long serialVersionUID = 1L;
    private final static int DEFAULT_AMMO = 25;
    final static int DIMX = ApplicationPrincipale6.REDIM ? 789 : 1000, DIMY = ApplicationPrincipale6.REDIM ? 482 : 950;

    private Tank tank;
    private MolType selectedAmmo = MolType.ACIDE;
    private Map<MolType, Integer> munitions = Collections.synchronizedMap(new HashMap<>());
    private Cartouches cartouche;
    private List<Molecule> mols = Collections.synchronizedList(new ArrayList<>());
    private List<Projectile> pewpews = Collections.synchronizedList(new ArrayList<Projectile>());
    private List<Projectile> pewPewsEnnemis = Collections.synchronizedList(new ArrayList<Projectile>());
    /** Une barre de vie qui représente les points de vie du char d'assaut **/
    private BarreDeVie barre;
    /** Un petit ajustement **/
    private double ajustement = -325;
    /** La vie courante du char d'assaut **/
    private double vieActuelleTank;
    /** Pourcentage de la vie courante du char d'assaut **/
    private double pourcentageVie = 10; // le tank a tous ses points de vie au départ

    /** Panneau qui affiche le score du joueur et la vague **/
    private Afficheur afficheur;

    private boolean goingLeft = true;

    private List<Drop> drops = Collections.synchronizedList(new ArrayList<Drop>());

    /** le domaine des nombres généré aléatoirement **/
    private final int DOMAINE_DROP = 100;
    /** propriété de triche du prof **/
    private boolean triche = false;

    private final static int DEFAULT_AMMO_TRICHE = 30;
    /** Ne faire bougé les molécule que tout les X ticks **/
    private int tick = 0;

    /**
     * Constructeur de la Scene, crée une scene simple avec un tanks et des
     * molecules Le nombre de Molécules depends du niveau actuel et de la difficulté
     */
    // Maysa Zahi
    public SceneDanimation() {
 

        this.afficheur = new Afficheur(50, 0);

        this.tank = new Tank(DIMX, DIMY);
        this.vieActuelleTank = tank.getVieMax(); // initialiser la vie actuelle à la vie maximale
        this.barre = new BarreDeVie(0, (DIMX / 2) - ajustement, DIMX, 10);
        cartouche = new Cartouches(DIMX, DIMY);
        this.pourcentageVie = 100.0; // pourcentage initiale de la barre de vie du char d'assaut

        barre.setDimScene(DIMX); // prend la largeur de la scene

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                manipulerTank(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                slowDown(e);

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cliquerSurCartouches(e);
            }

        });
        setBackground(Color.white);
        buildScene();
    }

    /**
     * Creer une scene, place le tanks au centre, et place les enemis au bons
     * endroits.
     */
    // Brian Normant
    private void buildScene() {
        implementeDiff();
        if (triche == false) {
            if (munitions.isEmpty()) {
                for (MolType v : MolType.values()) {
                    munitions.put(v, DEFAULT_AMMO);
                }
            } else {
                munitions.forEach((v, ammo) -> ammo += DEFAULT_AMMO);
                mols = Molecule.makeMolecules(3, DIMX, (int) (DIMY * 1 / 4));
                System.out.println("Création de " + mols.size() + " molécules!");
            }
        } else {
            if (munitions.isEmpty()) {
                for (MolType v : MolType.values()) {
                    munitions.put(v, DEFAULT_AMMO_TRICHE);
                }
            } else {
                munitions.forEach((v, ammo) -> ammo += DEFAULT_AMMO_TRICHE);
                mols = Molecule.makeMolecules(3, DIMX, (int) (DIMY * 1 / 4));

                System.out.println("Création de " + mols.size() + " molécules!");
            }
        }
        this.tank = new Tank(DIMX, DIMY);
    }// Fin methode

    /**
     * Reinitialise la scene, le score et la vague.
     */
    // Brian Normant
    public void reset() {
        Molecule.clean();
        synchronized (munitions) {
            munitions.clear();
        }
        synchronized (mols) {
            mols.clear();
        }
        synchronized (pewPewsEnnemis) {
            pewPewsEnnemis.clear();
        }
        synchronized (pewpews) {
            pewpews.clear();
        }
        synchronized (drops) {
            drops.clear();
        }

        pourcentageVie = tank.getVieMax() * 100;
        barre.setCoefficientReducteur(pourcentageVie / 100.0);

        Stat.score = 0;
        pewpews.clear();
        pewPewsEnnemis.clear();
        drops.clear();
        this.buildScene();
        Stat.reset();
        repaint();
    }// Fin methode

    /**
     * Tirer au cannon. Cette méthode verifie si le cannon peut tirer, puis fais
     * tirer un projectile avec le type de munitions spécifier
     */
    // Kepler Paul-Émile
    private void fireGun() {
        if (munitions.get(selectedAmmo) != 0) {
            // Spawn projectile here
            // The tanks need a get bullet location method to set the emplacement of the
            // projectile
            munitions.put(selectedAmmo, munitions.get(selectedAmmo) - 1);

            pewpews.add(tank.tirer(selectedAmmo));

        } else {
            // Do we want to tell the user the ammo selected is empty, Fire a sound effect
            // from here
        }
    }// Fin methode

    /**
     * méthode qui gère le mouvement des molécules et leur tirs
     * 
     */
    // Brian Normant
    private void handleMolecules() {

        if (Molecule.doBounce(mols, DIMX)) {
            mols.forEach(mol -> mol.move(!goingLeft));
            goingLeft = !goingLeft;
            mols.forEach(mol -> mol.descend());
        } else {
            mols.forEach(mol -> mol.move(goingLeft));
        }
        mols.forEach(mol -> {
            if (new Random().nextInt((int) (Math.round(80 / Jeu.getFacteur()))) < 1)
                pewPewsEnnemis.add(mol.tirer());
        });
    }// Fin methode

    /**
     * Retourne les dimentions du panel
     * 
     * @return
     */
    // Maysa Zahi
    public static int getDimX() {
        return DIMX;
    }

    /**
     * change les munitions
     * 
     * @param i l'indice du MolType dans l'Enum MolType
     */
    // Kepler Paul-Émile
    private void changeMunitions(int i) {

        selectedAmmo = MolType.values()[i - 1];
        tank.setCouleurTank(selectedAmmo.color());
        repaint();
    }// Fin methode

    /**
     * Permet l'accès à la longeur de la scène
     * 
     * @return DIMY La longeur de la scène
     */
    // Maysa Zahi
    public static int getDimY() {
        return DIMY;
    }

    /**
     * Manipule le char d'assaut, le fait bouger horizentalement Et lui permet de
     * tirer des projectiles
     * 
     * @param e L'evenement
     */
    // Maya Zahi
    private void manipulerTank(KeyEvent e) {

        int keyCode = e.getKeyCode();

        switch (keyCode) {
            // section ou on gère les changements de munitions
            case KeyEvent.VK_1 -> changeMunitions(1);
            case KeyEvent.VK_2 -> changeMunitions(2);
            case KeyEvent.VK_3 -> changeMunitions(3);
            case KeyEvent.VK_4 -> changeMunitions(4);
            case KeyEvent.VK_5 -> changeMunitions(5);
            case KeyEvent.VK_6 -> changeMunitions(6);
            case KeyEvent.VK_7 -> changeMunitions(7);
            case KeyEvent.VK_8 -> changeMunitions(8);
            case KeyEvent.VK_9 -> changeMunitions(9);
            // fin de la section

            case KeyEvent.VK_RIGHT -> {

                // tank se deplace vers la droite
                tank.goRight();
            }
            case KeyEvent.VK_LEFT -> {
                // tank se deplace vers la gauche
                tank.goLeft();
            }
            case KeyEvent.VK_SPACE -> {
                // tire des projectiles
                fireGun();
            }
            case KeyEvent.VK_P -> {
                activerDesactiverTriche();
            }
        }
    }// Fin methode

    /**
     * Permet au char d'assaut de ralentir jusqu'à s'arreter compltètement lorsqu'on
     * n'appuie plus sur les touches de déplacement
     */
    // Maysa Zahi
    private void slowDown(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT -> tank.motorOff();
        }
    }// Fin methode

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
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.scale(Jeu.FACTEUR_REDIM, Jeu.FACTEUR_REDIM);

        tank.dessiner(g2d);

        mols.forEach(mol -> mol.dessiner(g2d));

        synchronized (pewpews) {
            pewpews.forEach(pew -> pew.dessiner(g2d));
        }

        synchronized (pewPewsEnnemis) {
            pewPewsEnnemis.forEach(ennemi -> ennemi.dessiner(g2d));
        }
        synchronized (drops) {
            drops.forEach(drop -> drop.dessiner(g2d));
        }

        // Dessiner l'UI
        barre.dessiner(g2d);
        afficheur.dessiner(g2d);
        cartouche.dessiner(g2d, munitions);

    }// fin paintComponent

    /**
     * Permet au char d'assaut de rentrer en collision avec les murs de la scène
     * tout en changeant sa direction vers le coté opposé
     */
    // Maysa Zahi
    public void bounceTank() {

        double newX = tank.getX();

        tank.tick();

        if (tank.bounce(DIMX)) {
            // Fixer la nouvelle position x dans les limites du panel
            newX = Math.min(DIMX - Tank.getLargeur(), Math.max(0, newX));
            tank.setX(newX);
            tank.setVx(-tank.getVx()); // reverse la vitesse
            tank.tick();
        }
    }// Fin methode

    /**
     * Detecte si les projectiles ennemis ont touché le char d'assaut Si oui, le
     * char d'assaut se prend des degats et sa barre de vie diminue en conséquence
     */
    // Maysa Zahi
    public void detecterCollision() {
        List<Projectile> destructionTirEnnemis = new ArrayList<Projectile>();
        for (Projectile pewpew : pewPewsEnnemis) {
            if (pewpew.estTouche(tank) && !pewpew.aTouche()) {
                // un projectile a touché le tank pour la première fois
                vieActuelleTank -= pewpew.getDegats();
                destructionTirEnnemis.add(pewpew);
                if (vieActuelleTank < 0) {
                    vieActuelleTank = 0;
                }

                // Met à jour la barre de vie
                pourcentageVie = vieActuelleTank / tank.getVieMax() * 100;
                barre.setCoefficientReducteur(pourcentageVie / 100.0);

                // Marque le projectile comme ayant touché le char d'assaut
                pewpew.setTouche(true);

                break;
            }
        }
        pewPewsEnnemis.removeAll(destructionTirEnnemis);
    }// Fin methode

    /**
     * Permet de mettre à jour le score du joueur à mesure qu'il arrive à tuer des
     * ennemis
     */
    // Maysa Zahi
    public void calculerScore(int nombreMoleculesTouchees) {

        int scoreGagne = (int) (Math.round(nombreMoleculesTouchees * 10 * Jeu.getFacteur()));
        Stat.score += scoreGagne;
        System.out.println("Score: " + Stat.score);
    }// Fin methode

    /**
     * Boucle principale, se charge d'appelé des differentes logique lorsque le jeu
     * tourne
     * 
     */
    // Brian Normant
    public void loop() {
        if (Stat.lost)
            return;
        synchronized (this) {
            bounceTank();
            detecterCollision();
            tank.tick();
            collisionAvecMolecules();
            collisionDrop();
            collisionExtremiteProjectile();
            // GARDER CETTE MÉTHODE
            pewpews.forEach(Projectile::tick);
            pewPewsEnnemis.forEach(Projectile::tick);
            drops.forEach(Drop::tick);
            if (tick == 25) {
                handleMolecules();
                tick = 0;
            } else
                tick++;
            // regarde si la partie se termine
            if (vieActuelleTank <= 0 || Molecule.lowestLowerThan(mols, 585)) {
                Stat.lost = true;
                // JeuPrincipale se charger de tous reset puis de demandé au joueur son nom
            }
            if (mols.size() == 0) {
                // Arrête le thread s'il n'y a plus de molécules
                buildScene();
                Stat.vague++;
            }
        }
        repaint();
    }// Fin methode

    /**
     * Méthode qui détermine si un drop de munition apparait ou pas
     */
    // Kepler Paul-Émile
    public boolean chanceDrop() {
        boolean tombeOuPas = false;
        if (triche) {
            tombeOuPas = true;
        } else {
            if (new Random().nextInt(DOMAINE_DROP) % 5 == 0) {
                tombeOuPas = true;
            } else {
                tombeOuPas = false;
            }
        }
        return tombeOuPas;
    }// Fin methode

    /**
     * Méthode qui gère les collisions entre les munitions qui tombe et appelle la
     * méthode qui régénère les munitions
     */
    // Kepler Paul-Émile
    public void collisionDrop() {
        synchronized (drops) {
            List<Drop> destructionDrop = new ArrayList<Drop>();
            drops.forEach(drop -> {
                if (U.collision(tank.getZone(), drop.getArea())) {
                    destructionDrop.add(drop);
                    munitions.put(drop.type(), munitions.get(drop.type()) + drop.getMun());
                }
            });
            drops.removeAll(destructionDrop);
        }

    }// Fin methode

    /**
     * Méthode qui détecte la collision d'un projectile lancé par l'utilisateur avec
     * une molécule et agit en conséquence
     */
    // Kepler Paul-Émile
    private void collisionAvecMolecules() {
        synchronized (pewpews) {
            synchronized (mols) {
                List<Projectile> destructionTir = new ArrayList<Projectile>();
                List<Molecule> destructionMole = new ArrayList<Molecule>();
                mols.forEach(mol -> {

                    pewpews.forEach(tir -> {
                        {
                            if (tir.toucheMole(mol)) {

                                destructionTir.add(tir);
                                Stat.incType(mol.getMolType());
                                if (ReactionChecker.destruction(tir.getMolType(), mol.getMolType())) {
                                    destructionMole.add(mol);

                                    if (chanceDrop()) {
                                        drops.add(mol.genDrop());
                                    }
                                    calculerScore(1);
                                } else if (ReactionChecker.amplification(tir.getMolType(), mol.getMolType())) {
                                    // Rien n'arrive pour le moment à voir au sprint 3
                                }
                                // si aucune des des conditions n'est remplie le tir disparait tout simplement
                            }
                        }
                    });

                });
                pewpews.removeAll(destructionTir);
                mols.removeAll(destructionMole);
            }
        }
    }// Fin methode

    /**
     * Méthode qui reconnait le clique sur une cartouches
     * 
     * @param e gère l'évenement
     */
    // Kepler Paul-Émile
    private void cliquerSurCartouches(MouseEvent e) {
        // TODO Auto-generated method stub
        var touche = cartouche.reconnaitreClick(e.getX(), e.getY());

        if (touche != null) {
            changeMunitions(touche.getType().ordinal() + 1);
            // rien n'arrive
        }
    }// Fin Methode

    /**
     * Méthode qui détecte l'entré du projectile dans l'extrémité y de la fenetre
     * 
     */
    // Kepler Paul-Émile
    private void collisionExtremiteProjectile() {
        List<Projectile> destructionTir = new ArrayList<Projectile>();
        List<Projectile> destructionTirEn = new ArrayList<Projectile>();

        pewpews.forEach(tir -> {
            if (tir.getY() < -2) {

                destructionTir.add(tir);
                Stat.tirManquees++;
            }
        });
        pewpews.removeAll(destructionTir);

        pewPewsEnnemis.forEach(molTir -> {

            if (molTir.getY() > getDimY()) {

                destructionTirEn.add(molTir);

            }
        });
        pewPewsEnnemis.removeAll(destructionTirEn);
    }// Fin methode

    /**
     * Donne le nombre de vagues avant un mini-jeu
     */
    // YohannLaou
    public void calculNbVagues() {
        switch (Jeu.getDifficulty()) {
            case "Moyen": {
                Stat.totalVague = 4;
                break;
            }
            case "Difficule": {
                Stat.totalVague = 3;
                break;
            }
            default:
                Stat.totalVague = 2;
        }
    }// Fin Methode

    /**
     * Implemente la difficulte
     */
    // YohannLaou
    private void implementeDiff() {
        Jeu.calculFacteur();
        calculNbVagues();
    }// Fin methode

    //
    // Les méthodes de triche
    //
    //
    /***
     * active la triche du prof
     */
    // Kepler Paul-Émile
    private void activerDesactiverTriche() {
        // TODO Auto-generated method stub
        if (triche) {
            triche = false;
        } else {
            triche = true;
            System.out.println("triche enclenché");
        }
        reset();

    }
}
