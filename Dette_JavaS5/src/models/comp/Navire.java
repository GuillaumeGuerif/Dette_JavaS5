package models.comp;



import java.util.ArrayList;

/**
 * Permet la gestion des navires
*/
public class Navire {
	protected int id;
	protected int taille;
	protected int impactMissile;
	protected ArrayList<Coordonnees> listeCoordonnees;
	protected boolean coule;
	protected int direction;
	protected boolean touche;


        /**
         * Définie les paramétres d'un navire
        */
	public Navire(ArrayList<Coordonnees> listeCoordonnees, int direction) {
		this.listeCoordonnees = listeCoordonnees;
		this.coule = false;
		this.direction = direction;
		this.touche = false;
	}

        /**
         * Retourne l'id
         * @return id
        */
	public int getId() {
		return id;
	}

        /**
         * Retourne la taille
         * @return taille
        */
	public int getTaille() {
		return taille;
	}

        /**
         * Retourne l'impacte du missile
         * @return impactMissile
        */
	public int getImpactMissile() {
		return impactMissile;
	}

        /**
         * Retourne la liste des coordonnées
         * @return listeCoordonnees
        */
	public ArrayList<Coordonnees> getListeCoordonnees() {
		return listeCoordonnees;
	}

        /**
         * Retourne si le navire est coulé
         * @return coule
        */
	public boolean isCoule() {
		return coule;
	}

        /**
         * Permet de modifié l'état coulé d'un bateau
        */
	public void setCoule(boolean coule) {
		this.coule = coule;
	}

        /**
         * Retourne la direction du navire
         * @return direction
        */
	public int getDirection() {
		return direction;
	}

        /**
         * Retourne si le navire est touché
         * @return touche
        */
	public boolean isTouche() {
		return touche;
	}

        /**
         * Permet de modifié l'état touché d'un bateau
        */
	public void setTouche(boolean touche) {
		this.touche = touche;
	}

        /**
         * Permet de choisir la direction
         * @return new Coordonnees(coordLigne, coordColonne)
        */
	public Coordonnees validationFuturesCoordonnees(int directionDeplacement) {
		int coordLigne = 0;
		int coordColonne = 0;
		switch (directionDeplacement) {
		case 0: { // gauche
			coordLigne = listeCoordonnees.get(0).getLigne();
			coordColonne = listeCoordonnees.get(0).getColonne() - 1;

			break;
		}
		case 1: { // haut
			coordLigne = listeCoordonnees.get(0).getLigne() - 1;
			coordColonne = listeCoordonnees.get(0).getColonne();
			break;
		}
		case 2: { // droite
			coordLigne = listeCoordonnees.get(taille - 1).getLigne();
			coordColonne = listeCoordonnees.get(taille - 1).getColonne() + 1;
			break;
		}
		case 3: { // bas
			coordLigne = listeCoordonnees.get(taille - 1).getLigne() + 1;
			coordColonne = listeCoordonnees.get(taille - 1).getColonne();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + directionDeplacement);
		}
		return new Coordonnees(coordLigne, coordColonne);
	}


        /**
         * Permet de vérifié si le déplacement est possible
         * @return false/true
        */
	public boolean deplacable(int directionDeplacement) {
		Coordonnees c = validationFuturesCoordonnees(directionDeplacement);
		if (c.valideCoordonnees()) {
			return true;
		}
		return false;
	}


        /**
         * Permet de donner les nouvelles coordonnées de déplacement
         * @return new Coordonnees(coordLigne, coordColonne)
        */
	private Coordonnees MajCoordonnees(int directionDeplacement, Coordonnees c) {
		int coordLigne = 0;
		int coordColonne = 0;
		switch (directionDeplacement) {
		case 0: { // gauche
			coordLigne = c.getLigne();
			coordColonne = c.getColonne() - 1;
			break;
		}
		case 1: { // haut
			coordLigne = c.getLigne() - 1;
			coordColonne = c.getColonne();
			break;
		}
		case 2: { // droite
			coordLigne = c.getLigne();
			coordColonne = c.getColonne() + 1;
			break;
		}
		case 3: { // bas
			coordLigne = c.getLigne() + 1;
			coordColonne = c.getColonne();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + directionDeplacement);
		}

		return new Coordonnees(coordLigne, coordColonne);
	}


        /**
         * Permet déplacé le bateau
        */
	public void deplacer(int directionDeplacement) {
		if (deplacable(directionDeplacement)) {
			for (int i = 0; i < listeCoordonnees.size(); i++) {
				Coordonnees c = listeCoordonnees.get(i);
				listeCoordonnees.set(i, MajCoordonnees(directionDeplacement, c));
			}
		}
	}

        /**
         * Redéfinitions de la méthode toString
        */
	@Override
	public String toString() {
		return "Navire [id=" + id + ", taille=" + taille + ", impactMissile=" + impactMissile + ", listeCoordonnees="
				+ listeCoordonnees + ", coule=" + coule + ", direction=" + direction + ", touche=" + touche + "]";
	}

}
