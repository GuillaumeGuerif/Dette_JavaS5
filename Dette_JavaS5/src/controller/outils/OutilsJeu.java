package controller.outils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import config.ConfigurationJeu;
import controller.partie.Joueur;
import models.grille.Bloc;
import models.grille.Grille;
import models.comp.Coordonnees;
import models.comp.Croiseur;
import models.comp.Cuirasse;
import models.comp.Destroyer;
import models.comp.Flotte;
import models.comp.Navire;
import models.comp.SousMarin;


/**
 * Définie les outils du jeu
*/
public class OutilsJeu {


        /**
         * Crée une direction aléatoire
        */
	public static int directionAleatoire() {
		Random r = new Random();
		return (r.nextInt(2));
	}

        /**
         * Crée une valeur aléatoire
        */
	public static int valeurAleatoire(int borneSup) {
		Random r = new Random();

		return (r.nextInt(borneSup) + 1);
	}

        /**
         * Crée une valeur Zéro aléatoire por le déplacement aléatoire
        */
	public static int valeurZeroAleatoire(int borneSup) {
		Random r = new Random();

		return (r.nextInt(borneSup));
	}

        /**
         * Crée des coordonées aléatoire
        */
	public static Coordonnees generateurCoordonneesAleatoire() {
		int indiceLigne = valeurAleatoire(ConfigurationJeu.NB_LIGNE - 2);

		int indiceColonne = valeurAleatoire(ConfigurationJeu.NB_COLONNE - 2);

		return new Coordonnees(indiceLigne, indiceColonne);

	}

        /**
         * Vérifie l'indice de la ligne
        */
	public static boolean verifIndiceLigne(String indice) {
		String tabIndiceLigne[] = ConfigurationJeu.INDICE_LIGNE.split(",");
		for (int i = 0; i < tabIndiceLigne.length; i++) {
			if (indice.equals(tabIndiceLigne[i])) {
				return true;
			}
		}
		return false;
	}

        /**
         * Vérifie l'indice de la colonne
        */
	public static boolean verifIndiceColonne(String indice) {
		String tabIndiceColonne[] = ConfigurationJeu.INDICE_COLONNE.split(",");
		for (int i = 0; i < tabIndiceColonne.length; i++) {
			if (indice.equals(tabIndiceColonne[i])) {
				return true;
			}
		}
		return false;
	}

        /**
         * Traduit les coordonnées rentrée dans la console
        */
	public static Coordonnees traduitCoordonnees(String coordString) {
		String[] tabCoordString = coordString.split(" ");

		String indiceLigneString = tabCoordString[0];
		String indiceColonneString = tabCoordString[1];

		System.out.println("VERIFILGNE : " + verifIndiceLigne(indiceLigneString) + " && VERIFCOLONNE :"
				+ verifIndiceColonne(indiceColonneString));

		if (verifIndiceLigne(indiceLigneString) && verifIndiceColonne(indiceColonneString)) {
			int indiceColonne = Integer.parseInt(indiceColonneString);

			Coordonnees c = null;

			int i = 0;

			for (String string : ConfigurationJeu.INDICE_LIGNE.split(",")) {
				if (indiceLigneString.equals(string)) {
					c = new Coordonnees(i, indiceColonne);
				}
				i++;
			}

			return c;
		} else {
			System.out.println("\nLES COORDONNEES NE SONT SOIT : \n\t- PAS COMPRISE DANS LA GRILLE \n\t- FAUSSES");
			return null;
		}

	}

        /**
         * Retourne une id de navire aléatoire
         * @return OutilsJeu.valeurAleatoire(4)
        */
	public static int idNavireAleatoire() {
		return OutilsJeu.valeurAleatoire(4);
	}

        /**
         * Retourne le numéro d'un navire aléatoire à déplacer
         * @return OutilsJeu.valeurZeroAleatoire(10)
        */
	public static int numNavireDeplacer() {
		return OutilsJeu.valeurZeroAleatoire(10);
	}

        /**
         * Vérifie l'entrée dans la console
        */
	public static boolean verifEntrees(String coordonneesString) {
		if (coordonneesString.contains(" ")) {
			String[] coordonneesSplit = coordonneesString.split(" ");

			if ((coordonneesString.length() == 4) && coordonneesSplit.length == 2 && coordonneesSplit[0].length() == 1
					&& coordonneesSplit.length == 2) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}
	
        /**
         * Permet la sauvegarde de la partie
        */
	public static void sauvegardeFichier(Joueur joueur, Joueur robot) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
		Date date = new Date();
		String dateString = simpleDateFormat.format(date);
		String chemin = "src/sauvegarde/" + dateString + ".txt";
		File fichier = new File(chemin);

		ArrayList<Navire> listeNavireJoueur = joueur.getFlotte().getListeNavire();

		Grille grilleJoueur = joueur.getGrilleJoueur();
		Bloc[][] joueurBlocs = grilleJoueur.getBlocs();
		ArrayList<Bloc> listeBlocsJoueur = new ArrayList<>();

		for (int i = 0; i < ConfigurationJeu.NB_LIGNE; i++) {
			for (int j = 0; j < ConfigurationJeu.NB_COLONNE; j++) {
				listeBlocsJoueur.add(joueurBlocs[i][j]);
			}
		}

		ArrayList<Navire> listeNavireRobot = robot.getFlotte().getListeNavire();

		Grille grilleRobot = robot.getGrilleJoueur();
		Bloc[][] robotBlocs = grilleRobot.getBlocs();
		ArrayList<Bloc> listeBlocsRobot = new ArrayList<>();

		for (int i = 0; i < ConfigurationJeu.NB_LIGNE; i++) {
			for (int j = 0; j < ConfigurationJeu.NB_COLONNE; j++) {
				listeBlocsRobot.add(robotBlocs[i][j]);
			}
		}

		try {
			FileWriter ecrire = new FileWriter(fichier);

			@SuppressWarnings("resource")
			BufferedWriter bufferedWriter = new BufferedWriter(ecrire);

			for (Navire navire : listeNavireJoueur) {
				bufferedWriter.write(navire.getId() + "," + navire.getTaille() + "," + navire.getImpactMissile() + ","
						+ navire.isCoule() + "," + navire.getDirection() + "," + navire.isTouche());
				bufferedWriter.newLine();

				for (Coordonnees c : navire.getListeCoordonnees()) {
					bufferedWriter.write(c.getLigne() + "," + c.getColonne() + " ");
				}
				bufferedWriter.newLine();
			}
			bufferedWriter.write(joueur.getNbBlocTouche() + "," + joueur.getNbNavireCoule());
			bufferedWriter.newLine();
			bufferedWriter.newLine();

			for (Navire navire : listeNavireRobot) {
				bufferedWriter.write(navire.getId() + "," + navire.getTaille() + "," + navire.getImpactMissile() + ","
						+ navire.isCoule() + "," + navire.getDirection() + "," + navire.isTouche());
				bufferedWriter.newLine();

				for (Coordonnees c : navire.getListeCoordonnees()) {
					bufferedWriter.write(c.getLigne() + "," + c.getColonne() + " ");
				}
				bufferedWriter.newLine();

			}
			bufferedWriter.write(robot.getNbBlocTouche() + "," + robot.getNbNavireCoule());

			bufferedWriter.close();
			ecrire.close();

		} catch (IOException e) {// Si l'essai n'est pas positif
			// TODO Auto-generated catch block
			e.printStackTrace(); // Affiche erreur en console
		}

		// Methode servant a lire l'entierte d'un fichier ligne par ligne
		try {
			FileReader lire = new FileReader(fichier);
			BufferedReader bufferedReader = new BufferedReader(lire);
			String resultLecture = bufferedReader.readLine();
			while (resultLecture != null) {
				System.out.println(resultLecture);
				resultLecture = bufferedReader.readLine();
			}

			bufferedReader.close();
			lire.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

        /**
         * Permet de charger la partie
        */
	public static ArrayList<Joueur> chargerJoueur(String cheminFichier) {

		File fichier = new File(cheminFichier);

		ArrayList<Joueur> listeJoueurs = new ArrayList<>();
		
		Grille grilleJoueur = new Grille(ConfigurationJeu.NB_LIGNE, ConfigurationJeu.NB_COLONNE);
		Grille grilleRobot = new Grille(ConfigurationJeu.NB_LIGNE, ConfigurationJeu.NB_COLONNE);

		try {
			FileReader lire = new FileReader(fichier);
			BufferedReader bufferedReader = new BufferedReader(lire);
			String resultLecture = bufferedReader.readLine();

			int id = 0;
			int taille = 0;
			int impactMissile = 0;
			boolean coule = false;
			int direction = 0;
			boolean touche = false;
			int nbBlocTouche = 0;
			int nbNavireCoule = 0;
			ArrayList<Navire> listeNavires = new ArrayList<>();
			ArrayList<Coordonnees> c = new ArrayList<>();

			String[] splitLineNavire;
			String[] splitLineDetails;
			String[] splitLineCoord;
			
			Joueur joueur = null;
			Joueur robot = null;

			int i = 1;
			while (resultLecture != null) {
				if (resultLecture.equals("")) {

					joueur = new Joueur(grilleJoueur, grilleRobot, new Flotte(listeNavires), nbBlocTouche, nbNavireCoule);
					listeNavires = new ArrayList<>();

					i = 0;
				} else if (i % 21 == 0) {
					splitLineDetails = resultLecture.split(",");
					nbBlocTouche = Integer.parseInt(splitLineDetails[0]);
					nbNavireCoule = Integer.parseInt(splitLineDetails[1]);
				} else if (i % 2 == 1) {
					splitLineNavire = resultLecture.split(",");
					id = Integer.parseInt(splitLineNavire[0]);
					taille = Integer.parseInt(splitLineNavire[1]);
					impactMissile = Integer.parseInt(splitLineNavire[2]);
					coule = Boolean.parseBoolean(splitLineNavire[3]);
					direction = Integer.parseInt(splitLineNavire[4]);
					touche = Boolean.parseBoolean(splitLineNavire[5]);

				} else {
					splitLineCoord = resultLecture.split(" ");
					c = coodonneesCharger(splitLineCoord);

					if (id == 1) {
						listeNavires.add(new Cuirasse(id, taille, impactMissile, coule, direction, touche, c));
					} else if (id == 2) {
						listeNavires.add(new Croiseur(id, taille, impactMissile, coule, direction, touche, c));
					} else if (id == 3) {
						listeNavires.add(new Destroyer(id, taille, impactMissile, coule, direction, touche, c));
					} else {
						listeNavires.add(new SousMarin(id, taille, impactMissile, coule, direction, touche, c));
					}
				}
				i++;
				resultLecture = bufferedReader.readLine();
			}

			bufferedReader.close();
			lire.close();
			
			robot = new Joueur(grilleRobot, grilleJoueur, new Flotte(listeNavires), nbBlocTouche, nbNavireCoule);
			
			listeJoueurs.add(joueur);
			listeJoueurs.add(robot);
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
		return listeJoueurs;
	}

        /**
         * Charger les coordonnée du fichier
        */
	private static ArrayList<Coordonnees> coodonneesCharger(String[] splitLineCoord) {

		ArrayList<Coordonnees> listeCoordonnees = new ArrayList<Coordonnees>();

		for (int i = 0; i < splitLineCoord.length; i++) {
			String ligne = splitLineCoord[i].split(",")[0];
			String colonne = splitLineCoord[i].split(",")[1];

			Coordonnees c = new Coordonnees(Integer.parseInt(ligne), Integer.parseInt(colonne));
			listeCoordonnees.add(c);
		}

		return listeCoordonnees;
	}

}
