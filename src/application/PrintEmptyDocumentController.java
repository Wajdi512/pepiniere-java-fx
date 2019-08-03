package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import application.io.DocumentInterface;
import application.io.EmptyFileLocationConstants;
import application.io.PDFGenerateur;
import application.metier.BonDeLivraisonMetierImpl;
import application.model.BonDeLivraison;
import application.model.Commande;
import application.model.Devis;
import application.model.Facture;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrintEmptyDocumentController {

	private Main mainApp;
	private DocumentInterface document;
	private String cheminFichier;
	@FXML
	private ComboBox<String> typeDocCB;
	@FXML
	private TextField txtNumero;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtTel;


	public void initialize() {
		String[] typeDoc = { "Devis", "Facture", "Bon de livraison", "Commande" };
		typeDocCB.getItems().addAll(typeDoc);
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	@FXML
	private void imprimer() throws MalformedURLException, IOException {
		String selectedType = typeDocCB.getSelectionModel().getSelectedItem();
		if (!selectedType.equals("")) {
			int numero = 0;
			if(!txtNumero.getText().equals(""))
			numero = Integer.parseInt(txtNumero.getText());
			switch (selectedType) {
			case "Devis":
				this.cheminFichier = EmptyFileLocationConstants.DEVIS_VIDE;

				break;
			case "Commande":
				this.cheminFichier = EmptyFileLocationConstants.COMMANDE_VIDE;
				break;
			case "Facture":

				this.cheminFichier = EmptyFileLocationConstants.FACTURE_VIDE;

				break;
			case "Bon de livraison":
				this.cheminFichier = EmptyFileLocationConstants.BON_DE_LIVRAISON_VIDE;

				break;
			default:
				break;
			}

		}
		if(this.cheminFichier != null) {

			mainApp.printerWindow(this.cheminFichier,true);
		}
	}

}
