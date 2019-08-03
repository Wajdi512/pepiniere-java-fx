package application.statsbeans;
import application.model.Facture;

import java.util.Date;
import java.util.List;

import application.model.BonDeLivraison;;


public class StatsBean {

	private List<Facture> facture;
	private List<BonDeLivraison> bons;
	private Double credit;
	private Double total;
	private Double totalPaye;
	private Date dateDebut;
	private Date dateFin;
	private String nomClient;

	public List<Facture> getFacture() {
		return facture;
	}
	public void setFacture(List<Facture> facture) {
		this.facture = facture;
	}
	public List<BonDeLivraison> getBons() {
		return bons;
	}
	public void setBons(List<BonDeLivraison> bons) {
		this.bons = bons;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getTotalPaye() {
		return totalPaye;
	}
	public void setTotalPaye(Double totalPaye) {
		this.totalPaye = totalPaye;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
}
