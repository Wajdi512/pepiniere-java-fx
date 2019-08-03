package application.metier;

import java.util.List;

import application.dao.AbstractCommonCriteria;
import application.model.BonDeLivraison;
import application.model.Panier;
import application.model.Tranche;

public interface BonDeLivraisonMetierInterface {

	public void enregistrerBonDeLivraison(BonDeLivraison bon);
	public void enregistrerBonDeLivraisonSansModStock(BonDeLivraison bon);
	public void supprimerBonDeLivraison(BonDeLivraison bon);
	public List<BonDeLivraison> getAllBons();
	public void updateBon(BonDeLivraison bon);
	public List<BonDeLivraison> getBonDeLivraisonByMC(String mc);
	public List<BonDeLivraison> getBonDeLivraisonSansFacture();
	public void mergeBon(BonDeLivraison bon);
	public List<Tranche> getBonDeLivraisonTranches(int id);
	public List<Panier> getBonDeLivraisonPaniers(int id);
	public void updatePanier(Panier p);
	public void ajouterPanier(Panier p);
	public void supprimerPanier(Panier p);

	public void ajouterTranche(Tranche t);
	public void supprimerTranche(int id);
	public List<BonDeLivraison> getAllBonsByCriteria(AbstractCommonCriteria abc);

}
