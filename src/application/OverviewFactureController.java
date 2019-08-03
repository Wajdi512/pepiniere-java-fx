package application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.dao.AbstractCommonCriteria;
import application.metier.BonDeLivraisonMetierImpl;
import application.metier.BonDeLivraisonMetierInterface;
import application.metier.FactureMetierImpl;
import application.metier.FactureMetierInterface;
import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Panier;
import application.model.Tranche;
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

public class OverviewFactureController {

	private Main mainApp;
	private FactureMetierInterface factureMetier;

	@FXML
	private TableView<Facture> tableFactures;

	@FXML
	private TableColumn<Facture, Integer> idFactureColumn;
	@FXML
	private TableColumn<Facture, String> clientColumn;
	@FXML
	private TableColumn<Facture, String> telColumn;
	@FXML
	private TableColumn<Facture, Boolean> payeColumn;
	@FXML
	private TableColumn<Facture, Date> dateColumn;
	@FXML
	private TableColumn<Facture, Double> avanceColumn;
	@FXML
	private TableColumn<Facture, Double> montatntColumn;
	@FXML
	private TableColumn<Facture, Double> montantPayColumn;
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

	public void initialize() {
		factureMetier = new FactureMetierImpl();
		idFactureColumn.setCellValueFactory(new PropertyValueFactory<Facture, Integer>("id"));
		clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()+" "+cellData.getValue().getPrenomClient()));
		telColumn.setCellValueFactory(new PropertyValueFactory<Facture, String>("telClient"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Facture, Date>("date"));
		avanceColumn.setCellValueFactory(new PropertyValueFactory<Facture, Double>("avance"));
		montatntColumn.setCellValueFactory(new PropertyValueFactory<Facture, Double>("montant"));
		montantPayColumn.setCellValueFactory(new PropertyValueFactory<Facture, Double>("montantPaye"));
		payeColumn.setCellValueFactory(new PropertyValueFactory<Facture, Boolean>("paye"));
		tableFactures.setItems(FXCollections.observableArrayList(factureMetier.getAllFactures()));
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
	private void handleNewFacture() {
		System.out.println("handle facture");
		Facture f = new Facture();
		mainApp.showFactureEditDialog(f);
		tableFactures.setItems(FXCollections.observableArrayList(factureMetier.getAllFactures()));
	}

	@FXML
	public void chercherFacture() {
//		System.out.println("");
//		String mc = txtSearch.getText();
//		tableFactures.setItems(FXCollections.observableArrayList(factureMetier.getFactureByMC(mc)));
		AbstractCommonCriteria abc = this.createAbastractCommonCriteria();
		tableFactures.setItems(FXCollections.observableArrayList(factureMetier.getFactureCriteria(abc)));

	}

	@FXML
	private void voirFacture() {
		Facture selectedFacture = tableFactures.getSelectionModel().getSelectedItem();
		// f = factureMetier.getFactureById(f.getId());
		if (selectedFacture != null) {
			int idFacture = selectedFacture.getId();
			List<BonDeLivraison> bons = new ArrayList<BonDeLivraison>(0);
			bons.addAll(factureMetier.getBonsDesLivraisons(idFacture));
			List<Panier> paniers = new ArrayList<Panier>(0);
			paniers.addAll(factureMetier.getFacturePaniers(idFacture));
			List<Tranche> tranches = new ArrayList<Tranche>(0);
			tranches.addAll(factureMetier.getFactureTranches(idFacture));
			selectedFacture.setBonsDeLivraisons(bons);
			selectedFacture.setProduitsAchetes(paniers);
			selectedFacture.setTranches(tranches);
			mainApp.showFactureEditDialog(selectedFacture);
			tableFactures.setItems(FXCollections.observableArrayList(factureMetier.getAllFactures()));
		}
	}

	@FXML
	public void supprimerFacture() {
		Facture f = tableFactures.getSelectionModel().getSelectedItem();
		//f = factureMetier.getFactureById(f.getId());
		if(f !=null) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Validation");
		//alert.setHeaderText("Etes-vous sur?");
		String produits="";
		f.setProduitsAchetes(factureMetier.getFacturePaniers(f.getId()));
		for(Panier p: f.getProduitsAchetes()) produits ="["+p.getProduit().getDesignation()+": "+p.getQuantite()+"]\n";
		alert.setHeaderText("Voulez-vous supprimer la facture dont l'identifiant est "+f.getId()+" ?\n"
				+"[Client: "+f.getNomClient()+" "+f.getPrenomClient()+"]\n"
				+"[Tel client: "+f.getTelClient()+"]\n"
				+"[montant: "+f.getMontant()+"]\n"
				+"[payé: "+f.isPaye()+"]\n"+produits);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			factureMetier.supprimerFacture(f);
			tableFactures.getItems().remove(f);

		}

		}

	}
	@FXML
	public void imprimerFacture() {
		Facture selectedFacture = tableFactures.getSelectionModel().getSelectedItem();
		if (selectedFacture.getId() != 0) {
			selectedFacture.setProduitsAchetes(factureMetier.getFacturePaniers(selectedFacture.getId()));

			boolean gros = (selectedFacture.getProduitsAchetes().get(0).getProduit().getPrixGros() * selectedFacture.getProduitsAchetes().get(0).getQuantite()) == selectedFacture.getProduitsAchetes().get(0).getTotal();
			mainApp.printerWindow(selectedFacture,gros);
		}
	}

	@FXML
	public void genererBon() {
		Facture selectedFacture = tableFactures.getSelectionModel().getSelectedItem();
		if(selectedFacture != null) {
			// il faut récupérer les bons des livraisons, les tranches et les paniers
			int idFacture = selectedFacture.getId();
			List<BonDeLivraison> bons = new ArrayList<BonDeLivraison>(0);
			bons.addAll(factureMetier.getBonsDesLivraisons(idFacture));
			List<Panier> paniers = new ArrayList<Panier>(0);
			paniers.addAll(factureMetier.getFacturePaniers(idFacture));
			List<Tranche> tranches = new ArrayList<Tranche>(0);
			tranches.addAll(factureMetier.getFactureTranches(idFacture));
			selectedFacture.setBonsDeLivraisons(bons);
			selectedFacture.setProduitsAchetes(paniers);
			selectedFacture.setTranches(tranches);
			mainApp.showPanierToBon(selectedFacture);
		}
	}

	private AbstractCommonCriteria createAbastractCommonCriteria() {
		AbstractCommonCriteria abc = new AbstractCommonCriteria();
		if(txtSearch.getText() != null && !txtSearch.getText().isEmpty())
		abc.setClient(txtSearch.getText());
		if (txtDateDebut.getValue() != null && txtDateFin.getValue() != null) {
			LocalDate localDate = txtDateDebut.getValue();
			Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
			Date dateDebut = Date.from(instant);
			abc.setDateDebut(dateDebut);
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
		if(selectedItem!=0) {
			if(selectedItem == 1) {
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

}
