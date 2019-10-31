import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.event. *;

import javax.swing.JScrollBar;


class Affichage  extends JPanel implements ActionListener {

	Plateau regle;
	private JPanel joueurInfo = new JPanel();
	private JPanel tempsPourJouer = new JPanel();
	private JPanel scoreInfo = new JPanel();
	private JPanel prisonnierInfo = new JPanel();
	private JButton precedent = new JButton ("precedent"); 
	private JButton suivant = new JButton ("suivant"); 
	private JButton quitter = new JButton ("quitter la partie"); 
	private JButton passer = new JButton ("Passer son tour"); 
	private JButton abandonner = new JButton ("Abandonner la partie"); 

	private JTextArea listeHistorique = new JTextArea();


	public Affichage(Plateau regle){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.regle = regle;

		this.setBackground(new Color(238,221,221));

		JLabel ecrire;
		ecrire = new JLabel("Menu");
		ecrire.setFont(new Font("Menu",Font.BOLD,30));		
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(ecrire);

		/* ----------------------- PERSONNE QUI JOUE ----------------------- */
		this.joueurInfo.setLayout(new BoxLayout(this.joueurInfo, BoxLayout.Y_AXIS));
		ecrire = new JLabel("C'est au tour de :");
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.joueurInfo.add(ecrire);


		if ( this.regle.getGoban().getTourDeJouer() == 1 )
		{
			ecrire = new JLabel("J1");
		}
		else
		{
			ecrire = new JLabel("J2");
		}
		ecrire.setFont(new Font("Joueur",Font.BOLD,20));		
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.joueurInfo.add(ecrire);
		this.add(this.joueurInfo);

		/* ----------------------- TEMPS ----------------------- */
		this.tempsPourJouer.setLayout(new BoxLayout(this.tempsPourJouer, BoxLayout.X_AXIS));
		this.tempsPourJouer.setBackground(new Color(238,221,221));
		this.add(this.tempsPourJouer);


		/* ----------------------- NOMBRE DE PRISONNIER ----------------------- */
		this.prisonnierInfo.setLayout(new BoxLayout(this.prisonnierInfo, BoxLayout.Y_AXIS));
		this.prisonnierInfo.setBackground(new Color(238,221,221));

		ecrire = new JLabel("NOMBRE DE PRISONNIERS :");
		ecrire.setFont(new Font("Prisonnier",Font.BOLD,15));		
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.prisonnierInfo.add(ecrire);

		ecrire = new JLabel("J1 : " + this.regle.getGoban().getNbPrisonnierJ1());
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.prisonnierInfo.add(ecrire);

		ecrire = new JLabel("J2 : " + this.regle.getGoban().getNbPrisonnierJ2());
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.prisonnierInfo.add(ecrire);

		this.add(this.prisonnierInfo);


		/* ----------------------- SCORE ----------------------- */
		this.scoreInfo.setLayout(new BoxLayout(this.scoreInfo, BoxLayout.Y_AXIS));
		this.scoreInfo.setBackground(new Color(238,221,221));
		ecrire = new JLabel("SCORE :");
		ecrire.setFont(new Font("Score",Font.BOLD,15));	
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.scoreInfo.add(ecrire);

		ecrire = new JLabel("J1 : " + this.regle.getScore().calculScoreJ1());
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.scoreInfo.add(ecrire);

		ecrire = new JLabel("J2 : " + this.regle.getScore().calculScoreJ2());
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.scoreInfo.add(ecrire);

		this.add(this.scoreInfo);


		/* ----------------------- PASSER ----------------------- */
		this.passer.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.passer.addActionListener(this);
		this.add(this.passer);
		

		

		/* ----------------------- HISTORIQUE ----------------------- */
		ecrire = new JLabel("Historique");
		ecrire.setFont(new Font("Historique",Font.BOLD,15));	
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(ecrire);
		JPanel historique = new JPanel();
		historique.setLayout(new BoxLayout(historique, BoxLayout.Y_AXIS));

		JPanel historiqueBis = new JPanel();
		JPanel historiqueTer = new JPanel();
		historiqueBis.setLayout(new BoxLayout(historiqueBis, BoxLayout.X_AXIS));
       	
		this.precedent.setAlignmentX( Component.CENTER_ALIGNMENT );
		//this.precedent.setDefaultCapable(false);
		this.precedent.addActionListener(this);
		historiqueBis.add(this.precedent);

		this.suivant.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.suivant.addActionListener(this);
		historiqueBis.add(this.suivant);

		historique.add(historiqueBis);

		//Historique listeHistorique = new Historique(this.regle.getListeGoban(),this.regle.getListeGobanSup());
		//JScrollPane scrollPane = new JScrollPane(listeHistorique)/*,BorderLayout.CENTER);*/
		//historiqueTer.setLayout(null);
		historiqueTer.setLayout(new BoxLayout(historiqueTer, BoxLayout.X_AXIS));
		this.listeHistorique.setEditable(false);

		int x = -1;
		int y = -1;
		int tour = 1;
		//boolean existeGobanSuperieur = false;
		for ( int i = this.regle.getListeGobanSup().size()-1 ; i >= 0 ; i -- )
		{
			x = this.regle.getListeGobanSup().get(i).getPierreX()+1;
			y = this.regle.getListeGobanSup().get(i).getPierreY()+1;
			tour = this.regle.getListeGobanSup().get(i).getTourDeJouer();
			if ( tour == 1 )
			{
				this.listeHistorique.append(" noir              ||");
			}
			else
			{
				this.listeHistorique.append(" blanc           ||");
			}
			if ( x == 0 && y == 0 )
			{
				if ( i == this.regle.getListeGobanSup().size()-1 )
					this.listeHistorique.append("     EN COURS \n");
				else
					this.listeHistorique.append("     A PASSE SON TOUR \n");
			}
			else
			{
				this.listeHistorique.append("     x = " + x + "  --  y = " + y + " \n");
			}
			//existeGobanSuperieur = true;
		}

		//System.out.println(this.regle.getListeGoban().size());

		for ( int i = 0 ; i < this.regle.getListeGoban().size() ; i++ )
		{
			x = this.regle.getListeGoban().get(i).getPierreX()+1;
			y = this.regle.getListeGoban().get(i).getPierreY()+1;
			tour = this.regle.getListeGoban().get(i).getTourDeJouer();
			if ( i == 0 )
			{
				
				if ( tour == 1 )
				{
					this.listeHistorique.append(" noir joue      ||    PLATEAU ACTUEL\n");
				}
				else
				{
					this.listeHistorique.append(" blanc joue    ||    PLATEAU ACTUEL\n");
				}
			}
			//System.out.println(this.regle.getListeGoban().size());
			else
			{
				if ( tour == 1 )
				{
					this.listeHistorique.append(" noir              ||");
				}
				else
				{
					this.listeHistorique.append(" blanc           ||");
				}
				if ( x == 0 && y == 0 )
				{
					this.listeHistorique.append("     A PASSE SON TOUR \n");
				}
				else
				{
					this.listeHistorique.append("     x = " + x + "  --  y = " + y + " \n");
				}				
			}
		}

		/*if ( existeGobanSuperieur )
		{
			this.regle.setPositionScrollBarreHistorique(this.listeHistorique.getCaretPosition());
		}
		else
		{
			this.regle.setPositionScrollBarreHistorique(0);
		}

		this.listeHistorique.setCaretPosition(this.regle.getPositionScrollBarreHistorique());*/

		//int pos = this.listeHistorique.getCaretPosition() - ;
		this.listeHistorique.setCaretPosition(0);

		JScrollPane scrollPane = new JScrollPane(this.listeHistorique);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.createVerticalScrollBar();
		//scrollPane.getVerticalScrollbar().setValue((int)(this.listeHistorique.setCaretPosition/2));
		//scrollPane.getVerticalScrollbar().setValue(0);

		//System.out.println(scrollPane.getValue());

		/*historiqueTer.setPreferredSize(new Dimension(100,100));
		historiqueTer.setSize(100,100);

		scrollPane.setPreferredSize(new Dimension(100,100));
		scrollPane.setSize(100,100);

		this.listeHistorique.setPreferredSize(new Dimension(100,100));
		this.listeHistorique.setSize(100,100);*/

		historiqueTer.add(scrollPane);

		

		historique.add(historiqueTer);

		this.add(historique);
		//this.add(historiqueBis);



		/* ----------------------- ABANDONNER ----------------------- */
		this.abandonner.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.abandonner.addActionListener(this);
		this.add(this.abandonner);


		/* ----------------------- QUITTER ----------------------- */
		this.quitter.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.quitter.addActionListener(this);
		this.add(this.quitter);

	}

	public void actionPerformed(ActionEvent e) {
		if (this.precedent == e.getSource())
		{
			this.regle.precedentGoban();
		}
		if (this.suivant == e.getSource())
		{
			this.regle.suivantGoban();
		}
		if (this.quitter == e.getSource())
		{
			this.regle.quitterJeu();
		}
		if (this.passer == e.getSource())
		{
			this.regle.passerSonTour();
		}
		if (this.abandonner == e.getSource())
		{
			this.regle.abandonnerLeJeu();
		}
		
	}

	/* ------------------------- ACCESSEUR ------------------------- */
	public JPanel getTempsPourJouer()
	{
		return this.tempsPourJouer;
	}
	public void setTempsPourJouer(JPanel j)
	{
		this.tempsPourJouer = j;
	}


	public JTextArea getListeHistorique()
	{
		return this.listeHistorique ;
	}



	public void redessiner()
	{
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics pinceau) {
		super.paintComponent(pinceau);
	}
}