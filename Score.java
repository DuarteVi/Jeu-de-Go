import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.event. *;


class Score {
	Plateau plateau;
	Goban goban;

	/* int x_Ko = -1;
	int y_Ko = -1*/

	private boolean [][] visited;
	private int [][] territoire;

	public Score(Plateau plateau)
	{
		this.plateau = plateau;
		this.goban = plateau.getGoban();
	}

	public Score (Plateau plateau, Goban goban)
	{
		this.plateau = plateau;
		this.goban = goban;
	}

	public Score (Goban goban)
	{
		this.goban = goban;
	}

	public float calculScoreJ1()
	{
		float resultat = this.goban.getScoreJ1();
		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++)
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++)
			{
				if (this.goban.getPlateau()[i][j] == Goban.Etat.J1.getValue() 
					|| this.goban.getPlateau()[i][j] == Goban.Etat.J1VIVANTSURVOLE.getValue() )
				{
					resultat++;
				}
			}
		}


		this.territoire = new int [this.goban.getPlateau().length][this.goban.getPlateau().length];

		this.calculerTerritoires( Goban.Etat.J1.getValue() );

		//System.out.println("");
		//System.out.println("New J1");

		for ( int i = 0 ; i < this.territoire.length ; i++)
		{
			for ( int j = 0 ; j < this.territoire[i].length ; j++)
			{
				if ( this.territoire[i][j] == 1 )
				{
					resultat++;
				}

				//if ( this.territoire[j][i] == 1 )
				//{
				//	System.out.print("| 1 |");
				//}
				//else
				//{
				//	System.out.print("| 0 |");
				//}
			}
			//System.out.println("");
		}


		//resultat = resultat - this.goban.getNbPrisonnierJ2();


		return resultat;
	}

	public float calculScoreJ2()
	{
		float resultat = this.goban.getScoreJ2();
		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++)
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++)
			{
				if (this.goban.getPlateau()[i][j] == Goban.Etat.J2.getValue() 
					|| this.goban.getPlateau()[i][j] == Goban.Etat.J2VIVANTSURVOLE.getValue() )
				{
					resultat++;
				}
			}
		}

		
		this.territoire = new int [this.goban.getPlateau().length][this.goban.getPlateau().length];

		this.calculerTerritoires( Goban.Etat.J2.getValue() );

		//System.out.println("");
		//System.out.println("New J2");

		for ( int i = 0 ; i < this.territoire.length ; i++)
		{
			for ( int j = 0 ; j < this.territoire[i].length ; j++)
			{
				if ( this.territoire[i][j] == 1 )
				{
					resultat++;
				}

				//if ( this.territoire[j][i] == 1 )
				//{
				//	System.out.print("| 2 |");
				//}
				//else
				//{
				//	System.out.print("| 0 |");
				//}
			}
			//System.out.println("");
		}

		//resultat = resultat - this.goban.getNbPrisonnierJ1();

		return resultat;
	}

	public int sommeTerritoire(int moi)
	{
		int resultat = 0;

		this.calculerTerritoires( moi );

		for ( int i = 0 ; i < this.territoire.length ; i++)
		{
			for ( int j = 0 ; j < this.territoire[i].length ; j++)
			{
				if ( this.territoire[i][j] == 1 )
				{
					resultat++;
				}
			}
		}

		return resultat;
	}



		/* ----------------------------- COMPTER LES PRISONNIERS ----------------------------- */


	public int prisonnier( int lui , int[][] plateauPrecedent )
	{
		int resultat = 0 ;
		//System.out.println("lui="+lui);

		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++)
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++)
			{
				if ( plateauPrecedent[i][j] == lui && this.goban.getPlateau()[i][j] != lui )
				{
					resultat++;
					//System.out.println("test");
				}
			}
		}

		//System.out.println("resultat="+resultat);
		/*System.out.println("Nouveau");
		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++)
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++)
			{
				System.out.print("| "+this.goban.getPlateau()[j][i]+" |");
			}
			System.out.println("");
		}
		System.out.println("Ancien");
		for ( int i = 0 ; i < plateauPrecedent.length ; i++)
		{
			for ( int j = 0 ; j < plateauPrecedent[i].length ; j++)
			{
				System.out.print("| "+plateauPrecedent[j][i]+" |");
			}
			System.out.println("");
		}
		System.out.println("");
		System.out.println("");*/

		return resultat;
	}


		/* ----------------------------- COMPTER LES TERRITOIRES ----------------------------- */

	private int parcourirTerritoire(int moi,int ennemie, int x, int y,int presenceEnnemie)
	{
		this.visited[x][y] = true;

		//gauche
		if ( x > 0)
		{
			if ( this.visited[x-1][y] == false && presenceEnnemie == 0 )
			{
				if ( this.goban.getPlateau()[x-1][y] == ennemie 
					|| this.goban.getPlateau()[x-1][y] == Goban.Etat.PERSONNE.getValue() + ennemie)
					//Survole de ENNEMIE Vivant et ENNEMIE VIVANT NON SURVOLE
				{
					return 1;
				}
				else if ( this.goban.getPlateau()[x-1][y] != moi 
					&& this.goban.getPlateau()[x-1][y] != Goban.Etat.PERSONNE.getValue() + moi )
					//diférent de MOI et MOI SURVOLE VIVANT
				{
					presenceEnnemie = presenceEnnemie + parcourirTerritoire(moi,ennemie,x-1,y,presenceEnnemie);
				}
			}
		}

		//droite
		if ( x < this.goban.getPlateau().length - 1 && presenceEnnemie == 0 )
		{
			if ( this.visited[x+1][y] == false )
			{
				if ( this.goban.getPlateau()[x+1][y] == ennemie 
					|| this.goban.getPlateau()[x+1][y] == Goban.Etat.PERSONNE.getValue() + ennemie)
				{
					return 1;
				}
				else if ( this.goban.getPlateau()[x+1][y] != moi 
						 && this.goban.getPlateau()[x+1][y] != Goban.Etat.PERSONNE.getValue() + moi )
				{
					presenceEnnemie = presenceEnnemie + parcourirTerritoire(moi,ennemie,x+1,y,presenceEnnemie);
				}
			}
		}

		//haut
		if ( y > 0 && presenceEnnemie == 0 )
		{
			if ( this.visited[x][y-1] == false )
			{
				if ( this.goban.getPlateau()[x][y-1] == ennemie 
					|| this.goban.getPlateau()[x][y-1] == Goban.Etat.PERSONNE.getValue() + ennemie)	
				{
					return 1;
				}
				else if ( this.goban.getPlateau()[x][y-1] != moi 
						  && this.goban.getPlateau()[x][y-1] != Goban.Etat.PERSONNE.getValue() + moi )
				{
					presenceEnnemie = presenceEnnemie + parcourirTerritoire(moi,ennemie,x,y-1,presenceEnnemie);
				}
			}
		}

		//bas
		if ( y < this.goban.getPlateau()[x].length - 1 && presenceEnnemie == 0 )
		{
			if ( this.visited[x][y+1] == false )
			{
				if ( this.goban.getPlateau()[x][y+1] == ennemie 
					|| this.goban.getPlateau()[x][y+1] == Goban.Etat.PERSONNE.getValue() + ennemie)	
				{
					return 1;
				}
				else if ( this.goban.getPlateau()[x][y+1] != moi 
						  && this.goban.getPlateau()[x][y+1] != Goban.Etat.PERSONNE.getValue() + moi )
				{
					presenceEnnemie = presenceEnnemie + parcourirTerritoire(moi,ennemie,x,y+1,presenceEnnemie);
				}
			}
		}
		
		return presenceEnnemie;
	}

	private void calculerTerritoires(int moi)
	{
		int ennemie = Goban.Etat.J1.getValue();
		if ( moi == ennemie )
		{
			ennemie =  Goban.Etat.J2.getValue();
		}

		this.territoire = new int  [this.goban.getPlateau().length][this.goban.getPlateau().length];


		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++)
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++)
			{
				if (this.goban.getPlateau()[i][j] == moi)
				{
					if ( i > 0 )
					{
						if ( this.goban.getPlateau()[i-1][j] != moi &&
							 this.goban.getPlateau()[i-1][j] != ennemie &&
							 this.goban.getPlateau()[i-1][j] != Goban.Etat.PERSONNE.getValue() + moi &&
							 this.goban.getPlateau()[i-1][j] != Goban.Etat.PERSONNE.getValue() + ennemie &&
							 this.territoire[i-1][j] == 0 )
							//Différent de MOI et MOI SURVOLE et ENNEMIE et ENNEMIE SURVOLE
						{
							this.visited = new boolean [this.goban.getPlateau().length][this.goban.getPlateau().length];

							if ( parcourirTerritoire(moi,ennemie,i-1,j,0) == 0 )
							{
								for ( int l = 0 ; l < this.visited.length ; l++ )
								{
									for ( int k = 0 ; k < this.visited[l].length ; k++ )
									{
										if ( this.visited[l][k] )
										{
											this.territoire[l][k] = 1;
										}
									}
								}
							}
						}
					}
					if ( i < this.goban.getPlateau().length - 1 )
					{
						if ( this.goban.getPlateau()[i+1][j] != moi &&
							 this.goban.getPlateau()[i+1][j] != ennemie &&
							 this.goban.getPlateau()[i+1][j] != Goban.Etat.PERSONNE.getValue() + moi &&
							 this.goban.getPlateau()[i+1][j] != Goban.Etat.PERSONNE.getValue() + ennemie &&
							 this.territoire[i+1][j] == 0 )
						{
							this.visited = new boolean [this.goban.getPlateau().length][this.goban.getPlateau().length];

							if ( parcourirTerritoire(moi,ennemie,i+1,j,0) == 0 )
							{
								for ( int l = 0 ; l < this.visited.length ; l++ )
								{
									for ( int k = 0 ; k < this.visited[l].length ; k++ )
									{
										if ( this.visited[l][k] )
										{
											this.territoire[l][k] = 1;
										}
									}
								}
							}
						}
					}
					if ( j > 0 )
					{
						if ( this.goban.getPlateau()[i][j-1] != moi &&
							 this.goban.getPlateau()[i][j-1] != ennemie &&
							 this.goban.getPlateau()[i][j-1] != Goban.Etat.PERSONNE.getValue() + moi &&
							 this.goban.getPlateau()[i][j-1] != Goban.Etat.PERSONNE.getValue() + ennemie &&
							 this.territoire[i][j-1] == 0 )
						{
							this.visited = new boolean [this.goban.getPlateau().length][this.goban.getPlateau().length];

							if ( parcourirTerritoire(moi,ennemie,i,j-1,0) == 0 )
							{
								for ( int l = 0 ; l < this.visited.length ; l++ )
								{
									for ( int k = 0 ; k < this.visited[l].length ; k++ )
									{
										if ( this.visited[l][k] )
										{
											this.territoire[l][k] = 1;
										}
									}
								}
							}
						}
					}
					if ( j < this.goban.getPlateau()[i].length - 1 )
					{
						if ( this.goban.getPlateau()[i][j+1] != moi &&
							 this.goban.getPlateau()[i][j+1] != ennemie &&
							 this.goban.getPlateau()[i][j+1] != Goban.Etat.PERSONNE.getValue() + moi &&
							 this.goban.getPlateau()[i][j+1] != Goban.Etat.PERSONNE.getValue() + ennemie &&
							 this.territoire[i][j+1] == 0 )
						{
							this.visited = new boolean [this.goban.getPlateau().length][this.goban.getPlateau().length];

							if ( parcourirTerritoire(moi,ennemie,i,j+1,0) == 0 )
							{
								for ( int l = 0 ; l < this.visited.length ; l++ )
								{
									for ( int k = 0 ; k < this.visited[l].length ; k++ )
									{
										if ( this.visited[l][k] )
										{
											this.territoire[l][k] = 1;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}



	/* ----------------------------- INSTAURER LES REGLES ----------------------------- */

	private int degreDeLiberte(int moi, int x, int y)
	{
		int nbLiberte = 0 ;
		int ennemie = Goban.Etat.J1.getValue();
		if ( moi == ennemie )
		{
			ennemie = Goban.Etat.J2.getValue();
		} 

		this.visited[x][y] = true;

		//1er CAS - A gauche
		if ( x > 0 )
		{
			if ( this.goban.getPlateau()[x-1][y] == moi && !this.visited[x-1][y] )
			{
				nbLiberte = nbLiberte + degreDeLiberte(moi,x-1,y);
			}	
			if ( this.goban.getPlateau()[x-1][y] != ennemie && this.goban.getPlateau()[x-1][y] != moi )
			{
				nbLiberte++;
			}
		}
		//2e CAS - A droite
		if ( x < this.goban.getPlateau().length - 1 )
		{
			if ( this.goban.getPlateau()[x+1][y] == moi && !this.visited[x+1][y] )
			{
				nbLiberte = nbLiberte + degreDeLiberte(moi,x+1,y);
			}	
			if ( this.goban.getPlateau()[x+1][y] != ennemie && this.goban.getPlateau()[x+1][y] != moi )
			{
				nbLiberte++;
			}
		}
		//3e CAS - En haut
		if ( y > 0 )
		{
			if ( this.goban.getPlateau()[x][y-1] == moi && !this.visited[x][y-1] )
			{
				nbLiberte = nbLiberte + degreDeLiberte(moi,x,y-1);
			}	
			if ( this.goban.getPlateau()[x][y-1] != ennemie && this.goban.getPlateau()[x][y-1] != moi )
			{
				nbLiberte++;
			}
		}
		//4e CAS - En bas
		if ( y < this.goban.getPlateau().length - 1 )
		{
			if ( this.goban.getPlateau()[x][y+1] == moi && !this.visited[x][y+1] )
			{
				nbLiberte = nbLiberte + degreDeLiberte(moi,x,y+1);
			}	
			if ( this.goban.getPlateau()[x][y+1] != ennemie && this.goban.getPlateau()[x][y+1] != moi )
			{
				nbLiberte++;
			}
		}
 
		return nbLiberte;
	}

	public void mangerLesPions(int lui)
	{
		this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
		int liberte;

		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++ ) //On regarde ce qu'on enlève à J1
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++ )
			{
				if ( this.goban.getPlateau()[i][j] == lui)
				{
					liberte = degreDeLiberte(lui,i,j);

					if ( liberte == 0 )
					{
						for ( int x = 0 ; x < this.goban.getPlateau().length ; x++ )
						{
							for ( int y = 0 ; y < this.goban.getPlateau().length ; y++ )
							{
								if ( this.visited[x][y] == true )
								{
									this.goban.getPlateau()[x][y] = Goban.Etat.VIDE.getValue();
									//Le J2 capture des prisonniers

									/*if ( lui == Goban.Etat.J1.getValue() )
									{
										this.goban.setNbPrisonnierJ2( this.goban.getNbPrisonnierJ2() + 1 ); 
									}
									else
									{
										this.goban.setNbPrisonnierJ1( this.goban.getNbPrisonnierJ1() + 1 ); 
									}*/

									/*if ( this.x_Ko == -1 )
									{
										this.x_Ko = x;
										this.y_Ko = y;
									}
									else
									{
										this.x_Ko = -2;
									}*/
								}
							}
						}
					}
					this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
				}					
			}
		}
	}

	public void interdirePions(int moi)
	{
		this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
		int liberte;

		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++ ) //On regarde ce qu'on enlève à J1
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++ )
			{
				if ( this.goban.getPlateau()[i][j] == Goban.Etat.VIDE.getValue())
				{
					this.goban.getPlateau()[i][j] = moi;

					liberte = degreDeLiberte(moi,i,j);

					if ( liberte == 0 )
					{
						if ( moi == Goban.Etat.J1.getValue() )
						{
							this.goban.getPlateau()[i][j] = Goban.Etat.INTERDITJ1.getValue();
						}
						else
						{
							this.goban.getPlateau()[i][j] = Goban.Etat.INTERDITJ2.getValue();
						}
					}
					else
					{
						this.goban.getPlateau()[i][j] = Goban.Etat.VIDE.getValue();
					}
					//this.goban.getPlateau()[i][j] = Goban.Etat.INTERDITJ2.getValue();
					this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
				}					
			}
		}
	}

	public void nePasInterdire(int moi,int moiInterdit)
	{
		this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
		int liberte;
		boolean changerLaValeur = false;

		int ennemie = Goban.Etat.J1.getValue();
		if ( moi == ennemie )
		{
			ennemie = Goban.Etat.J2.getValue();
		} 

		for ( int i = 0 ; i < this.goban.getPlateau().length ; i++ ) //On regarde ce qu'on enlève à J1
		{
			for ( int j = 0 ; j < this.goban.getPlateau()[i].length ; j++ )
			{
				if ( this.goban.getPlateau()[i][j] == moiInterdit )
				{
					this.goban.getPlateau()[i][j] = moi;

					//GAUCHE
					if ( i > 0 && !changerLaValeur)
					{
						if ( this.goban.getPlateau()[i-1][j] == ennemie )
						{
							liberte = degreDeLiberte(ennemie,i-1,j);
							if ( liberte == 0 )
							{
								changerLaValeur = true;
							}
							this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
						}
					}

					//DROITE
					if ( i < this.goban.getPlateau().length-1 && !changerLaValeur)
					{
						if ( this.goban.getPlateau()[i+1][j] == ennemie )
						{
							liberte = degreDeLiberte(ennemie,i+1,j);
							if ( liberte == 0 )
							{
								changerLaValeur = true;
							}
							this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
						}
					}

					//HAUT
					if ( j > 0 && !changerLaValeur)
					{
						if ( this.goban.getPlateau()[i][j-1] == ennemie )
						{
							liberte = degreDeLiberte(ennemie,i,j-1);
							if ( liberte == 0 )
							{
								changerLaValeur = true;
							}
							this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
						}
					}

					//BAS
					if ( j < this.goban.getPlateau().length-1 && !changerLaValeur)
					{
						if ( this.goban.getPlateau()[i][j+1] == ennemie )
						{
							liberte = degreDeLiberte(ennemie,i,j+1);
							if ( liberte == 0 )
							{
								changerLaValeur = true;
							}
							this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
						}
					}

					
					if ( changerLaValeur )
					{
						this.goban.getPlateau()[i][j] = Goban.Etat.VIDE.getValue();
					}
					else
					{
						this.goban.getPlateau()[i][j] = moiInterdit;
					}
					//this.goban.getPlateau()[i][j] = Goban.Etat.INTERDITJ2.getValue();
					changerLaValeur = false;
					this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
				}
			}
		}

		/*if ( this.x_Ko >= 0 )
		{
			this.goban.getPlateau()[this.x_Ko][this.y_Ko] = moiInterdit;
		}
		this.x_Ko = -1;
		this.y_Ko = -1;*/
	}


	public void regleDuKo(int moi)
	{
		int interditMoi = Goban.Etat.INTERDITJ1.getValue();
		int ennemie = Goban.Etat.J2.getValue();

		if ( moi == Goban.Etat.J2.getValue() )
		{
			interditMoi = Goban.Etat.INTERDITJ2.getValue();
			ennemie = Goban.Etat.J1.getValue();
		}

		int [][] plateauAncien = {};
		int x_Ko = -1;
		int y_Ko = -1;

		int x_Ennemie = -1;
		int y_Ennemie = -1;

		int compteur = 0;

		if ( this.plateau.getListeGoban().size() > 2 )
		{
			plateauAncien = this.plateau.getListeGoban().get(1).getPlateau();
		}
		

		for ( int i = 0 ; i < plateauAncien.length ; i++ )
		{
			for ( int j = 0 ; j < plateauAncien[i].length ; j++ )
			{
				if ( this.goban.getPlateau()[i][j] != moi && plateauAncien[i][j] == moi)
				{
					if ( compteur == 0 )
					{
						x_Ko = i;
						y_Ko = j;
					}
					compteur++;					
				}
			}
		}

		for ( int i = 0 ; i < plateauAncien.length ; i++ )
		{
			for ( int j = 0 ; j < plateauAncien[i].length ; j++ )
			{
				if ( this.goban.getPlateau()[i][j] == ennemie && plateauAncien[i][j] != ennemie)
				{
					x_Ennemie = i;
					y_Ennemie = j;				
				}
			}
		}

		int compteur2 = 0;
		int liberte = 0;

		if ( x_Ennemie >= 0 && y_Ennemie >= 0 )
		{
			this.visited = new boolean[this.goban.getPlateau().length][this.goban.getPlateau().length];
			liberte = this.degreDeLiberte(ennemie,x_Ennemie,y_Ennemie);

			for ( int i = 0 ; i < this.visited.length && compteur2 < 2 ; i++ )
			{
				for ( int j = 0 ; j < this.visited.length && compteur2 < 2 ; j++ )
				{
					if ( this.visited[i][j] == true )
					{
						compteur2++;
					}
				}
			}
		}

		if ( compteur == 1 && compteur2 < 2  )
		{
			//System.out.println("-----> x="+x_Ko+" y="+y_Ko);
			this.goban.getPlateau()[x_Ko][y_Ko] = interditMoi;
		}

		
		/*for ( int i = 0 ; i < plateauAncien.length ; i++ )
		{
			for ( int j = 0 ; j < plateauAncien[i].length ; j++ )
			{
				System.out.print("| " + plateauAncien[j][i] + " |");
			}
			System.out.println("");
		}

		System.out.println("---------------");*/

	}


















/*


	public void regleDuKoBIS(int moi)
	{
		int interditMoi = Goban.Etat.INTERDITJ2.getValue();
		if ( moi == Goban.Etat.J1.getValue() )
		{
			interditMoi = Goban.Etat.INTERDITJ1.getValue();
		}

		int [][] plateauActuel = new int [this.goban.getPlateau().length][this.goban.getPlateau().length];
		int [][] plateauActuelBis = new int [this.goban.getPlateau().length][this.goban.getPlateau().length];
		boolean ko = false;
		boolean difference = false;
		int l = 0;
		int k = 0;

		Goban gobanTemp;
		Score scoreTemp;

		for ( int i = 0 ; i < plateauActuel.length ; i++ )
		{
			for ( int j = 0 ; j < plateauActuel[i].length ; j++ )
			{
				plateauActuel[i][j] = this.goban.getPlateau()[i][j];
				plateauActuelBis[i][j] = this.goban.getPlateau()[i][j];
			}
		}

		if ( this.plateau.getListeGoban().size() > 2 )
		{
			int [][] plateauAncien = this.plateau.getListeGoban().get(1).getPlateau();

			//System.out.println("test");

			for ( l = 0 ; l < plateauActuel.length && !ko ; l++ )
			{
				for ( k = 0 ; k < plateauActuel[l].length && !ko ; k++ )
				{
					if ( plateauActuel[l][k] == Goban.Etat.VIDE.getValue() )
					{
						plateauActuel[l][k] = moi;
						gobanTemp = new Goban(plateauActuel);
						scoreTemp = new Score(gobanTemp);
						scoreTemp.mangerLesPions(Goban.Etat.J1.getValue());
						//scoreTemp.interdirePions(Goban.Etat.J1.getValue());
			       		//scoreTemp.nePasInterdire(Goban.Etat.J1.getValue(),Goban.Etat.INTERDITJ1.getValue());
						//
						for ( int x = 0 ; x < plateauActuel.length && !difference ; x++ )
						{
							for ( int y = 0 ; y < plateauActuel[x].length && !difference ; y++ )
							{
								if ( gobanTemp.getPlateau()[x][y] == Goban.Etat.J1.getValue() && plateauAncien[x][y] != Goban.Etat.J1.getValue() ||
									 gobanTemp.getPlateau()[x][y] == Goban.Etat.J2.getValue() && plateauAncien[x][y] != Goban.Etat.J2.getValue() ||
									 gobanTemp.getPlateau()[x][y] != Goban.Etat.J1.getValue() && plateauAncien[x][y] == Goban.Etat.J1.getValue() ||
									 gobanTemp.getPlateau()[x][y] != Goban.Etat.J2.getValue() && plateauAncien[x][y] == Goban.Etat.J2.getValue() )
								{
									difference = true;
								}
							}
						}

						if ( !difference )
						{
							ko = true;
						}
						else
						{
							//for ( int i = 0 ; i < plateauActuelle.length ; i++ )
							//{
							//	for ( int j = 0 ; i < plateauActuelle[i].length ; i++ )
							//	{
							//		plateauActuel[i][j] = this.goban.getPlateau()[i][j];
							//		plateauActuelBis[i][j] = this.goban.getPlateau()[i][j];
							//	}
							//}

							plateauActuel[l][k] = Goban.Etat.J1.getValue();

							difference = false;
						}
					}
				}
			}

			if ( ko )
			{
				this.goban.getPlateau()[l][k] = interditMoi;
			}
		}
		

	}*/

	
}