package models.comp;

import java.util.ArrayList;


/**
 * Permet de définir la Flotte
*/
public class Flotte {
	private ArrayList<Navire> listeNavire;


        /**
         * La Flotte est composé des Navires de la liste
        */
	public Flotte(ArrayList<Navire> flotte) {
		this.listeNavire = flotte;
	}

        /**
         * Retourne la liste des Navires
         * @return listeNavire
        */
	public ArrayList<Navire> getListeNavire() {
		return listeNavire;
	}

        /**
         * Redéfinitions de la méthode toString
        */
	@Override
	public String toString() {
		return "Flotte [listeNavire=" + listeNavire + "]";
	}

}
