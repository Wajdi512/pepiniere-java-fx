package application.metier;

import java.util.List;

import application.dao.AbstractCommonCriteria;
import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Panier;
import application.model.Tranche;

public interface FactureMetierInterface {
	public void enregistrerFacture(Facture f);
	public List<Facture> getAllFactures();
	public List<Facture> getFactureByMC(String mc);
	public Facture getFactureById(int id);
	public void modifierFacture(Facture f);
	public void supprimerFacture(Facture f);
	public void updateFacture(Facture f,Facture old);
	public void enregistrerBonDeLivraisonToFacture(Facture f);
	public List<Tranche> getFactureTranches(int id);
	public List<Panier> getFacturePaniers(int id);
	public void updatePanier(Panier p);
	public void ajouterPanier(Panier p);
	public void supprimerPanier(Panier p);
	public List<BonDeLivraison> getBonsDesLivraisons(int idFacture);

	public void ajouterTranche(Tranche t);
	public void supprimerTranche(int id);
	public List<Facture> getFactureCriteria(AbstractCommonCriteria searchCriteria);



}
