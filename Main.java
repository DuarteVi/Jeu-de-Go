import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

import java.awt.Color;


public class Main {
public static void main(String[] args) {
	Fenetre fenetre = new Fenetre();
	Menu menu = new Menu(fenetre);
	fenetre.add(menu);

	//enetre.setBounds(0,0,1000,600);
	//fenetre.setSize(1000, 600);
	//fenetre.setSize(1000, 600);
	//fenetre.setPreferredSize(new Dimension(1000, 600));

	fenetre.setVisible(true);
	}
}