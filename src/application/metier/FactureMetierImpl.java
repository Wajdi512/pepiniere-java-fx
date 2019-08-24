package application.metier;

import java.util.List;

import application.dao.AbstractCommonCriteria;
import application.dao.FactureDao;
import application.dao.FactureDaoImpl;
import application.dao.PanierDaoImpl;
import application.dao.PanierDaoInterface;
import application.dao.ProduitDao;
import application.dao.ProduitDaoImpl;
import application.dao.TrancheDaoImpl;
import application.dao.TrancheDaoInterface;
import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Panier;
import application.model.Produit;
import application.model.Tranche;

/**
 * @author Wajdi
 *
 */
public class FactureMetierImpl implements FactureMetierInterface {

	private FactureDao factureDao;
	private ProduitDao produitDao;
	private PanierDaoInterface panierDao;
	private TrancheDaoInterface trancheDao;

	public FactureDao getFactureDao() {
		return factureDao;
	}

	public void setFactureDao(FactureDao factureDao) {
		this.factureDao = factureDao;
	}

	public FactureMetierImpl() {
		super();
		this.factureDao = new FactureDaoImpl();
		this.produitDao = new ProduitDaoImpl();
		this.panierDao = new PanierDaoImpl();
		this.trancheDao = new TrancheDaoImpl();
	}

	@Override
	public List<Facture> getAllFactures() {
		// TODO Auto-generated method stub
		return factureDao.listFacture();
	}

	public void enregistrerFacture(Facture f) {
		factureDao.addFacture(f);
		/* mettre à jour le stock */
		for (Panier p : f.getProduitsAchetes()) {
			Produit produit = p.getProduit();
			produit.setQuantite(produit.getQuantite() - p.getQuantite());
			produitDao.updateProduit(produit);
		}
	}

	@Override
	public List<Facture> getFactureByMC(String mc) {
		// TODO Auto-generated method stub
		return factureDao.getFacturesByMC(mc);
	}

	@Override
	public Facture getFactureById(int id) {
		// TODO Auto-generated method stub
		return factureDao.getFactureById(id);
	}

	@Override
	public void modifierFacture(Facture f) {

	}

	@Override
	public void supprimerFacture(Facture f) {
		for (Panier p : f.getProduitsAchetes()) {
			Produit produit = p.getProduit();
			produit.setQuantite(produit.getQuantite() + p.getQuantite());
			produitDao.updateProduit(produit);
		}
		factureDao.removeFacture(f.getId());
	}

	@Override
	public void updateFacture(Facture f, Facture oldf) {

		factureDao.updateFacture(f);

	}

	@Override
	public void enregistrerBonDeLivraisonToFacture(Facture f) {
		factureDao.addFacture(f);
	}

	@Override
	public List<Tranche> getFactureTranches(int id) {
		return trancheDao.getTrancheByIdFacture(id);
	}

	@Override
	public List<Panier> getFacturePaniers(int id) {
		return panierDao.getPanierByFacture(id);
	}

	@Override
	public void updatePanier(Panier p) {
		Panier p1 = panierDao.getPanierById(p.getId());
		int nvstock = p.getQuantite() - p1.getQuantite();
		Produit prod = p.getProduit();
		prod.setQuantite(prod.getQuantite() - nvstock);
		produitDao.updateProduit(prod);
	}

	@Override
	public void ajouterPanier(Panier p) {
		panierDao.addPanier(p);
		Produit prod = p.getProduit();
		prod.setQuantite(prod.getQuantite() - p.getQuantite());
		produitDao.updateProduit(prod);
	}

	@Override
	public void supprimerPanier(Panier p) {
		Produit produit = p.getProduit();
		produit.setQuantite(produit.getQuantite() + p.getQuantite());
		produitDao.updateProduit(produit);
		panierDao.deletePanier(p.getId());
	}

	@Override
	public void ajouterTranche(Tranche t) {
		trancheDao.addTranche(t);
	}

	@Override
	public void supprimerTranche(int id) {
		trancheDao.supprimerTranche(id);
	}

	@Override
	public List<Facture> getFactureCriteria(AbstractCommonCriteria searchCriteria) {
		return factureDao.getFactureCriteria(searchCriteria);
	}

	@Override
	public List<BonDeLivraison> getBonsDesLivraisons(int idFacture) {
		return factureDao.getBonsDesLivraisons(idFacture);
	}

}
