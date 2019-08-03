package application.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="ouvriers")
public class Ouvrier {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="nom")
	private String nom;
	@Column(name="prenom")
	private String prenom;
	@Column(name="fonction")
	private String fonction;
	@OneToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
	@JoinColumn(name="ouvrier_id")
	private List<CoutDeJour> mesAbsences;
	@JoinColumn(name="ouvrier_id")
	@OneToMany(cascade=CascadeType.REMOVE,fetch=FetchType.LAZY)
	private List<Avance> mesAvances;
    @Formula(value ="nom ||' '||prenom")
    private String nomComplet;
	@Column(name="date_ajout")
    private Date dateAjout;

	public Ouvrier() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ouvrier(int id, String nom, String prenom, String fonction,
			List<CoutDeJour> mesAbsences) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
		this.mesAbsences = mesAbsences;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public List<CoutDeJour> getMesAbsences() {
		return mesAbsences;
	}
	public void setMesAbsences(List<CoutDeJour> mesAbsences) {
		this.mesAbsences = mesAbsences;
	}
	public List<Avance> getMesAvances() {
		return mesAvances;
	}
	public void setMesAvances(List<Avance> mesAvances) {
		this.mesAvances = mesAvances;
	}
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public Date getDateAjout() {
		return dateAjout;
	}
	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}

}
