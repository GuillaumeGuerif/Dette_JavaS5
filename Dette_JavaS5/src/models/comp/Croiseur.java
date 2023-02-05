package models.comp;

import java.util.ArrayList;

import config.ConfigurationJeu;


/**
 * Permet de définir le Navire de type Croiseur
*/
public class Croiseur extends Navire {


        /**
         * Définie les paramétres d'un Croiseur
        */
	public Croiseur(ArrayList<Coordonnees> listeCoordonnees, int direction) {
		super(listeCoordonnees, direction);
		this.id = ConfigurationJeu.ID_CROISSEUR;
		this.taille = ConfigurationJeu.TAILLE_CROISSEUR;
		this.impactMissile = ConfigurationJeu.IMPACT_CROISSEUR;
	}
	
	

        /**
         * Définie les paramétres d'un Croiseur
        */
	public Croiseur( int id, int taille, int impactMissile , Boolean coule, int direction ,Boolean touche ,ArrayList<Coordonnees> listeCoordonnees) {
		super(listeCoordonnees, direction);
		this.id = id;
		this.taille = taille;
		this.impactMissile = impactMissile;
		this.coule = coule ;
		this.direction = direction;
		this.touche = touche;
		this.listeCoordonnees = listeCoordonnees;
	}

        /**
         * Redéfinitions de la méthode toString
        */
	@Override
	public String toString() {
		return "Croiseur [id=" + id + ", taille=" + taille + ", impactMissile=" + impactMissile + ", listeCoordonnees="
				+ listeCoordonnees + ", coule=" + coule + ", direction=" + direction + ", touche=" + touche + "]";
	}

}
