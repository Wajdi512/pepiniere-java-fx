package application.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tranches")
public class Tranche implements Serializable, Cloneable {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@OneToOne
	@JoinColumn(name="idFacture")
	private Facture facture;
	@OneToOne
	@JoinColumn(name="idBonDeLivraison")
	private BonDeLivraison bonDeLivraison;
	@Column(name="montant")
	private double montant;
	@Column(name="date")
	private Date date;


	public Tranche() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Tranche(int id, Facture facture, double montant, Date date) {
		super();
		this.id = id;
		this.facture = facture;
		this.montant = montant;
		this.date = date;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Facture getFacture() {
		return facture;
	}
	public void setFacture(Facture facture) {
		this.facture = facture;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}


	public BonDeLivraison getBonDeLivraison() {
		return bonDeLivraison;
	}


	public void setBonDeLivraison(BonDeLivraison bonDeLivraison) {
		this.bonDeLivraison = bonDeLivraison;
	}


}
