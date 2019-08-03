package application.metier;

import java.util.List;

import application.dao.AbstractCommonCriteria;
import application.dao.BonDeLivraisonDaoImpl;
import application.dao.BonDeLivraisonDaoInterface;
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

public class BonDeLivraisonMetierImpl implements BonDeLivraisonMetierInterface {

    private BonDeLivraisonDaoInterface bonDeLivraisonDao;
    private ProduitDao produitDao;
    private PanierDaoInterface panierDao;
    private TrancheDaoInterface trancheDao;


    public BonDeLivraisonMetierImpl() {
        super();
        this.bonDeLivraisonDao = new BonDeLivraisonDaoImpl();
        this.produitDao = new ProduitDaoImpl();
        this.panierDao = new PanierDaoImpl();
        this.trancheDao = new TrancheDaoImpl();
    }

    @Override
    public void enregistrerBonDeLivraison(BonDeLivraison bon) {
        bonDeLivraisonDao.addBonDeLivraison(bon);
        for(Panier p: bon.getProduitsAchetes()) {
            Produit produit = p.getProduit();
            produit.setQuantite(produit.getQuantite() - p.getQuantite());
            produitDao.updateProduit(produit);
        }
    }

    @Override
    public void supprimerBonDeLivraison(BonDeLivraison bon) {
    	for(Panier p: bon.getProduitsAchetes()) {
            Produit produit = p.getProduit();
            produit.setQuantite(produit.getQuantite() + p.getQuantite());
            produitDao.updateProduit(produit);
        }
    	bonDeLivraisonDao.removeBonDeLivraison(bon.getId());
    }

    @Override
    public List<BonDeLivraison> getAllBons() {
        return bonDeLivraisonDao.listBonDeLivraison();
    }

    @Override
    public void enregistrerBonDeLivraisonSansModStock(BonDeLivraison bon) {
        bonDeLivraisonDao.addBonDeLivraison(bon);

    }

    @Override
    public void updateBon(BonDeLivraison bon) {
       		/*for (Panier p : bon.getProduitsAchetes()) {
			Produit prod = p.getProduit();
			if (p.getId() != 0) {
				// update
				// si le panier est modifié
				Panier panierOrigine = panierDao.getPanierById(p.getId());
				if (p.getQuantite() != panierOrigine.getQuantite()) {
					panierDao.addPanier(p);
					int qte = p.getQuantite() - panierOrigine.getQuantite();
					prod.setQuantite(prod.getQuantite() + qte);
					produitDao.updateProduit(prod);
				}

			} else {
				// ajout
				panierDao.addPanier(p);
				prod.setQuantite(prod.getQuantite() - p.getQuantite());
				produitDao.updateProduit(prod);
			}
		}
        //mettre à jour les tranches
		for(Tranche t: bon.getTranches()){
			if(t.getId()==0) {

			}
		}*/
        bonDeLivraisonDao.updateBonDeLivraison(bon);
    }

    @Override
    public List<BonDeLivraison> getBonDeLivraisonByMC(String mc) {
        // TODO Auto-generated method stub
        return bonDeLivraisonDao.getBonDeLivraisonByMC(mc);
    }

    @Override
    public List<BonDeLivraison> getBonDeLivraisonSansFacture() {
        // TODO Auto-generated method stub
        return bonDeLivraisonDao.getBonDeLivraisonSansFacture();
    }

    @Override
    public void mergeBon(BonDeLivraison bon) {
        // TODO Auto-generated method stub
        bonDeLivraisonDao.mergeBonDeLivraisonMerge(bon);
    }

	@Override
	public List<Tranche> getBonDeLivraisonTranches(int id) {
		// TODO Auto-generated method stub
		return trancheDao.getTrancheByIdBon(id);
	}

	@Override
	public List<Panier> getBonDeLivraisonPaniers(int id) {
		// TODO Auto-generated method stub
		return panierDao.getPanierByBon(id);
	}

	@Override
	public void updatePanier(Panier p) {
		// TODO Auto-generated method stub
		Panier p1 = panierDao.getPanierById(p.getId());
		int nvstock = p.getQuantite()-p1.getQuantite();
		Produit prod = p.getProduit();
		prod.setQuantite(prod.getQuantite() - nvstock);
		produitDao.updateProduit(prod);
	}

	@Override
	public void ajouterTranche(Tranche t) {
		trancheDao.addTranche(t);
	}

	@Override
	public void supprimerTranche(int id) {
		// TODO Auto-generated method stub
		trancheDao.supprimerTranche(id);
	}

	@Override
	public void ajouterPanier(Panier p) {
		// TODO Auto-generated method stub
		panierDao.addPanier(p);
		Produit prod = p.getProduit();
		prod.setQuantite(prod.getQuantite()-p.getQuantite());
		produitDao.updateProduit(prod);
	}

	@Override
	public void supprimerPanier(Panier p) {
		// TODO Auto-generated method stub
		Produit produit = p.getProduit();
		produit.setQuantite(produit.getQuantite() + p.getQuantite());
		produitDao.updateProduit(produit);
		panierDao.deletePanier(p.getId());
	}

	@Override
	public List<BonDeLivraison> getAllBonsByCriteria(AbstractCommonCriteria abc) {
		// TODO Auto-generated method stub
		return bonDeLivraisonDao.getBonDeLivraisonCriteria(abc);
	}

}
