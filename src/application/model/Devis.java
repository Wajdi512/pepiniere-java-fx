package application.model;

import java.io.Serializable;
import java.util.ArrayList;
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

import application.io.DocumentInterface;

@Entity
@Table(name="devis")
public class Devis implements Serializable,DocumentInterface{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="montant")
	private double montant;

	@Column(name="remise")
	private double remise;

	@Column(name="nom_client")
	private String nomClient;

	@Column(name="prenom_client")
	private String prenomClient;

	@Column(name="tel_client")
	private String telClient;

	@Column(name="date")
	private Date date;

	@OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="idDevis")
	private List<Panier> produitsAchetes;

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

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}

	public String getTelClient() {
		return telClient;
	}

	public void setTelClient(String telClient) {
		this.telClient = telClient;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Panier> getProduitsAchetes() {
		return produitsAchetes;
	}

	public void setProduitsAchetes(List<Panier> produitsAchetes) {
		this.produitsAchetes = produitsAchetes;
	}

	public Devis(int id, double montant, String nomClient, String prenomClient, String telClient, Date date,
			List<Panier> produitsAchetes) {
		super();
		this.id = id;
		this.montant = montant;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.telClient = telClient;
		this.date = date;
		this.produitsAchetes = produitsAchetes;
	}

	public Devis() {
		super();
		this.produitsAchetes = new ArrayList(0);
		// TODO Auto-generated constructor stub
		this.nomClient = "";
		this.prenomClient = "";
	}

	public double calculerTotalDevis(){
		double somme = 0;
		somme -= this.remise;
		for (Panier p: this.getProduitsAchetes()) somme += p.getProduit().getPrix() * p.getQuantite();
		this.montant = somme;
		return somme;
	}

	public double calculerTotalDevisGros(){
		double somme = 0;
		somme -= this.remise;
		for (Panier p: this.getProduitsAchetes()) somme += p.getProduit().getPrixGros() * p.getQuantite();
		this.montant = somme;
		return somme;
	}

	@Override
	public double getRemise() {
		// TODO Auto-generated method stub
		return remise;
	}



	public void setRemise(double remise) {
		this.remise = remise;
	}

	@Override
	public double getAvance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getMontantPaye() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getChauffeur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMatricule() {
		// TODO Auto-generated method stub
		return null;
	}

}
