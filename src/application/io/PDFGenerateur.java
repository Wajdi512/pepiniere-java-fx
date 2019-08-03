package application.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.Pfm2afm;

import application.model.BonDeLivraison;
import application.model.Commande;
import application.model.Devis;
import application.model.Facture;
import application.model.Panier;

public class PDFGenerateur {
	private final Font TITRE = FontFactory.getFont("c:/windows/fonts/arialuni.ttf", BaseFont.IDENTITY_H, 12, Font.BOLD);
	public final Font NORMAL = FontFactory.getFont("c:/windows/fonts/arialuni.ttf", BaseFont.IDENTITY_H, 12,
			Font.NORMAL);
	public final Font UNDER = FontFactory.getFont("c:/windows/fonts/arialuni.ttf", BaseFont.IDENTITY_H, 14,
			Font.UNDERLINE);

	public final Font NORMALTXT = FontFactory.getFont("c:/windows/fonts/arialuni.ttf", BaseFont.IDENTITY_H, 12,
			Font.NORMAL);

	public final static String NOM_SOCIETE_FR = "Sté ALKHAYER du CENTRE ";
	public final static String INFO_SOCIETE_FR = "Vente Sentences & Plantes en Gros \n"
			+ "Route Sokrine - Moknine 5050 \n" + "GSM: 22 857 132 - 52 857 132";
	public final static String MATRICULE_FISCALE = "M.F.: 1411519 X/N/M 000";

	public final static String NOM_SOCIETE_AR = "شركـــة الخيـــــــر بالوســـط";
	public final static String INFO_SOCIETE_AR = "تجــارة البــــذور و المشـــاتـل بالجمــلة";
	public final static String ADR_SOCIETE_AR = "طــريق السكـــرين المكنيـــن 5050";
	public final static String CONTACT_SOCIETE_AR = "الهـــاتف: 132 857 22 - 132 857 52";

	private DocumentInterface facture;
	private boolean gros;
	private String cheminFichier;

	public boolean genererFacture() throws MalformedURLException, IOException {

		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./factures/facture_" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream( "./factures/facture_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);

			doc.add(new Paragraph("\n"));
			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Facture N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));

			float[] columnWidthsChau = { 5, 5 };
			PdfPTable tableClient = new PdfPTable(columnWidthsChau);
			tableClient.setWidthPercentage(100);

			PdfPCell nomClient = new PdfPCell(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));
			nomClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(nomClient);

			PdfPCell chauffeur = new PdfPCell(new Phrase("Chauffeur:       "+facture.getChauffeur()));
			chauffeur.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(chauffeur);

			PdfPCell tellClient = new PdfPCell(new Paragraph("Tel:                    " + facture.getTelClient(), NORMALTXT));
			tellClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(tellClient);

			PdfPCell direction = new PdfPCell(new Phrase("Direction:        "+facture.getDirection()));
			direction.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(direction);

			PdfPCell dateFacture = new PdfPCell(new Paragraph("Date:                " + Date.valueOf(LocalDate.now()), NORMALTXT));
			dateFacture.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(dateFacture);



			PdfPCell matricule = new PdfPCell(new Phrase("Immatricule:    "+facture.getMatricule()));
			matricule.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(matricule);
			doc.add(tableClient);





			doc.add(new Paragraph("\n"));

			System.out.println("nombre d'achats: " + facture.getProduitsAchetes().size());
			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrix() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);

			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);
			paiementTable.addCell("Remise");
			paiementTable.addCell(new Phrase(facture.getRemise() + "", NORMAL));
			paiementTable.addCell("TVA");
			paiementTable.addCell(new Phrase("0", NORMAL));
			paiementTable.addCell("Total Payé");
			paiementTable.addCell(new Phrase(facture.getMontantPaye() + "", NORMAL));
			paiementTable.addCell("Total");
			paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			paiementTable.addCell("Crédit");
			paiementTable.addCell(new Phrase(facture.getMontant()-facture.getMontantPaye()-facture.getRemise() + "", NORMAL));
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15,5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public boolean genererFactureGros() throws MalformedURLException, IOException {

		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./factures/facture_gr_" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream(" ./factures/facture_gr_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);
			doc.add(new Paragraph("\n"));
			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Facture N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));

			float[] columnWidthsChau = { 5, 5 };
			PdfPTable tableClient = new PdfPTable(columnWidthsChau);
			tableClient.setWidthPercentage(100);

			PdfPCell nomClient = new PdfPCell(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));
			nomClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(nomClient);

			PdfPCell chauffeur = new PdfPCell(new Phrase("Chauffeur:       "+facture.getChauffeur()));
			chauffeur.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(chauffeur);

			PdfPCell tellClient = new PdfPCell(new Paragraph("Tel:                    " + facture.getTelClient(), NORMALTXT));
			tellClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(tellClient);

			PdfPCell direction = new PdfPCell(new Phrase("Direction:        "+facture.getDirection()));
			direction.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(direction);

			PdfPCell dateFacture = new PdfPCell(new Paragraph("Date:                " + Date.valueOf(LocalDate.now()), NORMALTXT));
			dateFacture.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(dateFacture);



			PdfPCell matricule = new PdfPCell(new Phrase("Immatricule:    "+facture.getMatricule()));
			matricule.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(matricule);
			doc.add(tableClient);

			doc.add(new Paragraph("\n"));

			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrixGros() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);
			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);
			paiementTable.addCell("Montant payé");
			paiementTable.addCell(new Phrase(facture.getMontantPaye() + "", NORMAL));
			paiementTable.addCell("Remise");
			paiementTable.addCell(new Phrase(facture.getRemise() + "", NORMAL));
			paiementTable.addCell("Total");
			if(facture.getMontant() != 0) {
				paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			}
			else {
				paiementTable.addCell(new Phrase("", NORMAL));
			}
			paiementTable.addCell("Crédit");
			paiementTable.addCell(new Phrase(facture.getMontant()-facture.getMontantPaye()-facture.getRemise() + "", NORMAL));
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15, 5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public DocumentInterface getFacture() {
		return facture;
	}

	public void setFacture(DocumentInterface facture) {
		this.facture = facture;
	}

	public String getCheminFichier() {
		return cheminFichier;
	}

	public void setCheminFichier(String cheminFichier) {
		this.cheminFichier = cheminFichier;
	}

	public boolean genererDevis() throws MalformedURLException, IOException {
		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./devis/devis_" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream("./devis/devis_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };
			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell3 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell3.addElement(image);
			headTable.addCell(cell3);
			PdfPCell cell2 = new PdfPCell();
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell2.addElement(infoAr);
			headTable.addCell(cell2);

			doc.add(headTable);

			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Devis: N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));
			doc.add(new Paragraph("\n"));

			doc.add(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));

			doc.add(new Paragraph("Tel:                   " + facture.getTelClient(), NORMALTXT));

			doc.add(new Paragraph("Date:                 " + Date.valueOf(LocalDate.now()), NORMALTXT));

			doc.add(new Paragraph("\n"));

			System.out.println("nombre d'achats: " + facture.getProduitsAchetes().size());
			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrix() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);
			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);
			paiementTable.addCell("Total");
			if(facture.getMontant() != 0) {
				paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			}
			else {
				paiementTable.addCell(new Phrase("", NORMAL));
			}
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15,5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public boolean genererDevisGros() throws MalformedURLException, IOException {
		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./devis/devis_gr" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream("./factures/facture_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);

			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Devis: N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));
			doc.add(new Paragraph("\n"));

			doc.add(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));

			doc.add(new Paragraph("Tel:                   " + facture.getTelClient(), NORMALTXT));

			doc.add(new Paragraph("Date:                 " + Date.valueOf(LocalDate.now()), NORMALTXT));

			doc.add(new Paragraph("\n"));

			System.out.println("nombre d'achats: " + facture.getProduitsAchetes().size());
			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrixGros() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);
			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);

			paiementTable.addCell("Total");
			paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15,5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean genererCommandeGros() throws MalformedURLException, IOException {
		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./commandes/commande_gr" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream("./commandes/commande_gr" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);

			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Commande: N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));
			doc.add(new Paragraph("\n"));

			doc.add(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));

			doc.add(new Paragraph("Tel:                   " + facture.getTelClient(), NORMALTXT));

			doc.add(new Paragraph("Date:                 " + Date.valueOf(LocalDate.now()), NORMALTXT));

			doc.add(new Paragraph("\n"));

			System.out.println("nombre d'achats: " + facture.getProduitsAchetes().size());
			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);

			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrixGros() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);
			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);

			paiementTable.addCell("Total");
			paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15,5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean genererCommande() throws MalformedURLException, IOException {
		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./commandes/commande_" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream("./commandes/commande_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);

			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Commande: N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));
			doc.add(new Paragraph("\n"));

			doc.add(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));

			doc.add(new Paragraph("Tel:                   " + facture.getTelClient(), NORMALTXT));

			doc.add(new Paragraph("Date:                 " + Date.valueOf(LocalDate.now()), NORMALTXT));

			doc.add(new Paragraph("\n"));

			System.out.println("nombre d'achats: " + facture.getProduitsAchetes().size());
			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrix() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);
			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);

			paiementTable.addCell("Total");
			if(facture.getMontant() != 0) {
				paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			}
			else {
				paiementTable.addCell(new Phrase("", NORMAL));
			}
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15,5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean genererBon() throws MalformedURLException, IOException {

		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./bonsdelivraison/bonsdelivraison_" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream("./bonsdelivraison/bonsdelivraison_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);

			doc.add(new Paragraph("\n"));
			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Bon de livraison N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));

			float[] columnWidthsChau = { 5, 5 };
			PdfPTable tableClient = new PdfPTable(columnWidthsChau);
			tableClient.setWidthPercentage(100);

			PdfPCell nomClient = new PdfPCell(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));
			nomClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(nomClient);

			PdfPCell chauffeur = new PdfPCell(new Phrase("Chauffeur:       "+facture.getChauffeur()));
			chauffeur.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(chauffeur);

			PdfPCell tellClient = new PdfPCell(new Paragraph("Tel:                    " + facture.getTelClient(), NORMALTXT));
			tellClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(tellClient);

			PdfPCell direction = new PdfPCell(new Phrase("Direction:        "+facture.getDirection()));
			direction.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(direction);

			PdfPCell dateFacture = new PdfPCell(new Paragraph("Date:                " + Date.valueOf(LocalDate.now()), NORMALTXT));
			dateFacture.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(dateFacture);



			PdfPCell matricule = new PdfPCell(new Phrase("Immatricule:    "+facture.getMatricule()));
			matricule.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(matricule);
			doc.add(tableClient);





			doc.add(new Paragraph("\n"));

			System.out.println("nombre d'achats: " + facture.getProduitsAchetes().size());
			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrix() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);

			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);
			paiementTable.addCell("Remise");
			paiementTable.addCell(new Phrase(facture.getRemise() + "", NORMAL));
			paiementTable.addCell("TVA");
			paiementTable.addCell(new Phrase("0", NORMAL));
			paiementTable.addCell("Total Payé");
			paiementTable.addCell(new Phrase(facture.getMontantPaye() + "", NORMAL));
			paiementTable.addCell("Total");
			paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			paiementTable.addCell("Crédit");
			paiementTable.addCell(new Phrase(facture.getMontant()-facture.getMontantPaye()-facture.getRemise() + "", NORMAL));
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15,5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public boolean genererBonGros() throws MalformedURLException, IOException {

		Document doc = new Document(PageSize.A4);
		try {
			cheminFichier = "./bonsdelivraison/bondelivraison_gr_" + facture.getId() + ".pdf";

			PdfWriter.getInstance(doc, new FileOutputStream("./bonsdelivraison/bondelivraison_gr_" + facture.getId() + ".pdf"));
			doc.open();
			Paragraph infoFr = new Paragraph();
			infoFr.add(new Paragraph(NOM_SOCIETE_FR, TITRE));
			infoFr.add(new Paragraph(INFO_SOCIETE_FR, NORMAL));
			infoFr.add(new Paragraph(MATRICULE_FISCALE, NORMAL));

			Paragraph infoAr = new Paragraph();
			infoAr.setAlignment(Element.ALIGN_RIGHT);
			infoAr.add(new Paragraph(NOM_SOCIETE_AR, TITRE));
			infoAr.add(new Paragraph(INFO_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(ADR_SOCIETE_AR, NORMAL));
			infoAr.add(new Paragraph(CONTACT_SOCIETE_AR, NORMAL));
			float[] columnWidthsHeadTable = { 5, 2, 5 };

			PdfPTable headTable = new PdfPTable(columnWidthsHeadTable);
			headTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			headTable.setWidthPercentage(100);
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(PdfPCell.NO_BORDER);
			cell1.addElement(infoFr);
			headTable.addCell(cell1);
			Image image = Image.getInstance( ClassLoader.getSystemResource("application/views/images/alkhayer.jpg"));
			PdfPCell cell2 = new PdfPCell(image, false);
			image.scaleAbsolute(60f, 90f);
			image.setAlignment(PdfPCell.ALIGN_CENTER);
			cell2.setBorder(PdfPCell.NO_BORDER);
			cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell2.addElement(image);
			headTable.addCell(cell2);
			PdfPCell cell3 = new PdfPCell();
			cell3.setBorder(PdfPCell.NO_BORDER);
			cell3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
			cell3.addElement(infoAr);
			headTable.addCell(cell3);
			doc.add(headTable);
			doc.add(new Paragraph("\n"));
			DecimalFormat df = new DecimalFormat("000000");
			doc.add(new Paragraph("Bon de livraison N° " + df.format(facture.getId()), UNDER));
			doc.add(new Paragraph("\n"));

			float[] columnWidthsChau = { 5, 5 };
			PdfPTable tableClient = new PdfPTable(columnWidthsChau);
			tableClient.setWidthPercentage(100);

			PdfPCell nomClient = new PdfPCell(new Paragraph("Client:               " + facture.getNomClient() + " " + facture.getPrenomClient(),
					NORMALTXT));
			nomClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(nomClient);

			PdfPCell chauffeur = new PdfPCell(new Phrase("Chauffeur:       "+facture.getChauffeur()));
			chauffeur.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(chauffeur);

			PdfPCell tellClient = new PdfPCell(new Paragraph("Tel:                    " + facture.getTelClient(), NORMALTXT));
			tellClient.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(tellClient);

			PdfPCell direction = new PdfPCell(new Phrase("Direction:        "+facture.getDirection()));
			direction.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(direction);

			PdfPCell dateFacture = new PdfPCell(new Paragraph("Date:                " + Date.valueOf(LocalDate.now()), NORMALTXT));
			dateFacture.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(dateFacture);



			PdfPCell matricule = new PdfPCell(new Phrase("Immatricule:    "+facture.getMatricule()));
			matricule.setBorder(PdfPCell.NO_BORDER);
			tableClient.addCell(matricule);
			doc.add(tableClient);

			doc.add(new Paragraph("\n"));

			float[] columnWidths = { 2, 5, 2, 2 };

			PdfPTable table = new PdfPTable(columnWidths);
			table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);

			table.addCell(new Phrase("Quantité"));
			table.addCell(new Phrase("Designation"));
			table.addCell(new Phrase("P.Unit"));
			table.addCell(new Phrase("P.Total"));

			int j = 0;

			for (Panier p : facture.getProduitsAchetes()) {
				PdfPCell cellQte = new PdfPCell(new Phrase(p.getQuantite() + "", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				PdfPCell cellDesign = new PdfPCell(new Phrase(p.getProduit().getDesignation() + "", NORMAL));
				cellDesign.setBorder(PdfPCell.LEFT);
				table.addCell(cellDesign);
				PdfPCell cellPrix = new PdfPCell(new Phrase(p.getProduit().getPrixGros() + "", NORMAL));
				cellPrix.setBorder(PdfPCell.LEFT);
				table.addCell(cellPrix);
				PdfPCell cellTotal = new PdfPCell(new Phrase(p.getTotal() + "", NORMAL));
				cellTotal.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellTotal);
				j++;
			}

			for (int i = j; i < 60; i++) {
				PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
				cellQte.setBorder(PdfPCell.LEFT);
				table.addCell(cellQte);
				table.addCell(cellQte);
				table.addCell(cellQte);
				cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
				table.addCell(cellQte);

			}
			PdfPCell cellQte = new PdfPCell(new Phrase("", NORMAL));
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.addCell(cellQte);
			table.addCell(cellQte);
			cellQte.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.ALIGN_BOTTOM);
			table.addCell(cellQte);
			table.setWidthPercentage(100);

			doc.add(table);
			float[] columnWidthsPaiement = { 5, 2 };
			doc.add(new Phrase("\n"));

			PdfPTable paiementTable = new PdfPTable(columnWidthsPaiement);
			paiementTable.addCell("Montant payé");
			paiementTable.addCell(new Phrase(facture.getMontantPaye() + "", NORMAL));
			paiementTable.addCell("Remise");
			paiementTable.addCell(new Phrase(facture.getRemise() + "", NORMAL));
			paiementTable.addCell("Total");
			if(facture.getMontant() != 0) {
				paiementTable.addCell(new Phrase(facture.getMontant() + "", NORMAL));
			}
			else {
				paiementTable.addCell(new Phrase("", NORMAL));
			}
			paiementTable.addCell("Crédit");
			paiementTable.addCell(new Phrase(facture.getMontant()-facture.getMontantPaye()-facture.getRemise() + "", NORMAL));
			paiementTable.setWidthPercentage(100);
			doc.add(paiementTable);

			doc.add(new Phrase("\n"));
			float[] columnWidthsFooterTable = { 15, 5 };

			PdfPTable footerTable = new PdfPTable(columnWidthsFooterTable);
			footerTable.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			footerTable.setWidthPercentage(100);

			Paragraph signature = new Paragraph("Signature Bureau");
			PdfPCell signatureCell = new PdfPCell();
			signatureCell.setBorder(PdfPCell.NO_BORDER);
			signatureCell.addElement(signature);

			Paragraph total = new Paragraph("Signature Client");
			total.setAlignment(Paragraph.ALIGN_LEFT);
			PdfPCell totalCell = new PdfPCell();
			totalCell.setBorder(PdfPCell.NO_BORDER);
			totalCell.addElement(total);
			totalCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

			footerTable.addCell(signatureCell);
			footerTable.addCell(totalCell);
			doc.add(footerTable);
			// doc.add( new Paragraph("Total: "
			// +facture.getTotal()).ALIGN_RIGHT);

			doc.close();


		} catch (FileNotFoundException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		}
		return true;
	}




	public boolean isGros() {
		return gros;
	}

	public void setGros(boolean gros) {
		this.gros = gros;
	}

	public void genererPDF() throws MalformedURLException, IOException {
		System.out.println(facture.getId());

		System.out.println(facture.getNomClient());
		if (this.facture instanceof Commande) {
			this.setFacture((Commande) this.facture);
			if (gros) {
				this.genererCommandeGros();
			} else {
				this.genererCommande();
				System.out.println("generer commande");

			}

		} else if (this.facture instanceof Devis) {
			this.setFacture((Devis) this.facture);
			if(gros) {
				this.genererDevisGros();
			} else {
				System.out.println("generer devis");
				this.genererDevis();
			}
		} else if (this.facture instanceof Facture) {
			this.setFacture((Facture) this.facture);

			if(gros) {
				this.genererFactureGros();
			} else {
				System.out.println("generer factures");
				this.genererFacture();
			}
		} else if (this.facture instanceof BonDeLivraison) {
			this.setFacture((BonDeLivraison) this.facture);

			if(gros) {
				System.out.println("generer bon");

				this.genererBonGros();
			} else {
				this.genererBon();
			}
		}
	}

}
