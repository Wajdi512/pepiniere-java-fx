package application;

import java.util.Optional;

import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.model.Produit;
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

public class OverviewProduitController {

	private ProduitDao produitDao;
	private Main mainApp;

	@FXML
	private TableView<Produit> tableProduit;
	@FXML
	private TextField txtSearch;
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


	public void initialize() {
		produitDao = new ProduitDaoImpl();
		designationColumn.setCellValueFactory(new PropertyValueFactory<Produit, String>("designation"));
		prixColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prix"));
		prixGrosColumn.setCellValueFactory(new PropertyValueFactory<Produit, Double>("prixGros"));
		qteColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("dateAjout"));
		tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduit()));
	}



	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}



	@FXML
	private void handleNewProduct() {
		String mc = txtSearch.getText();
		Produit p = new Produit();
		boolean okClicked = mainApp.showProductEditDialog(p);
		if(p.getId() != 0) {
			tableProduit.getItems().add(p);
			tableProduit.refresh();
		}
	}

	@FXML
	private void handleEditProduct() {
		String mc = txtSearch.getText();
		Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
		System.out.println(selectedProduit);
		boolean okClicked = mainApp.showProductEditDialog(selectedProduit);
		//tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduitByMC(mc)));
		tableProduit.refresh();
	}

	@FXML
	private void handleRemoveProduct() {

		Produit selectedProduit = tableProduit.getSelectionModel().getSelectedItem();
		if(selectedProduit!=null){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Validation");
			//alert.setHeaderText("Etes-vous sur?");
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setHeaderText("Voulez-vous supprimer l'article "+selectedProduit.getDesignation()+" ?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				selectedProduit.setActif(false);
				produitDao.updateProduit(selectedProduit);
				tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduit()));
			}
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Attention");
			//alert.setHeaderText("Etes-vous sur?");
			alert.setHeaderText("Selectionner un article!");
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			Optional<ButtonType> result = alert.showAndWait();
		}

	}

	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}


	@FXML
	public void chercherProduit() {
		String mc = txtSearch.getText();
		tableProduit.setItems(FXCollections.observableArrayList(produitDao.listProduitByMC(mc)));
	}

}
