package models.grille;

/**
 * Permet la gestion des blocs sur la grille
*/
public class Bloc {
	private String valeur;
	private int ligne;
	private int colonne;
	private boolean touche;
	private boolean coule;
	private boolean eclaire;
	private int idNavire;


        /**
         * Définie par défault les valeur des paramétres d'un bloc
        */
	public Bloc(String valeur, int ligne, int colonne) {
		this.valeur = valeur;
		this.ligne = ligne;
		this.colonne = ligne;
		this.touche = false;
		this.coule = false;
		this.eclaire = false;
		this.idNavire = 0;
	}

        /**
         * Retourne la valeur d'un bloc
         * @return valeur
        */
	public String getValeur() {
		return valeur;
	}

        /**
         * Modifie la valeur d'un bloc
        */
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

        /**
         * Retourne la valeur de la ligne
         * @return ligne
        */
	public int getLigne() {
		return ligne;
	}

        /**
         * Retourne la valeur de la colonne
         * @return colonne
        */
	public int getColonne() {
		return colonne;
	}

        /**
         * Retourne si le bloc est touché
         * @return touche
        */
	public boolean isTouche() {
		return touche;
	}

        /**
         * Permet de mettre un bloc en mode touché
        */
	public void setTouche(boolean touche) {
		this.touche = touche;
	}

        /**
         * Retourne si le bloc est coulé
         * @return coule
        */
	public boolean isCoule() {
		return coule;
	}

        /**
         * Permet de mettre un bloc en mode coulé
        */
	public void setCoule(boolean coule) {
		this.coule = coule;
	}


	public boolean isEclaire() {
		return eclaire;
	}


	public void setEclaire(boolean eclaire) {
		this.eclaire = eclaire;
	}

        /**
         * Retourne l'id du navire
         * @return idNavire
        */
	public int getIdNavire() {
		return idNavire;
	}

        /**
         * Permet de modifié l'id d'un navire
        */
	public void setIdNavire(int idNavire) {
		this.idNavire = idNavire;
	}

        /**
         * Redéfinissions de la méthode toString
        */
	@Override
	public String toString() {
		return "Bloc [valeur=" + valeur + ", ligne=" + ligne + ", colonne=" + colonne + ", touche=" + touche
				+ ", coule=" + coule + ", eclaire=" + eclaire + "]";
	}

}
