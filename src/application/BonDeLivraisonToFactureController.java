package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.text.TabExpander;

import application.dao.AbstractCommonCriteria;
import application.metier.BonDeLivraisonMetierImpl;
import application.metier.BonDeLivraisonMetierInterface;
import application.metier.FactureMetierImpl;
import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Panier;
import application.model.Tranche;
import javafx.beans.property.SimpleDoubleProperty;
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

public class BonDeLivraisonToFactureController {

	private Main mainApp;
	private BonDeLivraisonMetierInterface bonDeLivraisonMetier;
	private FactureMetierImpl factureMetier;

	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtTel;

	@FXML
	private TextField searchTxt;

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
	private TableColumn<BonDeLivraison, Double> montatntColumn;

	/*Panier*/
	@FXML
	private TableView<Panier> tablePanier;
	@FXML
	private TableColumn<Panier, String> desigPanierColumn;

	@FXML
	private TableColumn<Panier, Integer> qtePanierColumn;

	@FXML
	private TableColumn<Panier, Double> prixUnitPanierColumn;

	@FXML
	private TableColumn<Panier, Double> prixTotalPanierColumn;

	@FXML
	private TableView<BonDeLivraison> tablePanierFacture;
	@FXML
	private TableColumn<BonDeLivraison, Integer> idBonLivrColumn;
	@FXML
	private TableColumn<BonDeLivraison, Double> montantCoumn;

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void initialize() {
		bonDeLivraisonMetier = new BonDeLivraisonMetierImpl();
		idBon.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Integer>("id"));
		clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()+" "+cellData.getValue().getPrenomClient()));
		telColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, String>("telClient"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Date>("date"));
		montatntColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("montant"));
		payeColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Boolean>("paye"));
		tableBons.setItems(FXCollections.observableArrayList(bonDeLivraisonMetier.getBonDeLivraisonSansFacture()));

		/*initialisation table panier*/
		desigPanierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getDesignation()));
		qtePanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Integer>("quantite"));
		prixUnitPanierColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
		prixTotalPanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Double>("total"));


		/*Livraison inclus*/
		montantCoumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("montant"));
		idBonLivrColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Integer>("id"));
		factureMetier = new FactureMetierImpl();

		tableBons.setOnMouseClicked(e -> showAchat());
		tablePanierFacture.setOnMouseClicked(e -> showAchat());
	}

	public void ajouterBon() {
		BonDeLivraison selectedBon = tableBons.getSelectionModel().getSelectedItem();
		if(selectedBon != null) {
			tableBons.getItems().remove(selectedBon);
			tablePanierFacture.getItems().add(selectedBon);
		}
	}

	public void retirerPanier() {
		BonDeLivraison selectedBon = tablePanierFacture.getSelectionModel().getSelectedItem();
		if(selectedBon != null) {
			tableBons.getItems().add(selectedBon);
//			tablePanier.getItems().removeAll(selectedBon.getProduitsAchetes());
			tablePanierFacture.getItems().remove(selectedBon);
		}
	}

	public BonDeLivraisonMetierInterface getBonDeLivraisonMetier() {
		return bonDeLivraisonMetier;
	}

	public void setBonDeLivraisonMetier(BonDeLivraisonMetierInterface bonDeLivraisonMetier) {
		this.bonDeLivraisonMetier = bonDeLivraisonMetier;
	}

	@FXML
	public void suavegarderFacture() {
		Facture f = new Facture();
		f.setNomClient(txtNom.getText());
		f.setPrenomClient(txtPrenom.getText());
		f.setTelClient(txtTel.getText());
		double montantPaye = 0;
		double montant = 0;
		double remise = 0;
		for(BonDeLivraison bon: tablePanierFacture.getItems()) {
			f.getProduitsAchetes().addAll(bon.getProduitsAchetes());
			f.getTranches().addAll(bon.getTranches());
			montant += bon.getMontant();
			montantPaye += bon.getMontantPaye();
			remise += bon.getRemise();
		}
		f.setBonsDeLivraisons(tablePanierFacture.getItems());
		f.setMontant(montant);
		f.setMontantPaye(montantPaye);
		factureMetier.enregistrerBonDeLivraisonToFacture(f);
		if (f.getId() != 0) {
			//succés
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Info!");
			alert.setHeaderText("La facture a été enregistrée");
			Optional<ButtonType> result = alert.showAndWait();
		}

	}

	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	private void showAchat() {
		BonDeLivraison selectedBon = tableBons.getSelectionModel().getSelectedItem();
		if(selectedBon != null) {
			List<Panier> paniers = new ArrayList<Panier>(0);
			List<Tranche> tranches = new ArrayList<Tranche>(0);
			paniers.addAll(bonDeLivraisonMetier.getBonDeLivraisonPaniers(selectedBon.getId()));
			tranches.addAll(bonDeLivraisonMetier.getBonDeLivraisonTranches(selectedBon.getId()));
			selectedBon.setProduitsAchetes(paniers);
			selectedBon.setTranches(tranches);
			tablePanier.setItems(FXCollections.observableArrayList(paniers));
		}
	}

	@Deprecated
	private void showAchatBon() {
		BonDeLivraison selectedBon = tablePanierFacture.getSelectionModel().getSelectedItem();
		if(selectedBon != null) {
			tablePanier.setItems(FXCollections.observableArrayList(selectedBon.getProduitsAchetes()));
		}
	}

	@FXML
	private void chercherBons() {
		AbstractCommonCriteria abc = new AbstractCommonCriteria();
		String search = searchTxt.getText();
		if (search != null) {
			abc.setClient(search);
			tableBons.setItems(FXCollections.observableArrayList(bonDeLivraisonMetier.getAllBonsByCriteria(abc)));
		}
	}

	@FXML
	private void voirTranche() {
	BonDeLivraison selectedBon = tableBons.getSelectionModel().getSelectedItem();
	if(selectedBon != null) {
		final List<Tranche> lesTranches = bonDeLivraisonMetier.getBonDeLivraisonTranches(selectedBon.getId());
		mainApp.voirTanches(lesTranches);
	}
	}

	@FXML
	private void voirPaiement() {
		final List<Tranche> lesTranches = new ArrayList<>(0);
		if(tablePanierFacture.getItems() != null && !tablePanierFacture.getItems().isEmpty()) {
			for(BonDeLivraison currentBon : tablePanierFacture.getItems()) {
				lesTranches.addAll(bonDeLivraisonMetier.getBonDeLivraisonTranches(currentBon.getId()));
			}
		}
		mainApp.voirTanches(lesTranches);
	}
}
