package application.dao;

import java.util.List;

import application.model.BonDeLivraison;
import application.model.Facture;

public interface FactureDao {

	public void addFacture(Facture f);
	public void updateFacture(Facture f);
	public List<Facture> listFacture();
	public List<BonDeLivraison> getBonsDesLivraisons(int idFacture);
	public Facture getFactureById(int id);
	public void removeFacture(int id);
	public List<Facture> getFacturesByMC(String mc);
	public List<Facture> getFactureNonPaye();
	public List<Facture> getFactureCriteria(AbstractCommonCriteria abc);
	Double getTotalCriteria(AbstractCommonCriteria searchCriteria);
	Double getMontantPayeCriteria(AbstractCommonCriteria searchCriteria);


}
