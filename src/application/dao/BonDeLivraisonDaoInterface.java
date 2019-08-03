package application.dao;

import java.util.List;

import application.model.BonDeLivraison;

public interface BonDeLivraisonDaoInterface {

	public void addBonDeLivraison(BonDeLivraison bon);
	public void updateBonDeLivraison(BonDeLivraison bon);
	public List<BonDeLivraison> listBonDeLivraison();
	public BonDeLivraison getBonDeLivraisonById(int id);
	public void removeBonDeLivraison(int id);
	public List<BonDeLivraison> getBonDeLivraisonByMC(String mc);
	public List<BonDeLivraison> getBonDeLivraisonNonPaye();
	public List<BonDeLivraison> getBonDeLivraisonSansFacture();
	public void updateBonDeLivraisonMerge(BonDeLivraison bon);
	public void mergeBonDeLivraisonMerge(BonDeLivraison bon);
	public List<BonDeLivraison> getBonDeLivraisonCriteria(AbstractCommonCriteria searchCriteria);
	Double getMontantPayeCriteria(AbstractCommonCriteria searchCriteria);
	Double getTotalCriteria(AbstractCommonCriteria searchCriteria);



}
