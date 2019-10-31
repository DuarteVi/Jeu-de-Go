import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.event. *;

class Menu extends JPanel implements ActionListener {

	Fenetre fenetre;

	private JButton jouer;
	private JButton quitter;


	private int tailleGoban = 19;	 //demande accesseur
	private JRadioButton taille9;
	private JRadioButton taille13;
	private JRadioButton taille19;

	private JRadioButton handicap0;
	private JRadioButton handicap1;
	private JRadioButton handicap2;
	private JRadioButton handicap3;
	private JRadioButton handicap4;
	private JRadioButton handicap5;
	private JRadioButton handicap6;
	private JRadioButton handicap7;
	private JRadioButton handicap8;
	private JRadioButton handicap9;

	private JPanel handicap0a5 = new JPanel();
	private JPanel handicap6a9 = new JPanel();
    private JPanel handicapPanel = new JPanel();


    private JPanel tempsType = new JPanel();
    private JPanel tempsChoixT = new JPanel();
	private JPanel tempsPanel = new JPanel();

    private JRadioButton tempsAucun;
    private JRadioButton tempsAbsolu;
    private JRadioButton tempsByoY;

    private JButton tempsPlus;
    private JButton tempsMoins;
    private int horloge = 1;
    private int secondes = 1800;


    private JButton tempsPlusReflexion;
    private JButton tempsMoinsReflexion;
    private int secondesRelexion = 600;

    private JButton tempsPlusByoY;
    private JButton tempsMoinsByoY;
    private int nbByoY = 3;

    private JButton tempsPlusPeriodeByoY;
    private JButton tempsMoinsPeriodeByoY;
    private int secondesPeriodeByoY = 15;


    private float komi = 7.5f;
    private JButton komiPlus;
    private JButton komiMoins;
    private JPanel panKomi;


	private int [] handicap;			//demande accesseur

	public Menu(Fenetre fenetre ){

		this.fenetre = fenetre;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel menu = new JLabel("Menu");
        menu.setFont(new Font("Menu",Font.BOLD,50));
		menu.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(menu);

		this.jouer = new JButton ("JOUER"); 
		this.jouer.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.jouer.addActionListener(this);
		this.add(this.jouer);


		JLabel option = new JLabel("Options");
        option.setFont(new Font("Options",Font.BOLD,30));
		option.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(option);

		JLabel ecrire;
		/* ---------------------------------------------------------------- OPTIONS ---------------------------------------------------------------- */

		/* ---------- Taille ---------- */
		ecrire = new JLabel("Taille");
        ecrire.setFont(new Font("Taille",Font.BOLD,20));
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(ecrire);
		this.taille9 = new JRadioButton("Goban taille 9");
        this.taille9.setBackground(new Color(238,221,221));
		this.taille9.addActionListener(this);
		this.taille13 = new JRadioButton("Goban taille 13");
        this.taille13.setBackground(new Color(238,221,221));
		this.taille13.addActionListener(this);
		this.taille19 = new JRadioButton("Goban taille 19");
        this.taille19.setBackground(new Color(238,221,221));
		this.taille19.setSelected(true);
		this.taille19.addActionListener(this);

		ButtonGroup taille = new ButtonGroup();
		taille.add(this.taille9);
		taille.add(this.taille13);
		taille.add(this.taille19);

		JPanel panTaille = new JPanel();
        //panTaille.setBackground(new Color(238,221,221));

		panTaille.setLayout(new BoxLayout(panTaille, BoxLayout.X_AXIS));

		panTaille.add(this.taille9);
		panTaille.add(this.taille13);
		panTaille.add(this.taille19);

		panTaille.setAlignmentX( Component.CENTER_ALIGNMENT );
        panTaille.setBackground(new Color(238,221,221));

		this.add(panTaille);

		/* ---------- Handicap ---------- */
		ecrire = new JLabel("Handicap");
        ecrire.setFont(new Font("Handicap",Font.BOLD,20));
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(ecrire);
		ButtonGroup handicapGroupe = new ButtonGroup();

		this.handicap0a5.setLayout(new BoxLayout(this.handicap0a5, BoxLayout.X_AXIS));

		this.handicap0 = new JRadioButton("0 pierre");
        this.handicap0.setBackground(new Color(238,221,221));
        this.handicap0.addActionListener(this);
		this.handicap0a5.add(this.handicap0);
		handicapGroupe.add(this.handicap0);
		this.handicap0.setSelected(true);
		this.handicap1 = new JRadioButton("1 pierre");
        this.handicap1.setBackground(new Color(238,221,221));
        this.handicap1.addActionListener(this);
		this.handicap0a5.add(this.handicap1);
		handicapGroupe.add(this.handicap1);
		this.handicap2 = new JRadioButton("2 pierres");
        this.handicap2.setBackground(new Color(238,221,221));
        this.handicap2.addActionListener(this);
		this.handicap0a5.add(this.handicap2);
		handicapGroupe.add(this.handicap2);
		this.handicap3 = new JRadioButton("3 pierres");
        this.handicap3.setBackground(new Color(238,221,221));
        this.handicap3.addActionListener(this);
		this.handicap0a5.add(this.handicap3);
		handicapGroupe.add(this.handicap3);
		this.handicap4 = new JRadioButton("4 pierres");
        this.handicap4.setBackground(new Color(238,221,221));
        this.handicap4.addActionListener(this);
		this.handicap0a5.add(this.handicap4);
		handicapGroupe.add(this.handicap4);
		this.handicap5 = new JRadioButton("5 pierres");
        this.handicap5.setBackground(new Color(238,221,221));
        this.handicap5.addActionListener(this);
		this.handicap0a5.add(this.handicap5);
		handicapGroupe.add(this.handicap5);
		
		this.handicap6a9.setLayout(new BoxLayout(this.handicap6a9, BoxLayout.X_AXIS));

		this.handicap6 = new JRadioButton("6 pierres");
        this.handicap6.setBackground(new Color(238,221,221));
        this.handicap6.addActionListener(this);
		this.handicap6a9.add(this.handicap6);
		handicapGroupe.add(this.handicap6);
		this.handicap7 = new JRadioButton("7 pierres");
        this.handicap7.setBackground(new Color(238,221,221));
        this.handicap7.addActionListener(this);
		this.handicap6a9.add(this.handicap7);
		handicapGroupe.add(this.handicap7);
		this.handicap8 = new JRadioButton("8 pierres");
        this.handicap8.setBackground(new Color(238,221,221));
        this.handicap8.addActionListener(this);
		this.handicap6a9.add(this.handicap8);
		handicapGroupe.add(this.handicap8);
		this.handicap9 = new JRadioButton("9 pierres");
        this.handicap9.setBackground(new Color(238,221,221));
        this.handicap9.addActionListener(this);
		this.handicap6a9.add(this.handicap9);
		handicapGroupe.add(this.handicap9);

		this.handicapPanel.setLayout(new BoxLayout(this.handicapPanel, BoxLayout.Y_AXIS));

        this.handicap0a5.setBackground(new Color(238,221,221));
        this.handicap6a9.setBackground(new Color(238,221,221));
        this.handicapPanel.setBackground(new Color(238,221,221));        

		this.handicapPanel.add(this.handicap0a5);
		this.handicapPanel.add(this.handicap6a9);
		this.add(this.handicapPanel);


		/* ---------- Temps ---------- */
        this.tempsPanel.setLayout(new BoxLayout(this.tempsPanel, BoxLayout.Y_AXIS));

        ecrire = new JLabel("Temps");
        ecrire.setFont(new Font("Temps",Font.BOLD,20));
        ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsPanel.add(ecrire);

        this.tempsType.setLayout(new BoxLayout(this.tempsType, BoxLayout.X_AXIS));

        ButtonGroup tempsGroupe = new ButtonGroup();
        //
        this.tempsAucun = new JRadioButton("Aucune Horloge");
        this.tempsAucun.setBackground(new Color(238,221,221));
        this.tempsType.add(this.tempsAucun);
        tempsGroupe.add(this.tempsAucun);
        this.tempsAucun.setSelected(true);
        this.tempsAucun.addActionListener(this);
        //
        this.tempsAbsolu = new JRadioButton("Horloge Absolue");
        this.tempsAbsolu.setBackground(new Color(238,221,221));
        this.tempsType.add(this.tempsAbsolu);
        tempsGroupe.add(this.tempsAbsolu);
        this.tempsAbsolu.addActionListener(this);
        //
        this.tempsByoY = new JRadioButton("Byo-Yomi");
        this.tempsByoY.setBackground(new Color(238,221,221));
        this.tempsType.add(this.tempsByoY);
        tempsGroupe.add(this.tempsByoY);
        this.tempsByoY.addActionListener(this);

        this.tempsChoixT.setLayout(new BoxLayout(this.tempsChoixT, BoxLayout.X_AXIS));
        //Se Remplie dans des fonctions
        
        this.tempsType.setBackground(new Color(238,221,221));
        this.tempsChoixT.setBackground(new Color(238,221,221));
        this.tempsPanel.setBackground(new Color(238,221,221));

        this.tempsPanel.add(this.tempsType);
        //this.tempsPanel.add(this.tempsChoixT);
        JScrollPane scrollPane = new JScrollPane(this.tempsPanel);
        scrollPane.setMinimumSize(new Dimension(700,120));
        scrollPane.setMaximumSize(new Dimension(700,120));
        scrollPane.setPreferredSize(new Dimension(700,120));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.add(scrollPane);


                //TEMPS BYO YOMI

        
        /* ---------- KOMI ---------- */
        ecrire = new JLabel("Komi");
        ecrire.setFont(new Font("Komi",Font.BOLD,20));
        ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.add(ecrire);

        this.panKomi = new JPanel();
        this.panKomi.setLayout(new BoxLayout(panKomi, BoxLayout.X_AXIS));
        this.panKomi.setBackground(new Color(238,221,221));


        this.komi();

        this.add(this.panKomi);

		/* ---------- Quitter ---------- */
		this.quitter = new JButton ("QUITTER"); 
		this.quitter.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.quitter.addActionListener(this);
		this.add(this.quitter);

		this.setBackground(new Color(238,221,221));

	}

    public void actionPerformed(ActionEvent e) {
    	//Lancer Partie
    	if (this.jouer == e.getSource())
    	{
    		this.commencerPartie();
    	}

    	//Gérer les handicaps
    	if (this.taille9 == e.getSource() || this.taille13 == e.getSource())
    	{
    		this.handicapPanel.remove(this.handicap6a9);

    		if ( this.handicap6.isSelected() || this.handicap7.isSelected() || this.handicap8.isSelected() || this.handicap9.isSelected())
    		{
    			this.handicap5.setSelected(true);
    		}
    	}
    	if (this.taille19 == e.getSource())
    	{
    		this.handicapPanel.add(this.handicap6a9);
    	}

        //Gérer handicap et Komi
        if ( this.handicap1 == e.getSource()  || this.handicap2 == e.getSource()  || this.handicap3 == e.getSource() ||
             this.handicap4 == e.getSource()  || this.handicap5 == e.getSource()  || this.handicap6 == e.getSource() ||
             this.handicap7 == e.getSource()  || this.handicap8 == e.getSource()  || this.handicap9 == e.getSource() )
        {
            this.komi = 0f;
            this.panKomi.removeAll();
        
            JLabel ecrire = new JLabel(""+this.komi+"");
            ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
            this.panKomi.add(ecrire);
            //this.add(this.panKomi);
        }
        if ( this.handicap0 == e.getSource() )
        {
            this.komi = 7.5f;
            this.komi();
        }

        //Gerer le temps
        if ( this.tempsAucun == e.getSource() )
        {
            this.tempsPanel.remove(this.tempsChoixT);
            this.horloge = 1;
        }
        if (  this.tempsAbsolu == e.getSource()  )
        {
            //this.tempsPanel.add(this.tempsChoixT);
            this.tempsAbsolu();
            this.horloge = 2;
        }
        if (  this.tempsByoY == e.getSource()  )
        {
            //this.tempsPanel.add(this.tempsChoixT);
            this.tempsByoY();
            this.horloge = 3;
        }

        //Gerer les secondes ABSOLUES
        if ( this.tempsPlus == e.getSource() )
        {
            this.tempsAbsoluModifier(60);
        }
        if ( this.tempsMoins == e.getSource() )
        {
            this.tempsAbsoluModifier(-60);
        }

        //Gerer les secondes REFLEXIONS
        if ( this.tempsPlusReflexion == e.getSource() )
        {
            this.tempsReflexionModifier(60);
        }
        if ( this.tempsMoinsReflexion == e.getSource() )
        {
            this.tempsReflexionModifier(-60);
        }

        //Gerer Nombres de Byoy
        if ( this.tempsPlusByoY == e.getSource() )
        {
            this.nombreByoYModifier(1);
        }
        if ( this.tempsMoinsByoY == e.getSource() )
        {
            this.nombreByoYModifier(-1);
        }

        //Gerer les secondes PERIODE
        if ( this.tempsPlusPeriodeByoY == e.getSource() )
        {
            this.tempsPeriodeModifier(5);
        }
        if ( this.tempsMoinsPeriodeByoY == e.getSource() )
        {
            this.tempsPeriodeModifier(-5);
        }

        //Komi
        if ( this.komiPlus == e.getSource() )
        {
            this.komiModifier(0.5f);
        }
        if ( this.komiMoins == e.getSource() )
        {
            this.komiModifier(-0.5f);
        }

    	this.repaint();
    	this.fenetre.setVisible(true);

    	//Quitter le jeu
    	if (this.quitter == e.getSource())
    	{
    		this.fenetre.dispose();
    	}
    }

/* ----------------------------------------------------------------------------------------------------- */
/* ------------ TEMPS ------------ */
    private void tempsAbsolu()
    {
        this.tempsChoixT.removeAll();

        JLabel ecrireSeconde;

        int min = this.secondes/60;
        int sec = this.secondes%60;

        ecrireSeconde = new JLabel(""+min+" minute(s) ");

        this.tempsChoixT.add(ecrireSeconde);

                //TEMPS ABSOLU
        JPanel tempsBoutons = new JPanel();
        tempsBoutons.setLayout(new BoxLayout(tempsBoutons, BoxLayout.Y_AXIS));

        this.tempsPlus = new JButton ("+"); 
        this.tempsPlus.setSize(20,20);
        this.tempsPlus.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsPlus.addActionListener(this);
        tempsBoutons.add(this.tempsPlus);
        //
        this.tempsMoins = new JButton ("-"); 
        this.tempsMoins.setSize(20,20);
        this.tempsMoins.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsMoins.addActionListener(this);
        tempsBoutons.add(this.tempsMoins);

        tempsBoutons.setBackground(new Color(238,221,221));

        this.tempsChoixT.add(tempsBoutons);

        this.tempsPanel.add(this.tempsChoixT);
    }

    private void tempsByoY()
    {
        this.tempsChoixT.removeAll();

        JLabel ecrire;
        int min = this.secondesRelexion/60;
        int sec = this.secondesRelexion%60;

        //Réfléxion
        ecrire = new JLabel("Temps de Reflexion : "+min+" minute(s) ");
        this.tempsChoixT.add(ecrire);

        JPanel tempsBoutons = new JPanel();
        tempsBoutons.setLayout(new BoxLayout(tempsBoutons, BoxLayout.Y_AXIS));

        this.tempsPlusReflexion = new JButton ("+"); 
        this.tempsPlusReflexion.setSize(20,20);
        this.tempsPlusReflexion.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsPlusReflexion.addActionListener(this);
        //
        this.tempsMoinsReflexion = new JButton ("-"); 
        this.tempsMoinsReflexion.setSize(20,20);
        this.tempsMoinsReflexion.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsMoinsReflexion.addActionListener(this);

        tempsBoutons.add(this.tempsPlusReflexion);
        tempsBoutons.add(this.tempsMoinsReflexion);
        tempsBoutons.setBackground(new Color(238,221,221));
        this.tempsChoixT.add(tempsBoutons);

        //Nombre
        ecrire = new JLabel("   Nombre de Byo-Yomi : " + this.nbByoY);
        this.tempsChoixT.add(ecrire);

        tempsBoutons = new JPanel();
        tempsBoutons.setLayout(new BoxLayout(tempsBoutons, BoxLayout.Y_AXIS));
        this.tempsPlusByoY = new JButton ("+"); 
        this.tempsPlusByoY.setSize(20,20);
        this.tempsPlusByoY.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsPlusByoY.addActionListener(this);
        //
        this.tempsMoinsByoY = new JButton ("-"); 
        this.tempsMoinsByoY.setSize(20,20);
        this.tempsMoinsByoY.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsMoinsByoY.addActionListener(this);

        tempsBoutons.add(this.tempsPlusByoY);
        tempsBoutons.add(this.tempsMoinsByoY);
        tempsBoutons.setBackground(new Color(238,221,221));
        this.tempsChoixT.add(tempsBoutons);


        //Période
        min = this.secondesPeriodeByoY/60;
        sec = this.secondesPeriodeByoY%60;
        if (this.secondesPeriodeByoY < 60 )
            ecrire = new JLabel("   Temps par Byo-Yomi : " + this.secondesPeriodeByoY+" secondes");
        else
        {
            if ( sec == 0 || sec == 5 )
                ecrire = new JLabel("   Temps par Byo-Yomi : " + min+" minute(s) 0"+sec+" seconde(s)");
            else
                ecrire = new JLabel("   Temps par Byo-Yomi : " + min+" minute(s) "+sec+" seconde(s)");
        }
        this.tempsChoixT.add(ecrire);

        tempsBoutons = new JPanel();
        tempsBoutons.setLayout(new BoxLayout(tempsBoutons, BoxLayout.Y_AXIS));
        this.tempsPlusPeriodeByoY = new JButton ("+"); 
        this.tempsPlusPeriodeByoY.setSize(20,20);
        this.tempsPlusPeriodeByoY.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsPlusPeriodeByoY.addActionListener(this);
        //
        tempsBoutons = new JPanel();
        tempsBoutons.setLayout(new BoxLayout(tempsBoutons, BoxLayout.Y_AXIS));
        this.tempsMoinsPeriodeByoY = new JButton ("-"); 
        this.tempsMoinsPeriodeByoY.setSize(20,20);
        this.tempsMoinsPeriodeByoY.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.tempsMoinsPeriodeByoY.addActionListener(this);

        tempsBoutons.add(this.tempsPlusPeriodeByoY);
        tempsBoutons.add(this.tempsMoinsPeriodeByoY);
        tempsBoutons.setBackground(new Color(238,221,221));
        this.tempsChoixT.add(tempsBoutons);


        this.tempsPanel.add(this.tempsChoixT);
    }


    public void komi()
    {
        this.panKomi.removeAll();
        
        JLabel ecrire = new JLabel(""+this.komi+"");
        ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.panKomi.add(ecrire);

        JPanel panKomiBouton = new JPanel();
        panKomiBouton.setLayout(new BoxLayout(panKomiBouton, BoxLayout.Y_AXIS));
        panKomiBouton.setBackground(new Color(238,221,221));

        this.komiPlus = new JButton ("+"); 
        this.komiPlus.setSize(10,20);
        this.komiPlus.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.komiPlus.addActionListener(this);
        panKomiBouton.add(this.komiPlus);

        this.komiMoins = new JButton ("-"); 
        this.komiMoins.setSize(10,20);
        this.komiMoins.setAlignmentX( Component.CENTER_ALIGNMENT );
        this.komiMoins.addActionListener(this);
        panKomiBouton.add(this.komiMoins);

        this.panKomi.add(panKomiBouton);

    }

    public void tempsAbsoluModifier(int valeur)
    {
        if ( this.secondes >= 1800 )
        {
            valeur = valeur * 5;
        }
        this.secondes = this.secondes + valeur;

        if ( this.secondes < 1200 )
        {
            this.secondes = 1200;
        }
        if ( this.secondes > 3600 )
        {
            this.secondes = 3600;
        }

        this.tempsAbsolu();
    }

    public void tempsReflexionModifier(int valeur)
    {
        if ( this.secondesRelexion >= 1200 )
            valeur = valeur * 5;

        this.secondesRelexion = this.secondesRelexion + valeur;

        if ( this.secondesRelexion < 60 )
        {
            this.secondesRelexion = 60;
        }
        if ( this.secondesRelexion > 3600 )
        {
            this.secondesRelexion = 3600;
        }

        this.tempsByoY();
    }

    public void nombreByoYModifier(int valeur)
    {
        this.nbByoY = this.nbByoY + valeur;

        if ( this.nbByoY < 2 )
        {
            this.nbByoY = 2;
        }
        if ( this.nbByoY > 10 )
        {
            this.nbByoY = 10;
        }

        this.tempsByoY();
    }

    public void tempsPeriodeModifier(int valeur)
    {
        if ( this.secondesPeriodeByoY >= 120 )
        {
            valeur = valeur * 6;
        }
        this.secondesPeriodeByoY = this.secondesPeriodeByoY + valeur;

        if ( this.secondesPeriodeByoY < 10 )
        {
            this.secondesPeriodeByoY = 10;
        }
        if ( this.secondesPeriodeByoY > 300 )
        {
            this.secondesPeriodeByoY = 300;
        }

        this.tempsByoY();
    }


    public void komiModifier(float valeur)
    {
        this.komi = this.komi + valeur;
        if ( this.komi > 7.5f )
            this.komi = 7.5f;
        if ( this.komi < 5.5f )
            this.komi = 5.5f;
        this.komi();
    }



/* ----------------------------------------------------------------------------------------------------- */
/* ------------ HANDICAP ------------ */
    public int[] obtenirHandicap()
    {
    	int [] tmpHandicap = null;

    	/* ------------ Handicap pour un terrain de taille 9 ------------ */
    	if ( this.tailleGoban == 9 )
    	{
    		if (this.handicap1.isSelected())
    		{
    			tmpHandicap = new int[] {4,4};
    		}
    		if (this.handicap2.isSelected())
    		{
    			tmpHandicap = new int[] {6,2,2,6};
    		}
    		if (this.handicap3.isSelected())
    		{
    			tmpHandicap = new int[] {6,2,6,6,2,6};
    		}
    		if (this.handicap4.isSelected())
    		{
    			tmpHandicap = new int[] {2,2,6,2,2,6,6,6};
    		}
    		if (this.handicap5.isSelected())
    		{
    			tmpHandicap = new int[] {2,2,6,2,4,4,2,6,6,6};
    		}
    	}

		/* ------------ Handicap pour un terrain de taille 13 ------------ */
    	if ( this.tailleGoban == 13 )
    	{
    		if (this.handicap1.isSelected())
    		{
    			tmpHandicap = new int[] {6,6};
    		}
    		if (this.handicap2.isSelected())
    		{
    			tmpHandicap = new int[] {3,9,9,3};
    		}
    		if (this.handicap3.isSelected())
    		{
    			tmpHandicap = new int[] {3,9,9,9,9,3};
    		}
    		if (this.handicap4.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,9,3,3,9,9,9};
    		}
    		if (this.handicap5.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,9,3,6,6,3,9,9,9};
    		}
    	}

    	/* ------------ Handicap pour un terrain de taille 19 ------------ */
    	if ( this.tailleGoban == 19 )
    	{
    		if (this.handicap1.isSelected())
    		{
    			tmpHandicap = new int[] {9,9};
    		}
    		if (this.handicap2.isSelected())
    		{
    			tmpHandicap = new int[] {15,3,3,15};
    		}
    		if (this.handicap3.isSelected())
    		{
    			tmpHandicap = new int[] {15,3,3,15,15,15};
    		}
    		if (this.handicap4.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,15,3,3,15,15,15};
    		}
    		if (this.handicap5.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,15,3,9,9,3,15,15,15};
    		}
    		if (this.handicap6.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,15,3,3,9,15,9,3,15,15,15};
    		}
    		if (this.handicap7.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,15,3,3,9,9,9,15,9,3,15,15,15};
    		}
    		if (this.handicap8.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,9,3,15,3,3,9,15,9,3,15,9,15,15,15};
    		}
    		if (this.handicap9.isSelected())
    		{
    			tmpHandicap = new int[] {3,3,9,3,15,3,3,9,9,9,15,9,3,15,9,15,15,15};
    		}
    	}

    	return tmpHandicap;
    }

    public void commencerPartie()
    {
    	if (this.taille9.isSelected())
    	{
    		this.tailleGoban = 9;
    	}
    	else if (this.taille13.isSelected())
    	{
    		this.tailleGoban = 13;
    	}
    	else
    	{
    		this.tailleGoban = 19;
    	}

    	
       	this.handicap = this.obtenirHandicap();

       	this.fenetre.remove(this);
       	Plateau plateau = new Plateau(this.fenetre,this);
       	this.fenetre.add(plateau);

       	this.fenetre.setVisible(true);
    }


    public void redessiner()
	{
		this.repaint();
	}


	@Override
	public void paintComponent(Graphics pinceau) {
		super.paintComponent(pinceau);
	
	}


	/* ------------------------- ACCESSEUR ------------------------- */
	public int getTailleGoban()
	{
		return this.tailleGoban;
	}

	public int[] getHandicap()
	{
		return this.handicap;
	}

    public int getHorloge()
    {
        return this.horloge;
    }

    public int getSecondes()
    {
        return this.secondes;
    }

    public float getKomi()
    {
        return this.komi;
    }

    public int getSecondesRelexion()
    {
        return this.secondesRelexion;
    }

    public int getSecondesPeriodeByoY()
    {
        return this.secondesPeriodeByoY;
    }
      
    public int getNbByoY()
    {
        return this.nbByoY;
    }
}