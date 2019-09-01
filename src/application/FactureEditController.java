package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.metier.FactureMetierImpl;
import application.metier.FactureMetierInterface;
import application.model.Facture;
import application.model.Panier;
import application.model.Produit;
import application.model.Tranche;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FactureEditController {

	private ProduitDao produitDao;
	private FactureMetierInterface factureMetier;
	private Facture facture;
	private List<Panier> paniersList;
	private List<Tranche> tranches;
	private Main mainApp;

	@FXML
	private AnchorPane pane1;
	@FXML
	private AnchorPane pane2;
	/* Facture */
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtTel;
	@FXML
	private TextField txtTotal;
	@FXML
	private TextField txtAvance;
	@FXML
	private TextField txtMontantPaye;
	@FXML
	private TextField txtMontantTranche;
	@FXML
	private TextField txtRemise;
	@FXML
	private TextField txtCredit;
	@FXML
	private TextField txtDirection;
	@FXML
	private TextField txtMatricule;
	@FXML
	private TextField txtChauffeur;

	/* Fin Facture */

	/* Produit */
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

	/* Fin Panier */
	/* Tranche */
	@FXML
	private TableView<Tranche> tableTranches;
	@FXML
	private TableColumn<Tranche, Double> montantTrancheCln;
	@FXML
	private TableColumn<Tranche, Date> dateTrancheCln;

	/* Tranche */

	@FXML
	private Button btnSaveFacture;

	private int showSlide;

	public void initialize() {
		translateAnimation(0.01, pane2, 2000);
		designationColumn.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
		prixColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
		prixGrosColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixGros"));
		qteColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("dateAjout"));
		this.produitDao = new ProduitDaoImpl();
		tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduit()));
		showSlide = 0;
		/* initialisation table panier */
		desigPanierColumn.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getDesignation()));
		qtePanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Integer>("quantite"));
		prixUnitPanierColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
		prixTotalPanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Double>("total"));

		factureMetier = new FactureMetierImpl();
		txtAvance.textProperty().addListener((observale, oldValue, newValue) -> {
			txtMontantPaye.setText(calulerTotalPaye() + "");
		});

		txtRemise.textProperty().addListener((observale, oldValue, newValue) -> {
			txtMontantPaye.setText(calulerTotalPaye() + "");
			txtCredit.setText(this.calculerCredit() + "");
		});
		/* initialisation de table tranches */
		montantTrancheCln.setCellValueFactory(new PropertyValueFactory<Tranche, Double>("montant"));
		dateTrancheCln.setCellValueFactory(new PropertyValueFactory<Tranche, Date>("date"));
		/*----*/

	}

	public void translateAnimation(double duration, Node node, double byX) {
		TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
		translateTransition.setByX(byX);
		translateTransition.play();
	}

	@FXML
	public void back(ActionEvent event) {
		System.out.println("back");
		if (showSlide == 1) {
			translateAnimation(0.5, pane2, 2000);
			translateAnimation(0.5, pane1, 2000);
			showSlide--;
		}
	}

	@FXML
	public void next(ActionEvent event) {
		System.out.println("next");
		if (showSlide == 0) {
			txtTotal.setText(totalLbl.getText());
			translateAnimation(0.5, pane1, -2000);
			translateAnimation(0.5, pane2, -2000);
			showSlide++;
		}
	}

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
			p.setFacture(facture);
			p.setQuantite(1);
			if (grosCB.isSelected()) {
				p.calculerTotalGros();
			} else {
				p.calculerTotal();
			}
			tablePanier.getItems().add(p);
			if (this.facture.getId() != 0) {
				factureMetier.ajouterPanier(p);
			}
			paniersList.add(p);
			totalLbl.setText(calculerTotalFacture() + "");
			txtCredit.setText(this.calculerCredit() + "");
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
			if (this.facture.getId() != 0) {
				factureMetier.supprimerPanier(selectedPanier);
			}
			tableProduit.refresh();
			totalLbl.setText(calculerTotalFacture() + "");
			tablePanier.getItems().remove(selectedPanier);
			paniersList.remove(selectedPanier);
			tablePanier.refresh();
			totalLbl.setText(calculerTotalFacture() + "");
			txtCredit.setText(this.calculerCredit() + "");

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
			} else {
				selectedPanier.setQuantite(qtePanier);
				selectedPanier.calculerTotal();
			}
			tablePanier.refresh();
			totalLbl.setText(calculerTotalFacture() + "");
			txtCredit.setText(this.calculerCredit() + "");

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
		} else {
			prixUnitPanierColumn.setCellValueFactory(
					cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
			for (Panier p : paniersList) {
				p.calculerTotal();
			}
			tablePanier.refresh();
		}
		totalLbl.setText(calculerTotalFacture() + "");
		txtTotal.setText(calculerTotalFacture() + "");
		txtCredit.setText(this.calculerCredit() + "");
	}

	@FXML
	public void saveFacture() {
		double avance = 0;
		double montant = 0;
		double montantPaye = 0;
		try {

			avance = Double.parseDouble(txtAvance.getText());
			montant = Double.parseDouble(txtTotal.getText());
			montantPaye = Double.parseDouble(txtMontantPaye.getText());
			facture.setNomClient(txtNom.getText());
			facture.setPrenomClient(txtPrenom.getText());
			facture.setTelClient(txtTel.getText());
			facture.setAvance(avance);
			facture.setProduitsAchetes(paniersList);
			facture.setTranches(tranches);
			facture.setMontant(montant);
			facture.setMontantPaye(montantPaye);
			facture.setPaye(facture.getMontantPaye() == facture.getMontant());
			facture.setDate(new Date());
			this.facture.setRemise(Double.parseDouble(txtRemise.getText()));
			if (txtMatricule.getText() != null)
				facture.setMatricule(txtMatricule.getText());
			if (txtChauffeur.getText() != null)
				facture.setChauffeur(txtChauffeur.getText());
			if (txtDirection.getText() != null)
				facture.setDirection(txtDirection.getText());
			if (facture.getId() == 0) {
				factureMetier.enregistrerFacture(facture);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Facture sauvegardé");
				alert.setHeaderText("La facture a été enregistré.");
				Optional<ButtonType> result = alert.showAndWait();
			} else {
				factureMetier.updateFacture(facture, null);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Facture Modifié");
				alert.setHeaderText("La facture a été modifiée correctement.");
				Optional<ButtonType> result = alert.showAndWait();
			}
		} finally {

		}

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
		txtNom.setText("");
		if (facture.getNomClient() != null)
			txtNom.setText(facture.getNomClient());
		txtPrenom.setText("");
		if (facture.getPrenomClient() != null)
			txtNom.setText(facture.getPrenomClient());
		txtChauffeur.setText("");
		if (facture.getChauffeur() != null)
			txtNom.setText(facture.getChauffeur());
		txtDirection.setText("");
		if (facture.getDirection() != null)
			txtNom.setText(facture.getDirection());
		if (facture.getProduitsAchetes().isEmpty()) {
			paniersList = new ArrayList<Panier>();
		} else {
			paniersList = facture.getProduitsAchetes();
		}
		if (facture.getTranches().isEmpty()) {
			tranches = new ArrayList<Tranche>();
		} else {
			tranches = facture.getTranches();
		}
		tableTranches.setItems(FXCollections.observableArrayList(tranches));
		tablePanier.setItems(FXCollections.observableArrayList(paniersList));
		for (Panier p : facture.getProduitsAchetes()) {
			tableProduit.getItems().remove(p.getProduit());
		}
		/* info facture */
		txtNom.setText(facture.getNomClient());
		txtPrenom.setText(facture.getPrenomClient());
		txtTel.setText(facture.getTelClient());
		txtAvance.setText(facture.getAvance() + "");
		txtMontantPaye.setText(facture.getMontantPaye() + "");
		txtTotal.setText(facture.getMontant() + "");
		totalLbl.setText(facture.getMontant() + "");
		txtCredit.setText(facture.getCredit() + "");
		txtRemise.setText(facture.getRemise() + "");
		txtChauffeur.setText(facture.getChauffeur());
		if (facture.getDirection() != null)
			txtDirection.setText(facture.getDirection());
		txtMatricule.setText(facture.getMatricule());
		if (this.facture.getId() != 0) {
			txtCredit.setText(this.facture.getCredit() + "");
			if (this.facture.isGros()) {
				grosCB.setSelected(true);
				prixUnitPanierColumn.setCellValueFactory(
						cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrixGros())
								.asObject());
				tablePanier.refresh();
			}
		}
	}

	private double calculerTotalFacture() {
		double somme = 0;
		for (Panier p : tablePanier.getItems()) {
			somme += p.getTotal();
		}
		return somme;
	}

	private double calulerTotalPaye() {
		double somme = Double.parseDouble(txtAvance.getText());
		if (facture.getTranches() != null) {
			for (Tranche t : facture.getTranches()) {
				somme += t.getMontant();
			}
		}
		return somme;
	}

	@FXML
	public void ajouterTranche() {
		if (txtMontantTranche.getText() != null) {
			double montantTranche = Double.parseDouble(txtMontantTranche.getText());
			Tranche t = new Tranche();
			t.setDate(new Date());
			t.setMontant(montantTranche);
			t.setFacture(this.facture);
			tranches.add(t);
			facture.setTranches(tranches);
			if (this.facture.getId() != 0) {
				factureMetier.ajouterTranche(t);
			}
			tableTranches.setItems(FXCollections.observableArrayList(tranches));
			txtMontantPaye.setText(calulerTotalPaye() + "");
			txtCredit.setText(this.calculerCredit() + "");

		}
	}

	@FXML
	public void supprimerTranche() {
		Tranche t = tableTranches.getSelectionModel().getSelectedItem();
		if (t != null) {
			tranches.remove(t);
			if (this.facture.getId() != 0) {
				factureMetier.supprimerTranche(t.getId());
			}
			tableTranches.setItems(FXCollections.observableArrayList(tranches));
		}
		txtMontantPaye.setText(calulerTotalPaye() + "");

	}

	@FXML
	public void imprimerFacture() {
		if (facture.getId() != 0) {
			mainApp.printerWindow(facture, grosCB.isSelected());
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Attention!");
			alert.setHeaderText("Vous devez sauvegarder la facture avant de l'imprimer.");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	private double calculerCredit() {
		double remise = 0;
		if (txtRemise.getText().length() > 0)
			remise = Double.parseDouble(txtRemise.getText());
		double avance = Double.parseDouble(txtAvance.getText());
		double credit = Double.parseDouble(txtTotal.getText());
		credit -= remise;
		credit -= avance;
		for (Tranche t : this.facture.getTranches()) {
			credit -= t.getMontant();
		}
		return credit;
	}
}
