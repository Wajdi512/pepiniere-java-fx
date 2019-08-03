package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import application.io.FicheIpGenerateurImpl;
import application.metier.OuvrierMetierImpl;
import application.metier.OuvrierMetierInterface;
import application.model.Absence;
import application.model.Avance;
import application.model.CoutDeJour;
import application.model.Ouvrier;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VoirAbsenceController {

	private Main mainApp;
	private Ouvrier ouvrier;
	private List<Absence> lesAbsences;
	private List<Avance> lesAvances;
	private OuvrierMetierInterface ouvrierMetier;

	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private TextField txtFonction;
	@FXML
	private TextField txtJourn;
	@FXML
	private TextField montantAvanceTxt;
	@FXML
	private TextField sommeAvance;
	@FXML
	private TextField sommeJourTravail;
	@FXML
	private TextField resteSalaire;

	@FXML
	private TableView<Absence> tableAbsence;
	@FXML
	private TableColumn<Absence, Integer> idColumn;
	@FXML
	private TableColumn<Absence, String> dateColumn;
	@FXML
	private TableColumn<Absence, Double> coutColumn;

	@FXML
	private DatePicker dateAbsence;
	@FXML
	private DatePicker dateDebPresence;
	@FXML
	private DatePicker dateFinPresence;

	@FXML
	private TableView<Avance> tableAvance;
	@FXML
	private TableColumn<Avance, String> dateAvanceColumn;
	@FXML
	private TableColumn<Avance, Double> avanceColumn;

	@FXML
	private ComboBox<String> moisAvanceCB;

	@FXML
	private DatePicker startDate;

	@FXML
	private DatePicker endDate;

	final static String[] MOIS = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre",
			"Octobre", "Novembre", "Decembre" };

	CoutDeJour coutDejourCourant;

	public void initialize() {
		ouvrierMetier = new OuvrierMetierImpl();
		this.ouvrierMetier = new OuvrierMetierImpl();
		idColumn.setCellValueFactory(new PropertyValueFactory<Absence, Integer>("id"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Absence, String>("dateAbsence"));
		coutColumn.setCellValueFactory(
				CellData -> new SimpleDoubleProperty(CellData.getValue().getCoutDeJour().getCout()).asObject());
		lesAbsences = new ArrayList<Absence>();
		tableAbsence.setItems(FXCollections.observableArrayList(lesAbsences));
		lesAvances = new ArrayList<>();
		dateAvanceColumn.setCellValueFactory(new PropertyValueFactory<Avance, String>("dateAvance"));
		avanceColumn.setCellValueFactory(new PropertyValueFactory<Avance, Double>("montant"));
		tableAvance.setItems(FXCollections.observableArrayList(lesAvances));
	}

	public void setOuvrier(Ouvrier ouvrier) {
		this.ouvrier = ouvrier;
		txtNom.setText(ouvrier.getNom());
		txtPrenom.setText(ouvrier.getPrenom());
		txtFonction.setText(ouvrier.getFonction());
		if (ouvrier.getId() != 0) {
			if (ouvrier.getMesAbsences().size() > 0)
				txtJourn.setText(ouvrier.getMesAbsences().get(ouvrier.getMesAbsences().size() - 1).getCout() + "");
		}
		prepareAbsences(ouvrier);
		if (ouvrier.getMesAvances() != null && !ouvrier.getMesAvances().isEmpty()) {
			this.lesAvances = ouvrier.getMesAvances();
			tableAvance.setItems(FXCollections.observableArrayList(this.lesAvances));
		}
	}

	private void prepareAbsences(Ouvrier ouvrier) {
		if (ouvrier.getMesAbsences() != null && !ouvrier.getMesAbsences().isEmpty()) {
			// this.lesAbsences = ouvrier.getMesAbsences();
			int size = ouvrier.getMesAbsences().size() - 1;
			tableAbsence.getItems().clear();
			System.out.println(size);
			if (ouvrier.getMesAbsences().get(size).getJoursTravaile() != null)
				this.coutDejourCourant = ouvrier.getMesAbsences().get(size);
			for (CoutDeJour cdj : ouvrier.getMesAbsences()) {
				tableAbsence.getItems().addAll(cdj.getJoursTravaile());
			}
		}
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * la date d'absence est en fait utilisée en tant que date de présence
	 */
	@FXML
	public void ajouterAbsence() {
		if (dateAbsence.getValue() != null) {
			Absence a = new Absence();
			a.setDateAbsence(Date.from(dateAbsence.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			// a.setOuvrier(this.ouvrier);
			a.setCoutDeJour(coutDejourCourant);
			if (coutDejourCourant != null) {
				coutDejourCourant.getJoursTravaile().add(a);
			}
			ouvrierMetier.ajouterAbsence(a);
			ouvrier.setMesAbsences(ouvrierMetier.getCoutJourOuvrier(ouvrier));
			for (CoutDeJour cdj : ouvrier.getMesAbsences()) {
				cdj.setJoursTravaile(ouvrierMetier.listAbsencesCdj(cdj.getId()));
			}
			tableAbsence.getItems().clear();
			prepareAbsences(ouvrier);
			// this.ouvrier.setMesAbsences(lesAbsences);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Absence ajoutée");
			alert.setHeaderText("Une absence ( " + dateAbsence.getValue() + " ) a été ajoutée !");
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			Optional<ButtonType> result = alert.showAndWait();

		}
	}

	@FXML
	public void ajouterAvance() {
		double avance = 0;
		avance = Double.parseDouble(montantAvanceTxt.getText());
		if (montantAvanceTxt.getText() != null && !montantAvanceTxt.getText().equals("")) {
			Avance avanceBean = new Avance();
			Date d = new Date();
			d.setHours(0);
			d.setSeconds(0);
			d.setMinutes(0);
			avanceBean.setDateAvance(d);
			avanceBean.setMontant(avance);
			avanceBean.setOuvrier(ouvrier);
			ouvrierMetier.ajouterAvance(avanceBean);
			tableAvance.getItems().add(avanceBean);
			this.ouvrier.getMesAvances().add(avanceBean);
			final Alert alert = new Alert(AlertType.INFORMATION);
			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
			alert.setTitle("Avance ajoutée");
			alert.setHeaderText("Une avance avance de [ " + avance + " dt] a été ajoutée!");
			Optional<ButtonType> result = alert.showAndWait();
		}
	}

	@FXML
	public void trouverAvance() {
		LocalDate startlocalDate = startDate.getValue();
		if(startlocalDate == null) startlocalDate = LocalDate.of(2017, Month.JANUARY, 1);
		Instant instant = Instant.from(startlocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateStart = Date.from(instant);
		LocalDate endLocalDate = endDate.getValue();
		if(endLocalDate == null) endLocalDate = LocalDate.now();
		Instant instant2 = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateEnd = Date.from(instant2);
		tableAvance.setItems(FXCollections
				.observableArrayList(ouvrierMetier.listAvancesByMonth(dateStart, dateEnd, ouvrier.getId())));
	}

	@FXML
	public void touverAbsences() {
		LocalDate startlocalDate = dateDebPresence.getValue();
		if(startlocalDate == null) startlocalDate = LocalDate.of(2017, Month.JANUARY, 1);
		Instant instant = Instant.from(startlocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateStart = Date.from(instant);
		LocalDate endLocalDate = dateFinPresence.getValue();
		if(endLocalDate == null) endLocalDate = LocalDate.now();
		Instant instant2 = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateEnd = Date.from(instant2);
		List<CoutDeJour> cdjs = ouvrierMetier.getCoutJourOuvrier(ouvrier);
		Integer ids[] = new Integer[cdjs.size()];
		int i = 0;
		for (CoutDeJour cdj : cdjs) {
			ids[i] = cdj.getId();
			i++;
		}
		tableAbsence.setItems(
				FXCollections.observableArrayList(ouvrierMetier.listAbsenceBetwenDate(dateStart, dateEnd, ids)));
	}

	@FXML
	public void calculerReste() {
		System.out.println("calcul Reste");
		LocalDate startlocalDate = dateDebPresence.getValue();
		if(startlocalDate == null) startlocalDate = LocalDate.of(2017, Month.JANUARY, 1);
		Instant instant = Instant.from(startlocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateStart = Date.from(instant);
		LocalDate endLocalDate = dateFinPresence.getValue();
		if(endLocalDate == null) endLocalDate = LocalDate.now();
		Instant instant2 = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateEnd = Date.from(instant2);
		// long nbrePresences = ouvrierMetier.nombreDeJourTravailler(dateStart,
		// dateEnd, this.ouvrier.getId());
		List<CoutDeJour> cdjs = ouvrierMetier.getCoutJourOuvrier(this.ouvrier);
		Integer[] ids = new Integer[cdjs.size()];
		for (int i = 0; i < cdjs.size(); i++) {
			ids[i] = new Integer(cdjs.get(i).getId());
		}
		List<Absence> listes = ouvrierMetier.listAbsenceBetwenDate(dateStart, dateEnd, ids);
		double salaire = 0;
		for (Absence a : listes) {
			salaire += a.getCoutDeJour().getCout();
		}
		double sommeAvc = ouvrierMetier.getSommeAvance(dateStart, dateEnd, this.ouvrier.getId());
		sommeAvance.setText(sommeAvc + "");
		sommeJourTravail.setText(salaire + "");
		double reste = salaire - sommeAvc;
		resteSalaire.setText(reste + "");
	}

	@FXML
	public void imprimerRelever() {
		FicheIpGenerateurImpl fiche = new FicheIpGenerateurImpl();
//		if (dateDebPresence.getValue() == null) {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Erreur");
//			alert.setHeaderText("Veuillez saisir la date de début !");
//			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
//			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
//			Optional<ButtonType> result = alert.showAndWait();
//			return;
//
//		}
//		if (dateFinPresence.getValue() == null) {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Erreur");
//			alert.setHeaderText("Veuillez saisir la date de fin !");
//			final Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
//			stageAlert.getIcons().add(new Image("application/views/images/alkhayer.jpg"));
//			Optional<ButtonType> result = alert.showAndWait();
//			return;
//		}
		fiche.setOuvrier(ouvrier);
		fiche.setAbs(tableAbsence.getItems());
		LocalDate startlocalDate = dateDebPresence.getValue();
		if(startlocalDate == null) startlocalDate = LocalDate.of(2017, Month.JANUARY, 1);
		Instant instant = Instant.from(startlocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateStart = Date.from(instant);
		LocalDate endLocalDate = dateFinPresence.getValue();
		if(endLocalDate == null) endLocalDate = LocalDate.now();
		Instant instant2 = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));
		Date dateEnd = Date.from(instant2);
		fiche.setAvances(ouvrierMetier.listAvancesByMonth(dateStart, dateEnd, ouvrier.getId()));
		fiche.setDebut(dateStart);
		fiche.setFin(dateEnd);
		fiche.genererFicheIp();
		mainApp.printerWindow(fiche.getCheminFichier(), true);

	}

	@FXML
	public void supprimerPresence() {
		Absence selectedAbs = tableAbsence.getSelectionModel().getSelectedItem();
		if (selectedAbs != null) {
			selectedAbs.setCoutDeJour(null);
			ouvrierMetier.supprimerAbsence(selectedAbs);
			tableAbsence.getItems().remove(selectedAbs);
			tableAbsence.refresh();

		}

	}

	@FXML
	public void supprimerAvance() {
		Avance selectedAvance = tableAvance.getSelectionModel().getSelectedItem();
		if (selectedAvance != null) {
			ouvrierMetier.supprimerAvance(selectedAvance);
			tableAvance.getItems().remove(selectedAvance);
			tableAvance.refresh();
		}

	}

}
