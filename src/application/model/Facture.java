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

import org.hibernate.annotations.Formula;

import application.io.DocumentInterface;

@Entity
@Table(name="factures")
public class Facture implements Serializable, Cloneable, DocumentInterface{

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

    @Column(name="chauffeur")
    private String chauffeur;

    @Column(name="matricule")
    private String matricule;

    @Column(name="direction")
    private String direction;

	@Column(name="date")
	private Date date;

	@Column(name="paye")
	private boolean paye;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="idFacture")
	private List<Panier> produitsAchetes;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="idFacture")
	private List<Tranche> tranches;

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="idFacture")
	private List<BonDeLivraison> bonsDeLivraisons;
    @Formula(value ="nom_client ||' '||prenom_client")
    private String nomComplet;



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
	public double getRemise() {
		return remise;
	}
	public void setRemise(double remise) {
		this.remise = remise;
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


	public List<Panier> getProduitsAchetes() {
		return produitsAchetes;
	}
	public void setProduitsAchetes(List<Panier> produitsAchetes) {
		this.produitsAchetes = produitsAchetes;
	}
	public double getMontantPaye() {
		return montantPaye;
	}
	public void setMontantPaye(double montantPaye) {
		this.montantPaye = montantPaye;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isPaye() {
		return paye;
	}
	public void setPaye(boolean paye) {
		this.paye = paye;
	}
	public List<Tranche> getTranches() {
		return tranches;
	}
	public void setTranches(List<Tranche> tranches) {
		this.tranches = tranches;
	}
	public List<BonDeLivraison> getBonsDeLivraisons() {
		return bonsDeLivraisons;
	}
	public void setBonsDeLivraisons(List<BonDeLivraison> bonsDeLivraisons) {
		this.bonsDeLivraisons = bonsDeLivraisons;
	}
	public Facture() {
		super();
		tranches = new ArrayList<Tranche>();
		produitsAchetes = new ArrayList<Panier>();
		this.matricule ="";
		this.chauffeur="";
		this.direction="";
		this.nomClient="";
		this.prenomClient="";

	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public double getCredit() {
		double credit = this.getMontant();
		credit -= this.getRemise();
		credit -= this.getAvance();
		for (Tranche t : this.getTranches()) {
			credit -= t.getMontant();
		}
		return credit;
	}
	public boolean isGros() {
		if(this.produitsAchetes != null && !this.produitsAchetes.isEmpty()){
			return this.produitsAchetes.get(0).getTotal() == this.produitsAchetes.get(0).getProduit().getPrixGros() * this.produitsAchetes.get(0).getQuantite();
		}
		return false;
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
	public String getNomComplet() {
		return nomComplet;
	}
	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}
	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}

}
