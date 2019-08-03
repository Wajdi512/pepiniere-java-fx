package application;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.metier.UserMetierImpl;
import application.metier.UserMetierInterface;
import application.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginWindowController {

	private UserMetierInterface userMetier;
	private Main mainApp;

	@FXML
	private JFXButton btnSeConnecter;
	@FXML
	private JFXTextField pseudo;
	@FXML
	private JFXPasswordField password;

	@FXML
	public void goToHomeWindow() {
		String pseudo = this.pseudo.getText();
		String password = this.password.getText();
		User user = userMetier.authentification(pseudo, password);
		if (user != null){
			mainApp.homeWindow();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Le nom d'utilisateur et le mot de passe sont incorrects");
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			Optional<ButtonType> result = alert.showAndWait();
		}
	}


	public Main getMainApp() {
		return mainApp;
	}


	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}


	public LoginWindowController() {
		super();
	}

	@FXML
	public void initialize() {
		this.userMetier = new UserMetierImpl();
	}


}
