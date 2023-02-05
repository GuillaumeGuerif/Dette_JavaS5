package controller.jeu;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import config.ConfigurationJeu;
import controller.outils.OutilsJeu;
import controller.partie.Joueur;
import controller.partie.Partie;
import models.comp.Navire;


/**
 * Permet la gestion de la partie (lancer une partie/sauvegarder/charger)
 */
public class Controleur {

	private String winnerName;

        /**
        * Réinitialise le gagnant(Winner) et vérifie si une partie n'est pas en cours
        */
	public Controleur() {
		this.winnerName = null;

		System.out.println("\nBienvenue dans le jeu Bataille Navale !!! \n");

		try (Scanner in = new Scanner(System.in)) {

			runConsole(in);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

        /**
        * Vérifie si la partie n'est pas gagné
        */
	public String verifWin(Partie p) {
		Joueur joueur = p.getJoueur();
		Joueur robot = p.getRobot();
		
		int navireCouleJoueur =0;
		int navireCoulerobot =0;
		
		for (Navire navireJoueur : joueur.getFlotte().getListeNavire()) {
			if(navireJoueur.isCoule()) {
				navireCouleJoueur++;
			}
		}
		
		for (Navire navireRobot : robot.getFlotte().getListeNavire()) {
			if(navireRobot.isCoule()) {
				navireCoulerobot++;
			}
		}

		if (navireCouleJoueur == ConfigurationJeu.NB_NAVIRE) {
			return "ROBOT";
		} else if (navireCoulerobot == ConfigurationJeu.NB_NAVIRE) {
			return "JOUEUR";
		}
		return null;
	}


        /**
        * Permet au joueur de naviguer dans le menu
        */
	public void runConsole(Scanner in) {

		System.out.println(
				"\nVoulez-vous lancer une nouvelle partie ou charger une partie deja commence ? (Nouvelle partie => n || Charger une partie => c)");

		String scanValue = in.nextLine();

		while (!(scanValue.equals("n") || scanValue.equals("c"))) {
			System.out.println("\nVous n\'avez pas rentre \"l\" ou \"c\" , veuillez respecter la consigne demande !!!!");
			System.out.println("\nVoulez-vous lancer une nouvelle partie ou charger une partie deja commence ? (Nouvelle partie => n || Charger une partie => c)");
			scanValue = in.nextLine(); // Lecture de la console
		}

		Partie p;

		if (scanValue.equals("n")) {
			p = new Partie(in);
		} else {
			System.out.println("\nVous avez choisi de charger une partie, rendez-vous dans le dossier 'sauvegarde' et donne nous le nom et l'extension du fichier contenant la partie que vous vous charger ?");
			
			scanValue = in.nextLine();
			String cheminFichier = "src/sauvegarde/"+scanValue;
			
			while (!new File(cheminFichier).exists()) {
				System.out.println("\nLe nom du fichier que vous avez donne n'existe pas, veuillez respecter la consigne !!!");
				System.out.println("\nVous avez choisi de charger une partie, rendez-vous dans le dossier 'sauvegarde' et donne nous le nom et l'extension du fichier contenant la partie que vous vous charger ?");
				
				scanValue = in.nextLine();
				cheminFichier = "src/sauvegarde/"+scanValue;
			}
			
                        ArrayList<Joueur> listeJoueurs = OutilsJeu.chargerJoueur(cheminFichier);
			Joueur joueur = listeJoueurs.get(0);
			Joueur robot = listeJoueurs.get(1);
			
			p = new Partie(joueur, robot, in);
		}

		while (!(ConfigurationJeu.WIN || ConfigurationJeu.QUITTER)) {
			p.tourJoueur(in);
			
			winnerName = verifWin(p);
			if (winnerName != null) {
				ConfigurationJeu.WIN = true;
			}
			
			if(!ConfigurationJeu.QUITTER && !ConfigurationJeu.WIN) {
				p.tourRobot();
				
				winnerName = verifWin(p);
				if (winnerName != null) {
					ConfigurationJeu.WIN = true;
				}
			}
		}
		
		if(ConfigurationJeu.WIN) {
			
			System.out.println("\nFIN DE PARTIE !!!!!");
			
			System.out.println(ConfigurationJeu.ANSI_JAUNE+"\nFelicitations "+winnerName+" tu es le grand vainqueur de cette partie de Bataille Navale !!!!!!" +ConfigurationJeu.ANSI_RESET);
		}
		
		if(ConfigurationJeu.QUITTER) {
			System.out.println(ConfigurationJeu.ANSI_CYAN+"\nVous avez choisi de quiter la partie. Un fichier de sauvegarde de celle-ci vient d'etre creee dans le dossier sauvegarde" +ConfigurationJeu.ANSI_RESET);
			
			System.out.println("\nMerci d'avoir joue !!!!!!");
		}
	}


}
