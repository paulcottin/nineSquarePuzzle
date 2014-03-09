package graphique;

import java.io.File;

/**
 * @author Flament Damien
 */


  /** Cette classe permet d'ajouter un filtre basé sur l'extension des fichiers. */
public class FiltreExtension extends javax.swing.filechooser.FileFilter {

        /** Le texte qui sera affiché dans le comboBox du JFileChooser. */
        private String descriptions;
        /** Extension de notre fichier (inclus le '.') */
        private String[] extension;
        /** Constructeur.
         * @param description, le texte de description pour l'utilisateur.
         * @param extentions, les extension du fichier
         */
        public FiltreExtension(String description, String[] extensions){
           super();
           this.descriptions = description;
           this.extension = (String[]) extensions.clone();
        }
        /** Constructeur.
         * @param description, le texte de description pour l'utilisateur.
         * @param extention, l'extention du fichier (commencant par '.')
         */
        public FiltreExtension( String description, String extensions ) {
                this(description,new String[]{extensions});
        }

        /** Indique si le fichier est accepté par le filtre.
         * @return vrai si le fichier est accepté.
         */
        public boolean accept(File file) {
                if(file.isDirectory()) {
                   return true;
                }

                String nomFichier = file.getPath().toLowerCase();
                int n = extension.length;
                for(int i=0; i<n; i++) {
                   if(nomFichier.endsWith(extension[i])) {
                      return true;
                    }
                 }
                 return false;
        }

        public String getDescription() {
                return(this.descriptions+"(*"+this.extension+")");
        }
}

