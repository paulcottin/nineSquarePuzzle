package nineSquarePuzzle;

import java.util.Date;

public class Instrumentation {

	Date debut, fin;
	boolean estFini = false;
	int nbAppelRecursifs;
	
	public Instrumentation(){ 
		nbAppelRecursifs = 0;
	}
	
	public void start(){
		debut = new Date();
	}
	
	public void stop(){
		fin = new Date();
		estFini = true; 
	}
	
	public String tempsEcoule(){
		long duree = 0;
		int heure = 0, minute = 0, seconde = 0, millisec = 0;
		if (estFini) {
			duree = fin.getTime() - debut.getTime();
			millisec = (int) (duree % 1000);
			seconde = (int) (duree /1000);
			minute = (int) (duree / 60000);
			heure = (int) (duree / 3600000);
			return (heure+"h "+minute+"min "+seconde+"s "+millisec+"ms");
		}else {
			System.out.println("La solution n'a pas encore été trouvée !");
			return "-1";
		}
	}
	
	public void appel(){
		this.nbAppelRecursifs++;
	}
	
	public String afficheInfos(){
		return ("----------\nTemps d'exécution : "+this.tempsEcoule()+"\nNombre d'appels récursifs : "+nbAppelRecursifs);
	}
}
