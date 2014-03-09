package nineSquarePuzzle;

import java.util.Date;

public class Instrumentation {

	Date debut, fin;
	String duree;
	boolean estFini = false;
	int nbAppelRecursifs;
	
	public Instrumentation(){ 
		nbAppelRecursifs = 0;
		duree = "-1";
		debut = new Date();
		fin = new Date();
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
			return "-1";
		}
	}
	
	public String tempsEcouleCourant(){
		long duree = 0;
		int heure = 0, minute = 0, seconde = 0, millisec = 0;
		duree = (new Date()).getTime() - debut.getTime();
		millisec = (int) (duree % 1000);
		seconde = (int) (duree /1000);
		minute = (int) (duree / 60000);
		heure = (int) (duree / 3600000);
		return (heure+"h "+minute+"min "+seconde+"s "+millisec+"ms");
	}
	
	public void appel(){
		this.nbAppelRecursifs++;
	}
	
	public String afficheInfos(){
		return ("----------\n Temps d'exécution : "+this.tempsEcoule()+"\nNombre d'appels récursifs : "+nbAppelRecursifs);
	}

	public int getNbAppelRecursifs() {
		return nbAppelRecursifs;
	}

	public void setNbAppelRecursifs(int nbAppelRecursifs) {
		this.nbAppelRecursifs = nbAppelRecursifs;
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}
}
