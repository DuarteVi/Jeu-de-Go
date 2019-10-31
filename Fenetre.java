import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;

class Fenetre extends JFrame{

	private int decalageGobanGauche = 50;
	private int decalageGobanHaut = 50 ;
	private int tailleCaseGoban = 0;

	public Fenetre ()
	{
		this.setLocation(0,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLayout(new BoxLayout(this.getco, BoxLayout.X_AXIS));	
		this.setTitle("Jeu de Go");

		//400
		//460
		//this.setMinimumSize(new Dimension(900,1150));

		this.setMinimumSize(new Dimension(900,1150));
		//this.setMinimumSize(new Dimension(400,460));
		this.setSize(950, 580);
		this.setPreferredSize(new Dimension(950, 580));

		this.setBackground(new Color(238,221,221));

		Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
    	this.setIconImage(icon);

    	this.setCursor(new Cursor(Cursor.HAND_CURSOR));

    	//System.out.println("getWidth"+this.getContentPane().getWidth());
    	//System.out.println("getHeight"+this.getContentPane().getHeight());

    	//this.setBounds(0,0,600,1000);
	}

}