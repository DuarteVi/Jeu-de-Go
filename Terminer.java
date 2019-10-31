import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;

class Terminer extends JPanel implements ActionListener,MouseListener,MouseMotionListener {

	Goban goban;
	Plateau plateau;
	Fenetre fenetre;
	Menu menu;
	int[][] jeu;

	private int decalageGauche = 280;
	private int decalageHaut = 100;

	private int gaucheFF = 0;
	private int hautFF = 0;

	private float scoreJ1 = 0;
	private float scoreJ2 = 0;


	private int nbPrisonnierJ1 = 0;
	private int nbPrisonnierJ2 = 0;

	private JButton continuer;
	private JButton valider;

	//private JPanel affPlateau = new JPanel();
	private JPanel affBouton = new JPanel();
	private JPanel affScore = new JPanel();
 
 	private boolean [][] visited;

	Terminer(Goban g, Plateau p, Fenetre f, Menu m)
	{
		this.goban = g;
		this.plateau = p;
		this.fenetre = f;
		this.menu = m;

		this.jeu = new int [this.goban.getPlateau().length][this.goban.getPlateau().length];
		this.visited = new boolean [this.goban.getPlateau().length][this.goban.getPlateau().length];

		for ( int i = 0 ; i < this.goban.getPlateau().length ; i ++ )
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j ++ )
			{
				if ( this.goban.getPlateau()[i][j] == Goban.Etat.J1.getValue() || this.goban.getPlateau()[i][j] == Goban.Etat.J2.getValue() )
					this.jeu[i][j] = this.goban.getPlateau()[i][j];
			}
		}
 		
 		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 		this.setBackground(new Color(238,221,221));
 		this.affBouton.setLayout(new BoxLayout(this.affBouton, BoxLayout.X_AXIS));
 		this.affBouton.setBackground(new Color(238,221,221));
 		this.affScore.setLayout(new BoxLayout(this.affScore, BoxLayout.X_AXIS));
 		this.affScore.setBackground(new Color(238,221,221));
 		//this.affPlateau.setLayout(new BoxLayout(this.affPlateau, BoxLayout.Y_AXIS));
 		//this.affPlateau.setBackground(new Color(238,221,221));

 		this.continuer = new JButton ("CONTINUER A JOUER"); 
		this.continuer.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.continuer.addActionListener(this);
		this.affBouton.add(this.continuer);

		this.valider = new JButton ("VALIDER LA PARTIE"); 
		this.valider.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.valider.addActionListener(this);
		this.affBouton.add(this.valider);

		this.add(this.affBouton);

		JLabel test = new JLabel("test");
		test.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.affScore.add(test);
		test = new JLabel("test");
		test.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.affScore.add(test);
		this.add(this.affScore);

		/*PartieJeu partieJeu = new PartieJeu(this.jeu,this.goban.getTaille(),this.goban.getNombre());

		this.affPlateau.add(partieJeu);

		this.add(this.affPlateau);*/
		this.afficherScore();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		}

	private void afficherScore()
	{
		this.affScore.removeAll();

		this.calculerScore();

		JPanel affScore1 = new JPanel();
		affScore1.setLayout(new BoxLayout(affScore1, BoxLayout.Y_AXIS));
		JPanel affScore2 = new JPanel();
		affScore2.setLayout(new BoxLayout(affScore2, BoxLayout.Y_AXIS));

		JLabel ecrire = new JLabel("Score j1 : " + this.scoreJ1);
		ecrire.setFont(new Font("score1",Font.BOLD,20));
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		affScore1.add(ecrire);

		ecrire = new JLabel("Score j2 : " + this.scoreJ2);
		ecrire.setFont(new Font("score2",Font.BOLD,20));
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		affScore1.add(ecrire);



		ecrire = new JLabel(" || Nombre de prisonniers j1 : " + this.nbPrisonnierJ1);
		ecrire.setFont(new Font("score1",Font.BOLD,20));
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		affScore2.add(ecrire);

		ecrire = new JLabel(" || Nombre de prisonniers j2 : " + this.nbPrisonnierJ2);
		ecrire.setFont(new Font("score2",Font.BOLD,20));
		ecrire.setAlignmentX( Component.CENTER_ALIGNMENT );
		affScore2.add(ecrire);

		affScore1.setBackground(new Color(238,221,221));
		affScore2.setBackground(new Color(238,221,221));
		affScore.setBackground(new Color(238,221,221));

		this.affScore.add(affScore1);
		this.affScore.add(affScore2);

		this.fenetre.setVisible(true);

		/*if ( this.deuxFois )
		{
			this.deuxFois = false;
			this.afficherScore();
		}
		else
			this.deuxFois = true;*/
	} 

	public void calculerScore()
	{
		this.nbPrisonnierJ1 = this.goban.getNbPrisonnierJ1();
		this.nbPrisonnierJ2 = this.goban.getNbPrisonnierJ2();

		Goban tmpGoban = new Goban();
		tmpGoban.setPlateau(this.jeu);

		Score tmpScore = new Score(tmpGoban);

		int territoireJ1 = tmpScore.sommeTerritoire( Goban.Etat.J1.getValue() ) ;
		int territoireJ2 = tmpScore.sommeTerritoire( Goban.Etat.J2.getValue() ) ;

		//float resultatJ1 = this.goban.getScoreJ1() ;
		float resultatJ1 = 0 ;
		//float resultatJ2 = this.goban.getScoreJ2() ;
		float resultatJ2 = 0 ;

		for ( int i = 0 ; i < this.jeu.length ; i++)
		{
			for ( int j = 0 ; j < this.jeu[i].length ; j++)
			{
				if ( this.jeu[i][j] == Goban.Etat.J1.getValue() || this.jeu[i][j] == Goban.Etat.J1VIVANTSURVOLE.getValue() )
				{
					resultatJ1++;
				}
				else if ( this.jeu[i][j] == Goban.Etat.J2.getValue() || this.jeu[i][j] == Goban.Etat.J2VIVANTSURVOLE.getValue() )
				{
					resultatJ2++;
				} 
				else if ( this.jeu[i][j] == Goban.Etat.J1MORT.getValue() || this.jeu[i][j] == Goban.Etat.J1MORTSURVOLE.getValue() )
				{
					this.nbPrisonnierJ2++;
				} 
				else if ( this.jeu[i][j] == Goban.Etat.J2MORT.getValue() || this.jeu[i][j] == Goban.Etat.J2MORTSURVOLE.getValue() )
				{
					this.nbPrisonnierJ1++;
				}

			}
		}

		/*if ( resultatJ1 == 0 && resultatJ2 != 0 )
		{
			resultatJ2 = this.goban.getScoreJ2() + compteurTerrain ;
			resultatJ1 = this.goban.getScoreJ1();
		}
		else if ( resultatJ1 != 0 && resultatJ2 == 0 )
		{
			resultatJ2 = this.goban.getScoreJ2();
			resultatJ1 = this.goban.getScoreJ1() + compteurTerrain;
		}
		else
		{*/
			resultatJ1 = this.goban.getScoreJ1() + resultatJ1 + territoireJ1  ;
			resultatJ2 = this.goban.getScoreJ2() + resultatJ2 + territoireJ2 ; 
		//}

		this.scoreJ1 = resultatJ1 ;
		this.scoreJ2 = resultatJ2 ;

		//this.scoreJ1 = resultatJ1 + territoireJ1 ;
		//this.scoreJ2 = resultatJ2 + territoireJ2 ;

	}


	public void actionPerformed(ActionEvent e){

		Fin fin;
		Score score;

		if ( e.getSource() == this.continuer)
		{
			this.fenetre.remove(this);

			this.plateau.removeAll();

			Affichage a = new Affichage(this.plateau);
			this.plateau.setAffichage(a);

			this.plateau.add(this.plateau.getGoban());
			this.plateau.add(this.plateau.getAffichage());


			this.fenetre.add(this.plateau);

			this.plateau.getGoban().redessiner();

			this.plateau.tempsDeJeu();



			this.fenetre.setVisible(true);
			this.plateau.chronoStart();
		}

		if ( e.getSource() == this.valider)
		{
			/*score = new Score(this.plateau);

			scorej1 = score.calculScoreJ1();
			scorej2 = score.calculScoreJ2();*/

			fin = new Fin(this.fenetre,this.menu,this.scoreJ1,this.scoreJ2,false,false,false,false,this.plateau);
			this.fenetre.remove(this);
			this.fenetre.add(fin);
			this.fenetre.setVisible(true);
		}
	}


	@Override
	public void mouseDragged(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void mouseClicked(MouseEvent e){}


	/* ======================= SURVOLER ===================== */
	private void survolerGroupe(int x, int y, int moi)
	{
		this.visited[x][y] = true;

		//1er CAS - A gauche
		if ( x > 0 )
		{
			if ( this.jeu[x-1][y] == moi && this.visited[x-1][y] != true )
			{
				this.survolerGroupe(x-1,y,moi);
			}
		}
		//2e CAS - A droite
		if ( x < this.jeu.length - 1 )
		{
			if ( this.jeu[x+1][y] == moi && this.visited[x+1][y] != true )
			{
				this.survolerGroupe(x+1,y,moi);
			}
		}
		//3e CAS - En haut
		if ( y > 0 )
		{
			if ( this.jeu[x][y-1] == moi && this.visited[x][y-1] != true )
			{
				this.survolerGroupe(x,y-1,moi);
			}
		}
		//4e CAS - En bas
		if ( y < this.jeu.length - 1 )
		{
			if ( this.jeu[x][y+1] == moi && this.visited[x][y+1] != true )
			{
				this.survolerGroupe(x,y+1,moi);
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e){
		for ( int i = 0 ; i < this.jeu.length ; i++)
		{
			for ( int j = 0 ; j < this.jeu[i].length ; j++)
			{
				if (this.jeu[i][j] == Goban.Etat.J1VIVANTSURVOLE.getValue())
					this.jeu[i][j] = Goban.Etat.J1.getValue();

				else if (this.jeu[i][j] == Goban.Etat.J2VIVANTSURVOLE.getValue())
					this.jeu[i][j] = Goban.Etat.J2.getValue();

				else if (this.jeu[i][j] == Goban.Etat.J1MORTSURVOLE.getValue())
					this.jeu[i][j] = Goban.Etat.J1MORT.getValue();

				else if (this.jeu[i][j] == Goban.Etat.J2MORTSURVOLE.getValue())
					this.jeu[i][j] = Goban.Etat.J2MORT.getValue();
			}
		}

		this.sourisSurvoler(e);
	}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e){
		for ( int i = 0 ; i < this.jeu.length ; i++)
		{
			for ( int j = 0 ; j < this.jeu[i].length ; j++)
			{
				if ( this.jeu[i][j] == Goban.Etat.J1VIVANTSURVOLE.getValue() )
				{
					//this.jeu[i][j] = Goban.Etat.J1MORTSURVOLE.getValue();
					this.jeu[i][j] = Goban.Etat.J1MORT.getValue();
				}

				else if ( this.jeu[i][j] == Goban.Etat.J2VIVANTSURVOLE.getValue() )
				{
					//this.jeu[i][j] = Goban.Etat.J2MORTSURVOLE.getValue();
					this.jeu[i][j] = Goban.Etat.J2MORT.getValue();
				}

				else if ( this.jeu[i][j] == Goban.Etat.J1MORTSURVOLE.getValue() )
				{
					//this.jeu[i][j] = Goban.Etat.J1VIVANTSURVOLE.getValue();
					this.jeu[i][j] = Goban.Etat.J1.getValue();
				}

				else if ( this.jeu[i][j] == Goban.Etat.J2MORTSURVOLE.getValue() )
				{
					//this.jeu[i][j] = Goban.Etat.J2VIVANTSURVOLE.getValue();
					this.jeu[i][j] = Goban.Etat.J2.getValue();
				}
			}
		}
		this.repaint();

		this.afficherScore();


		this.sourisSurvoler(e);

	}

	@Override
	public void mouseReleased(MouseEvent e){}



	private void sourisSurvoler(MouseEvent e)
	{
		int x = (e.getX() - this.decalageGauche)/(this.goban.getTaille());
		double x1 = (double)(e.getX() - this.decalageGauche)/(double)(this.goban.getTaille());
		if ( x1 >= (double)x+0.5 )
		{
			x = x + 1;
		}
		int y = (e.getY() - this.decalageHaut )/(this.goban.getTaille());
		double y1 = (double)(e.getY() - this.decalageHaut )/(double)this.goban.getTaille();
		if ( y1 >= (double)y+0.5 )
		{
			y = y + 1;
		}
		x = x - 1;
		y = y - 1;

		/*float xF = (float) (( e.getX() - this.decalageGauche ) / ( this.goban.getTaille() )) - (float)(0.5);
		float yF = (float) (( e.getY() - this.decalageHaut ) / ( this.goban.getTaille() )) - (float)(0.5);

		int x = (int) xF;
		int y = (int) yF;*/

		boolean dessus = false;
		this.visited = new boolean [this.goban.getPlateau().length][this.goban.getPlateau().length];
		int value = 0;

		if ( x >= 0 && y >= 0 && x < this.jeu.length && y < this.jeu.length)
		{
			if ( this.jeu[x][y] == Goban.Etat.J1.getValue() || this.jeu[x][y] == Goban.Etat.J1VIVANTSURVOLE.getValue())
			{
				this.survolerGroupe(x,y,Goban.Etat.J1.getValue());
				dessus = true;
				value = Goban.Etat.J1VIVANTSURVOLE.getValue();
			}
			else if ( this.jeu[x][y] == Goban.Etat.J2.getValue() )
			{
				this.survolerGroupe(x,y,Goban.Etat.J2.getValue());
				dessus = true;
				value = Goban.Etat.J2VIVANTSURVOLE.getValue();
			}
			else if ( this.jeu[x][y] == Goban.Etat.J1MORT.getValue() )
			{
				this.survolerGroupe(x,y,Goban.Etat.J1MORT.getValue());
				dessus = true;
				value = Goban.Etat.J1MORTSURVOLE.getValue();
				}
			else if ( this.jeu[x][y] == Goban.Etat.J2MORT.getValue() )
			{
				this.survolerGroupe(x,y,Goban.Etat.J2MORT.getValue());
				dessus = true;
				value = Goban.Etat.J2MORTSURVOLE.getValue();
			}

			/*System.out.println("test");
			System.out.println("x = " + x + " et y = " + y );
			System.out.println("dessus = " + dessus );
			System.out.println("value = " + value );*/
		}

		


		if ( dessus )
		{

			for ( int i = 0 ; i < this.visited.length ; i++)
			{
				for ( int j = 0 ; j < this.visited[i].length ; j++)
				{
					if ( this.visited[i][j] )
					{
						this.jeu[i][j] = value;
					}
				}
			}
		}
		
		this.repaint();
	}



	// ================= NOIR =================

	void pierreNoirVivant(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(Color.black);

		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void pierreNoirMort(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(new Color(0,0,0,100));
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void pierreNoirVivantSurvole(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(Color.black);
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine

		pinceau.setColor(new Color(84,248,215));

		pinceau.drawOval(x1,y1,taille,taille); //on le dessine
	}

	void pierreNoirMortSurvole(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(new Color(0,0,0,100));
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine

		pinceau.setColor(new Color(84,248,215));

		pinceau.drawOval(x1,y1,taille,taille); //on le dessine
	}

	// ================= BLANC =================

	void pierreBlancVivant(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(Color.white);
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void pierreBlancMort(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(new Color(255,255,255,100));
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void pierreBlancVivantSurvole(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(Color.white);
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine

		pinceau.setColor(new Color(251,39,34));

		pinceau.drawOval(x1,y1,taille,taille); //on le dessine
	}

	void pierreBlancMortSurvole(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(new Color(255,255,255,100));
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine

		pinceau.setColor(new Color(251,39,34));

		pinceau.drawOval(x1,y1,taille,taille); //on le dessine
	}

	// ============= AUTRE ===============

	void hoshi(Graphics pinceau,int x, int y, int taille){
		int x1=x-taille/2;
		int y1=y-taille/2;
		pinceau.setColor(Color.black);
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void ligneVerticale(Graphics pinceau,int x1, int y1) {
		int x2 = this.decalageGauche+this.goban.getNombre()*this.goban.getTaille();
		int y2 = y1;
		pinceau.drawLine(x1,y1,x2,y2);
	}
	void ligneHonrizontale(Graphics pinceau,int x1, int y1) {
		int x2 = x1;
		int y2 = this.decalageHaut+this.goban.getNombre()*this.goban.getTaille();
		pinceau.drawLine(x1,y1,x2,y2);
	}

	void carre(Graphics pinceau,int x, int y, int taille) {
		//pinceau.setColor(Color.red);
		pinceau.setColor(new Color(182,101,12));
		pinceau.fillRect(x,y,taille,taille); //on le dessine
		pinceau.setColor(Color.black);
		pinceau.drawRect(x,y,taille,taille); //on le dessine
	}

	@Override
	public void paintComponent(Graphics pinceau) {
		super.paintComponent(pinceau);

		if ( this.goban.getNombre() == 9 )
			this.decalageGauche = Math.max( 0 , Math.min( 280 , 280 + this.plateau.getFenetre().getWidth() - 1000 ) ) ;
		else
			this.decalageGauche = Math.max( 10 , Math.min( 280 , 280 + this.plateau.getFenetre().getWidth() - 1000 ) ) ;
		this.decalageHaut = 100 ; // Math.max( 100 , Math.min( 100 , 100 + this.plateau.getFenetre().getHeight() - 600 ) ) ;

		//this.gaucheFF = Math.max( 0 , Math.min( 100 , 100 + this.plateau.getFenetre().getWidth() - 1000 ) ) ;
		//this.hautFF = Math.max( 0 , Math.min( 100 , 100 + this.plateau.getFenetre().getHeight() - 600 ) ) ;

		/*int t1 =  ((this.regle.getFenetre().getWidth()*400 / 1000 )/(this.nombre) );
		int t2 =  ((this.regle.getFenetre().getHeight()*400 / 600 )/(this.nombre) );
		this.taille = Math.min(t1,t2);*/

		int gauche = this.decalageGauche;
		int haut = this.decalageHaut;
		int taille = this.goban.getTaille();
		int nombre = this.goban.getNombre();

		this.carre(pinceau,gauche,haut,nombre*taille+taille);

		for ( int i = 0 ; i < this.jeu.length ; i++)
		{
			for ( int j = 0 ; j < this.jeu[i].length ; j++)
			{
				pinceau.setColor(Color.black);
				this.ligneVerticale(pinceau,gauche+i+taille,haut+j*taille+taille);
				this.ligneHonrizontale(pinceau,gauche+i*taille+taille,haut+taille);

				if(nombre == 9  && (i==2&&j==2||i==2&&j==6||i==6&&j==2||i==6&&j==6||i==4&&j==4) || 
				   nombre == 13 && (i==3&&j==3||i==3&&j==9||i==9&&j==3||i==9&&j==9||i==6&&j==6) || 
				   nombre == 19 && (i==3&&j==3||i==3&&j==9||i==3&&j==15||
				   						 i==9&&j==3||i==9&&j==9||i==9&&j==15||
				   						 i==15&&j==3||i==15&&j==9||i==15&&j==15))
				{
					this.hoshi(pinceau,gauche+i*taille+taille,haut+j*taille+taille,taille/4);
				}
			}
		}



		for ( int i = 0 ; i < this.jeu.length ; i++)
		{
			for ( int j = 0 ; j < this.jeu[i].length ; j++)
			{
				
				if ( jeu[i][j] == Goban.Etat.J1.getValue() )
				{
					this.pierreNoirVivant(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));	
				}

				else if ( jeu[i][j] == Goban.Etat.J2.getValue() )
				{
					this.pierreBlancVivant(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

				else if ( jeu[i][j] == Goban.Etat.J1MORT.getValue() )
				{
					this.pierreNoirMort(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

				else if ( jeu[i][j] == Goban.Etat.J2MORT.getValue() )
				{
					this.pierreBlancMort(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

				else if ( jeu[i][j] == Goban.Etat.J1VIVANTSURVOLE.getValue() )
				{
					this.pierreNoirVivantSurvole(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

				else if ( jeu[i][j] == Goban.Etat.J2VIVANTSURVOLE.getValue() )
				{
					this.pierreBlancVivantSurvole(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

				else if ( jeu[i][j] == Goban.Etat.J1MORTSURVOLE.getValue() )
				{
					this.pierreNoirMortSurvole(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

				else if ( jeu[i][j] == Goban.Etat.J2MORTSURVOLE.getValue() )
				{
					this.pierreBlancMortSurvole(pinceau,gauche+i*taille+taille,haut+j*taille+taille,(taille*9/10));
				}

			}
		}




	}
}