package application.metier;

import java.util.List;

import application.model.Devis;
import application.model.Panier;

public interface DevisMetierInterface {

	public void addDevis(Devis d);
	public void removeDevis(int id);
	public List<Devis> getAllDevis();
	public List<Devis> getDevisByMC(String mc);
	public Devis getDevisById(int id);
	public void updateDevis(Devis d);
	public void updatePanier(Panier p);
	public void ajouterPanier(Panier p);
	public void supprimerPanier(Panier p);
	public List<Panier> getDevisPanier(int id);
}
