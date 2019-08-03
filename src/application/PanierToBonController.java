package application;

import java.util.Date;

import application.dao.ProduitDaoImpl;
import application.metier.BonDeLivraisonMetierImpl;
import application.metier.BonDeLivraisonMetierInterface;
import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Panier;
import application.model.Produit;
import application.model.Tranche;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PanierToBonController {

	private Main mainApp;
	private Facture facture;
	private BonDeLivraisonMetierInterface bonMetier;
	boolean gros;

	@FXML
	private TextField txtQte;

	/*Panier 0 */
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

	/*Panier 1 */
	@FXML
	private TableView<Panier> tablePanier1;
	@FXML
	private TableColumn<Panier, String> desigPanierColumn1;

	@FXML
	private TableColumn<Panier, Integer> qtePanierColumn1;

	@FXML
	private TableColumn<Panier, Double> prixUnitPanierColumn1;

	@FXML
	private TableColumn<Panier, Double> prixTotalPanierColumn1;

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

	public void initialize() {


		/*initialisation table panier*/
		desigPanierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getDesignation()));
		qtePanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Integer>("quantite"));
		prixUnitPanierColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
		prixTotalPanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Double>("total"));

		desigPanierColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getDesignation()));
		qtePanierColumn1.setCellValueFactory(new PropertyValueFactory<Panier, Integer>("quantite"));
		prixUnitPanierColumn1.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
		prixTotalPanierColumn1.setCellValueFactory(new PropertyValueFactory<Panier, Double>("total"));
		bonMetier = new BonDeLivraisonMetierImpl();

		idBon.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Integer>("id"));
		clientColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomClient()+" "+cellData.getValue().getPrenomClient()));
		telColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, String>("telClient"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Date>("date"));
		avanceColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("avance"));
		montatntColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("montant"));
		montantPayColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Double>("montantPaye"));
		payeColumn.setCellValueFactory(new PropertyValueFactory<BonDeLivraison, Boolean>("paye"));


	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
		this.tablePanier.setItems(FXCollections.observableArrayList(facture.getProduitsAchetes()));
		this.tableBons.setItems(FXCollections.observableArrayList(facture.getBonsDeLivraisons()));
	}

	@FXML
	public void ajouter() {
		Panier selectedPanier = tablePanier.getSelectionModel().getSelectedItem();
		if(selectedPanier!=null) {
			this.gros = selectedPanier.getProduit().getPrixGros() * selectedPanier.getQuantite() == selectedPanier.getTotal();
			int q = selectedPanier.getQuantite();
			if(!txtQte.getText().equals(""))
			q = Integer.parseInt(txtQte.getText());
			Panier p = new Panier();
			p.setQuantite(q);
			p.setProduit(selectedPanier.getProduit());
			if(gros) {
				p.setTotal(q * p.getProduit().getPrixGros());
			} else {
				p.setTotal(q * p.getProduit().getPrix());
			}
			tablePanier1.getItems().add(p);
			if(p.getQuantite() == selectedPanier.getQuantite()) {
				tablePanier.getItems().remove(selectedPanier);
			}
		}
	}
	@FXML
	public void imprimer() {
		BonDeLivraison selectedBon = tableBons.getSelectionModel().getSelectedItem();
		if(selectedBon != null)
		mainApp.printerWindow(selectedBon, this.gros);
	}

	@FXML
	public void genererBon() {
		BonDeLivraison bon = new BonDeLivraison();
		bon.setNomClient(facture.getNomClient());
		bon.setPrenomClient(facture.getPrenomClient());
		bon.setTelClient(facture.getTelClient());
		bon.setDate(new Date());
		bon.setFacture(facture);
		bon.setPaye(true);
		bon.setProduitsAchetes(tablePanier1.getItems());
		double montant = 0;
		for(Panier p:bon.getProduitsAchetes()) {
			montant += p.getTotal();
		}
		bon.setMontantPaye(montant);
		bon.setMontant(montant);
		bonMetier.enregistrerBonDeLivraisonSansModStock(bon);
	}

}
