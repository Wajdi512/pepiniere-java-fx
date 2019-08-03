package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.dao.AbstractCommonCriteria;
import application.dao.BonDeLivraisonDaoImpl;
import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.metier.BonDeLivraisonMetierImpl;
import application.metier.BonDeLivraisonMetierInterface;
import application.metier.FactureMetierImpl;
import application.model.BonDeLivraison;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class BonDeLivraisonEditController {
	private ProduitDao produitDao;
	private List<Panier> paniersList;
	private BonDeLivraison bon;
	private BonDeLivraison copieBon;
	private List<Tranche> tranches;
	private BonDeLivraisonMetierInterface bonMetier;
	private Main mainApp;


	@FXML
	private AnchorPane pane1;
	@FXML
	private AnchorPane pane2;
	/*Facture*/
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
	private TextField txtRemise;
	@FXML
	private TextField txtCredit;
	@FXML
	private TextField txtMontantPaye;
	@FXML
	private TextField txtMontantTranche;
	@FXML
	private TextField txtDirection;
	@FXML
	private TextField txtMatricule;
	@FXML
	private TextField txtChauffeur;
	/*Fin Facture*/

	/*Produit*/
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
	/*Fin produit*/

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
	private TextField txtQtePanier;
	@FXML
	private CheckBox grosCB;
	@FXML
	private Label totalLbl;

	/*Fin Panier*/
	/*Tranche*/
	@FXML
	private TableView<Tranche> tableTranches;
	@FXML
	private TableColumn<Tranche, Double> montantTrancheCln;
	@FXML
	private TableColumn<Tranche, Date> dateTrancheCln;

	@FXML
	private Button btnSaveBon;
	/*Tranche*/
	private int showSlide;


	public BonDeLivraison getBon() {
		return bon;
	}

	public void setBon(BonDeLivraison bon) {
		this.bon = bon;
		txtTotal.setText(bon.getMontant() + "");
		paniersList = bon.getProduitsAchetes();
		tranches = bon.getTranches();
		txtNom.setText(bon.getNomClient());
		txtPrenom.setText(bon.getPrenomClient());
		txtTel.setText(bon.getTelClient());
		tablePanier.setItems(FXCollections.observableArrayList(bon.getProduitsAchetes()));
		tableTranches.setItems(FXCollections.observableArrayList(bon.getTranches()));
		totalLbl.setText(bon.getMontant() + "");
		txtAvance.setText(bon.getAvance() + "");
		txtMontantPaye.setText(bon.getMontantPaye() + "");
		txtCredit.setText(0+"");
		txtRemise.setText(bon.getRemise()+"");
		txtChauffeur.setText(bon.getChauffeur());
		txtDirection.setText(bon.getDirection());
		txtMatricule.setText(bon.getMatricule());
		if (this.bon.getId() != 0) {
			txtCredit.setText(this.bon.getCredit() + "");
			if (this.bon.isGros()) {
				grosCB.setSelected(true);
				prixUnitPanierColumn.setCellValueFactory(
						cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrixGros())
								.asObject());
				tablePanier.refresh();
			}
		}
	}




	public void initialize() {

		translateAnimation(0.01, pane2, 2000);
		bonMetier = new BonDeLivraisonMetierImpl();
		designationColumn.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
		prixColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
		prixGrosColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixGros"));
		qteColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("dateAjout"));
		this.produitDao = new ProduitDaoImpl();
		tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduit()));
		showSlide = 0;
		txtNom.setText("");
		txtPrenom.setText("");
		txtChauffeur.setText("");
		txtDirection.setText("");
		/*initialisation table panier*/
		desigPanierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduit().getDesignation()));
		qtePanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Integer>("quantite"));

		prixUnitPanierColumn.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());

		prixTotalPanierColumn.setCellValueFactory(new PropertyValueFactory<Panier, Double>("total"));

		txtAvance.textProperty().addListener((observale, oldValue, newValue) -> {
			txtMontantPaye.setText(calulerTotalPaye() + "");
			txtCredit.setText(this.calculerCredit()+"");
		});
		txtRemise.textProperty().addListener((observale, oldValue, newValue) -> {
			txtMontantPaye.setText(calulerTotalPaye() + "");
			txtCredit.setText(this.calculerCredit()+"");
		});

		/* initialisation de table tranches*/
		montantTrancheCln.setCellValueFactory(new PropertyValueFactory<Tranche, Double>("montant"));
		dateTrancheCln.setCellValueFactory(new PropertyValueFactory<Tranche, Date>("date"));

	}

	public void translateAnimation(double duration, Node node, double byX){
		TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
		translateTransition.setByX(byX);
		translateTransition.play();
	}


	@FXML
	public void chercherProduit(){
	String mc = txtSearch.getText();
	tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduitByMC(mc)));
	}
	@FXML
	public void validerQte() {
		Panier selectedPanier = tablePanier.getSelectionModel().getSelectedItem();
		if (selectedPanier != null) {
			if(selectedPanier.getId() == 0) {
				int qtePanier = Integer.parseInt(txtQtePanier.getText());
				if (grosCB.isSelected()) {
					selectedPanier.setQuantite(qtePanier);
					selectedPanier.calculerTotalGros();
				} else {
					selectedPanier.setQuantite(qtePanier);
					selectedPanier.calculerTotal();
				}
				tablePanier.refresh();
				totalLbl.setText(calculerTotalBon()+"");
			}
			else {
				int qtePanier = Integer.parseInt(txtQtePanier.getText());
				if (grosCB.isSelected()) {
					selectedPanier.setQuantite(qtePanier);
					selectedPanier.calculerTotalGros();
				} else {
					selectedPanier.setQuantite(qtePanier);
					selectedPanier.calculerTotal();
				}
				bonMetier.updatePanier(selectedPanier);
				tablePanier.refresh();
				totalLbl.setText(calculerTotalBon()+"");
				txtCredit.setText(this.calculerCredit()+"");

			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerte");
			alert.setHeaderText("Veuillez sélectionner un Achat!");
			Optional<ButtonType> result = alert.showAndWait();
		}

	}

	@FXML
	public void ajouterProduit(){
		Produit selectedProduct = tableProduit.getSelectionModel().getSelectedItem();
		if(selectedProduct != null) {
			Panier p = new Panier();
			p.setProduit(selectedProduct);
			p.setBon(bon);
			p.setQuantite(1);
			if(grosCB.isSelected()){
				p.calculerTotalGros();
			}else{
				p.calculerTotal();
			}
			if (this.bon.getId() != 0) {
				bonMetier.ajouterPanier(p);
			}
			tablePanier.getItems().add(p);
			paniersList.add(p);
			totalLbl.setText(calculerTotalBon()+"");
			txtCredit.setText(this.calculerCredit()+"");
			tableProduit.getItems().remove(selectedProduct);
			tableProduit.refresh();

		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerte");
			alert.setHeaderText("Veuillez sélectionner un produit!");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	@FXML
	public void supprimerProduit(){
		Panier selectedPanier = tablePanier.getSelectionModel().getSelectedItem();
		if (selectedPanier != null) {

			tableProduit.getItems().add(selectedPanier.getProduit());
			if(this.bon.getId()!=0) bonMetier.supprimerPanier(selectedPanier);
			tableProduit.refresh();
			tablePanier.getItems().remove(selectedPanier);
			paniersList.remove(selectedPanier);
			tablePanier.refresh();
			totalLbl.setText(calculerTotalBon()+"");
			txtCredit.setText(this.calculerCredit()+"");

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alerte");
			alert.setHeaderText("Veuillez sélectionner un produit acheté!");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	@FXML
	public void changerAchats(){

		if (grosCB.isSelected()) {
			prixUnitPanierColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrixGros()).asObject());
			for(Panier p: paniersList){
				p.calculerTotalGros();
			}
			tablePanier.refresh();
		}else{
			prixUnitPanierColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
			for(Panier p: paniersList){
				p.calculerTotal();
			}
			tablePanier.refresh();
		}
		totalLbl.setText(calculerTotalBon()+"");
		txtTotal.setText(calculerTotalBon()+"");
		txtCredit.setText(this.calculerCredit()+"");
	}

	private double calculerTotalBon() {
		double somme = 0;
		for(Panier p : paniersList) {
			somme += p.getTotal();
		}
		return somme;
	}

	@FXML
	public void ajouterTranche() {
		if(txtMontantTranche.getText()!=null) {
			double montantTranche = Double.parseDouble(txtMontantTranche.getText());
			Tranche t = new Tranche();
			t.setDate(new Date());
			t.setMontant(montantTranche);
			t.setBonDeLivraison(bon);
			tranches.add(t);
			bon.setTranches(tranches);
			if (this.bon.getId() != 0) {
				bonMetier.ajouterTranche(t);
			}
			tableTranches.setItems(FXCollections.observableArrayList(tranches));
			txtMontantPaye.setText(calulerTotalPaye()+"");
			txtCredit.setText(this.calculerCredit()+"");

		}
	}

	@FXML
	public void supprimerTranche() {
		Tranche t = tableTranches.getSelectionModel().getSelectedItem();
		if(t != null) {
			tranches.remove(t);
			if (this.bon.getId() != 0) {
				bonMetier.supprimerTranche(t.getId());
			}
			tableTranches.setItems(FXCollections.observableArrayList(tranches));
			txtMontantPaye.setText(calulerTotalPaye()+"");
			txtCredit.setText(this.calculerCredit()+"");
		}
	}

	private double calulerTotalPaye() {
		double somme = 0;
		somme = Double.parseDouble(txtAvance.getText());
		if (bon.getTranches() != null) {
			for (Tranche t : bon.getTranches()) {
				somme += t.getMontant();
			}
		}
		return somme;
	}

	@FXML
	public void saveBon() {
		double avance = 0;
		double montant = 0;
		double montantPaye = 0;
		double remise = 0;
		try{

			avance = Double.parseDouble(txtAvance.getText());
			remise = Double.parseDouble(txtRemise.getText());
//			montant = Double.parseDouble(txtMontantPaye.getText());
			montant = this.calculerTotalBon();
			montantPaye =this.calulerTotalPaye();

			bon.setNomClient(txtNom.getText());
			bon.setPrenomClient(txtPrenom.getText());
			bon.setTelClient(txtTel.getText());
			bon.setAvance(avance);
			bon.setProduitsAchetes(paniersList);
			bon.setRemise(remise);
			bon.setTranches(tranches);
			bon.setMontant(montant);
			bon.setMontantPaye(montantPaye);
			bon.setPaye(bon.getMontantPaye() == bon.getMontant());
			bon.setDate(new Date());
			bon.setMatricule(txtMatricule.getText());
			bon.setChauffeur(txtChauffeur.getText());
			bon.setDirection(txtDirection.getText());
			if (bon.getId() == 0) {
				bonMetier.enregistrerBonDeLivraison(bon);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bon de Livraison");
				alert.setHeaderText("Bon de Livraison a été enrgistré");
				Optional<ButtonType> result = alert.showAndWait();
			} else {
				bonMetier.updateBon(bon);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Bon de Livraison");
				alert.setHeaderText("Bon de Livraison a été modifié");
				Optional<ButtonType> result = alert.showAndWait();
			}
		} finally {

		}
	}

	@FXML
	public void back(ActionEvent event){
		System.out.println("back");
		if(showSlide == 1) {
			translateAnimation(0.5, pane2, 2000);
			translateAnimation(0.5, pane1, 2000);
			showSlide--;
		}
	}

	@FXML
	public void next(ActionEvent event){
		System.out.println("next");
		if(showSlide == 0) {
			txtTotal.setText(totalLbl.getText());
			translateAnimation(0.5, pane1, -2000);
			translateAnimation(0.5, pane2, -2000);
			showSlide++;
		}
	}

	@FXML
	public void imprimerBon() {
		if (bon.getId() != 0) {
			mainApp.printerWindow(bon, grosCB.isSelected());
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Attention!");
			alert.setHeaderText("Vous devez sauvegarder le bon de livraison avant de l'imprimer.");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	private double calculerCredit() {
		double remise = 0;
		if (txtRemise.getText().length() > 0)
			remise = Double.parseDouble(txtRemise.getText());
		double avance = Double.parseDouble(txtAvance.getText());
		double credit = Double.parseDouble(txtTotal.getText());
		credit -= remise;
		credit -= avance;
		for (Tranche t : this.bon.getTranches()) {
			credit -= t.getMontant();
		}
		return credit;
	}


}
