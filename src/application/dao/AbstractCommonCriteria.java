package application.dao;

import java.util.Date;

public  class AbstractCommonCriteria {

	private Date dateDebut;
	private Date dateFin;
	private Integer id;
	private String client;
	private String chauffeur;
	private Boolean paye;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getChauffeur() {
		return chauffeur;
	}
	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}
	public Boolean isPaye() {
		return paye;
	}
	public void setPaye(Boolean paye) {
		this.paye = paye;
	}



}
