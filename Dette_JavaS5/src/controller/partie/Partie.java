package controller.partie;

import java.util.Scanner;

import config.ConfigurationJeu;
import controller.outils.OutilsJeu;
import models.grille.Grille;
import models.comp.Coordonnees;
import view.console.AffichageConsole;

/**
 * Définie une partie
*/
public class Partie {

	private int nombreLigneGrille = ConfigurationJeu.NB_LIGNE;
	private int nombreColonneGrille = ConfigurationJeu.NB_COLONNE;

	private Grille grilleJoueur;

	private Grille grilleRobot;

	private Joueur joueur;
	private Joueur robot;
	private boolean modeTriche;

	private AffichageConsole affichageCLI = new AffichageConsole();


        /**
         * Choix du mode de jeu et crée la partie
        */
	public Partie(Scanner in) {

		this.grilleJoueur = new Grille(nombreLigneGrille, nombreColonneGrille);
		this.grilleRobot = new Grille(nombreLigneGrille, nombreColonneGrille);

		this.joueur = new Joueur(grilleJoueur, grilleRobot);
		this.robot = new Joueur(grilleRobot, grilleJoueur);

		System.out.println("\nBienvenue dans le jeu Bataille Navale en mode console !!! \n");
		System.out.println("\nVoulez-vous lancer le jeu en mode triche ? ( OUI => o et NON => n )");
		// Dimensions
		String scanValue = in.nextLine(); // Lecture de la console
                // String scanValue = "o";
		while (!(scanValue.equals("o") || scanValue.equals("n"))) {
			System.out.println("\nVous n\'avez pas rentre \"n\" ou \"o\" , veuillez respecter la consigne demande !!!!");
			System.out.println("\nVoulez-vous lancer le jeu en mode triche ? ( OUI => o et NON => n )");
			scanValue = in.nextLine(); // Lecture de la console
		}

		if (scanValue.equals("o")) {
			this.modeTriche = true;
		} else {
			this.modeTriche = false;
		}

		System.out.println("\n\t Votre partie a ete cree avec succes !! ");

	}


        /**
         * Choix du mode de jeu et crée la partie
        */
	public Partie(Joueur joueur, Joueur robot, Scanner in) {

		this.joueur = joueur;
		this.robot = robot;

		System.out.println("\nBienvenue dans le jeu Bataille Navale en mode console !!! \n");
		System.out.println("\nVoulez-vous lancer le jeu en mode triche ? ( OUI => o et NON => n )");
		// Dimensions
		String scanValue = in.nextLine(); // Lecture de la console
                // String scanValue = "o";
		while (!(scanValue.equals("o") || scanValue.equals("n"))) {
			System.out.println("\nVous n\'avez pas rentre \"n\" ou \"o\" , veuillez respecter la consigne demande !!!!");
			System.out.println("\nVoulez-vous lancer le jeu en mode triche ? ( OUI => o et NON => n )");
			scanValue = in.nextLine(); // Lecture de la console
		}

		if (scanValue.equals("o")) {
			this.modeTriche = true;
		} else {
			this.modeTriche = false;
		}

		System.out.println("\n\t Votre partie a ete cree avec succes !! ");

	}

        /**
         * Retourne le joueur
         * @return joueur
        */
	public Joueur getJoueur() {
		return joueur;
	}

        /**
         * Retourne le robot
         * @return robot
        */
	public Joueur getRobot() {
		return robot;
	}

        /**
         * Retourne l'état du mode triche
         * @return modeTriche
        */
	public boolean isModeTriche() {
		return modeTriche;
	}


        /**
         * Tour du joueur
        */
	public void tourJoueur(Scanner in) {
		System.out.println("\n-------------------------------------------------\n");

		System.out.println("\nAffichage de votre carte contenant vos navires : \n");

		Grille grilleJoueur = joueur.getGrilleJoueur();
		affichageCLI.afficheGrille(grilleJoueur);

		System.out.println("\n-------------------------------------------------\n");

		System.out.println("\nAffichage de la carte ennemi : \n");

		Grille grilleEnnemi = joueur.getGrilleEnnemi();
		affichageCLI.afficheGrilleEnnemi(grilleEnnemi, modeTriche);

		System.out.println("\n-------------------------------------------------\n");

		System.out.println(
				"\nVoulez-vous tirer ou deplacer un de vos navires ? ( TIRER => t || DEPLACER => d || QUITTER => q ) ");
		String scanValue = in.nextLine(); // Lecture de la console

		while (!(scanValue.equals("t") || scanValue.equals("d") || scanValue.equals("q"))) {
			System.out.println(
					"\nVous n\'avez pas rentre la valeur attendu, veuillez respecter la consigne demande !!!!");
			System.out.println(
					"\nVoulez-vous tirer ou deplacer un de vos navires ? ( TIRER => t || DEPLACER => d || QUITTER ET SAUVEGARDER => q ) ");
			scanValue = in.nextLine(); // Lecture de la console
		}

		if (!joueur.NbNavireDeplacable()) {
			System.out.println(
					"\nTous vos navires ont ete touche, vous ne pouvez donc pas en deplacer un. Nous modifions votre choix de coup a realiser en tirer !!!\n");
			scanValue = "t";
		}

		if (scanValue.equals("q")) {
			OutilsJeu.sauvegardeFichier(joueur, robot);

			ConfigurationJeu.QUITTER = true;

		} else if (scanValue.equals("s")) {
			OutilsJeu.sauvegardeFichier(joueur, robot);

		} else if (scanValue.equals("t")) {
			System.out.println(
					"\nDonner les coordonnees d'ou vous voulez tirer ? (Coordonnees de type : Lettre Nombre (exemples : a 10 | m 16 / Dans le cas ou le nombre est un chiffre veuillez ajouter un zero devant celui-ci (exemples: d 06 | k 02 ) )");
			String coordonneesString = in.nextLine();
			String[] coordonneesSplit = coordonneesString.split(" ");

			while ((!OutilsJeu.verifEntrees(coordonneesString)) || (!OutilsJeu.verifIndiceLigne(coordonneesSplit[0]))
					|| (!OutilsJeu.verifIndiceColonne(coordonneesSplit[1]))) {
				System.out.println(
						"\nVous n\'avez pas rentre la valeur attendu, veuillez respecter la consigne demande !!!!");
				System.out.println(
						"\nDonner les coordonnees d'ou vous voulez tirer ? (Coordonnees de type : Lettre Nombre (exemples : a 10 | m 16 / Dans le cas ou le nombre est un chiffre veuillez ajouter un zero devant celui-ci (exemples: d 06 | k 02 ) )");
				coordonneesString = in.nextLine(); // Lecture de la console
				coordonneesSplit = coordonneesString.split(" ");
			}

			Coordonnees coordonnees = OutilsJeu.traduitCoordonnees(coordonneesString);

			System.out.println(
					"\nAvec quel type de navire voulez-vous tirer ? (CUIRASSE => 1 | CROISSEUR => 2 | DESTROYER => 3 | SOUS-MARAIN => 4) ");
			int idNavire = in.nextInt(); // Lecture de la console
			while (!(idNavire >= 1 && idNavire <= 4) || !joueur.verifNavireDispo(idNavire)) {
				System.out.println(
						"\nVous n\'avez pas rentre la valeur attendu ou ce navire est indisponible, veuillez respecter la consigne demande ou le type de navire demander n'est plus disponible !!!!");
				System.out.println(
						"\nAvec quel type de navire voulez-vous tirer, regardez votre carte pour ne pas choisir un Navire coule ? (CUIRASSE => 1 | CROISSEUR => 2 | DESTROYER => 3 | SOUS-MARAIN => 4) ");
				idNavire = in.nextInt(); // Lecture de la console
			}

			if (idNavire == 3) {
				System.out.println(
						"\nVous avez choisi de tirer avec un destroyeur et comme il vous reste des fusee eclairante, vous en tirez une !");
			}

			joueur.tirer(coordonnees, idNavire);

			if (idNavire != 3) {
				robot.verifNavireCoule();
				System.out.println("NOMBRE DE NAVIRE COULE :" + robot.getNbNavireCoule());
			}

			System.out.println("\nAffichage de la carte ennemi apr�s le tire : \n");
			affichageCLI.afficheGrilleEnnemi(grilleEnnemi, modeTriche);

		} else {
			affichageCLI.afficheFlotteNumerote(joueur);

			System.out.println(
					"\nDonner le numero du navire que vous souhaitez deplacer ? (Voir la carte ci-dessus : )  )");
			int indiceNavire = in.nextInt();

			while (!(indiceNavire >= 0 && indiceNavire <= 9) || !joueur.verifNavireDeplacable(indiceNavire)) {
				System.out.println(
						"\nLe Navire choisi est touche ou coule il ne peut donc pas se deplacer,choississez en un autre !!!!");
				System.out.println(
						"\nDonner le numero du navire que vous souhaitez deplacer ? (Voir la carte ci-dessus : )  ");
				indiceNavire = in.nextInt(); // Lecture de la console
			}

			System.out.println(
					"\nDonner la direction d'ou vous voulez vous deplacer ? (Direction : Ouest => 0 | Nord => 1 | Est => 2 | Sud => 3 )  )");
			int direction = in.nextInt();

			while (!(direction >= 0 && direction <= 3)) {
				System.out.println(
						"\nVous n\'avez pas rentre la valeur attendu, veuillez respecter la consigne demande !!!!");
				System.out.println(
						"\nDonner la direction d'ou vous voulez vous deplacer ? (Direction : Gauche => 0 | Haut => 1 | Droite => 2 | Bas => 3 )  )");
				direction = in.nextInt(); // Lecture de la console
			}

			while (!joueur.deplacer(indiceNavire, direction)) {
				System.out.println(
						"\nLe deplacement est impossible soit car : \n\t - La case est deja prise \n\t - Le bateau touche les bornes de la carte \n\t - Le bateau choisi n'est pas dans le bon sens ( Exemple : Bateau Horizontale et deplacement en haut => IMPOSSIBLE )");

				System.out.println(
						"\nDonner la direction d'ou vous voulez vous deplacer ? (Direction : Gauche => 0 | Haut => 1 | Droite => 2 | Bas => 3 ) ");
				direction = in.nextInt(); // Lecture de la console
			}

			System.out.println("\nAffichage de votre carte apr�s deplacement de votre navire : \n");

			affichageCLI.afficheGrille(grilleJoueur);

		}
	}


        /**
         * Tour du robot
        */
	public void tourRobot() {
		System.out.println("\n-------------------------------------------------\n");

		System.out
				.println("\nROBOT voulez-vous tirer ou deplacer un de vos navires ? ( TIRER => t et DEPLACER => d ) ");

		String scanValue = robot.coupAleatoire();// Lecture de la console

		if (!joueur.NbNavireDeplacable()) {
			System.out.println(
					"\nTous vos navires ont ete touche, vous ne pouvez donc pas en deplacer un. Nous modifions votre choix de coup a realiser en tirer !!!\n");
			scanValue = "t";
		}

		if (scanValue.equals("t")) {
			System.out.println("\nROBOT a choisi de tirer !!!");

			System.out.println("\nChoix des coordonnees !!!!");
			Coordonnees coordonnees = OutilsJeu.generateurCoordonneesAleatoire();

			System.out.println(
					"\nChoix du navire avec lequel le ROBOT veur tirer ? (CUIRASSE => 1 | CROISSEUR => 2 | DESTROYER => 3 | SOUS-MARAIN => 4) ");

			int idNavire = OutilsJeu.idNavireAleatoire();
			System.out.println("\nNAVIRE CHOISI: " + idNavire);
			while (!robot.verifNavireDispo(idNavire)) {
				System.out.println("\nLe type de Navire choisi est indisponible il ne peut donc pas etre utilise !!!!");
				System.out.println("\nROBOT recommence le choix du type navire avec lequel tiree ? ");
				System.out.println("NAVIRE CHOISI: " + idNavire);
				idNavire = OutilsJeu.idNavireAleatoire();
			}
			System.out.println("NAVIRE CHOISI: " + idNavire);

			robot.tirer(coordonnees, idNavire);

			if (idNavire != 3) {
				joueur.verifNavireCoule();
				System.out.println("NOMBRE DE NAVIRE COULE :" + joueur.getNbNavireCoule());
			}

			affichageCLI.afficheGrille(robot.getGrilleEnnemi());

		} else {
			System.out.println("\nROBOT a choisi de deplacer un navire !!!");

			System.out.println("\nChoix aleatoire du navire a deplacer ? ");

			affichageCLI.afficheFlotteNumerote(robot);

			int indiceNavire = OutilsJeu.numNavireDeplacer();

			System.out.println("\nVERIF DEPLACABLE  : " + robot.verifNavireDeplacable(indiceNavire));
			System.out.println("NAVIRE CHOISI : " + indiceNavire);
			while (!robot.verifNavireDeplacable(indiceNavire)) {
				affichageCLI.afficheFlotteNumerote(robot);
				System.out.println(robot.verifNavireDeplacable(indiceNavire));

				System.out.println("VERIF DEPLACABLE  : " + robot.verifNavireDeplacable(indiceNavire));
				System.out.println("NAVIRE CHOISI : " + indiceNavire);
				indiceNavire = OutilsJeu.numNavireDeplacer();
			}
			System.out.println("VERIF DEPLACABLE  : " + robot.verifNavireDeplacable(indiceNavire));
			System.out.println("NAVIRE CHOISI : " + indiceNavire);

			System.out.println(
					"\nChoix aleatoire de la direction d'ou vous voulez vous deplacer ?   (Direction : Gauche => 0 | Haut => 1 | Droite => 2 | Bas => 3 ) ");
			int direction = robot.directionAleatoire();

			while (!robot.checkDeplacementAleatoire(indiceNavire, direction)) {

				System.out.println(
						"\nLe deplacement est impossible soit car : \n\t - La case est deja prise \n\t - Le bateau coule les bornes de la carte \n\t - Le bateau choisi n'est pas dans le bon sens ( Exemple : Bateau Horizontale et deplacement en haut => IMPOSSIBLE");

				System.out.println(
						"\nDonner la direction d'ou vous voulez vous deplacer ? (Direction : Gauche => 0 | Haut => 1 | Droite => 2 | Bas => 3 )  ");
				direction = robot.directionAleatoire();
				System.out.println("NAVIRE CHOISI : " + indiceNavire);
				System.out.println("DIRECTION CHOISI : " + direction);
			}

			System.out.println("NAVIRE CHOISI : " + indiceNavire + " || DIRECTION CHOISI : " + direction);
			System.out.println("\nROBOT choisi de deplace un navire \n");
			robot.deplacer(indiceNavire, direction);

			if (modeTriche) {
				affichageCLI.afficheGrilleEnnemi(robot.getGrilleJoueur(), modeTriche);
			}

		}

	}

        /**
         * Redéfinitions de la méthode toString
        */
	@Override
	public String toString() {
		return "Partie [nombreLigneGrille=" + nombreLigneGrille + ", nombreColonneGrille=" + nombreColonneGrille
				+ ", grilleJoueur=" + grilleJoueur + ", grilleRobot=" + grilleRobot + ", joueur=" + joueur + ", robot="
				+ robot + ", modeTriche=" + modeTriche + ", affichageCLI=" + affichageCLI + "]";
	}

}
