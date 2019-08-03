package application;

import java.util.Date;

import application.metier.DevisMetierImpl;
import application.metier.DevisMetierInterface;
import application.model.Devis;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class OverviewDevisController {

	private Main mainApp;
	private DevisMetierInterface devisMetier;

	@FXML
	private TableView<Devis> tableDevis;
	@FXML
	private TableColumn<Devis, Integer> idColumn;
	@FXML
	private TableColumn<Devis, String> clientColumn;
	@FXML
	private TableColumn<Devis, String> telColumn;
	@FXML
	private TableColumn<Devis, Date> dateColumn;
	@FXML
	private TableColumn<Devis, Double> montatntColumn;
	@FXML
	private TextField txtSearch;

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void initialize() {
		this.devisMetier = new DevisMetierImpl();
		idColumn.setCellValueFactory(new PropertyValueFactory<Devis, Integer>("id"));
		clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()+" "+cellData.getValue().getPrenomClient()));
		telColumn.setCellValueFactory(new PropertyValueFactory<Devis, String>("telClient"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Devis, Date>("date"));
		montatntColumn.setCellValueFactory(new PropertyValueFactory<Devis, Double>("montant"));
		tableDevis.setItems(FXCollections.observableArrayList(devisMetier.getAllDevis()));
	}

	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	@FXML
	private void handleNewDevis() {
		mainApp.showDevisEditDialog(new Devis());
		tableDevis.setItems(FXCollections.observableArrayList(devisMetier.getAllDevis()));
	}

	@FXML
	private void handleEditDevis() {
		Devis selectedDevis = tableDevis.getSelectionModel().getSelectedItem();
		if (selectedDevis != null) {
			selectedDevis.setProduitsAchetes(devisMetier.getDevisPanier(selectedDevis.getId()));
			mainApp.showDevisEditDialog(selectedDevis);
			tableDevis.setItems(FXCollections.observableArrayList(devisMetier.getAllDevis()));
		}
	}

	@FXML
	private void printDevis() {
		Devis selectedDevis = tableDevis.getSelectionModel().getSelectedItem();
		if(selectedDevis != null) {
			selectedDevis.setProduitsAchetes(devisMetier.getDevisPanier(selectedDevis.getId()));
			boolean gros = (selectedDevis.getProduitsAchetes().get(0).getProduit().getPrixGros() * selectedDevis.getProduitsAchetes().get(0).getQuantite()) == selectedDevis.getProduitsAchetes().get(0).getTotal();
			mainApp.printerWindow(selectedDevis,gros);
		}
	}

	@FXML
	private void deleteDevis() {

	}

	@FXML
	public void chercherDevis() {
		String mc = txtSearch.getText();
		tableDevis.setItems(FXCollections.observableArrayList(devisMetier.getDevisByMC(mc)));

	}

}
