package application;

import java.util.Date;
import java.util.Optional;

import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.model.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ProduitEditController {
	@FXML
	private TextField designationField;
	@FXML
	private TextField prixField;
	@FXML
	private TextField prixGrosField;
	@FXML
	private TextField quantiteField;

	private ProduitDao productDao;
	private Stage dialogStage;

	private boolean okClicked = false;
	private Produit product;
	@FXML
	private void initialize() {
		this.productDao = new ProduitDaoImpl();
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setProduct(Produit p) {
		this.product = p;
		if (p != null) {
			designationField.setText(p.getDesignation());
			prixField.setText(p.getPrix() + "");
			prixGrosField.setText(p.getPrixGros() + "");
			quantiteField.setText(p.getQuantite() + "");
		}
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public boolean isInputValdi() {
		String msg = "";

		if (designationField.getText().length() > 0) {
			msg += "Désignation invalide\n";
		}
		return true;
	}

	@FXML
	public void handleValider() {
		boolean validInput = isInputValdi();
		System.out.println(product);
		if (validInput) {
			if (product.getId() == 0) {
				// ajout nouveau produit
				product = new Produit();
				product.setDesignation(designationField.getText() + "");
				product.setActif(true);
				product.setDateAjout(new Date());
				product.setPrix(Double.parseDouble(prixField.getText()));
				product.setPrixGros(Double.parseDouble(prixGrosField.getText()));
				product.setQuantite(Integer.parseInt(quantiteField.getText()));
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Validation");
				alert.setHeaderText("Etes-vous sur?");
				alert.setContentText("Voulez-vous ajouter?");
				final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
				stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					productDao.addProduit(product);
					dialogStage.close();

				}
			} else {
				//modification
				product.setDesignation(designationField.getText() + "");
				product.setActif(true);
				product.setDateAjout(new Date());
				product.setPrixGros(Double.parseDouble(prixGrosField.getText()));
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Validation");
				alert.setHeaderText("Etes-vous sur?");
				alert.setContentText("Voulez-vous modifier?");
				final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
				stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					double prix = Double.parseDouble(prixField.getText());
					double prixGros = Double.parseDouble(prixGrosField.getText());
					int qte = Integer.parseInt(quantiteField.getText());
					if(product.getPrix() == prix ||	product.getPrixGros() == prixGros) {
						product.setActif(false);
						productDao.updateProduit(product);
						product.setPrix(prix);
						product.setPrixGros(prixGros);
						product.setActif(true);
						product.setQuantite(qte);
						productDao.addProduit(product);
						dialogStage.close();
					} else {
						product.setQuantite(qte);
						productDao.updateProduit(product);
						dialogStage.close();
					}
				}
			}

		}

	}

	@FXML
	private void handleAnnuler() {
		dialogStage.close();
	}

	@FXML
	private void handleRemoveProduct() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Validation");
		alert.setHeaderText("Etes-vous sur?");
		alert.setContentText("Voulez-vous supprimer l'article +"+product.getDesignation()+"?");
		final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
		stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			product.setActif(false);
			productDao.updateProduit(product);
		}
	}
}
