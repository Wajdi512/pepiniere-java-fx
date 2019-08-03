package application;

import application.metier.UserMetierImpl;
import application.metier.UserMetierInterface;
import application.model.User;
import javafx.fxml.FXML;

public class HomeWindowController {

	private Main mainApp;
	private UserMetierInterface userMetier;



	public HomeWindowController() {
		super();
		// TODO Auto-generated constructor stub
		userMetier = new UserMetierImpl();
	}

	@FXML
	public void openProduitWindow() {
		mainApp.produitWindow();
	}

	@FXML
	public void openFactureWindow() {
		mainApp.factureWindow();
	}

	@FXML
	public void openBonDeLivraisonWindow() {
		mainApp.bonLivraisonWindow();
	}

	@FXML
	public void openOuvrierWIndow() {
		mainApp.ouvrierWindow();
	}

	public void openCommandeWindow() {
		mainApp.commandeWindow();
	}
	public void openBonToFacture() {
		mainApp.bonDeLivraisonToFactureWindow();
	}

	public void openPrintEmpty() {
		mainApp.printEmptyWindow();
	}

	public void openDevisOverview(){
		mainApp.overviewDevisWindow();
	}

	public void openRecuParJour(){
		mainApp.recuParJourWindow();;
	}

	public void openParametres(){
		User user = userMetier.getUserById(1);
		mainApp.parametresWindow(user);
	}


	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}



}
