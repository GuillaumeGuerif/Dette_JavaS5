package models.grille;

import config.ConfigurationJeu;
import models.comp.Coordonnees;


/**
 * Permet la gestion de la grille
*/
public class Grille {

	private Bloc[][] blocs; // La grille est compose d'un tableau a deux dimensions de Bloc

	private int nombreLigne;
	private int nombreColonne;

        /**
         * Permet de remplir la grille de blocs avec pour coordonée les lignes et les colonnes de la grille
        */
	public Grille(int nombreLigne, int nombreColonne) {
		this.nombreLigne = nombreLigne;
		this.nombreColonne = nombreColonne;

		blocs = new Bloc[nombreLigne][nombreColonne];

		String indicePremiereColonne = ConfigurationJeu.INDICE_LIGNE;
		String[] indiceColonneSplit = indicePremiereColonne.split(",");

		for (int indiceLigne = 0; indiceLigne < nombreLigne; indiceLigne++) {
			blocs[indiceLigne][0] = new Bloc(indiceColonneSplit[indiceLigne], indiceLigne, 0);
		}

		String indicePremiereLigne = ConfigurationJeu.INDICE_COLONNE;
		String[] indiceLigneSplit = indicePremiereLigne.split(",");

		for (int indiceColonne = 0; indiceColonne < nombreLigne; indiceColonne++) {
			blocs[0][indiceColonne] = new Bloc(indiceLigneSplit[indiceColonne], indiceColonne, 0);
		}

		for (int indiceLigne = 1; indiceLigne < nombreLigne; indiceLigne++) {
			for (int indiceColonne = 1; indiceColonne < nombreColonne; indiceColonne++) {
				blocs[indiceLigne][indiceColonne] = new Bloc("  ", indiceLigne, indiceColonne);
			}
		}
	}

        
        /**
         * Retourne la grille
         * @return blocs
        */
	public Bloc[][] getBlocs() {
		return blocs;
	}

        /**
         * Retourne le nombre de ligne
         * @return nombreLigne
        */
	public int getNombreLigne() {
		return nombreLigne;
	}

        /**
         * Retourne le nombre de colonne
         * @return nombreColonne
        */
	public int getNombreColonne() {
		return nombreColonne;
	}


        /**
         * Retourne un blocs spécifique de la grille avec la ligne et la colonne
         * @return blocs[ligne][colonne]
        */
	public Bloc getBloc(int ligne, int colonne) {
		return blocs[ligne][colonne];
	}


        /**
         * Retourne un blocs à l'aide des coordonnée de la grille
         * @return blocs[c.getLigne()][c.getColonne()]
        */
	public Bloc getBloc(Coordonnees c) {
		return blocs[c.getLigne()][c.getColonne()];
	}

}
