package application;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import application.dao.AbstractCommonCriteria;
import application.metier.OuvrierMetierImpl;
import application.metier.OuvrierMetierInterface;
import application.model.Absence;
import application.model.CoutDeJour;
import application.model.Ouvrier;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OverviewOuvrierController {

	private Main mainApp;
	private OuvrierMetierInterface ouvrierMetier;

	@FXML
	private TableView<Ouvrier> tableOuvrier;
	@FXML
	private TableColumn<Ouvrier, Integer> idColumn;
	@FXML
	private TableColumn<Ouvrier, String> nomColumn;
	@FXML
	private TableColumn<Ouvrier, String> prenomColumn;

	@FXML
	private TableColumn<Ouvrier, String> dateAjout;

	@FXML
	private TableColumn<Ouvrier, String> fonctionColumn;

	@FXML
	private TextField txtSearch;


	public void initialize() {
		this.ouvrierMetier = new OuvrierMetierImpl();
		idColumn.setCellValueFactory(new PropertyValueFactory<Ouvrier, Integer>("id"));
		nomColumn.setCellValueFactory(new PropertyValueFactory<Ouvrier, String>("nom"));
		prenomColumn.setCellValueFactory(new PropertyValueFactory<Ouvrier, String>("prenom"));
		fonctionColumn.setCellValueFactory(new PropertyValueFactory<Ouvrier, String>("fonction"));
		dateAjout.setCellValueFactory(new PropertyValueFactory<Ouvrier, String>("dateAjout"));
		tableOuvrier.setItems(FXCollections.observableArrayList(ouvrierMetier.listeOuvrier()));
	}
	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void chercher() {
		final String search = txtSearch.getText();
		AbstractCommonCriteria criteria = new AbstractCommonCriteria();
		criteria.setClient(search);
		tableOuvrier.setItems(FXCollections.observableArrayList(ouvrierMetier.listeOuvrierByCriteria(criteria)));
	}



	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	@FXML
	public void openOuvrierWindow() {
		Ouvrier selectedOuvrier = tableOuvrier.getSelectionModel().getSelectedItem();
		if(selectedOuvrier != null) {
			selectedOuvrier.setMesAbsences(ouvrierMetier.getCoutJourOuvrier(selectedOuvrier));
			mainApp.showEditOuvrier(selectedOuvrier);
		} else {
			mainApp.showEditOuvrier(new Ouvrier());
		}
		tableOuvrier.setItems(FXCollections.observableArrayList(ouvrierMetier.listeOuvrier()));

	}

	@FXML
	public void openVoirAbs() {
		Ouvrier selectedOuvrier = tableOuvrier.getSelectionModel().getSelectedItem();
		if(selectedOuvrier != null) {
		//	selectedOuvrier.setMesAbsences(ouvrierMetier.listAbsences(selectedOuvrier.getId()));
			selectedOuvrier.setMesAbsences(ouvrierMetier.getCoutJourOuvrier(selectedOuvrier));
			for(CoutDeJour cdj: selectedOuvrier.getMesAbsences()) {
				cdj.setJoursTravaile(ouvrierMetier.listAbsencesCdj(cdj.getId()));
			}
			selectedOuvrier.setMesAvances(ouvrierMetier.listAvances(selectedOuvrier.getId()));
			mainApp.showVoirAbsenceOuvrier(selectedOuvrier);
		}
	}
	@FXML
	public void supprimerOuvrier() {
		Ouvrier selectedOuvrier = tableOuvrier.getSelectionModel().getSelectedItem();
		if(selectedOuvrier != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Attention !");
			alert.setHeaderText("Voulez vous vraiment supprimer:"
					+ " "+selectedOuvrier.getNom()+" "+selectedOuvrier.getPrenom());
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				ouvrierMetier.supprimerOuvrier(selectedOuvrier.getId());
				tableOuvrier.getItems().remove(selectedOuvrier);
				tableOuvrier.refresh();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Erreur");
			alert.setHeaderText("Selectionez un ouvrier !");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

}
