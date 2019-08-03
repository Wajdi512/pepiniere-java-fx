package application;

import java.util.Optional;

import application.metier.UserMetierImpl;
import application.metier.UserMetierInterface;
import application.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;

public class ParametresOverviewController {

	private  UserMetierInterface userMetier;

	private Main mainApp;
	@FXML
	private TextField txtPseudo;
	@FXML
	private PasswordField txtMdpCourante;
	@FXML
	private PasswordField txtMdpNew;
	@FXML
	private PasswordField txtMdpNewVerif;
	@FXML
	private ImageView showCurrentPassword;
	@FXML
	private ImageView showNewPassword;
	@FXML
	private ImageView showNewVerificationPassword;
	@FXML
	private Label motDepasseCouranteLabel;
	@FXML
	private Label nouveauMotDePasseLabel;

	@FXML
	private Label nouveauMotDePasseVerifLabel;



	private User user;

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	private void goHome() {
		System.out.println("Go Home");
		mainApp.homeWindow();
	}

	@FXML
	public void initialize() {
		userMetier = new UserMetierImpl();
		motDepasseCouranteLabel.setText("");
		nouveauMotDePasseLabel.setText("");
		nouveauMotDePasseVerifLabel.setText("");
		showCurrentPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			if (motDepasseCouranteLabel.getText().equals("")) {
				motDepasseCouranteLabel.setText("Mot de passe actuel: " + txtMdpCourante.getText());
			} else {
				motDepasseCouranteLabel.setText("");
			}
		});
		showNewPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			if (nouveauMotDePasseLabel.getText().equals("")) {
				nouveauMotDePasseLabel.setText("Nouveau mot de passe: " + txtMdpNew.getText());
			} else {
				nouveauMotDePasseLabel.setText("");
			}
		});
		showNewVerificationPassword.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			if (nouveauMotDePasseVerifLabel.getText().equals("")) {
				nouveauMotDePasseVerifLabel.setText("Mot de passe: " + txtMdpNewVerif.getText());
			} else {
				nouveauMotDePasseVerifLabel.setText("");
			}
		});
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		mappedUser(user);
	}

	private void mappedUser(User user) {
		this.txtPseudo.setText(user.getPseudo());
		this.txtMdpCourante.setText(user.getMotDePasse());
	}

	@FXML
	public void sauvegarder() {
		final String nouveauMotDePasse = txtMdpNew.getText();
		final String verifMotDePasse = txtMdpNewVerif.getText();
		if(nouveauMotDePasse != null && !nouveauMotDePasse.isEmpty()) {
			if(verifMotDePasse != null && !verifMotDePasse.isEmpty()) {
				if(nouveauMotDePasse.equals(verifMotDePasse)) {
					user.setMotDePasse(nouveauMotDePasse);
					userMetier.update(user);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success");
					alert.setHeaderText("votre mot de passe a été bien changé!");
					Optional<ButtonType> result = alert.showAndWait();
				} else {
					//les deux mots des passe ne sont pas identiques
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erreur");
					alert.setHeaderText("vérifier votre mot de passe !");
					Optional<ButtonType> result = alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Veuillez saisir votre mot de passe de nouveau!");
				Optional<ButtonType> result = alert.showAndWait();
			}
		}
	}


}
