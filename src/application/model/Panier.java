package application.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="paniers")
public class Panier implements Serializable,Cloneable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="quantite")
	private int quantite;

	@Column(name="total")
	private Double total;

	@OneToOne
	@JoinColumn(name="id_produit")
	private Produit produit;
	@OneToOne
	@JoinColumn(name="idFacture")
	private Facture facture;

	@OneToOne
	@JoinColumn(name="idBon")
	private BonDeLivraison bon;

	@OneToOne
	@JoinColumn(name="idCmd")
	private Commande cmd;

	@OneToOne
	@JoinColumn(name="idDevis")
	private Devis devis;




	public Panier(int id, int quantite, Produit produit, Facture factures) {
		super();
		this.id = id;
		this.quantite = quantite;
		this.produit = produit;
		this.facture = factures;
	}

	public Panier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void calculerTotal(){
		this.total = this.produit.getPrix() * this.quantite;
	}

	public void calculerTotalGros(){
		this.total = this.produit.getPrixGros() * this.quantite;
	}

	public BonDeLivraison getBon() {
		return bon;
	}

	public void setBon(BonDeLivraison bon) {
		this.bon = bon;
	}

	public Commande getCmd() {
		return cmd;
	}

	public void setCmd(Commande cmd) {
		this.cmd = cmd;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public Devis getDevis() {
		return devis;
	}

	public void setDevis(Devis devis) {
		this.devis = devis;
	}





}
