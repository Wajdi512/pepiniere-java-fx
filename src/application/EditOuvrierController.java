package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import application.metier.OuvrierMetierImpl;
import application.metier.OuvrierMetierInterface;
import application.model.CoutDeJour;
import application.model.Ouvrier;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EditOuvrierController {

	private Main mainApp;
	private Ouvrier ouvrier;
	private OuvrierMetierInterface ouvrierMetier;
	private Stage dialogStage;

	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtFonction;
	@FXML
	private TextField txtJourn;

	public void initialize() {
		ouvrierMetier  = new OuvrierMetierImpl();
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Ouvrier getOuvrier() {
		return ouvrier;
	}

	public void setOuvrier(Ouvrier ouvrier) {
		this.ouvrier = ouvrier;
		txtNom.setText(ouvrier.getNom());
		txtPrenom.setText(ouvrier.getPrenom());
		txtFonction.setText(ouvrier.getFonction());
		if(ouvrier.getMesAbsences() !=null && !ouvrier.getMesAbsences().isEmpty()){
			txtJourn.setText(ouvrier.getMesAbsences().get(ouvrier.getMesAbsences().size()-1).getCout()+"");
		}else {
			ouvrier.setMesAbsences(new ArrayList<>());
		}

	}

	@FXML
	public void addOrUpdate() {
		System.out.println("add or update");

		if(ouvrier.getId() == 0) {
			ouvrier.setNom(txtNom.getText());
			ouvrier.setPrenom(txtPrenom.getText());
			ouvrier.setDateAjout(new Date());
	//		ouvrier.setCoutJourneeTravail(Double.parseDouble(txtJourn.getText()));
			CoutDeJour cdj = new CoutDeJour();
			cdj.setCout(Double.parseDouble(txtJourn.getText()));
			ouvrier.setFonction(txtFonction.getText());
			ouvrierMetier.ajouterOuvrier(ouvrier);
			System.out.println("id ouvrier: "+ouvrier.getId());
			cdj.setOuvrier(ouvrier);
			ArrayList<CoutDeJour> lesCdj = new ArrayList<>();
			lesCdj.add(cdj);
			ouvrier.setMesAbsences(lesCdj);
			ouvrierMetier.addCoutJourOuvrier(cdj);
			Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Info!");
			alert.setHeaderText("Ouvrier Ajouté!");
			Optional<ButtonType> result = alert.showAndWait();
			this.dialogStage.close();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Attention!");
			alert.setHeaderText("Voulez vous vraiment modifier les information de l'ouvrier:"
					+ "\n"+ouvrier.getNom()+" "+ouvrier.getPrenom());
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				ouvrier.setNom(txtNom.getText());
				ouvrier.setPrenom(txtPrenom.getText());
	//			ouvrier.setCoutJourneeTravail(Double.parseDouble(txtJourn.getText()));
				double cout = Double.parseDouble(txtJourn.getText());
				if (ouvrier.getMesAbsences().isEmpty()){
					CoutDeJour cdj1 = new CoutDeJour();
					cdj1.setCout(cout);
					cdj1.setDateCreation(new Date());
					cdj1.setOuvrier(ouvrier);
					ouvrier.getMesAbsences().add(cdj1);
					ouvrierMetier.addCoutJourOuvrier(cdj1);

				}
				else if (ouvrier.getMesAbsences().get(ouvrier.getMesAbsences().size() - 1).getCout() != cout) {
					CoutDeJour cdj1 = new CoutDeJour();
					cdj1.setCout(cout);
					cdj1.setDateCreation(new Date());
					cdj1.setOuvrier(ouvrier);
					ouvrier.getMesAbsences().add(cdj1);
					ouvrierMetier.addCoutJourOuvrier(cdj1);
				}
				ouvrier.setFonction(txtFonction.getText());
				ouvrierMetier.mettreAjour(ouvrier);
			}
		}
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}




}
