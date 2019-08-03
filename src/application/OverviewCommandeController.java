package application;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.dao.ProduitDaoImpl;
import application.metier.BonDeLivraisonMetierInterface;
import application.metier.CommandeMetierImpl;
import application.metier.CommandeMetierInterface;
import application.model.BonDeLivraison;
import application.model.Commande;
import application.model.Facture;
import application.model.Produit;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OverviewCommandeController {

	private Main mainApp;
	private CommandeMetierInterface commandeMetier;

	@FXML
	private TableView<Commande> tableCmds;
	@FXML
	private TableColumn<Commande, Integer> idCmdColumn;
	@FXML
	private TableColumn<Commande, String> clientColumn;
	@FXML
	private TableColumn<Commande, String> telColumn;
	@FXML
	private TableColumn<Commande, Date> dateColumn;
	@FXML
	private TableColumn<Commande, Double> avanceColumn;
	@FXML
	private TableColumn<Commande, Double> montatntColumn;

	@FXML
	private TextField txtSearch;

	private BonDeLivraisonMetierInterface bonDeLivraisonMetier;

	public void initialize() {
		commandeMetier = new CommandeMetierImpl();
		idCmdColumn.setCellValueFactory(new PropertyValueFactory<Commande, Integer>("id"));
		clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()+" "+cellData.getValue().getPrenomClient()));
		telColumn.setCellValueFactory(new PropertyValueFactory<Commande, String>("telClient"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Commande, Date>("date"));
		montatntColumn.setCellValueFactory(new PropertyValueFactory<Commande, Double>("montant"));
		tableCmds.setItems(FXCollections.observableArrayList(commandeMetier.getAllCommande()));

	}


	public Main getMainApp() {
		return mainApp;
	}




	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}


	@FXML
	public void handleNewCommande() {
		Commande cmd = new Commande();
		mainApp.showCommandeEditDialog(cmd);
		String mc = txtSearch.getText();
		tableCmds.setItems(FXCollections.observableArrayList(commandeMetier.getCommandesByMC(mc)));
	}


	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	@FXML
	public void handleEditCommande() {
		Commande cmd = tableCmds.getSelectionModel().getSelectedItem();
		if (cmd != null) {
			cmd.setProduitsAchetes(commandeMetier.getCommandePanier(cmd.getId()));
			mainApp.showCommandeEditDialog(cmd);
			String mc = txtSearch.getText();
			tableCmds.setItems(FXCollections.observableArrayList(commandeMetier.getCommandesByMC(mc)));
		}
	}

	@FXML
	public void imprimerCommande() {
		Commande selectedCmd = tableCmds.getSelectionModel().getSelectedItem();
		if (selectedCmd.getId() != 0) {
			selectedCmd.setProduitsAchetes(commandeMetier.getCommandePanier(selectedCmd.getId()));
			boolean gros = (selectedCmd.getProduitsAchetes().get(0).getProduit().getPrixGros()
					* selectedCmd.getProduitsAchetes().get(0).getQuantite()) == selectedCmd.getProduitsAchetes().get(0)
							.getTotal();
			mainApp.printerWindow(selectedCmd, gros);
		}
	}


	@FXML
	public void chercherCommande() {
		String mc = txtSearch.getText();
		tableCmds.setItems(FXCollections.observableArrayList(commandeMetier.getCommandesByMC(mc)));
	}

	public void supprimerCommande() {
		String mc = txtSearch.getText();
		Commande selectedCmd = tableCmds.getSelectionModel().getSelectedItem();
		if (selectedCmd != null) {
			selectedCmd.setProduitsAchetes(commandeMetier.getCommandePanier(selectedCmd.getId()));
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Validation");
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setHeaderText("Voulez-vous vraiment supprimer la commande selectionnée?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				commandeMetier.supprimerCommande(selectedCmd);
			}
			tableCmds.setItems(FXCollections.observableArrayList(commandeMetier.getCommandesByMC(mc)));
		}
	}

}
