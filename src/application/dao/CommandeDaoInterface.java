package application.dao;

import java.util.List;

import application.model.Commande;

public interface CommandeDaoInterface {

	public void addCommande(Commande cmd);
	public void updateCommande(Commande cmd);
	public List<Commande> listCommande();
	public Commande getCommandeById(int id);
	public List<Commande> getCommandesbyMC(String mc);
	public void removeCommande(int id);
}
