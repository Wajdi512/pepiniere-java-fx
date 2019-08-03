package application;

import java.io.IOException;
import java.util.List;

import application.io.DocumentInterface;
import application.model.BonDeLivraison;
import application.model.Commande;
import application.model.Devis;
import application.model.Facture;
import application.model.Ouvrier;
import application.model.Produit;
import application.model.Tranche;
import application.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	Stage stage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.stage = primaryStage;
		mainWindow();
	}

	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/loginWindow.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Alkhayer APP v1.0");
			stage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			LoginWindowController controller = loader.getController();
			controller.setMainApp(this);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void homeWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/HomeWindow.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Alkhayer App");
			// stage.initStyle(StageStyle.UNDECORATED);
			HomeWindowController controller = loader.getController();
			controller.setMainApp(this);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void produitWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OverviewProduit.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Alkhayer App");
			OverviewProduitController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean voirTanches(List<Tranche> tranches) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/VoirTranches.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Liste de Tranches");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.setScene(scene);
			VoirTranche controller = loader.getController();
			controller.setMainApp(this);
			controller.setLesTranches(tranches);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void factureWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OverviewFacture.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Alkhayer App");
			OverviewFactureController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bonLivraisonWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OverviewBonDeLivraison.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Alkhayer App");
			OverviewBonDeLivraisonController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean showProductEditDialog(Produit product) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/EditProduct.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Product");
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ProduitEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setProduct(product);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean showFactureEditDialog(Facture f) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/EditFacture.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.setTitle("Edition facture");
			if(f.getId() == 0)
			dialogStage.setTitle("Création facture");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setResizable(false);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			FactureEditController controller = loader.getController();
			controller.setFacture(f);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showBonEditDialog(BonDeLivraison bon) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/EditBonDeLivraison.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edition bon de livraison");
			if(bon.getId() == 0)
			dialogStage.setTitle("Création bon de livraison");
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setResizable(false);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			BonDeLivraisonEditController controller = loader.getController();
			controller.setBon(bon);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public void commandeWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OverviewCommande.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Alkhayer App");
			OverviewCommandeController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void bonDeLivraisonToFactureWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/BonDeLivraisonToFacture.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Alkhayer App");
			BonDeLivraisonToFactureController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean showCommandeEditDialog(Commande cmd) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/EditCommande.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.setTitle("Edition commande");
			if(cmd.getId() == 0)
			dialogStage.setTitle("Création commande");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			CommandeEditController controller = loader.getController();
			controller.setCmd(cmd);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void printerWindow(DocumentInterface docImprimer, boolean gros) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/Impression.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Impressions");
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ImpressionController controller = loader.getController();
			controller.setGros(gros);
			controller.setDocImprimer(docImprimer);
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void printerWindow(String cheminFichier, boolean gros) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/Impression.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Impressions");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ImpressionController controller = loader.getController();
			controller.setCheminFichier(cheminFichier);
			controller.setGros(gros);
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public boolean showPanierToBon(Facture facture) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/PanierToBon.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Generer bon de livraison");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			PanierToBonController controller = loader.getController();
			controller.setFacture(facture);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public void ouvrierWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OverviewOuvrier.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Ouvrier");
			OverviewOuvrierController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean showEditOuvrier(Ouvrier o) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/AddOuvrier.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.setTitle("Edition Ouvrier");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			EditOuvrierController controller = loader.getController();
			controller.setMainApp(this);
			controller.setOuvrier(o);
			controller.setDialogStage(dialogStage);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showVoirAbsenceOuvrier(Ouvrier o) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/VoirAbsences.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setResizable(false);
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.setTitle("Absences");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			VoirAbsenceController controller = loader.getController();
			controller.setMainApp(this);
			controller.setOuvrier(o);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void printEmptyWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/PrintEmptyDocument.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Impression");
			PrintEmptyDocumentController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void overviewDevisWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/OverviewDevis.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Devis");
			OverviewDevisController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean showDevisEditDialog(Devis d) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("views/EditDevis.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			dialogStage.setTitle("Edition devis");
			if (d.getId() == 0)
				dialogStage.setTitle("Création Devis");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Set the person into the controller.
			DevisEditController controller = loader.getController();
			controller.setDevis(d);
			controller.setMainApp(this);
			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void recuParJourWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/RecuParJour.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(true);
			stage.setTitle("Devis");
			RecuParJourController controller = loader.getController();
			controller.setMainApp(this);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parametresWindow(User user) {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/ParametresOverview.fxml"));
			AnchorPane overviewPage = (AnchorPane) loader.load();
			Scene scene = new Scene(overviewPage);
			scene.getStylesheets().addAll(Main.class.getResource("style.css").toExternalForm());
			stage.setResizable(false);
			stage.setTitle("Paramétres");
			ParametresOverviewController controller = loader.getController();
			controller.setMainApp(this);
			controller.setUser(user);
			// stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
