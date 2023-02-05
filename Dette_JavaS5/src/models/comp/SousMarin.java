package models.comp;

import java.util.ArrayList;



import config.ConfigurationJeu;


/**
 * Permet de définir le Navire de type SousMarin
*/
public class SousMarin extends Navire {


        /**
         * Définie les paramétres d'un SousMarin
        */
	public SousMarin(ArrayList<Coordonnees> listeCoordonnees, int direction) {
		super(listeCoordonnees, direction);
		this.id = ConfigurationJeu.ID_SOUSMARIN;
		this.taille = ConfigurationJeu.TAILLE_SOUSMARIN;
		this.impactMissile = ConfigurationJeu.IMPACT_SOUSMARIN;
	}
	

        /**
         * Définie les paramétres d'un SousMarin
        */
	public SousMarin( int id, int taille, int impactMissile , Boolean coule, int direction ,Boolean touche ,ArrayList<Coordonnees> listeCoordonnees) {
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
		return "SousMarin [id=" + id + ", taille=" + taille + ", impactMissile=" + impactMissile + ", listeCoordonnees="
				+ listeCoordonnees + ", coule=" + coule + ", direction=" + direction + ", touche=" + touche + "]";
	}
}
