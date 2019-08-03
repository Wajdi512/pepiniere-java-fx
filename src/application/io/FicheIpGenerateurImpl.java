package application.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import application.model.Absence;
import application.model.Avance;
import application.model.Ouvrier;

public class FicheIpGenerateurImpl implements FicheIpGenerateurInterface {

	private String cheminFichier;
	private Ouvrier ouvrier;
	private List<Absence> abs;
	private List<Avance> avances;
	private Date debut;
	private Date fin;

	@Override
	public void genererFicheIp() {
		// TODO Auto-generated method stub
		this.cheminFichier = "./ouvriers/fiche_"+ouvrier.getNom()+"_"+ouvrier.getPrenom()+".pdf";
		Document doc = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(cheminFichier));
			doc.open();
			doc.add(this.generateInfoOuvrierTable(ouvrier, debut, fin));
			doc.add(new Phrase("\n"));
			doc.add(new Phrase("\n"));
			doc.add(new Phrase("\nJours travaillés"));
			doc.add(this.generateAbsenceTable(this.abs));
			doc.add(new Phrase("\n"));
			doc.add(new Phrase("\n"));
			doc.add(new Phrase("\nLes avances:"));
			doc.add(this.generateAvanceTable(this.avances));
			doc.add(new Phrase("\n"));
			doc.add(this.generateTotalTable());

			doc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.open();


	}

	private PdfPTable generateInfoOuvrierTable(Ouvrier ouvrier, Date debut, Date fin) {
		float[] columnWidthsHeadTable = { 5, 5};
		PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
		headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
		headTable.setWidthPercentage(100);
		PdfPCell cell1 = new PdfPCell();
		cell1.setBorder(PdfPCell.NO_BORDER);
		Paragraph infoOuvrier = new Paragraph();
		infoOuvrier.add("Nom   :"+ouvrier.getNom()+"\n");
		infoOuvrier.add("Prénom:"+ouvrier.getPrenom());

		cell1.addElement(infoOuvrier);
		PdfPCell cell2 = new PdfPCell();
		cell1.setBorder(PdfPCell.NO_BORDER);
		Paragraph dateExtrait = new Paragraph();
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String debutString = DATE_FORMAT.format(debut);
        String finString = DATE_FORMAT.format(fin);
		dateExtrait.add("Relevé\n");
		dateExtrait.add("du "+debutString+" à "+finString);
		cell2.addElement(dateExtrait);
		cell2.setBorder(PdfPCell.NO_BORDER);
		cell2.setHorizontalAlignment(0);
		headTable.addCell(cell1);
		headTable.addCell(cell2);
		return headTable;
	}

	private PdfPTable generateAbsenceTable(List<Absence> abs) {
		float[] columnWidthsHeadTable = { 3,6,3};
		PdfPTable bodyTable = new PdfPTable(columnWidthsHeadTable);
		bodyTable.addCell(new Phrase("N°"));
		bodyTable.addCell(new Phrase("Date"));
		bodyTable.addCell(new Phrase("Montant"));
		int i = 1;
		for(Absence a: abs) {
	        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	        String date = DATE_FORMAT.format(a.getDateAbsence());
			bodyTable.addCell(new Phrase(i+""));
			bodyTable.addCell(new Phrase(date+""));
			bodyTable.addCell(new Phrase(a.getCoutDeJour().getCout()+""));
			i++;
		}

		return bodyTable;
	}


	private PdfPTable generateAvanceTable(List<Avance> abs) {
		float[] columnWidthsHeadTable = { 3,6,3};
		PdfPTable bodyTable = new PdfPTable(columnWidthsHeadTable);
		bodyTable.addCell(new Phrase("N°"));
		bodyTable.addCell(new Phrase("Date"));
		bodyTable.addCell(new Phrase("Montant"));
		int i = 1;
		for(Avance a:abs) {
			bodyTable.addCell(new Phrase(i+""));
	        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	        String date = DATE_FORMAT.format(a.getDateAvance());
			bodyTable.addCell(new Phrase(date+""));
			bodyTable.addCell(new Phrase(a.getMontant()+""));
			i++;
		}
		return bodyTable;
	}

	private PdfPTable generateTotalTable() {
		float[] columnWidthsHeadTable = { 3, 7 };
		PdfPTable bodyTable = new PdfPTable(columnWidthsHeadTable);
        double salaire = 0;
		for (Absence a : this.getAbs()) {
			salaire += a.getCoutDeJour().getCout();
		}
		double avance = 0;
		for(Avance a: this.getAvances()) {
			avance += a.getMontant();
		}
		bodyTable.addCell(new Phrase("Total"));
		bodyTable.addCell(new Phrase(salaire+""));
		bodyTable.addCell(new Phrase("Total Avance"));
		bodyTable.addCell(new Phrase(avance+""));
		bodyTable.addCell(new Phrase("Reste"));
		bodyTable.addCell(new Phrase(salaire-avance+""));
		return bodyTable;
	}

	public String getCheminFichier() {
		return cheminFichier;
	}

	public void setCheminFichier(String cheminFichier) {
		this.cheminFichier = cheminFichier;
	}

	public Ouvrier getOuvrier() {
		return ouvrier;
	}

	public void setOuvrier(Ouvrier ouvrier) {
		this.ouvrier = ouvrier;
	}

	public List<Absence> getAbs() {
		return abs;
	}

	public void setAbs(List<Absence> abs) {
		this.abs = abs;
	}

	public List<Avance> getAvances() {
		return avances;
	}

	public void setAvances(List<Avance> avances) {
		this.avances = avances;
	}

	public Date getDebut() {
		return debut;
	}

	public void setDebut(Date debut) {
		this.debut = debut;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}


}
