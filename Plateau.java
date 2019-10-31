import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.event. *;

import javax.swing.Timer;

class Plateau  extends JPanel implements ActionListener {
	Fenetre fenetre;
	Menu menu;
	Goban goban;
	Affichage affichage;
	Score score;

	private LinkedList<Goban> listeGoban = new LinkedList<Goban>();
	private LinkedList<Goban> listeGobanSup = new LinkedList<Goban>();

	private int nbPasserSonTour = 0;
	private boolean tourPasserOuNon = false;

	private int positionScrollBarreHistorique = 0;

	private Timer chrono;
	//private Timer chronoJ2;
	private int delay = 1000;

	public Plateau(Fenetre fenetre, Menu menu) {
		this.fenetre = fenetre;
		this.menu = menu;

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));	

        Goban goban = new Goban(this.menu.getTailleGoban(),this);

       	int x;
       	int y;
       	if (this.menu.getHandicap() != null)
       	{
       		goban.setTourDeJouer(-1);
       		for ( int i = 0 ; i < this.menu.getHandicap().length ; i = i + 2 )
       		{
       			x = this.menu.getHandicap()[i];
       			y = this.menu.getHandicap()[i+1];
       			goban.getPlateau()[x][y] = 1;
       		}
       		goban.setNbPierreNoir(this.menu.getHandicap().length);
       	}

       	if ( this.menu.getHorloge() == 2)
		{
			goban.setTempsDeJeuJ1DeBase(this.menu.getSecondes());
			goban.setTempsDeJeuJ1(this.menu.getSecondes());
			goban.setTempsDeJeuJ2DeBase(this.menu.getSecondes());
			goban.setTempsDeJeuJ2(this.menu.getSecondes());
		}
		if ( this.menu.getHorloge() == 3)
		{
			goban.setTempsDeJeuJ1DeBase(this.menu.getSecondesRelexion());
			goban.setTempsDeJeuJ1(this.menu.getSecondesRelexion());
			goban.setTempsDeJeuJ2DeBase(this.menu.getSecondesRelexion());
			goban.setTempsDeJeuJ2(this.menu.getSecondesRelexion());
			goban.setNbByoYJ1(this.menu.getNbByoY());
			goban.setNbByoYJ2(this.menu.getNbByoY());
		}

  		goban.setScoreJ2(this.menu.getKomi());

       this.listeGoban.addFirst(goban);
       this.goban = goban;
       this.score = new Score(this);

       	this.affichage = new Affichage(this);

        //test();


       	this.add(this.goban);
       	this.add(this.affichage);

       	this.tempsDeJeu();

       	this.chrono = new Timer(this.delay,this);
       	this.chrono.start();
	}

	/* ------------------------- CHRONOMETRE ------------------------- */
	public void actionPerformed(ActionEvent e) {
		
		int temps = 1;
		Fin fin;
		float scorej1 ; // = this.score.calculScoreJ1();
		float scorej2  ; //= this.score.calculScoreJ2();

		if ( e.getSource() == this.chrono && this.menu.getHorloge() != 1 )
		{
			scorej1 = this.score.calculScoreJ1();
			scorej2 = this.score.calculScoreJ2();

			if ( this.goban.getTourDeJouer() == 1 )
			{
				this.goban.setTempsDeJeuJ1( this.goban.getTempsDeJeuJ1() - 1 );
				temps = this.goban.getTempsDeJeuJ1();
				if ( temps <= 0)
				{
					if ( this.menu.getHorloge() == 3 )
					{
						if ( this.goban.getNbByoYJ1() > 1 )
						{
							this.goban.setNbByoYJ1( this.goban.getNbByoYJ1() - 1 );
							this.goban.setTempsDeJeuJ1DeBase( this.menu.getSecondesPeriodeByoY() );
							this.goban.setPeriodeByoYJ1(true);
							this.goban.setTempsDeJeuJ1( this.goban.getTempsDeJeuJ1DeBase() );
						}
						else
						{
							fin = new Fin(this.fenetre,this.menu,scorej1,scorej2,false,false,true,false,this);
							this.fenetre.remove(this);
							this.fenetre.add(fin);
							this.fenetre.setVisible(true);
						}
					}
					else
					{
						fin = new Fin(this.fenetre,this.menu,scorej1,scorej2,false,false,true,false,this);
						this.fenetre.remove(this);
						this.fenetre.add(fin);
						this.fenetre.setVisible(true);
					}
				}
			}
			else
			{
				this.goban.setTempsDeJeuJ2( this.goban.getTempsDeJeuJ2() - 1 );
				this.tempsDeJeu();
				temps = this.goban.getTempsDeJeuJ2();
				if ( temps <= 0)
				{
					if ( this.menu.getHorloge() == 3 )
					{
						if ( this.goban.getNbByoYJ2() > 1 )
						{
							this.goban.setNbByoYJ2( this.goban.getNbByoYJ2() - 1 );
							this.goban.setTempsDeJeuJ2DeBase( this.menu.getSecondesPeriodeByoY() );
							this.goban.setPeriodeByoYJ2(true);
							this.goban.setTempsDeJeuJ2( this.goban.getTempsDeJeuJ2DeBase() );
						}
						else
						{
							fin = new Fin(this.fenetre,this.menu,scorej1,scorej2,false,false,false,true,this);
							this.fenetre.remove(this);
							this.fenetre.add(fin);
							this.fenetre.setVisible(true);
						}
					}
					else
					{
						fin = new Fin(this.fenetre,this.menu,scorej1,scorej2,false,false,false,true,this);
						this.fenetre.remove(this);
						this.fenetre.add(fin);
						this.fenetre.setVisible(true);
					}
				}
			}
			this.tempsDeJeu();
		}
	}

	public void chronoStop()
	{
		this.chrono.stop();
	}

	public void chronoStart()
	{
		this.chrono.start();
	}



	/* ------------------------- ORGANISATION DES GOBANS ------------------------- */

	public void prochainGoban(Goban oldGoban, int value)
	{
		this.remove(this.goban);
       	this.remove(this.affichage);

       	if ( !this.tourPasserOuNon )
       	{
			oldGoban.getPlateau()[oldGoban.get_X()][oldGoban.get_Y()] = Goban.Etat.VIDE.getValue();
			oldGoban.setPierreX(oldGoban.get_X());
			//System.out.println("x="+oldGoban.get_X());
			oldGoban.setPierreY(oldGoban.get_Y());
       	}

		Goban newGoban = new Goban(this.menu.getTailleGoban(),this);
		newGoban.setTourDeJouer( oldGoban.getTourDeJouer() * -1 );


		for ( int i = 0 ; i < newGoban.getPlateau().length ; i++)
		{
			for ( int j = 0 ; j < newGoban.getPlateau()[i].length ; j++)
			{
				if ( oldGoban.getPlateau()[i][j] == Goban.Etat.J1.getValue()|| oldGoban.getPlateau()[i][j] == Goban.Etat.J2.getValue() )
				{
					newGoban.getPlateau()[i][j] = oldGoban.getPlateau()[i][j];
				}
				else
				{
					newGoban.getPlateau()[i][j] = Goban.Etat.VIDE.getValue();
				}
			}
		}

		if ( !this.tourPasserOuNon )
       	{
			newGoban.getPlateau()[oldGoban.get_X()][oldGoban.get_Y()] = value;
		}

		newGoban.egaliserGoban(oldGoban);

		newGoban.setTempsDeJeuJ1DeBase( oldGoban.getTempsDeJeuJ1() );
		newGoban.setTempsDeJeuJ2DeBase( oldGoban.getTempsDeJeuJ2() );

		if ( oldGoban.getPeriodeByoYJ1() )
		{
			newGoban.setTempsDeJeuJ1DeBase( this.menu.getSecondesPeriodeByoY() );
			newGoban.setTempsDeJeuJ1( this.menu.getSecondesPeriodeByoY() );
		}
		if ( oldGoban.getPeriodeByoYJ2() )
		{
			newGoban.setTempsDeJeuJ2DeBase( this.menu.getSecondesPeriodeByoY() );
			newGoban.setTempsDeJeuJ2( this.menu.getSecondesPeriodeByoY() );
		}

		this.goban = newGoban;

		this.listeGoban.addFirst(newGoban);

		this.listeGobanSup.clear();
		//this.listeGobanSup = new LinkedList<Goban>();


		/*this.add(this.goban);
       	this.add(this.affichage);*/
       	this.reAfficherPlateau();

		//this.test();

				this.fenetre.setVisible(true);

		this.tempsDeJeu();

		if ( !this.tourPasserOuNon )
		{
			this.nbPasserSonTour = 0;
		}
		this.tourPasserOuNon = false;
	}

	public void precedentGoban()
	{
		this.remove(this.goban);
       	this.remove(this.affichage);	

       	Goban inferieur;

       	if ( this.listeGoban.size() > 1)
       	{
       		inferieur = this.listeGoban.pollFirst();
			this.listeGobanSup.addFirst(inferieur);

			this.goban = this.listeGoban.getFirst();
       	}
       	

       	this.reAfficherPlateau();

       	this.goban.redessiner();

       	this.goban.setTempsDeJeuJ1(this.goban.getTempsDeJeuJ1DeBase());
       	this.goban.setTempsDeJeuJ2(this.goban.getTempsDeJeuJ2DeBase());
       	this.tempsDeJeu();
	}

	public void suivantGoban()
	{
		this.remove(this.goban);
       	this.remove(this.affichage);	

       	Goban superieur;

       	if ( this.listeGobanSup.size() > 0)
       	{
       		superieur = this.listeGobanSup.pollFirst();
       		this.listeGoban.addFirst(superieur);

       		this.goban = this.listeGoban.getFirst();
       	}

       	this.reAfficherPlateau();

       	this.goban.redessiner();

       	this.goban.setTempsDeJeuJ1(this.goban.getTempsDeJeuJ1DeBase());
       	this.goban.setTempsDeJeuJ2(this.goban.getTempsDeJeuJ2DeBase());
       	this.tempsDeJeu();
	}


	/* ------------------------- TEMPS POUR JOUER ------------------------- */
	public void tempsDeJeu()
	{
		this.affichage.getTempsPourJouer().removeAll();

		JPanel tempsInfo = new JPanel();
		JPanel tempsByo = new JPanel();
		tempsInfo.setLayout(new BoxLayout(tempsInfo, BoxLayout.Y_AXIS));
		tempsByo.setLayout(new BoxLayout(tempsByo, BoxLayout.X_AXIS));

		JLabel ecrire;
		ecrire = new JLabel("Temps Restant Pour Jouer :");
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		//this.affichage.getTempsPourJouer().add(ecrire);
		tempsInfo.add(ecrire);

		int min;
		int sec;

		if ( this.menu.getHorloge() == 2 || this.menu.getHorloge() == 3 )
		{
			if ( this.goban.getTourDeJouer() == 1 )
			{
				//this.goban.setTempsDeJeuJ1( this.goban.getTempsDeJeuJ1() + 1 );
				min = this.goban.getTempsDeJeuJ1()/60;
				sec = this.goban.getTempsDeJeuJ1()%60;
			}
			else
			{
				min = this.goban.getTempsDeJeuJ2()/60;
				sec = this.goban.getTempsDeJeuJ2()%60;
			}
			ecrire = new JLabel( min + "\' " + sec + "\'\' ");
		}
		else 
		{
			ecrire = new JLabel("Aucune limite");
		}

		ecrire.setFont(new Font("Temps",Font.BOLD,20));		

		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		//this.affichage.getTempsPourJouer().add(ecrire);
		tempsInfo.add(ecrire);

		if ( this.menu.getHorloge() == 3 )
		{
			if ( this.goban.getTourDeJouer() == 1 )
			{
				ecrire = new JLabel("Nombre de Byo-Yomi restant : " + this.goban.getNbByoYJ1() );
			}
			else
			{
				ecrire = new JLabel("Nombre de Byo-Yomi restant : " + this.goban.getNbByoYJ2() );
			}

			ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
			//this.affichage.getTempsPourJouer().add(ecrire);
			tempsInfo.add(ecrire);

			if ( this.goban.getTourDeJouer() == 1 )
			{
				
				for ( int i = 0 ; i < this.goban.getNbByoYJ1() ; i++ )
				{
					ecrire = new JLabel(" o ");
					tempsByo.add(ecrire);
				}
			}
			else
			{
				for ( int i = 0 ; i < this.goban.getNbByoYJ2() ; i++ )
				{
					ecrire = new JLabel(" o ");
					tempsByo.add(ecrire);
				}
			}

			tempsByo.setBackground(new Color(238,221,221));
			ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
			tempsInfo.add(tempsByo);
		}

		tempsInfo.setBackground(new Color(238,221,221));
		this.affichage.getTempsPourJouer().add(tempsInfo);

		this.fenetre.setVisible(true);
	}

	/* ------------------------- FONCTIONS UTILES ------------------------- */

	public void quitterJeu()
	{
		this.chrono.stop();
		this.fenetre.remove(this);
		this.fenetre.add(this.menu);
		this.menu.redessiner();
		this.fenetre.setVisible(true);
	}

	public void reAfficherPlateau()
	{	
		//System.out.println("1--");
		//test();
		//MANGER - INTERDIRE DE SE SUICIDER - FAIRE UN KO
		this.score = new Score(this);
       	//this.goban.setPlateau(this.score.redessinerLePlateauDuGoban());
       	if ( this.goban.getTourDeJouer() == 1)
       	{
       		this.score.mangerLesPions(Goban.Etat.J1.getValue());
       		this.score.interdirePions(Goban.Etat.J1.getValue());
       		this.score.nePasInterdire(Goban.Etat.J1.getValue(),Goban.Etat.INTERDITJ1.getValue());
       		this.score.regleDuKo(Goban.Etat.J1.getValue());
       	}
       	else
       	{       		
       		this.score.mangerLesPions(Goban.Etat.J2.getValue());
       		this.score.interdirePions(Goban.Etat.J2.getValue());
       		this.score.nePasInterdire(Goban.Etat.J2.getValue(),Goban.Etat.INTERDITJ2.getValue());
       		this.score.regleDuKo(Goban.Etat.J2.getValue());
       	}

       	//LE NOMBRE DE PRISONNIERS

       	if ( this.listeGoban.size() > 1 )
       	{
       		if ( this.goban.getTourDeJouer() == 1)
       			this.goban.setNbPrisonnierJ2( this.listeGoban.get(1).getNbPrisonnierJ2() + this.score.prisonnier( Goban.Etat.J1.getValue() , this.listeGoban.get(1).getPlateau() ) ); 
       		else
       			this.goban.setNbPrisonnierJ1( this.listeGoban.get(1).getNbPrisonnierJ1() + this.score.prisonnier( Goban.Etat.J2.getValue() , this.listeGoban.get(1).getPlateau() ) ); 				
       	}
       	else
       	{
       		this.goban.setNbPrisonnierJ2( 0 ); 
			this.goban.setNbPrisonnierJ1( 0 ); 
						
       	}

       	//System.out.println("2--");
		//test();

       	//PARTIE POUR GERER LE TRIPLE KO

       	int pierresNoirs = 0;
       	int pierresBlanches = 0;
       	int [][] plateauGobanTemp = new int [this.goban.getPlateau().length][this.goban.getPlateau().length];	//demande accesseur

       	for( int i = 0 ; i < this.goban.getPlateau().length ; i ++ )
       	{
       		for( int j = 0 ; j < this.goban.getPlateau()[i].length ; j ++ )
       		{
       			if ( this.goban.getPlateau()[i][j] == Goban.Etat.J1.getValue() )
       				pierresNoirs++;
       			else if ( this.goban.getPlateau()[i][j] == Goban.Etat.J2.getValue() )
       				pierresBlanches++;

       			plateauGobanTemp[i][j] = this.goban.getPlateau()[i][j];
       		}
       	}

		this.goban.setNbPierreNoir( pierresNoirs );
		this.goban.setNbPierreBlanche( pierresBlanches );

		//A ce stade on a sauvegarger le nomnbre de pierres blanches et noires et le plateau dans plateauGobanTemp


		//PARTIE POUR GERER LE TRIPLE KO

		int pierresNoirsBis = 0;
       	int pierresBlanchesBis = 0;

       	int compteurPlateau = 0;
       	boolean gobanEgaux = true;

		int valuePierre;
		int valuePierreEnnemi;
       	int valuePierreInterdit;
       	int valuePierreInterditEnnemi;
       	Score scoreTripleKo;
       	//Permet d'écrire qu'une seul fois la suite
       	if ( this.goban.getTourDeJouer() == 1)
       	{
       		valuePierre =  Goban.Etat.J1.getValue();
       		valuePierreInterdit = Goban.Etat.INTERDITJ1.getValue();
       		valuePierreEnnemi = Goban.Etat.J2.getValue();
       		valuePierreInterditEnnemi = Goban.Etat.INTERDITJ2.getValue();
       	}
       	else
       	{
       		valuePierre =  Goban.Etat.J2.getValue();
       		valuePierreInterdit = Goban.Etat.INTERDITJ2.getValue();
       		valuePierreEnnemi = Goban.Etat.J1.getValue();
       		valuePierreInterditEnnemi = Goban.Etat.INTERDITJ1.getValue();
       	}

       	//System.out.println(valuePierre);

       	//Règle du triple ko appliquée ici
		for( int i = 0 ; i < this.goban.getPlateau().length ; i ++ )
       	{
       		for( int j = 0 ; j < this.goban.getPlateau()[i].length ; j ++ )
       		{
       			//on simule qu'on joue là où on peut jouer
       			if ( this.goban.getPlateau()[i][j] == Goban.Etat.VIDE.getValue() )
       			{
       				//Nombre de plateau qui seront égaux à nous
       				compteurPlateau = 0;

       				this.goban.getPlateau()[i][j] = valuePierre;

       				scoreTripleKo = new Score(this);

       				//On modifie le terrain en fonction de ce qu'on prévoit de jouer
       				scoreTripleKo.mangerLesPions(valuePierreEnnemi);
		       		scoreTripleKo.interdirePions(valuePierreEnnemi);
		       		scoreTripleKo.nePasInterdire(valuePierre,valuePierreInterditEnnemi);
		       		scoreTripleKo.regleDuKo(valuePierreEnnemi);

		       		pierresNoirsBis = 0;
       				pierresBlanchesBis = 0;

       				
       				//On recompte les pierres de ce nouveau tableau
		       		for( int iBis = 0 ; iBis < this.goban.getPlateau().length ; iBis ++ )
			       	{
			       		for( int jBis = 0 ; jBis < this.goban.getPlateau()[iBis].length ; jBis ++ )
			       		{
			       			if ( this.goban.getPlateau()[iBis][jBis] == Goban.Etat.J1.getValue() )
			       				pierresNoirsBis++;
			       			else if ( this.goban.getPlateau()[iBis][jBis] == Goban.Etat.J2.getValue() )
			       				pierresBlanchesBis++;
			       		}
			       	}

			       	//On parcours notre liste de goban inférieur
		       		for ( int k = 1 ; k < this.listeGoban.size() && compteurPlateau < 2 ; k++)
		       		{
		       			//Si on a le même nombre de pierres blanches et noires
		       			//Alors on est susceptible d'être égaux
		       			if ( this.listeGoban.get(k).getNbPierreNoir() == pierresNoirsBis && this.listeGoban.get(k).getNbPierreBlanche() == pierresBlanchesBis )
		       			{
		       				gobanEgaux = true;
		       				//On parcours les plateaux jusqu'à ce qu'on trouve que l'un a une pierre que n'a pas l'autre		
		       				for ( int x = 0 ; x < this.goban.getPlateau().length && gobanEgaux ; x++ )
		       				{
		       					for ( int y = 0 ; y < this.goban.getPlateau().length && gobanEgaux ; y++ )
		       					{
		       						if ( this.goban.getPlateau()[x][y] == Goban.Etat.J1.getValue() &&
		       							 this.listeGoban.get(k).getPlateau()[x][y] != Goban.Etat.J1.getValue()
		       							 ||
		       							 this.goban.getPlateau()[x][y] != Goban.Etat.J1.getValue() &&
		       							 this.listeGoban.get(k).getPlateau()[x][y] == Goban.Etat.J1.getValue()
		       							 ||
		       							 this.goban.getPlateau()[x][y] == Goban.Etat.J2.getValue() &&
		       							 this.listeGoban.get(k).getPlateau()[x][y] != Goban.Etat.J2.getValue()
		       							 ||
		       							 this.goban.getPlateau()[x][y] != Goban.Etat.J2.getValue() &&
		       							 this.listeGoban.get(k).getPlateau()[x][y] == Goban.Etat.J2.getValue()
		       							 )
		       						{
		       							gobanEgaux = false;
		       							//non égaux
		       						}
		       					}
		       				}

		       				//Si finalement ils sont bien égaux
		       				if ( gobanEgaux )
		       				{
		       					compteurPlateau++;
		       				}

		       			}
		       		}

		       		//S'il existe déjà deux plateaux qui nous ressemble
		       		if ( compteurPlateau >= 2 )
		       		{
		       			plateauGobanTemp[i][j] = valuePierreInterdit;
		       			//pas le droit de jouer

		       		}

		       		//On ré-initialise le goban de this (en fonction de l'interdit si besoin)
		       		for( int iBis = 0 ; iBis < this.goban.getPlateau().length ; iBis ++ )
			       	{
			       		for( int jBis = 0 ; jBis < this.goban.getPlateau()[iBis].length ; jBis ++ )
			       		{
			       			this.goban.getPlateau()[iBis][jBis] = plateauGobanTemp[iBis][jBis];
			       		}
			       	}

       			}
       		}
       	}

		//System.out.println("pierre noir = "+this.goban.getNbPierreNoir()+"   pierre blanche = "+this.goban.getNbPierreBlanche());

       	//this.score.redessinerLePlateauDuGoban();

       	//this.positionScrollBarreHistorique = this.affichage.getListeHistorique().getCaretPosition();
       	
       	this.affichage = new Affichage(this);

       	this.add(this.goban);
       	this.add(this.affichage);	
       	this.fenetre.setVisible(true);

       	//System.out.println("2--");
		//test();
	}

	public void passerSonTour(){
		this.nbPasserSonTour++;

		/*float scorej1 = this.score.calculScoreJ1();
		float scorej2 = this.score.calculScoreJ2();

		Fin fin = new Fin(this.fenetre,this.menu,scorej1,scorej2,false,false,false,false,this);*/
		Terminer terminer;

		if ( this.nbPasserSonTour >= 2 )
		{    
			//this.tourPasserOuNon = true;
			this.tourPasserOuNon = true;
			this.nbPasserSonTour = 0;
			//this.nbPasserSonTour = 0;
			this.prochainGoban( this.goban , 0 );
			this.chrono.stop();

			//this.removeAll();
			

			terminer = new Terminer(this.goban,this,this.fenetre,this.menu);
			this.fenetre.remove(this);
			//this.fenetre.add(fin);
			this.fenetre.add(terminer);
			this.fenetre.setVisible(true);


			/*this.remove(this.goban);
			this.remove(this.affichage);
			this.affichage = new Affichage(this);
	       	//this.add(this.goban);
	       	this.tempsDeJeu();
	       	this.add(this.goban);
	       	this.add(this.affichage);

	       	this.repaint();
	       	this.goban.redessiner();*/


		}
		else
		{
			this.tourPasserOuNon = true;
			this.prochainGoban( this.goban , 0 );
		}
	}

	public void abandonnerLeJeu()
	{
		float scorej1 = this.score.calculScoreJ1();
		float scorej2 = this.score.calculScoreJ2();

		boolean abandonJ1 = false;
		boolean abandonJ2 = false;

		if ( this.goban.getTourDeJouer() == 1 )
		{
			abandonJ1 = true;
		}
		else
		{
			abandonJ2 = true;
		}

		Fin fin = new Fin(this.fenetre,this.menu,scorej1,scorej2,abandonJ1,abandonJ2,false,false,this);

		this.fenetre.remove(this);
		this.fenetre.add(fin);
		this.fenetre.setVisible(true);
	}


	/* ------------------------- ACCESSEUR ------------------------- */
	public Goban getGoban()
	{
		return this.goban;
	}
	public Score getScore()
	{
		return this.score;
	}
	public Menu getMenu()
	{
		return this.menu;
	}
	public Affichage getAffichage()
	{
		return this.affichage;
	}

	public Fenetre getFenetre()
	{
		return this.fenetre;
	}

	public void setAffichage(Affichage a)
	{
		this.affichage = a;
	}

	public LinkedList<Goban> getListeGoban()
	{
		return this.listeGoban;
	}

	public LinkedList<Goban> getListeGobanSup()
	{
		return this.listeGobanSup;
	}
	public int getPositionScrollBarreHistorique()
	{
		return this.positionScrollBarreHistorique;
	}
	public void setPositionScrollBarreHistorique(int p)
	{
		this.positionScrollBarreHistorique = p;
	}


	/*public void test()
	{
		System.out.println("");
		System.out.println("");
		System.out.println("--------------------------------------------------------------");
		System.out.println("Nouvel liste");
		System.out.println("--------------------------------------------------------------");
		System.out.println("");
		for ( int i = 0 ; i < this.listeGoban.size() ; i++ )
		{
			for ( int x = 0 ; x < this.listeGoban.get(i).getPlateau().length ; x++)
			{
				for ( int y = 0 ; y < this.listeGoban.get(i).getPlateau()[x].length ; y++)
				{
					System.out.print("| "+this.listeGoban.get(i).getPlateau()[y][x]+" |");
				}
				System.out.println("");
			}
			System.out.println("");
		}
		System.out.println("--------------------------------------------------------------");

		for ( int x = 0 ; x < this.goban.getPlateau().length ; x++)
			{
				for ( int y = 0 ; y < this.goban.getPlateau()[x].length ; y++)
				{
					System.out.print("| "+this.goban.getPlateau()[y][x]+" |");
				}
				System.out.println("");
			}
			System.out.println("");
	}*/
	
}