package nineSquarePuzzle;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import graphique.Fenetre;
import graphique.PoolCarre;

public class Main {
	
	public static String path = "data1.txt";

	public static void main(String[] args){
//		Main sans GUI (interface graphique)
//		int[] tab = {1,-2,-3,4};
//		Instrumentation i = new Instrumentation();
//		Piece p = new Piece("A", tab);
//		i.start();
//		System.out.println(p.toString());
//		p.tourne(1);
//		System.out.println(p.toString());
//		i.stop();
//		System.out.println(i.afficheInfos());
		
//		Avec GUI
		Instrumentation i = new Instrumentation();
		Pool pool = new Pool(path);
		Fenetre fen = new Fenetre(pool, i);
		i.start();
		i.stop();
		fen.setInstrumentation();
	}
}
