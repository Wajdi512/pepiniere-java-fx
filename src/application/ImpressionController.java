package application;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import application.io.DocumentInterface;
import application.io.ImpressionImpl;
import application.io.PDFGenerateur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ImpressionController {

	private Stage dialogStage;
	private boolean okClicked = false;
	private boolean gros;
	private DocumentInterface docImprimer;
	private String cheminFichier;

	private Main mainApp;

	@FXML
	private TextField txtNumCopies;

	@FXML
	private ComboBox<String> listeImprimante;

	public DocumentInterface getDocImprimer() {
		return docImprimer;
	}

	public synchronized void setDocImprimer(DocumentInterface docImprimer) {
		this.docImprimer = docImprimer;
		PDFGenerateur pg = new PDFGenerateur();
		pg.setFacture(docImprimer);
		pg.setGros(gros);
		try {
			pg.genererPDF();
			cheminFichier = pg.getCheminFichier();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fromset:"+pg.getCheminFichier());
	}

	public boolean isGros() {
		return gros;
	}

	public void setGros(boolean gros) {
		this.gros = gros;
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void initialize() {
		listeImprimante.getItems().addAll(ImpressionImpl.getPrintNames());

	}

	@FXML
	public void imprimer() {
		PDDocument document = null;
		String printerName = null;
		try {
			System.out.println("Printing...");

			printerName = this.listeImprimante.getSelectionModel().getSelectedItem().toString();
			System.out.println("printerName: "+printerName);
			System.out.println("chemin fichier: "+cheminFichier);

			document = PDDocument.load(new File(cheminFichier));
			PrintService myPrintService = ImpressionImpl.findPrintService(printerName);
			PrinterJob job = PrinterJob.getPrinterJob();

			PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
			int nbCopies = Integer.parseInt(txtNumCopies.getText());
//		    aset.add(new Copies(nbCopies));
//			//job.setCopies());

			job.setPageable(new PDFPageable(document));
			job.setCopies(nbCopies);
			job.setPrintService(myPrintService);
			job.print();
			System.out.println("End Printing...");
		} catch (NullPointerException e) {

			if (printerName == null) {
				/*
				 * S'il n a pas spécifier l'imprimante sur lequel se deroulera
				 * l'impression
				 */
				Alert imprimanteAlerte = new Alert(AlertType.WARNING);
				imprimanteAlerte.setTitle("Attention");
				imprimanteAlerte.setContentText("Veuillez choisir une imprimante!");
				imprimanteAlerte.showAndWait();
			} else if (document == null) {
				/* S'il n a pas enregistre la facture */
				Alert imprimanteAlerte = new Alert(AlertType.WARNING);
				imprimanteAlerte.setTitle("Attention");
				imprimanteAlerte
						.setContentText("Vous devriez enregistrer la facture pour que vous puissiez l'imprimer!");
				imprimanteAlerte.showAndWait();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void setOkClicked(boolean okClicked) {
		this.okClicked = okClicked;
	}

	public String getCheminFichier() {
		return cheminFichier;
	}

	public void setCheminFichier(String cheminFichier) {
		this.cheminFichier = cheminFichier;
	}

}
