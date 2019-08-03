package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.model.BonDeLivraison;
import application.model.Tranche;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VoirTranche {

	private Main mainApp;
	private List<Tranche> lesTranches;

	@FXML
	private TableView<Tranche> tableTranches;

	@FXML
	private TableColumn<Tranche,Date> dateColumn;
	@FXML
	private TableColumn<Tranche,Double> montantColumn;

	public void initialize() {
		dateColumn.setCellValueFactory(new PropertyValueFactory<Tranche, Date>("date"));
		montantColumn.setCellValueFactory(new PropertyValueFactory<Tranche, Double>("montant"));
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public List<Tranche> getLesTranches() {
		return lesTranches;
	}

	public void setLesTranches(List<Tranche> lesTranches) {
		this.lesTranches = lesTranches;
		tableTranches.setItems(FXCollections.observableArrayList(lesTranches));
	}


}
