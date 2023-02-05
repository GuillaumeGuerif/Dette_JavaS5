package controller.partie;

import java.util.ArrayList;

import java.util.Random;

import config.ConfigurationJeu;
import controller.outils.OutilsJeu;
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
 * Définie le joueur
*/
public class Joueur {

	private Grille grilleJoueur;
	private Grille grilleEnnemi;

	private ArrayList<Coordonnees> listeCoordonneesFlotte;

	private Flotte flotte;

	private int NbBlocTouche;
	private int NbNavireCoule;


        /**
         * Définie les paramétres d'un joueur
        */
	public Joueur(Grille carteJoueur, Grille carteEnnemi, Flotte flotte, int nbBlocTouche, int nbNavireCoule) {
		this.grilleJoueur = carteJoueur;
		this.grilleEnnemi = carteEnnemi;
		this.listeCoordonneesFlotte = new ArrayList<Coordonnees>();
		this.flotte = flotte;
		placementFlotte();
		NbBlocTouche = nbBlocTouche;
		NbNavireCoule = nbNavireCoule;
	}

        /**
         * Définie les paramétres d'un joueur
        */
	public Joueur(Grille carteJoueur, Grille carteEnnemi) {
		this.grilleJoueur = carteJoueur;
		this.grilleEnnemi = carteEnnemi;
		this.listeCoordonneesFlotte = new ArrayList<Coordonnees>();

		this.flotte = generationFlotte(carteJoueur);
		placementFlotte();

		NbBlocTouche = 0;
		NbNavireCoule = 0;

	}

        /**
         * Retourne la grille du joueur
         * @return grilleJoueur
        */
	public Grille getGrilleJoueur() {
		return grilleJoueur;
	}

        /**
         * Retourne la grille de l'ennemie
         * @return grilleEnnemi
        */
	public Grille getGrilleEnnemi() {
		return grilleEnnemi;
	}

        /**
         * Modifie la grille du joueur
        */
	public void setGrilleJoueur(Grille carteJoueur) {
		this.grilleJoueur = carteJoueur;
	}

        /**
         * Retourne la flotte
         * @return flotte
        */
	public Flotte getFlotte() {
		return flotte;
	}

        /**
         * Retourne le nombre de bloc touché
         * @return NbBlocTouche
        */
	public int getNbBlocTouche() {
		return NbBlocTouche;
	}

        /**
         * Modifie le nombre de navire touché
        */
	public void setNbNavireCoule(int nbNavireCoule) {
		NbNavireCoule = nbNavireCoule;
	}

        /**
         * Modifie le nombre de navire coulé
        */
	public int getNbNavireCoule() {
		return NbNavireCoule;
	}

        /**
         * Modifie la valeur d'un bloc
        */
	public void modifValueBlocs(Coordonnees c, int id, boolean coule) {

		int BlocLigne = c.getLigne();
		int BlocColonne = c.getColonne();
		String value = null;

		switch (id) {
		case 1: {
			value = ConfigurationJeu.SIGNE_CUIRASSE;
			break;
		}
		case 2: {
			value = ConfigurationJeu.SIGNE_CROISSEUR;
			break;
		}
		case 3: {
			value = ConfigurationJeu.SIGNE_DESTROYER;
			break;
		}
		case 4: {
			value = ConfigurationJeu.SIGNE_SOUSMARIN;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + id);
		}

		Bloc b = grilleJoueur.getBloc(BlocLigne, BlocColonne);

		b.setValeur(value);
		b.setIdNavire(id);
		if (coule) {
			b.setTouche(true);
			b.setCoule(true);
		}
	}

        /**
         * Initialise un navire
        */
	public void initializeNavire(Navire n) {
		int id = n.getId();
		boolean coule = n.isCoule();

		for (Coordonnees c : n.getListeCoordonnees()) {
			modifValueBlocs(c, id, coule);
		}
	}

        /**
         * Place la flotte
        */
	public void placementFlotte() {
		for (Navire n : flotte.getListeNavire()) {
			initializeNavire(n);
		}
	}

        /**
         * Vérifie la taille
        */
	public boolean verifEgal(int verif, int taille) {
		if (verif != taille) {
			return false;
		} else {
			return true;
		}
	}

        /**
         * Vérifie si la flotte existe
        */
	public boolean verifExistFlotte(Coordonnees c2) {
		int verif = 0;
		for (Coordonnees c1 : listeCoordonneesFlotte) {
			if (c1.verifCoord(c2)) {
				verif++;
			}
		}
		if (verif > 0) {
			return true;
		} else {
			return false;
		}
	}

        /**
         * Vérifie les coordonnées disponible
        */
	public ArrayList<Coordonnees> checkCoordonneesDisponible(int CoordLigne, int CoordColonne, int taille,
			int direction) {
		ArrayList<Coordonnees> c = new ArrayList<Coordonnees>();
		int verif = 0;

		switch (direction) {
		case 0: {
			if (ConfigurationJeu.NB_COLONNE > CoordColonne + taille) {
				for (int i = 0; i < taille; i++) {
					if (!verifExistFlotte(new Coordonnees(CoordLigne, (CoordColonne + i)))) {
						verif++;
						c.add(new Coordonnees(CoordLigne, CoordColonne + i));
					}
				}
			} else {
				verif = 0;
			}
			break;
		}
		case 1: {
			if (ConfigurationJeu.NB_LIGNE > CoordLigne + taille) {
				for (int i = 0; i < taille; i++) {
					if (!verifExistFlotte(new Coordonnees((CoordLigne + i), CoordColonne))) {
						verif++;
						c.add(new Coordonnees(CoordLigne + i, CoordColonne));
					}
				}

			} else {
				verif = 0;
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}

		if (verifEgal(verif, taille)) {
			return c;
		} else {
			return null;
		}

	}

        /**
         * Crée une liste de coordonée
         * @return listeCoordonnees
        */
	public ArrayList<Coordonnees> listeCoordonneesAleatoire(int taille, int direction) {
		Random r = new Random();
		int CordLigne = r.nextInt(16) + 1;
		int CordColonne = r.nextInt(16) + 1;

		while (checkCoordonneesDisponible(CordLigne, CordColonne, taille, direction) == null) {
			CordLigne = r.nextInt(16) + 1;
			CordColonne = r.nextInt(16) + 1;
		}
		ArrayList<Coordonnees> listeCoordonnees = checkCoordonneesDisponible(CordLigne, CordColonne, taille, direction);

		listeCoordonneesFlotte.addAll(listeCoordonnees);

		return listeCoordonnees;

	}

        /**
         * Crée les nouvelles coordonnées
         * @return listeCoordonnees
        */
	public ArrayList<Coordonnees> generationCoordonnees(int taille, int direction) {
		ArrayList<Coordonnees> listeCoordonnees = new ArrayList<Coordonnees>();

		listeCoordonnees = listeCoordonneesAleatoire(taille, direction);

		return listeCoordonnees;
	}

        /**
         * Crée un Cuirasse
         * @return cuirasse
        */
	public Cuirasse generationCuirasse() {
		int direction = OutilsJeu.directionAleatoire();
		ArrayList<Coordonnees> listeCoordCuirasse = generationCoordonnees(ConfigurationJeu.TAILLE_CUIRASSE, direction);

		Cuirasse cuirasse = new Cuirasse(listeCoordCuirasse, direction);

		return cuirasse;

	}

        /**
         * Crée un Croisseur
         * @return croiseur
        */
	public Croiseur generationCroisseur() {
		int direction = OutilsJeu.directionAleatoire();
		ArrayList<Coordonnees> listeCoordCroisseur = generationCoordonnees(ConfigurationJeu.TAILLE_CROISSEUR,
				direction);

		Croiseur croiseur = new Croiseur(listeCoordCroisseur, direction);

		return croiseur;

	}

        /**
         * Crée un Destroyer
         * @return destroyer
        */
	public Destroyer generationDestroyer() {
		int direction = OutilsJeu.directionAleatoire();
		ArrayList<Coordonnees> listeCoordDestroyer = generationCoordonnees(ConfigurationJeu.TAILLE_DESTROYER,
				direction);

		Destroyer destroyer = new Destroyer(listeCoordDestroyer, direction);

		return destroyer;
	}

        /**
         * Crée un SousMarin
         * @return sousMarin
        */
	public SousMarin generationSousMarin() {
		int direction = OutilsJeu.directionAleatoire();
		ArrayList<Coordonnees> listeCoordSousMarin = generationCoordonnees(ConfigurationJeu.TAILLE_SOUSMARIN,
				direction);

		SousMarin sousMarin = new SousMarin(listeCoordSousMarin, direction);

		return sousMarin;
	}

        /**
         * Crée la Flotte
         * @return f
        */
	public Flotte generationFlotte(Grille grilleJoueur) {
		ArrayList<Navire> listeNavire = new ArrayList<Navire>();

		listeNavire.add(generationCuirasse());

		for (int i = 1; i <= 2; i++) {
			listeNavire.add(generationCroisseur());
		}

		for (int i = 1; i <= 3; i++) {
			listeNavire.add(generationDestroyer());
		}

		for (int i = 1; i <= 4; i++) {
			listeNavire.add(generationSousMarin());
		}
		Flotte f = new Flotte(listeNavire);

		return f;
	}

        /**
         * Gére l'impacte des tires
        */
	public void stockBlocTouche(Coordonnees coordonnees, int impact, int id) {
		int coordX = coordonnees.getLigne();
		int coordY = coordonnees.getColonne();

		Grille carteEnemi = grilleEnnemi;

		ArrayList<Coordonnees> listCoordonnees = new ArrayList<>();

		listCoordonnees.add(new Coordonnees(coordX, coordY));

		listCoordonnees.add(new Coordonnees(coordX + 1, coordY));
		listCoordonnees.add(new Coordonnees(coordX - 1, coordY));
		listCoordonnees.add(new Coordonnees(coordX, coordY + 1));
		listCoordonnees.add(new Coordonnees(coordX, coordY - 1));

		listCoordonnees.add(new Coordonnees(coordX + 1, coordY + 1));
		listCoordonnees.add(new Coordonnees(coordX - 1, coordY - 1));
		listCoordonnees.add(new Coordonnees(coordX - 1, coordY + 1));
		listCoordonnees.add(new Coordonnees(coordX + 1, coordY - 1));

		Bloc b = null;
		if (impact == 9) {

			System.out.println("\nTIRE DE CUIRASSE EN " + coordonnees.toString() + "\n");

			for (int i = 0; i < impact; i++) {
				Coordonnees c = listCoordonnees.get(i);
				b = carteEnemi.getBloc(c);
				if (b.getIdNavire() != 4) {
					b.setTouche(true);
				}

			}

			System.out.println("\nIMPACT DE : " + impact + " CASES !!!\n");

		} else if (impact == 4) {

			System.out.println("\nTIRE DE CROISSEUR EN " + coordonnees.toString() + "\n");

			for (int i = 0; i <= impact; i++) {
				Coordonnees c = listCoordonnees.get(i);
				b = carteEnemi.getBloc(c);
				if (b.getIdNavire() != 4) {
					b.setTouche(true);
				}
			}

			System.out.println("\nIMPACT DE : " + impact + " CASES !!!\n");

		} else if (id == 3 && ConfigurationJeu.NB_FUSEE_ECLAIRANTE > 0) {

			System.out.println("\nTIRE DE FUSEE ECLAIRANTE EN " + coordonnees.toString() + "\n");

			for (int i = 0; i <= 4; i++) {// 4 IMPACTS
				Coordonnees c = listCoordonnees.get(i);
				b = carteEnemi.getBloc(c);

				if (!b.isTouche()) {
					b.setEclaire(true);
				}
			}

			System.out.println("\nECLAIRAGE DE : " + 4 + " CASES !!!\n");
			ConfigurationJeu.NB_FUSEE_ECLAIRANTE--;
			System.out.println("\nIl VOUS RESTE " + ConfigurationJeu.NB_FUSEE_ECLAIRANTE + " FUSEE ECLAIRANTE.");

		} else if (id == 3) {
			System.out.println("\nTIRE DE DESTROYEUR EN " + coordonnees.toString() + "\n");

			for (int i = 0; i < impact; i++) {
				Coordonnees c = listCoordonnees.get(i);
				b = carteEnemi.getBloc(c);
				if (b.getIdNavire() != 4) {
					b.setTouche(true);
				}
			}

			System.out.println("\nIMPACT DE : " + impact + " CASES !!!\n");

		} else {
			System.out.println("\nTIRE DE SOUS-MARIN EN " + coordonnees.toString() + "\n");

			for (int i = 0; i < impact; i++) {
				Coordonnees c = listCoordonnees.get(i);
				b = carteEnemi.getBloc(c);
				b.setTouche(true);
			}

			System.out.println("\nIMPACT DE : " + impact + " CASES !!!\n");
		}

	}


        /**
         * Mets à jour les navires touchés
        */
	public void updateNavireTouche(int id) {
		Bloc[][] blocs = grilleJoueur.getBlocs();

		ArrayList<Navire> listeNav = flotte.getListeNavire();

		ArrayList<Coordonnees> listeCoord;

		int verif;

		for (int i = 0; i < listeNav.size(); i++) {
			listeCoord = listeNav.get(i).getListeCoordonnees();
			verif = 0;
			for (int j = 0; j < listeCoord.size(); j++) {
				int line = listeCoord.get(j).getLigne();
				int colonne = listeCoord.get(j).getColonne();
				if (blocs[line][colonne].isTouche()) {
					verif++;
				}
			}
			Navire navire = listeNav.get(i);
			if (navire.getId() != 4) {
				if (verif > 0) {
					navire.setTouche(true);
				}
			} else {
				if (navire.getId() == id) {
					navire.setTouche(true);
				}
			}

		}
	}


        /**
         * Permet de tiré
        */
	public void tirer(Coordonnees coordonnees, int id) {

		System.out.println(coordonnees);

		if (id == 1) {
			stockBlocTouche(coordonnees, ConfigurationJeu.IMPACT_CUIRASSE, id);
		} else if (id == 2) {
			stockBlocTouche(coordonnees, ConfigurationJeu.IMPACT_CROISSEUR, id);
		} else if (id == 3) {
			stockBlocTouche(coordonnees, ConfigurationJeu.IMPACT_DESTROYER, id);
		} else {
			stockBlocTouche(coordonnees, ConfigurationJeu.IMPACT_SOUSMARIN, id);
		}

		updateNavireTouche(id);
	}

        /**
         * Mets à jour le symbole du navire
        */
	public void MAJSigne(Navire navire) {
		for (Coordonnees c : navire.getListeCoordonnees()) {
			modifValueBlocs(c, navire.getId(), navire.isCoule());
		}
	}


        /**
         * Supprime le symbole du navire
        */
	public void DeleteSigne(Navire navire) {
		String value = "  ";
		int BlocLigne = 0;
		int BlocColonne = 0;
		for (Coordonnees c : navire.getListeCoordonnees()) {

			BlocLigne = c.getLigne();
			BlocColonne = c.getColonne();

			Bloc b = grilleJoueur.getBloc(BlocLigne, BlocColonne);
			b.setValeur(value);
			b.setIdNavire(0);
		}
	}

        /**
         * Vérifie le déplacement d'un navire par rapport à sa position
        */
	public boolean deplacementCoherent(Navire navire, int directionDeplacement) {
		ArrayList<Coordonnees> listeCoord = navire.getListeCoordonnees();
		Coordonnees cDebut;
		Coordonnees cFin;
		if (navire.getDirection() == 0) {// 0 = horizontale
			cDebut = listeCoord.get(0);
			cFin = listeCoord.get(listeCoord.size() - 1);
			if (directionDeplacement == 0 && cDebut.getColonne() != 0
					|| directionDeplacement == 2 && cFin.getColonne() != ConfigurationJeu.NB_COLONNE - 2) {
				return true;
			} else {
				return false;
			}
		} else { // 1 = verticale
			cDebut = listeCoord.get(0);
			cFin = listeCoord.get(listeCoord.size() - 1);
			if (directionDeplacement == 1 && cDebut.getLigne() != 0
					|| directionDeplacement == 3 && cFin.getLigne() != ConfigurationJeu.NB_COLONNE - 2) {
				return true;
			} else {
				return false;
			}
		}
	}

        /**
         * Vérifie les blocs disponible
        */
	public boolean checkBlocDisponible(Navire navire, int directionDeplacement) {
		Coordonnees coordonneesDebutNavire = null;
		int BlocLigne = 0;
		int BlocColonne = 0;

		int futureCoordLigne = 0;
		int futureCoordColonne = 0;

		switch (directionDeplacement) {
		case 0: { // gauche

			coordonneesDebutNavire = navire.getListeCoordonnees().get(0);
			BlocLigne = coordonneesDebutNavire.getLigne();
			BlocColonne = coordonneesDebutNavire.getColonne();

			futureCoordLigne = BlocLigne;
			futureCoordColonne = BlocColonne - 1;

			break;
		}
		case 1: { // haut

			coordonneesDebutNavire = navire.getListeCoordonnees().get(0);
			BlocLigne = coordonneesDebutNavire.getLigne();
			BlocColonne = coordonneesDebutNavire.getColonne();

			futureCoordLigne = BlocLigne - 1;
			futureCoordColonne = BlocColonne;
			break;
		}
		case 2: { // droite

			coordonneesDebutNavire = navire.getListeCoordonnees().get(navire.getTaille() - 1);
			BlocLigne = coordonneesDebutNavire.getLigne();
			BlocColonne = coordonneesDebutNavire.getColonne();

			futureCoordLigne = BlocLigne;
			futureCoordColonne = BlocColonne + 1;
			break;
		}
		case 3: { // bas
			coordonneesDebutNavire = navire.getListeCoordonnees().get(navire.getTaille() - 1);
			BlocLigne = coordonneesDebutNavire.getLigne();
			BlocColonne = coordonneesDebutNavire.getColonne();

			futureCoordLigne = BlocLigne + 1;
			futureCoordColonne = BlocColonne;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + directionDeplacement);
		}

		Bloc FutureBlocNavire = grilleJoueur.getBloc(futureCoordLigne, futureCoordColonne);

		if (FutureBlocNavire.getValeur().equals("  ")) {
			return true;
		} else {
			return false;
		}

	}


        /**
         * Mise à jour des coordonnées
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
         * Valide les nouvelles coordonnées
        */
	public Coordonnees validationFuturesCoordonnees(Navire navire, int directionDeplacement) {
		ArrayList<Coordonnees> listeCoordonnees = navire.getListeCoordonnees();
		int taille = navire.getTaille();
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
         * Retourne si le navires peut etre déplacé
         * @return false/true
        */
	public boolean navireDeplacable(Navire navire, int directionDeplacement) {
		Coordonnees c = validationFuturesCoordonnees(navire, directionDeplacement);
		if (c.valideCoordonnees()) {
			return true;
		}
		return false;
	}


        /**
         * Déplace le navire
        */
	public void deplaceNavire(Navire navire, int directionDeplacement) {
		ArrayList<Coordonnees> listeCoordonnees = navire.getListeCoordonnees();

		if (navireDeplacable(navire, directionDeplacement)) {
			for (int i = 0; i < listeCoordonnees.size(); i++) {
				Coordonnees c = listeCoordonnees.get(i);
				listeCoordonnees.set(i, MajCoordonnees(directionDeplacement, c));
			}
		}
	}


        /**
         * Déplace le navire
        */
	public boolean deplacer(int indiceNavire, int directionDeplacement) {
		boolean verif = false;

		Navire navire = flotte.getListeNavire().get(indiceNavire);
		if (!navire.isTouche()) {
			if (deplacementCoherent(navire, directionDeplacement)) {
				if (checkBlocDisponible(navire, directionDeplacement)) {
					DeleteSigne(navire);
					deplaceNavire(navire, directionDeplacement);
					MAJSigne(navire);
					verif = true;
				}
			}
		} else {
			System.out.println("ATTENTION ! VOUS NE POUVEZ PAS DEPLACER UN NAVIRE TOUCHE !!!");
		}

		return verif;
	}


        /**
         * Vérifie si la liste existe
        */
	public boolean verifExistListe(Coordonnees c2, ArrayList<Coordonnees> liste) {
		int verif = 0;
		for (Coordonnees c1 : liste) {
			if (c1.verifCoord(c2)) {
				verif++;
			}
		}
		if (verif > 0) {
			return true;
		} else {
			return false;
		}
	}


        /**
         * Retourne l'indice du navire
         * @return i/44
        */
	public int recupIndice(int CoordLigne, int CoordColonne) {
		Coordonnees coordBloc = new Coordonnees(CoordLigne, CoordColonne);
		ArrayList<Navire> listeNaviresFlottes = flotte.getListeNavire();

		for (int i = 0; i < listeNaviresFlottes.size(); i++) {
			if (verifExistListe(coordBloc, listeNaviresFlottes.get(i).getListeCoordonnees())) {
				return i;
			}
		}
		return 44;
	}

        /**
         * Incrémente le nombre de bateaux touché
        */
	public void incrementeNombreBlocTouche() {
		Bloc[][] blocs = grilleEnnemi.getBlocs();

		for (int i = 1; i < ConfigurationJeu.NB_LIGNE; i++) {
			for (int j = 1; j < ConfigurationJeu.NB_COLONNE; j++) {
				if (blocs[i][j].isTouche() && blocs[i][j].getValeur() != "  ") {
					NbBlocTouche++;
				}
			}
		}
	}


        /**
         * Verifie si le navire est coulé
        */
	public void verifNavireCoule() {
		ArrayList<Navire> listeNav = flotte.getListeNavire();

		Bloc[][] blocs = grilleJoueur.getBlocs();

		for (int i = 0; i < listeNav.size(); i++) {
			ArrayList<Coordonnees> listeCoord = listeNav.get(i).getListeCoordonnees();
			int sizeListeCoord = listeCoord.size();
			int verif = 0;
			for (int j = 0; j < sizeListeCoord; j++) {
				int coordLigne = listeCoord.get(j).getLigne();
				int coordColonne = listeCoord.get(j).getColonne();
				if (blocs[coordLigne][coordColonne].isTouche()) {
					verif++;
				}
			}
			if (!listeNav.get(i).isCoule()) {
				if (verif == sizeListeCoord) {
					listeNav.get(i).setCoule(true);
					System.out.println("BRAVO !!  Vous avez eliminer un Navire de l'ennemi");
					NbNavireCoule++;
					CouleSigne(listeNav.get(i));
				}
			}

		}
	}


        /**
         * Applique le symbole coulé au navire dont toutes ces parties son touché
        */
	public void CouleSigne(Navire navire) {
		String value = "::";
		int BlocLigne = 0;
		int BlocColonne = 0;
		for (Coordonnees c : navire.getListeCoordonnees()) {

			BlocLigne = c.getLigne();
			BlocColonne = c.getColonne();

			Bloc b = grilleJoueur.getBloc(BlocLigne, BlocColonne);
			b.setCoule(true);
			b.setValeur(value);
		}
	}


        /**
         * Coup aléatoire du robot
        */
	public String coupAleatoire() {
		int valeurAleatoire = OutilsJeu.valeurAleatoire(2);
		if (valeurAleatoire == 1) {
			return "t";
		} else {
			return "d";
		}
	}


        /**
         * Retourne l'indice du navire déplacé
         * @return OutilsJeu.valeurZeroAleatoire(10)
        */
	public int indiceNavireDeplacement() {
		return OutilsJeu.valeurZeroAleatoire(10);
	}

        /**
         * Direction aléatoire du robot
        */
	public int directionAleatoire() {
		int direction = OutilsJeu.valeurZeroAleatoire(4);
		System.out.println("DIRECTION ALEATOIRE TIREE : " + direction);
		return direction;
	}

        /**
         * Vérifie si le déplacement aléatoire est possible
        */
	public boolean checkDeplacementAleatoire(int indiceNavire, int directionDeplacement) {
		ArrayList<Navire> listeNav = flotte.getListeNavire();

		Navire navire = listeNav.get(indiceNavire);

		ArrayList<Coordonnees> listeCoord = navire.getListeCoordonnees();
		Coordonnees cDebut;
		Coordonnees cFin;
		if (navire.getDirection() == 0) {// 0 = horizontale
			cDebut = listeCoord.get(0);
			cFin = listeCoord.get(listeCoord.size() - 1);
			if (directionDeplacement == 0 && cDebut.getColonne() != 0
					|| directionDeplacement == 2 && cFin.getColonne() != ConfigurationJeu.NB_COLONNE - 2) {
				return true;
			} else {
				return false;
			}
		} else { // 1 = verticale
			cDebut = listeCoord.get(0);
			cFin = listeCoord.get(listeCoord.size() - 1);
			if (directionDeplacement == 1 && cDebut.getLigne() != 0
					|| directionDeplacement == 3 && cFin.getLigne() != ConfigurationJeu.NB_COLONNE - 2) {
				return true;
			} else {
				return false;
			}
		}
	}


        /**
         * Vérifie si le navire est touché
        */
	public boolean verifTouche(int indiceNavire) {
		Navire navire = flotte.getListeNavire().get(indiceNavire);

		if (navire.isTouche()) {
			return true;
		} else {
			return false;
		}
	}

        /**
         * Vérifie si le navire est coulé
        */
	public boolean verifCoule(int indiceNavire) {
		Navire navire = flotte.getListeNavire().get(indiceNavire);

		if (navire.isCoule()) {
			return true;
		} else {
			return false;
		}
	}

        /**
         * Vérifie si le déplacement est possible
        */
	public boolean verifNavireDeplacable(int indiceNavire) {
		if ((verifTouche(indiceNavire) && verifCoule(indiceNavire))
				|| (verifCoule(indiceNavire) || verifTouche(indiceNavire))) {
			return false;
		} else {
			return true;
		}
	}

        /**
         * Vérifie si le navire est disponible pour le déplacement
        */
	public boolean verifNavireDispo(int idNavire) {
		ArrayList<Navire> listeNav = flotte.getListeNavire();

		int verif = 0;

		for (Navire navire : listeNav) {
			if (navire.getId() == idNavire) {
				if (!navire.isCoule()) {
					verif++;
				}
			}
		}
		if (verif > 0) {
			return true;
		} else {
			return false;
		}
	}


        /**
         * Retourne le nombre de navire déplacable
         * @return verif > 0
        */
	public boolean NbNavireDeplacable() {
		ArrayList<Navire> listeNavires = flotte.getListeNavire();
		int verif = 0;
		for (Navire navire : listeNavires) {
			if (!navire.isTouche()) {
				verif++;
			}
		}
		return verif > 0;
	}

        /**
         * Redéfinitions de la méthode toString
        */
	@Override
	public String toString() {
		return "Joueur [grilleJoueur=" + grilleJoueur + ", grilleEnnemi=" + grilleEnnemi + ", flotte=" + flotte + "]";
	}

}
