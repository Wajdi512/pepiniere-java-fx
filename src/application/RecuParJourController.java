package application;


import java.util.Date;
import java.util.List;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

import application.dao.AbstractCommonCriteria;
import application.dao.FactureDao;
import application.metier.StatsMetier;
import application.metier.StatsMetierImpl;
import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Panier;
import application.model.Produit;
import application.statsbeans.StatsBean;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;

public class RecuParJourController {
	private StatsMetier statsMetiers;
	private Main mainApp;
	@FXML
	private DatePicker dateDebut;
	@FXML
	private DatePicker dateFin;

	@FXML
	private TableView<Object> tableFactures;
	@FXML
	private Label totalLabel;
	@FXML
	private Label creditLabel;
	@FXML
	private Label payeLabel;

	@FXML
	private TableColumn<Object, String> nomClientColumn;
	@FXML
	private TableColumn<Object, String> telClientColumn;
	@FXML
	private TableColumn<Object, Double> totalColumn;
	@FXML
	private TableColumn<Object, Double> montantPayeColumn;
	@FXML
	private TableColumn<Object, Double> montantRestantColumn;
	@FXML
	private TableColumn<Object, Date> dateColumn;
	@FXML
	private ComboBox<String> selectedDoc;

	private StatsBean stats;


	@FXML
    private void initialize() {
		selectedDoc.getItems().add("Facture");
		selectedDoc.getItems().add("Bon de livraison");
		selectedDoc.getSelectionModel().selectFirst();
		dateDebut.setEditable(true);
		dateFin.setEditable(true);
		statsMetiers = new StatsMetierImpl();
		nomClientColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(((Facture) cellData.getValue()).getNomClient()
						+ " " + ((Facture) cellData.getValue()).getPrenomClient()));
		telClientColumn.setCellValueFactory(new PropertyValueFactory<Object, String>("telClient"));
		montantPayeColumn.setCellValueFactory(new PropertyValueFactory<Object, Double>("montantPaye"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Object, Double>("montant"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Object, Date>("date"));
		selectedDoc.setOnAction((event) -> {
			if(selectedDoc.getSelectionModel().getSelectedIndex()== 0){
				nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((Facture) cellData.getValue()).getNomClient()+" "+((Facture) cellData.getValue()).getPrenomClient()));

			}else {
				nomClientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(((BonDeLivraison) cellData.getValue()).getNomClient()+" "+((BonDeLivraison) cellData.getValue()).getPrenomClient()));

			}
		});
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void loadTable(){
		Date date = null;
		try{
			AbstractCommonCriteria abc = this.createAbastractCommonCriteria();
			if(selectedDoc.getSelectionModel().getSelectedIndex() == 0) {
				stats = statsMetiers.getStatsBeanFacture(abc);
				List<Facture> factures = stats.getFacture();
				if(factures !=null) {
					tableFactures.setItems(FXCollections.observableArrayList(factures));
				} else {
					tableFactures.setItems(FXCollections.observableArrayList());

				}
				totalLabel.setText(stats.getTotal()+"");
				creditLabel.setText(stats.getCredit()+"");
				payeLabel.setText(stats.getTotalPaye()+"");
			}
			else {
				stats = statsMetiers.getStatsBeanBons(abc);
				List<BonDeLivraison> bons = stats.getBons();
				if(bons!=null){
					tableFactures.setItems(FXCollections.observableArrayList(bons));
				}
				totalLabel.setText(stats.getTotal()+"");
				creditLabel.setText(stats.getCredit()+"");
				payeLabel.setText(stats.getTotalPaye()+"");

			}
		}finally {

		}

		}

	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	private AbstractCommonCriteria createAbastractCommonCriteria() {
		AbstractCommonCriteria abc = new AbstractCommonCriteria();

		if (dateDebut.getValue() != null ) {
			LocalDate localDate = dateDebut.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date dateD = Date.from(instant);
			abc.setDateDebut(dateD);

		}
		if (dateFin.getValue() != null) {
			LocalDate localDate1 = dateFin.getValue();
			Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
			Date dateF = Date.from(instant1);
			Calendar c = Calendar.getInstance();
			c.setTime(dateF);
			c.add(Calendar.DATE, 1);
			dateF = c.getTime();
			abc.setDateFin(dateF);
		}
		return abc;
	}

	@FXML
	public void resetDate() {
		dateFin.setValue(null);
		dateDebut.setValue(null);
	}

	@FXML
	public void imprimerRecette() {
		System.out.println("imprimer Recette");
	}
}
