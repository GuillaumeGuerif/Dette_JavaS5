package models.comp;


/**
 * Définie le systèmes de coordonnées
*/
public class Coordonnees {
	private int ligne;
	private int colonne;


        /**
         * Une coordonnées est définie pas sa ligne et sa colonne
        */
	public Coordonnees(int ligne, int colonne) {
		this.ligne = ligne;
		this.colonne = colonne;
	}

        /**
         * Retourne la ligne
         * @return ligne
        */
	public int getLigne() {
		return ligne;
	}

        /**
         * Mofie la valeur de la ligne
        */
	public void setLigne(int x) {
		this.ligne = x;
	}

        /**
         * Retourne la colonne
         * @return colonne
        */
	public int getColonne() {
		return colonne;
	}

        /**
         * Mofie la valeur de la colonne
        */
	public void setColonne(int y) {
		this.colonne = y;
	}


        /**
         * Vérifie si les coordonnées ne sortent pas de la grille
         * @return true/false
        */
	public boolean valideCoordonnees() {
		if (getLigne() >= 1 && getLigne() <= 14 && getColonne() >= 1 && getColonne() <= 14) {
			return true;
		} else {
			return false;
		}
	}


        /**
         * Vérifie si les coordonnée sont valables
         * @return true/false
        */
	public boolean verifCoord(Coordonnees c2) {
		if (getLigne() == c2.getLigne() && getColonne() == c2.getColonne()) {
			return true;
		} else {
			return false;
		}
	}

        /**
         * Redéfinitions de la méthode toString
        */
	@Override
	public String toString() {
		return "Coordonnees [ligne=" + ligne + ", colonne=" + colonne + "]";
	}

}
