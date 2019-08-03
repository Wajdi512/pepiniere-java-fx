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

import application.io.DocumentInterface;

@Entity
@Table(name="commandes")
public class Commande implements DocumentInterface{


	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="montant")
	private double montant;

	@Column(name="montant_paye")
	private double montantPaye;

	@Column(name="remise")
	private double remise;

	@Column(name="avance")
	private double avance;

	@Column(name="nom_client")
	private String nomClient;

	@Column(name="prenom_client")
	private String prenomClient;

	@Column(name="tel_client")
	private String telClient;

	@Column(name="date")
	private Date date;

	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="idCommande")
	private List<Panier> produitsAchetes;

	public Commande() {
		super();
		this.nomClient = "";
		this.prenomClient = "";
	}

	public Commande(int id, double montant, double montantPaye, double avance, String nomClient, String prenomClient,
			String telClient, Date date, List<Panier> produitsAchetes) {
		super();
		this.id = id;
		this.montant = montant;
		this.montantPaye = montantPaye;
		this.avance = avance;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.telClient = telClient;
		this.date = date;
		this.produitsAchetes = produitsAchetes;
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

	public double getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(double montantPaye) {
		this.montantPaye = montantPaye;
	}

	public double getAvance() {
		return avance;
	}

	public void setAvance(double avance) {
		this.avance = avance;
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

	public double calculerTotalCommande(){
		double somme = 0;
		for (Panier p: this.getProduitsAchetes()) somme += p.getProduit().getPrix() * p.getQuantite();
		this.montant = somme;
		return somme;
	}

	public double calculerTotalCommandeGros(){
		double somme = 0;
		for (Panier p: this.getProduitsAchetes()) somme += p.getProduit().getPrixGros() * p.getQuantite();
		this.montant = somme;
		return somme;
	}

	@Override
	public double getRemise() {
		// TODO Auto-generated method stub
		return this.remise;
	}

	public void setRemise(double remise) {
		this.remise = remise;
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
