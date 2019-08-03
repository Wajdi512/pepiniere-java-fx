package application.dao;

import java.util.List;

import application.model.Devis;

public interface DevisDaoInterface {

	public void addDevis(Devis d);
	public void removeDevis(int id);
	public List<Devis> getAllDevis();
	public List<Devis> getDevisByMC(String mc);
	public Devis getDevisById(int id);
	public void updateDevis(Devis d);
}
