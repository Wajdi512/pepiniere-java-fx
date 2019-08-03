package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.io.PDFGenerateur;
import application.metier.CommandeMetierImpl;
import application.metier.CommandeMetierInterface;
import application.metier.FactureMetierImpl;
import application.model.Commande;
import application.model.Panier;
import application.model.Produit;
import application.model.Tranche;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CommandeEditController {
	private Main mainApp;
	private Commande cmd;
	private ProduitDao produitDao;
	private List<Panier> paniersList;
	private CommandeMetierInterface commandeMetier;

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
		commandeMetier = new CommandeMetierImpl();
		txtRemise.textProperty().addListener((observale, oldValue, newValue) -> {
			totalLbl.setText(this.calculerTotalCommande()+"");
		});
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Commande getCmd() {
		return cmd;
	}

	public void setCmd(Commande cmd) {
		this.cmd = cmd;
		txtNom.setText(cmd.getNomClient());
		txtPrenom.setText(cmd.getPrenomClient());
		txtTel.setText(cmd.getTelClient());
		if(cmd.getProduitsAchetes() != null && !cmd.getProduitsAchetes().isEmpty())
		tablePanier.setItems(FXCollections.observableArrayList(cmd.getProduitsAchetes()));
		totalLbl.setText(cmd.getMontant() + "");

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
			p.setCmd(cmd);
			p.setQuantite(1);
			if (grosCB.isSelected()) {
				p.calculerTotalGros();
			} else {
				p.calculerTotal();
			}
			if (this.cmd.getId() != 0) {
				commandeMetier.ajouterPanier(p);
			}
			tablePanier.getItems().add(p);
			paniersList.add(p);
			cmd.setProduitsAchetes(tablePanier.getItems());
			totalLbl.setText(cmd.calculerTotalCommande() + "");
			tableProduit.getItems().remove(selectedProduct);
			tableProduit.refresh();

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
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
			cmd.setProduitsAchetes(tablePanier.getItems());
			if (this.cmd.getId() != 0) {
				commandeMetier.supprimerPanier(selectedPanier);
			}
			if (grosCB.isSelected()) {
				totalLbl.setText(cmd.calculerTotalCommandeGros() + "");
			} else {
				totalLbl.setText(cmd.calculerTotalCommande() + "");
			}
			tablePanier.refresh();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
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
				totalLbl.setText(cmd.calculerTotalCommandeGros() + "");
			} else {
				selectedPanier.setQuantite(qtePanier);
				selectedPanier.calculerTotal();
				totalLbl.setText(cmd.calculerTotalCommande() + "");
			}
			if(selectedPanier.getId() != 0) {
				commandeMetier.updatePanier(selectedPanier);
			}
			tablePanier.refresh();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
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
			cmd.calculerTotalCommandeGros();

		} else {
			prixUnitPanierColumn.setCellValueFactory(
					cellData -> new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix()).asObject());
			for (Panier p : paniersList) {
				p.calculerTotal();
			}
			tablePanier.refresh();
			cmd.calculerTotalCommande();

		}
		totalLbl.setText(cmd.calculerTotalCommande() + "");
	}

	@FXML
	public void saveCommande() {
		if (cmd.getId() == 0) {
			cmd.setNomClient(txtNom.getText());
			cmd.setPrenomClient(txtPrenom.getText());
			cmd.setTelClient(txtTel.getText() + "");
			cmd.setDate(new Date());
			cmd.setProduitsAchetes(tablePanier.getItems());
			commandeMetier.enregistrerCommande(cmd);
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Commande Enregistré!");
			alert.setHeaderText("Commande sauvegardé.");
			Optional<ButtonType> result = alert.showAndWait();

		} else {
			commandeMetier.updateCommande(cmd);
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Commande modifé!");
			alert.setHeaderText("Commande Modifié.");
			Optional<ButtonType> result = alert.showAndWait();
		}

	}

	@FXML
	public void imprimerCommande() {
		if (cmd.getId() != 0) {
			mainApp.printerWindow(cmd, grosCB.isSelected());
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Attention!");
			alert.setHeaderText("Vous devez sauvegarder la commade pour l'imprimer.");
			Optional<ButtonType> result = alert.showAndWait();

		}
	}

	private double calculerTotalCommande() {
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
