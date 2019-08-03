package application.dao;

import java.util.List;

import application.model.Panier;

public interface PanierDaoInterface {

	public void addPanier(Panier p);
	public Panier getPanierById(int id);

	public void deletePanier(int id);
	public void updatePanier(Panier p);
	public List<Panier> getPanierByFacture(int id);
	public List<Panier> getPanierByBon(int id);
	public List<Panier> getPanierByCommande(int id);
	public List<Panier> getPanierByDevis(int id);
	public void updatePanierMerge(Panier p);



}
