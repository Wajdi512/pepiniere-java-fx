package application.metier;

import java.util.List;

import application.dao.CommandeDaoImpl;
import application.dao.CommandeDaoInterface;
import application.dao.PanierDaoImpl;
import application.dao.PanierDaoInterface;
import application.model.Commande;
import application.model.Panier;
import application.model.Produit;

public class CommandeMetierImpl implements CommandeMetierInterface {

	private CommandeDaoInterface commandeDao;
	private PanierDaoInterface panierDao;
	public CommandeMetierImpl() {
		super();
		commandeDao = new CommandeDaoImpl();
		panierDao = new PanierDaoImpl();
	}

	@Override
	public void enregistrerCommande(Commande cmd) {
		commandeDao.addCommande(cmd);
	}

	@Override
	public List<Commande> getAllCommande() {
		return commandeDao.listCommande();
	}

	@Override
	public void updateCommande(Commande cmd) {
		// TODO Auto-generated method stub
		commandeDao.updateCommande(cmd);
	}

	@Override
	public List<Commande> getCommandesByMC(String mc) {
		// TODO Auto-generated method stub
		return commandeDao.getCommandesbyMC(mc);
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
	public List<Panier> getCommandePanier(int id) {
		// TODO Auto-generated method stub
		return panierDao.getPanierByCommande(id);
	}

	@Override
	public void supprimerCommande(Commande c) {
		// TODO Auto-generated method stub
		commandeDao.removeCommande(c.getId());
	}

}
