package application.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="produits")
public class Produit implements Serializable{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="designation")
	private String designation;

	@Column(name="prix")
	private double prix;

	@Column(name="prix_gros")
	private double prixGros;

	@Column(name="quantite")
	private int quantite;

	@Column(name="date_ajout")
	private Date dateAjout;

	@Column(name="actif")
	private boolean actif;



	public Produit() {
		super();
	}

	public Produit(String designation, double prix, double prixGros, int quantite, Date dateAjout,
			boolean actif) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.prixGros = prixGros;
		this.quantite = quantite;
		this.dateAjout = dateAjout;
		this.actif = actif;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public double getPrixGros() {
		return prixGros;
	}
	public void setPrixGros(double prixGros) {
		this.prixGros = prixGros;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public Date getDateAjout() {
		return dateAjout;
	}
	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return "Produit [id=" + id + ", designation=" + designation + ", prix=" + prix + ", prixGros=" + prixGros
				+ ", quantite=" + quantite + ", dateAjout=" + dateAjout + ", actif=" + actif + "]";
	}




}
