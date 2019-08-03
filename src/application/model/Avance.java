package application.model;

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
@Table(name="avances")
public class Avance {
	@Id
	@Column(name="id")
	@GeneratedValue
	private int id;
	@Column(name="montant")
	private double montant;
	@Column(name="date_avance")
	private Date dateAvance;
	@OneToOne
	@JoinColumn(name="ouvrier_id")
	private Ouvrier ouvrier;


	public Avance() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Avance(double montant, Date dateAvance, Ouvrier ouvrier) {
		super();
		this.montant = montant;
		this.dateAvance = dateAvance;
		this.ouvrier = ouvrier;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDateAvance() {
		return dateAvance;
	}
	public void setDateAvance(Date dateAvance) {
		this.dateAvance = dateAvance;
	}
	public Ouvrier getOuvrier() {
		return ouvrier;
	}
	public void setOuvrier(Ouvrier ouvrier) {
		this.ouvrier = ouvrier;
	}



}
