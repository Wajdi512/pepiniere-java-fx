package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.metier.CommandeMetierImpl;
import application.metier.CommandeMetierInterface;
import application.metier.DevisMetierImpl;
import application.metier.DevisMetierInterface;
import application.model.Commande;
import application.model.Devis;
import application.model.Panier;
import application.model.Produit;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class DevisEditController {


	private Main mainApp;
	private Devis devis;
	private ProduitDao produitDao;
	private List<Panier> paniersList;
	private DevisMetierInterface devisMetier;

	/* Facture */
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtTel;
	@FXML
	private TextField txtRemise;

	@FXML
	private TextField txtSearch;
	@FXML
	private TableView<Produit> tableProduit;
	@FXML
	private TableColumn<Produit, String> designationColumn;

	@FXML
	private TableColumn<Produit, Double> prixColumn;

	@FXML
	private TableColumn<Produit, Double> prixGrosColumn;

	@FXML
	private TableColumn<Produit, Integer> qteColumn;

	@FXML
	private TableColumn<Produit, Integer> dateColumn;
	/* Fin produit */

	/* Panier */
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
	private TextField txtQtePanier;
	@FXML
	private CheckBox grosCB;
	@FXML
	private Label totalLbl;

	public void initialize() {
		paniersList = new ArrayList<Panier>();
		designationColumn.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
		prixColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
		prixGrosColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixGros"));
		qteColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("dateAjout"));
		this.produitDao = new ProduitDaoImpl();
		tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduit()));

		/* initialisation table panier */
		desigPanierColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getDesignation()));
		qtePanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Integer>("quantite"));
		prixUnitPanierColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
		prixTotalPanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Double>("total"));
		devisMetier = new DevisMetierImpl();

		txtRemise.textProperty().addListener((observale, oldValue, newValue) -> {
				this.totalLbl.setText(this.calculerTotalDevis()+"");
			});

	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Devis getDevis() {
		return devis;
	}

	public void setDevis(Devis devis) {
		this.devis = devis;
		txtNom.setText(devis.getNomClient());
		txtPrenom.setText(devis.getPrenomClient());
		txtTel.setText(devis.getTelClient());
		if(devis.getProduitsAchetes() != null && !devis.getProduitsAchetes().isEmpty())
		tablePanier.setItems(FXCollections.observableArrayList(devis.getProduitsAchetes()));
		totalLbl.setText(devis.getMontant() + "");
		txtRemise.textProperty().addListener((observale, oldValue, newValue) -> {
			totalLbl.setText(this.calculerTotalDevis()+"");
		});	}

	@FXML
	public void chercherProduit() {
		String mc = txtSearch.getText();
		tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduitByMC(mc)));
	}

	@FXML
	public void ajouterProduit() {
		Produit selectedProduct = tableProduit.getSelectionModel().getSelectedItem();
		if (selectedProduct != null) {
			Panier p = new Panier();
			p.setProduit(selectedProduct);
			p.setDevis(devis);;
			p.setQuantite(1);
			if (grosCB.isSelected()) {
				p.calculerTotalGros();
			} else {
				p.calculerTotal();
			}
			tablePanier.getItems().add(p);
			paniersList.add(p);
			devis.setProduitsAchetes(tablePanier.getItems());
			totalLbl.setText(devis.calculerTotalDevis() + "");
			tableProduit.getItems().remove(selectedProduct);
			tableProduit.refresh();

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerte");
			alert.setHeaderText("Veuillez sélectionner un produit!");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	@FXML
	public void supprimerProduit() {
		Panier selectedPanier = tablePanier.getSelectionModel().getSelectedItem();
		if (selectedPanier != null) {

			tableProduit.getItems().add(selectedPanier.getProduit());
			tableProduit.refresh();
			tablePanier.getItems().remove(selectedPanier);
			devis.setProduitsAchetes(tablePanier.getItems());
			if (grosCB.isSelected()) {
				totalLbl.setText(devis.calculerTotalDevisGros() + "");
			} else {
				totalLbl.setText(devis.calculerTotalDevis() + "");
			}
			tablePanier.refresh();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerte");
			alert.setHeaderText("Veuillez sélectionner un produit acheté!");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	@FXML
	public void validerQte() {
		Panier selectedPanier = tablePanier.getSelectionModel().getSelectedItem();
		if (selectedPanier != null) {
			int qtePanier = Integer.parseInt(txtQtePanier.getText());
			if (grosCB.isSelected()) {
				selectedPanier.setQuantite(qtePanier);
				selectedPanier.calculerTotalGros();
				totalLbl.setText(devis.calculerTotalDevisGros() + "");
			} else {
				selectedPanier.setQuantite(qtePanier);
				selectedPanier.calculerTotal();
				totalLbl.setText(devis.calculerTotalDevis() + "");
			}
			tablePanier.refresh();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerte");
			alert.setHeaderText("Veuillez sélectionner un Achat!");
			Optional<ButtonType> result = alert.showAndWait();
		}

	}

	@FXML
	public void changerAchats() {

		if (grosCB.isSelected()) {
			prixUnitPanierColumn.setCellValueFactory(
					cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrixGros()).asObject());
			for (Panier p : paniersList) {
				p.calculerTotalGros();
			}
			tablePanier.refresh();
			devis.calculerTotalDevisGros();

		} else {
			prixUnitPanierColumn.setCellValueFactory(
					cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
			for (Panier p : paniersList) {
				p.calculerTotal();
			}
			tablePanier.refresh();
			devis.calculerTotalDevis();

		}
		totalLbl.setText(devis.calculerTotalDevis() + "");
	}

	@FXML
	public void saveCommande() {
		if (devis.getId() == 0) {
			devis.setNomClient(txtNom.getText());
			devis.setPrenomClient(txtPrenom.getText());
			devis.setTelClient(txtTel.getText() + "");
			devis.setDate(new Date());
			devis.setMontant(Double.parseDouble(totalLbl.getText()));
			devis.setProduitsAchetes(tablePanier.getItems());
			devisMetier.addDevis(devis);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Devis");
			alert.setHeaderText("le devis a été enrgistré");
			Optional<ButtonType> result = alert.showAndWait();
		} else {
			devis.setNomClient(txtNom.getText());
			devis.setPrenomClient(txtPrenom.getText());
			devis.setTelClient(txtTel.getText() + "");
			devis.setDate(new Date());
			devis.setMontant(Double.parseDouble(totalLbl.getText()));
			devis.setProduitsAchetes(tablePanier.getItems());
			devisMetier.addDevis(devis);
			devisMetier.updateDevis(devis);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Devis");
			alert.setHeaderText("le devis a été modifié");
			Optional<ButtonType> result = alert.showAndWait();
		}

	}

	@FXML
	public void imprimerCommande() {
		if (devis.getId() != 0) {
			mainApp.printerWindow(devis,grosCB.isSelected());
		}
	}

	private double calculerTotalDevis() {
		double remise = 0;
		if(txtRemise.getText().length()>0) remise = Double.parseDouble(txtRemise.getText());
		double total = 0;
		total -= remise;
		for(Panier p:tablePanier.getItems()) {
			total += p.getTotal();
		}
	return total;
	}

}
