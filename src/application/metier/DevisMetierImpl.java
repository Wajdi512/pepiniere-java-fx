package application.metier;

import java.util.List;

import application.dao.DevisDaoImpl;
import application.dao.DevisDaoInterface;
import application.dao.PanierDaoImpl;
import application.dao.PanierDaoInterface;
import application.model.Devis;
import application.model.Panier;

public class DevisMetierImpl implements DevisMetierInterface {

	private DevisDaoInterface devisDao;
	private PanierDaoInterface panierDao;


	public DevisMetierImpl() {
		super();
		this.devisDao = new DevisDaoImpl();
		this.panierDao = new PanierDaoImpl();
		// TODO Auto-generated constructor stub
	}



	public DevisMetierImpl(DevisDaoInterface devisDao) {
		super();
		this.devisDao = devisDao;
	}



	@Override
	public void addDevis(Devis d) {
		// TODO Auto-generated method stub
		devisDao.addDevis(d);
	}

	@Override
	public void removeDevis(int id) {
		// TODO Auto-generated method stub
		devisDao.removeDevis(id);
	}

	@Override
	public List<Devis> getAllDevis() {
		// TODO Auto-generated method stub
		return devisDao.getAllDevis();
	}

	@Override
	public List<Devis> getDevisByMC(String mc) {
		// TODO Auto-generated method stub
		return devisDao.getDevisByMC(mc);
	}

	@Override
	public Devis getDevisById(int id) {
		// TODO Auto-generated method stub
		return devisDao.getDevisById(id);
	}

	@Override
	public void updateDevis(Devis d) {
		// TODO Auto-generated method stub
		devisDao.updateDevis(d);
	}

	@Override
	public void updatePanier(Panier p) {
		// TODO Auto-generated method stub
		panierDao.updatePanier(p);
	}

	@Override
	public void ajouterPanier(Panier p) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		panierDao.addPanier(p);
	}

	@Override
	public void supprimerPanier(Panier p) {
		// TODO Auto-generated method stub
		panierDao.deletePanier(p.getId());
	}

	@Override
	public List<Panier> getDevisPanier(int id) {
		// TODO Auto-generated method stub
		return panierDao.getPanierByDevis(id);
	}

}
