package application.dao;

import java.util.List;

import application.model.Ouvrier;

public interface OuvrierDaoInterface {

	public void ajouterOuvrier(Ouvrier o);
	public void update(Ouvrier o);
	public void supprimerOuvrier(int id);
	public List<Ouvrier> listeOuvrier();
	public List<Ouvrier> listeOuvrierByCriteria(AbstractCommonCriteria criteria);


}
