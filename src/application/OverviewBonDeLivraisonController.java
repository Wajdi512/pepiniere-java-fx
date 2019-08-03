package application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import application.dao.AbstractCommonCriteria;
import application.metier.BonDeLivraisonMetierImpl;
import application.metier.BonDeLivraisonMetierInterface;
import application.metier.FactureMetierImpl;
import application.model.BonDeLivraison;
import application.model.Facture;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OverviewBonDeLivraisonController {

	private Main mainApp;
	@FXML
	private TableView<BonDeLivraison> tableBons;
	@FXML
	private TableColumn<BonDeLivraison, Integer> idBon;
	@FXML
	private TableColumn<BonDeLivraison, String> clientColumn;
	@FXML
	private TableColumn<BonDeLivraison, String> telColumn;
	@FXML
	private TableColumn<BonDeLivraison, Boolean> payeColumn;
	@FXML
	private TableColumn<BonDeLivraison, Date> dateColumn;
	@FXML
	private TableColumn<BonDeLivraison, Double> avanceColumn;
	@FXML
	private TableColumn<BonDeLivraison, Double> montatntColumn;
	@FXML
	private TableColumn<BonDeLivraison, Double> montantPayColumn;
	@FXML
	private TextField txtSearch;
	@FXML
	private TextField txtNUmero;
	@FXML
	private DatePicker txtDateDebut;
	@FXML
	private DatePicker txtDateFin;
	@FXML
	private ComboBox<String> payeSelected;

	private BonDeLivraisonMetierInterface bonDeLivraisonMetier;

	public void initialize() {
		bonDeLivraisonMetier = new BonDeLivraisonMetierImpl();
		idBon.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Integer>("id"));
		clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getNomClient() + " " + cellData.getValue().getPrenomClient()));
		telColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, String>("telClient"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Date>("date"));
		avanceColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("avance"));
		montatntColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("montant"));
		montantPayColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("montantPaye"));
		payeColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Boolean>("paye"));
		tableBons.setItems(FXCollections.observableArrayList(bonDeLivraisonMetier.getAllBons()));
		payeSelected.getItems().add("All");
		payeSelected.getItems().add("Payé");
		payeSelected.getItems().add("Non payé");
		payeSelected.getSelectionModel().selectFirst();
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
	public void handleNewBon() {
		BonDeLivraison bon = new BonDeLivraison();
		mainApp.showBonEditDialog(bon);
		tableBons.setItems(FXCollections.observableArrayList(bonDeLivraisonMetier.getAllBons()));
	}

	@FXML
	public void handleEditBon() {
		BonDeLivraison bonSelected = tableBons.getSelectionModel().getSelectedItem();
		bonSelected.setProduitsAchetes(bonDeLivraisonMetier.getBonDeLivraisonPaniers(bonSelected.getId()));
		bonSelected.setTranches(bonDeLivraisonMetier.getBonDeLivraisonTranches(bonSelected.getId()));
		mainApp.showBonEditDialog(bonSelected);
		tableBons.refresh();
	}

	@FXML
	public void imprimerBon() {
		BonDeLivraison selectedFacture = tableBons.getSelectionModel().getSelectedItem();
		if (selectedFacture.getId() != 0) {
			selectedFacture.setProduitsAchetes(bonDeLivraisonMetier.getBonDeLivraisonPaniers(selectedFacture.getId()));
			boolean gros = (selectedFacture.getProduitsAchetes().get(0).getProduit().getPrixGros()
					* selectedFacture.getProduitsAchetes().get(0).getQuantite()) == selectedFacture.getProduitsAchetes()
							.get(0).getTotal();
			mainApp.printerWindow(selectedFacture, gros);
		}
	}

	@FXML
	public void chercherBon() {
		/*
		 * String mc = txtSearch.getText();
		 * tableBons.setItems(FXCollections.observableArrayList(
		 * bonDeLivraisonMetier.getBonDeLivraisonByMC(mc)));
		 */
		AbstractCommonCriteria abc = this.createAbastractCommonCriteria();
		tableBons.setItems(FXCollections.observableArrayList(bonDeLivraisonMetier.getAllBonsByCriteria(abc)));
	}

	private AbstractCommonCriteria createAbastractCommonCriteria() {
		AbstractCommonCriteria abc = new AbstractCommonCriteria();
		if (txtSearch.getText() != null && !txtSearch.getText().isEmpty())
			abc.setClient(txtSearch.getText());
		if (txtDateDebut.getValue() != null) {
			LocalDate localDate = txtDateDebut.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date dateDebut = Date.from(instant);
			abc.setDateDebut(dateDebut);
//			LocalDate localDate1 = txtDateFin.getValue();
//			Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
//			Date dateFin = Date.from(instant1);
//			Calendar c = Calendar.getInstance();
//			c.setTime(dateFin);
//			c.add(Calendar.DATE, 1);
//			dateFin = c.getTime();
//			abc.setDateFin(dateFin);
		}
		if (txtDateFin.getValue() != null) {
			LocalDate localDate1 = txtDateFin.getValue();
			Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
			Date dateFin = Date.from(instant1);
			Calendar c = Calendar.getInstance();
			c.setTime(dateFin);
			c.add(Calendar.DATE, 1);
			dateFin = c.getTime();
			abc.setDateFin(dateFin);
		}
		int selectedItem = payeSelected.getSelectionModel().getSelectedIndex();
		if (selectedItem != 0) {
			if (selectedItem == 1) {
				abc.setPaye(true);
			} else {
				abc.setPaye(false);
			}
		}

		if (txtNUmero.getText() != null && !txtNUmero.getText().isEmpty())
			abc.setId(Integer.parseInt(txtNUmero.getText()));
		return abc;
	}

	@FXML
	public void resetDate() {
		txtDateDebut.setValue(null);
		txtDateFin.setValue(null);
	}

	@FXML
	public void supprimerBon() {
		BonDeLivraison bon = tableBons.getSelectionModel().getSelectedItem();
		if (bon != null) {
			bon.setProduitsAchetes(bonDeLivraisonMetier.getBonDeLivraisonPaniers(bon.getId()));
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Validation");
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setHeaderText("Voulez-vous vraiment supprimer le bon de livraison selectionné?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				bonDeLivraisonMetier.supprimerBonDeLivraison(bon);
			}
			tableBons.setItems(FXCollections.observableArrayList(bonDeLivraisonMetier.getAllBons()));
		}
	}

}
