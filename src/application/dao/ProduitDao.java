package application.dao;

import java.util.List;

import application.model.Produit;

public interface ProduitDao {

	public void addProduit(Produit p);
	public void updateProduit(Produit p);
	public void mergeProduit(Produit p);
	public List<Produit> listProduit();
	public List<Produit> listProduitByMC(String mc);
	public Produit getProduitById(int id);
	public void removeProduit(int id);

}
