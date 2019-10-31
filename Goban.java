import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;

class Goban extends JPanel implements MouseListener,MouseMotionListener {
	private int taille;
	private int tailleDeBase;
	private int nombre;
	private int decalage;
	private int [][] plateau;	//demande accesseur

	private int x = -1;	//demande accesseur
	private int y = -1;	//demande accesseur
	private int pierreX = -1;	//demande accesseur
	private int pierreY = -1;	//demande accesseur
	private int tourDeJouer = 1;	//demande accesseur

	private int nbPierreNoir = 0;
	private int nbPierreBlanche = 0;


	private int gaucheFF = 0;
	private int hautFF = 0;


	Plateau regle;

	private float scoreJ1 = 0f;
	private float scoreJ2 = 7.5f;
	//private float scoreDeDepartJ2 = 7.5f;

	private int nbPrisonnierJ1 = 0;
	private int nbPrisonnierJ2 = 0;

	private int tempsDeJeuJ1 = -1;
	private int tempsDeJeuJ1DeBase = -1;
	private int nbByoYJ1 = -1;
	private int tempsDeJeuJ2 = -1;
	private int tempsDeJeuJ2DeBase = -1;
	private int nbByoYJ2 = -1;
	private boolean periodeByoYJ1 = false;
	private boolean periodeByoYJ2 = false;


	public static enum Etat {
		VIDE(0),
		J1(1),
		J2(2),
		SURVOLE(3),
		INTERDITJ1(4),
		INTERDITJ2(5),		
		J2MORT(6),			 // PERSONNE - J2
		J1MORT(7),			 // PERSONNE - J1
		PERSONNE(8),				//Ca va servir dans la class Score	
		J1VIVANTSURVOLE(9),	 // PERSONNE + J1
		J2VIVANTSURVOLE(10), // PERSONNE + J2
		J1MORTSURVOLE(11),
		J2MORTSURVOLE(12);
	 
		/** L'attribut qui contient la valeur associé à l'enum */
		private final int value;
	 
		/** Le constructeur qui associe une valeur à l'enum */
		private Etat(int value) {
			this.value = value;
		}
	 
		/** La méthode accesseur qui renvoit la valeur de l'enum */
		public int getValue() {
			return this.value;
		}
	};
	

	/*LinkedList<Goban> listeGoban = new LinkedList<Goban>();
	int offsetGoban;*/
	public Goban(){}


	public Goban(int nombre,Plateau regle) {

		this.regle = regle;

		if (nombre < 12)
		{
			this.nombre = 9;
		}
		else if (nombre < 18)
		{
			this.nombre = 13;
		}
		else
		{
			this.nombre = 19;
		}


		this.taille = 400/(this.nombre);
		this.decalage = this.taille;
		this.tailleDeBase = this.taille;


		this.plateau = new int[nombre][nombre];

		this.setLocation(500,500);
		this.setSize(this.taille*this.nombre+this.decalage,this.taille*this.nombre+this.decalage);
		//this.setMinimumSize(new Dimension(this.taille*this.nombre+this.decalage,this.taille*this.nombre+this.decalage));
		this.setPreferredSize(new Dimension(this.taille*this.nombre+this.decalage,this.taille*this.nombre+this.decalage));
		this.setBackground(new Color(238,221,221));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/*public Goban(int [][] plateau){
		this.plateau = plateau;
	}*/


	public void redessiner()
	{
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e){
	}

	@Override
	public void mouseExited(MouseEvent e){
	}

	@Override
	public void mouseClicked(MouseEvent e){
		/*if (e.getButton() == MouseEvent.BUTTON1)
		{
			//if (this.x >= 0 && this.x < this.plateau.length && this.y >= 0 && this.y < this.plateau[this.x].length && this.plateau[this.x][this.y]!=1 && this.plateau[this.x][this.y]!=2)	
			if (this.x >= 0 && this.x < this.plateau.length && this.y >= 0 && this.y < this.plateau[this.x].length && this.plateau[this.x][this.y]==Goban.Etat.SURVOLE.getValue())	
			{
				if ( this.tourDeJouer == 1){
					this.regle.prochainGoban(this,1);

				}
				else{
					this.regle.prochainGoban(this,2);
				}
			}
		}
		this.repaint();*/
	}

	@Override
	public void mouseMoved(MouseEvent e){

		//int this.gaucheFF = this.regle.getFenetre().getDecalageGobanHaut() ;
		//int this.hautFF = this.regle.getFenetre().getDecalageGobanGauche() ;

		//System.out.println("moved : x="+e.getX()/this.nombre+" y="+e.getY()/this.nombre);
		for ( int i = 0 ; i < this.plateau.length ; i++)
		{
			for ( int j = 0 ; j < this.plateau[i].length ; j++)
			{
				if (this.plateau[i][j] == Goban.Etat.SURVOLE.getValue())
					this.plateau[i][j] = Goban.Etat.VIDE.getValue();
			}
		}
		//System.out.println("cliked : x="+e.getX()+" y="+e.getY());
		//int x = (e.getX()/this.taille) + (this.hautFF/this.taille);
		//double x1 = ((double)e.getX()/(double)this.taille) + ((double)this.hautFF/(double)this.taille) ;
		int x = (e.getX() - this.gaucheFF)/this.taille;
		double x1 = ((double)e.getX() - (double)this.gaucheFF)/(double)this.taille;
		if ( x1 >= (double)x+0.5 )
		{
			x = x + 1;
		}

		//int y = (e.getY()/this.taille) + (this.gaucheFF/this.taille);
		//double y1 = ((double)e.getY()/(double)this.taille) + ((double)this.gaucheFF/(double)this.taille) ;
		int y = (e.getY() - this.hautFF)/this.taille;
		double y1 = ((double)e.getY() - (double)this.hautFF)/(double)this.taille;
		if ( y1 >= (double)y+0.5 )
		{
			y = y + 1;
		}


		x = x - 1;
		y = y - 1;
		this.x = x;
		this.y = y;

		/*if ( this.tourDeJouer == 1 )
		{
			if (x >= 0 && x < this.plateau.length && y >= 0 && y < this.plateau[x].length &&
			 this.plateau[x][y]!=Goban.Etat.J1.getValue() && this.plateau[x][y]!=Goban.Etat.J2.getValue() && 
			 this.plateau[x][y]!=Goban.Etat.INTERDITJ1.getValue() )	
			{
				this.plateau[x][y] = Goban.Etat.SURVOLE.getValue();
			}
		}
		else
		{
			if (x >= 0 && x < this.plateau.length && y >= 0 && y < this.plateau[x].length &&
			 this.plateau[x][y]!=Goban.Etat.J1.getValue() && this.plateau[x][y]!=Goban.Etat.J2.getValue() && 
			 this.plateau[x][y]!=Goban.Etat.INTERDITJ2.getValue() )	
			{
				this.plateau[x][y] = Goban.Etat.SURVOLE.getValue();
			}
		}*/
		if (x >= 0 && x < this.plateau.length && y >= 0 && y < this.plateau[x].length &&
			 this.plateau[x][y]!=Goban.Etat.J1.getValue() && this.plateau[x][y]!=Goban.Etat.J2.getValue() && 
			 this.plateau[x][y]!=Goban.Etat.INTERDITJ1.getValue() && this.plateau[x][y]!=Goban.Etat.INTERDITJ2.getValue() )	
			{
				this.plateau[x][y] = Goban.Etat.SURVOLE.getValue();
			}
		
		this.repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e){
		//System.out.println("entered : x="+e.getX()/this.nombre+" y="+e.getY()/this.nombre);
	}

	@Override
	public void mousePressed(MouseEvent e){
		//System.out.println("pressed : x="+e.getX()/this.nombre+" y="+e.getY()/this.nombre);
		if (e.getButton() == MouseEvent.BUTTON1)
		{
			//if (this.x >= 0 && this.x < this.plateau.length && this.y >= 0 && this.y < this.plateau[this.x].length && this.plateau[this.x][this.y]!=1 && this.plateau[this.x][this.y]!=2)	
			if (this.x >= 0 && this.x < this.plateau.length && this.y >= 0 && this.y < this.plateau[this.x].length && this.plateau[this.x][this.y]==Goban.Etat.SURVOLE.getValue())	
			{
				if ( this.tourDeJouer == 1){
					this.regle.prochainGoban(this,1);

				}
				else{
					this.regle.prochainGoban(this,2);
				}
			}
		}
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e){
		//System.out.println("released : x="+e.getX()/this.nombre+" y="+e.getY()/this.nombre);
	}



	/* ------------------------- FONCTION UTILES ------------------------- */
	public void egaliserGoban(Goban g){
		this.tempsDeJeuJ1 = g.getTempsDeJeuJ1();
		this.tempsDeJeuJ1DeBase = g.getTempsDeJeuJ1DeBase();
		this.tempsDeJeuJ2 = g.getTempsDeJeuJ2();
		this.tempsDeJeuJ2DeBase = g.getTempsDeJeuJ2DeBase();

		//this.scoreDeDepartJ2 = g.getScoreDeDepartJ2();
		this.scoreJ1 = g.getScoreJ1();
		this.scoreJ2 = g.getScoreJ2();

		this.nbPrisonnierJ1 = g.getNbPrisonnierJ1();
		this.nbPrisonnierJ2 = g.getNbPrisonnierJ2();

		this.nbByoYJ1 = g.getNbByoYJ1();
		this.nbByoYJ2 = g.getNbByoYJ2();

		this.periodeByoYJ1 = g.getPeriodeByoYJ1();
		this.periodeByoYJ2 = g.getPeriodeByoYJ2();
	}

	/* ------------------------- ACCESSEUR ------------------------- */


	public int[][] getPlateau()
	{
		return this.plateau;
	}
	public void setPlateau(int[][] p)
	{
		this.plateau = p;
	}

	public int get_X()
	{
		return this.x;
	}
	public void set_X(int i)
	{
		this.x = i;
	}

	public int get_Y()
	{
		return this.y;
	}
	public void set_Y(int j)
	{
		this.y = j;
	}

	public int getTourDeJouer()
	{
		return this.tourDeJouer;
	}
	public void setTourDeJouer(int t)
	{
		this.tourDeJouer = t;
	}

	public float getScoreJ1()
	{
		return this.scoreJ1;
	}
	public void setScoreJ1(float f)
	{
		this.scoreJ1 = f;
	}

	public float getScoreJ2()
	{
		return this.scoreJ2;
	}
	public void setScoreJ2(float f)
	{
		this.scoreJ2 = f;
	}

	/*public float getScoreDeDepartJ2()
	{
		return this.scoreDeDepartJ2;
	}
	public void setScoreDeDepartJ2(float f)
	{
		this.scoreDeDepartJ2 = f;
	}*/

	public int getTempsDeJeuJ1()
	{
		return this.tempsDeJeuJ1;
	}
	public void setTempsDeJeuJ1(int t)
	{
		this.tempsDeJeuJ1 = t;
	}

	public int getTempsDeJeuJ2()
	{
		return this.tempsDeJeuJ2;
	}
	public void setTempsDeJeuJ2(int t)
	{
		this.tempsDeJeuJ2 = t;
	}

	public int getTempsDeJeuJ1DeBase()
	{
		return this.tempsDeJeuJ1DeBase;
	}
	public void setTempsDeJeuJ1DeBase(int t)
	{
		this.tempsDeJeuJ1DeBase = t;
	}

	public int getTempsDeJeuJ2DeBase()
	{
		return this.tempsDeJeuJ2DeBase;
	}
	public void setTempsDeJeuJ2DeBase(int t)
	{
		this.tempsDeJeuJ2DeBase = t;
	}

	public int getNbPrisonnierJ1()
	{
		return this.nbPrisonnierJ1;
	}
	public void setNbPrisonnierJ1(int n)
	{
		this.nbPrisonnierJ1 = n;
	}

	public int getNbPrisonnierJ2()
	{
		return this.nbPrisonnierJ2;
	}
	public void setNbPrisonnierJ2(int n)
	{
		this.nbPrisonnierJ2 = n;
	}


	
	public int getPierreX()
	{
		return this.pierreX;
	}
	public void setPierreX(int i)
	{
		this.pierreX = i;
	}

	public int getPierreY()
	{
		return this.pierreY;
	}
	public void setPierreY(int j)
	{
		this.pierreY = j;
	}

	public int getNbPierreNoir()
	{
		return this.nbPierreNoir;
	}
	public void setNbPierreNoir(int nb)
	{
		this.nbPierreNoir = nb;
	}

	public int getNbPierreBlanche()
	{
		return this.nbPierreBlanche;
	}
	public void setNbPierreBlanche(int nb)
	{
		this.nbPierreBlanche = nb;
	}



	public int getNbByoYJ1()
	{
		return this.nbByoYJ1;
	}
	public void setNbByoYJ1(int i)
	{
		this.nbByoYJ1 = i;
	}
	public int getNbByoYJ2()
	{
		return this.nbByoYJ2;
	}
	public void setNbByoYJ2(int i)
	{
		this.nbByoYJ2 = i;
	}

	public boolean getPeriodeByoYJ1()
	{
		return this.periodeByoYJ1;
	}
	public void setPeriodeByoYJ1(boolean b)
	{
		this.periodeByoYJ1 = b;
	}
	public boolean getPeriodeByoYJ2()
	{
		return this.periodeByoYJ2;
	}
	public void setPeriodeByoYJ2(boolean b)
	{
		this.periodeByoYJ2 = b;
	}

	public int getTaille()
	{
		return this.taille;
	}
	public int getNombre()
	{
		return this.nombre;
	}



	/* ------------------------- DESSINER ------------------------- */

	void cercleNoir(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(Color.black);

		pinceau.fillOval(x1,y1,taille,taille); //on le dessine

	}

	void cercleBlanc(Graphics pinceau,int x, int y, int taille) {
		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		pinceau.setColor(Color.white);
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void hoshi(Graphics pinceau,int x, int y, int taille){
		int x1=x-taille/2;
		int y1=y-taille/2;
		pinceau.setColor(Color.black);
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void ligneVerticale(Graphics pinceau,int x1, int y1) {

		int x2 = this.nombre*this.taille + this.gaucheFF;
		int y2 = y1;
		pinceau.drawLine(x1,y1,x2,y2);
	}
	void ligneHonrizontale(Graphics pinceau,int x1, int y1) {
		int x2 = x1;
		int y2 = this.nombre*this.taille + this.hautFF;
		pinceau.drawLine(x1,y1,x2,y2);
	}

	void survole(Graphics pinceau,int x, int y, int taille) {
		//pinceau.setColor(Color.red);
		if ( this.tourDeJouer == 1 )
		{
			pinceau.setColor(new Color(0,0,0,100));
		}
		else
		{
			pinceau.setColor(new Color(255,255,255,100));
		}
		
		//pinceau.fillRect(x-(taille/2),y-(taille/2),taille,taille); //on le dessine
		//pinceau.fillRect(x,y,taille,taille); //on le dessine

		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		//pinceau.setColor(Color.black);

		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
	}

	void cercleRouge(Graphics pinceau,int x, int y, int taille)
	{
		pinceau.setColor(new Color(255,0,0,100));

		int x1 = x - (taille/2);
		int y1 = y - (taille/2);
		
		pinceau.fillOval(x1,y1,taille,taille); //on le dessine
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

		//FF = Fonction de la Fenetre
		/*this.gaucheFF = this.regle.getFenetre().getDecalageGobanHaut() ;
		this.hautFF = this.regle.getFenetre().getDecalageGobanGauche() ;*/
		//int tailleFF = this.regle.getFenetre().getTailleCaseGoban() ;

		this.gaucheFF = Math.max( 0 , Math.min( 100 , 100 + this.regle.getFenetre().getWidth() - 1000 ) ) ;
		this.hautFF = Math.max( 0 , Math.min( 100 , 100 + this.regle.getFenetre().getHeight() - 600 ) ) ;

		int t1 =  ((this.regle.getFenetre().getWidth()*400 / 1000 )/(this.nombre) );
		int t2 =  ((this.regle.getFenetre().getHeight()*400 / 600 )/(this.nombre) );
		this.taille = Math.min(t1,t2);
		this.decalage = this.taille;
		//System.out.println(this.regle.getFenetre().getWidth()+" == "+this.regle.getFenetre().getHeight());

		// => 100 -> 400
		// => x -> 

		this.carre(pinceau,this.gaucheFF,this.hautFF,this.nombre*this.taille+this.decalage);
		
		for ( int i = 0 ; i < this.plateau.length ; i++)
		{
			for ( int j = 0 ; j < this.plateau[i].length ; j++)
			{
				pinceau.setColor(Color.black);
				this.ligneVerticale(pinceau,this.gaucheFF+i+this.decalage,this.hautFF+j*this.taille+this.decalage);
				this.ligneHonrizontale(pinceau,this.gaucheFF+i*this.taille+this.decalage,this.hautFF+j+this.decalage);

				if(this.nombre == 9  && (i==2&&j==2||i==2&&j==6||i==6&&j==2||i==6&&j==6||i==4&&j==4) || 
				   this.nombre == 13 && (i==3&&j==3||i==3&&j==9||i==9&&j==3||i==9&&j==9||i==6&&j==6) || 
				   this.nombre == 19 && (i==3&&j==3||i==3&&j==9||i==3&&j==15||
				   						 i==9&&j==3||i==9&&j==9||i==9&&j==15||
				   						 i==15&&j==3||i==15&&j==9||i==15&&j==15))
				{
					this.hoshi(pinceau,this.gaucheFF+i*this.taille+this.decalage,this.hautFF+j*this.taille+this.decalage,this.taille/4);
				}
			}
		}

		for ( int i = 0 ; i < this.plateau.length ; i++)
		{
			for ( int j = 0 ; j < this.plateau[i].length ; j++)
			{
				
				if ( plateau[i][j] == Goban.Etat.J1.getValue() )
				{
					this.cercleNoir(pinceau,this.gaucheFF+i*this.taille+this.decalage,this.hautFF+j*this.taille+this.decalage,(this.taille*9/10));	
				}
				else if ( plateau[i][j] == Goban.Etat.J2.getValue() )
				{
					this.cercleBlanc(pinceau,this.gaucheFF+i*this.taille+this.decalage,this.hautFF+j*this.taille+this.decalage,(this.taille*9/10));
				}

				else if ( plateau[i][j] == Goban.Etat.SURVOLE.getValue() )
				{
					this.survole(pinceau,this.gaucheFF+i*this.taille+this.decalage,this.hautFF+j*this.taille+this.decalage,(this.taille*9/10));
				}

				else if ( plateau[i][j] != Goban.Etat.VIDE.getValue() )
				{
					this.cercleRouge(pinceau,this.gaucheFF+i*this.taille+this.decalage,this.hautFF+j*this.taille+this.decalage,(this.taille*9/10));
				}

			}
		}
	}

}

