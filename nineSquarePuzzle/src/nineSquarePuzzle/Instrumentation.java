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
	
	public long tempsEcoule(){
		if (estFini) {
			return (fin.getTime() - debut.getTime());
		}else {
			System.out.println("La solution n'a pas encore été trouvée !");
			return -1;
		}
	}
	
	public String afficheInfos(){
		return ("----------\nTemps d'exécution : "+this.tempsEcoule()+"\nNombre d'appels récursifs : "+nbAppelRecursifs+"\ndebut : "+this.debut.getTime()+"\nfin : "+this.fin.getTime());
	}
}
