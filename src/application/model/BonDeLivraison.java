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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import application.io.DocumentInterface;

@Entity
@Table(name="bond_de_livraisons")
public class BonDeLivraison implements Serializable,DocumentInterface, Cloneable{

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="montant")
    private double montant;

    @Column(name="remise")
    private double remise;

    @Column(name="avance")
    private double avance;

    @Column(name="montant_paye")
    private double montantPaye;


    @Column(name="nom_client")
    private String nomClient;

    @Column(name="prenom_client")
    private String prenomClient;

    @Column(name="chauffeur")
    private String chauffeur;

    @Column(name="matricule")
    private String matricule;

    @Column(name="direction")
    private String direction;

    @Column(name="tel_client")
    private String telClient;

    @Column(name="date")
    private Date date;

    @Column(name="paye")
    private boolean paye;

    @OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="idBon")
    private List<Panier> produitsAchetes;

    @OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="idBon")
    private List<Tranche> tranches;

    @OneToOne(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="idFacture")
    private Facture facture;
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

    public List<Panier> getProduitsAchetes() {
        return produitsAchetes;
    }

    public void setProduitsAchetes(List<Panier> produitsAchetes) {
        this.produitsAchetes = produitsAchetes;
    }

    public List<Tranche> getTranches() {
        return tranches;
    }

    public void setTranches(List<Tranche> tranches) {
        this.tranches = tranches;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(double montantPaye) {
        this.montantPaye = montantPaye;
    }


    public BonDeLivraison() {
        super();
        // TODO Auto-generated constructor stub
        this.produitsAchetes = new ArrayList<>();
        this.tranches = new ArrayList();
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

    public String getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(String chauffeur) {
        this.chauffeur = chauffeur;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

}
