package view.console;


import config.ConfigurationJeu;
import controller.partie.Joueur;
import models.grille.Bloc;
import models.grille.Grille;

/**
 * Affiche les grilles du jeu en mode console
*/
public class AffichageConsole {


        /**
         * Affiche la grille du joueur
        */
	public void afficheGrille(Grille grilleJoueur) {

		System.out
				.println(ConfigurationJeu.ANSI_VERT + "\n AFFICHAGE DE VOTRE GRILLE \n" + ConfigurationJeu.ANSI_RESET);

		Bloc[][] blocs = grilleJoueur.getBlocs();
		for (int i = 0; i < ConfigurationJeu.NB_LIGNE - 1; i++) {
			for (int j = 0; j < ConfigurationJeu.NB_COLONNE - 1; j++) {
				if (i < 1 || j < 1) {
					System.out.print("[" + blocs[i][j].getValeur() + "]");
				} else if(blocs[i][j].isCoule()) {
					System.out.print("[::]");
				} else if(blocs[i][j].isTouche() == true && !blocs[i][j].isCoule()) {
					if (!blocs[i][j].getValeur().equals("  ") ){
						System.out.print("[//]");
					} else {
						System.out.print("[" + blocs[i][j].getValeur() + "]");
					}
				}else {
					System.out.print("[" + blocs[i][j].getValeur() + "]");
				}
			}
			System.out.println();
		}
	}


        /**
         * Affiche la grilles de l'ennemie en mode triche ou non
        */
	public void afficheGrilleEnnemi(Grille grilleEnnemi, boolean modeTriche) {
		Bloc[][] blocs = grilleEnnemi.getBlocs();
		if (modeTriche) {

			System.out.println(ConfigurationJeu.ANSI_VERT + "\n AFFICHAGE DE LA GRILLE ENNEMI EN MODE TRICHE \n"
					+ ConfigurationJeu.ANSI_RESET);

			for (int i = 0; i < ConfigurationJeu.NB_LIGNE - 1; i++) {
				for (int j = 0; j < ConfigurationJeu.NB_COLONNE - 1; j++) {
					if (i < 1 || j < 1) {
						System.out.print("[" + blocs[i][j].getValeur() + "]");
					} else if (blocs[i][j].isTouche() == true && blocs[i][j].isCoule() == true) {
						System.out.print("[::]");
					} else if (blocs[i][j].isTouche() == true) {
						if (!blocs[i][j].getValeur().equals("  ") ){
							System.out.print("[//]");
						} else {
							System.out.print("[" + blocs[i][j].getValeur() + "]");
						}
					} else {
						System.out.print("[" + blocs[i][j].getValeur() + "]");
					}
				}
				System.out.println();
			}
		} else {

			System.out.println(
					ConfigurationJeu.ANSI_VERT + "\n AFFICHAGE DE LA GRILLE ENNEMI \n" + ConfigurationJeu.ANSI_RESET);

			for (int i = 0; i < ConfigurationJeu.NB_LIGNE - 1; i++) {
				for (int j = 0; j < ConfigurationJeu.NB_COLONNE - 1; j++) {
					if (i < 1 || j < 1) {
						System.out.print("[" + blocs[i][j].getValeur() + "]");
					} else if (blocs[i][j].isTouche() == true && blocs[i][j].isCoule() == true) {
						System.out.print("[" + blocs[i][j].getValeur() + "]");
					} else if (blocs[i][j].isEclaire()) {
						System.out.print("[" + blocs[i][j].getValeur() + "]");
					} else if (blocs[i][j].isTouche() == true) {
						if (!blocs[i][j].getValeur().equals("  ") ) {
							System.out.print("[//]");
						} else {
							System.out.print("[" + blocs[i][j].getValeur() + "]");
						}
					} else {
						System.out.print("[--]");
					}
				}
				System.out.println();
			}
		}
	}
	
	

        /**
         * Affiche les bateaux en mode numéros pour les déplacements
        */
	public void afficheFlotteNumerote(Joueur joueur) {

		System.out.println("\nAFFICHAGE GRILLE NUMEROTE : \n");

		Bloc[][] blocs = joueur.getGrilleJoueur().getBlocs();

		for (int i = 0; i < ConfigurationJeu.NB_LIGNE - 1; i++) {
			for (int j = 0; j < ConfigurationJeu.NB_COLONNE - 1; j++) {
				if ((i < 1 || j < 1) || blocs[i][j].getValeur().equals("//") || blocs[i][j].getValeur().equals("::")) {
					System.out.print("[" + blocs[i][j].getValeur() + "]");
				} else if (!blocs[i][j].getValeur().equals("  ") && !blocs[i][j].getValeur().equals("//")
						&& !blocs[i][j].getValeur().equals("::")) {
					System.out.print("[0" + joueur.recupIndice(i, j) + "]");
				} else {
					System.out.print("[--]");
				}
			}
			System.out.println();
		}
	}


}
