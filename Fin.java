import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;
import java.awt.event. *;

class Fin extends JPanel implements ActionListener {

	Fenetre fenetre;
	Menu menu;

	private JButton quitter;



	public Fin(Fenetre fenetre, Menu menu, float scorej1, float scorej2, boolean abandonJ1, boolean abandonJ2,boolean tempsJ1, boolean tempsJ2, Plateau plateau){
		this.fenetre = fenetre;
		this.menu = menu;
		plateau.chronoStop();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel fin = new JLabel("Partie Terminee");
		fin.setFont(new Font("fin",Font.BOLD,50));
		fin.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(fin);

		/* --------------------------------------- GAGNANT --------------------------------------- */
		JPanel panGagnant = new JPanel();
		panGagnant.setAlignmentX( Component.CENTER_ALIGNMENT );
		panGagnant.setLayout(new BoxLayout(panGagnant, BoxLayout.Y_AXIS));

		JLabel gagnant = new JLabel("J1 gagne la partie");
		if ( abandonJ1 || tempsJ1 || (scorej1 < scorej2 && !abandonJ2 && !tempsJ2) )
		{
			gagnant.setText("J2 gagne la partie");
		}
		if ( !tempsJ1 && !tempsJ2 && !abandonJ1 && !abandonJ2 && scorej1 == scorej2 )
		{
			gagnant.setText("Egalite");
		}
		gagnant.setFont(new Font("Gagnant",Font.BOLD,35));
		gagnant.setAlignmentX( Component.CENTER_ALIGNMENT );
		panGagnant.add(gagnant);
		this.add(panGagnant);

		/* --------------------------------------- SCORE --------------------------------------- */
		JLabel score = new JLabel("Score :");
		score.setFont(new Font("Score",Font.BOLD,27));
		score.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.add(score);

		JPanel panScore = new JPanel();
		panScore.setAlignmentX( Component.CENTER_ALIGNMENT );
		panScore.setLayout(new BoxLayout(panScore, BoxLayout.Y_AXIS));

		JLabel j1 = new JLabel("j1 : " + scorej1);
		j1.setAlignmentX( Component.LEFT_ALIGNMENT );
		if ( abandonJ1 )
		{
			j1.setText("j1 : " + scorej1 + " (abandon)");
		}
		if ( tempsJ1 )
		{
			j1.setText("j1 : " + scorej1 + " (temps ecoule)");
		}
		JLabel j2 = new JLabel("j2 : " + scorej2);
		j2.setAlignmentX( Component.LEFT_ALIGNMENT );
		if ( abandonJ2 )
		{
			j2.setText("j2 : " + scorej2 + " (abandon)");
		}
		if ( tempsJ2 )
		{
			j2.setText("j2 : " + scorej2 + " (temps ecoule)");
		}
		j1.setFont(new Font("J1",Font.BOLD,20));
		j2.setFont(new Font("J2",Font.BOLD,20));
		panScore.add(j1);
		panScore.add(j2);
		panScore.setBackground(new Color(238,221,221));

		this.add(panScore);

		/* --------------------------------------- QUITTER --------------------------------------- */

		this.quitter = new JButton ("QUITTER"); 
		this.quitter.setAlignmentX( Component.CENTER_ALIGNMENT );
		this.quitter.addActionListener(this);
		this.add(this.quitter);

		this.setBackground(new Color(238,221,221));
	}

	public void actionPerformed(ActionEvent e) {
		this.fenetre.remove(this);
		this.fenetre.add(this.menu);
		this.menu.redessiner();
		this.fenetre.setVisible(true);
	}



	@Override
	public void paintComponent(Graphics pinceau) {
		super.paintComponent(pinceau);
	
	}


}