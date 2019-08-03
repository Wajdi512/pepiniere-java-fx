package application.metier;

import java.util.List;

import application.model.Commande;
import application.model.Panier;

public interface CommandeMetierInterface {

	public void enregistrerCommande(Commande cmd);
	public List<Commande> getAllCommande();
	public void updateCommande(Commande cmd);
	public List<Commande> getCommandesByMC(String mc);
	public void updatePanier(Panier p);
	public void ajouterPanier(Panier p);
	public void supprimerPanier(Panier p);
	public List<Panier> getCommandePanier(int id);
	public void supprimerCommande(Commande c);


}
