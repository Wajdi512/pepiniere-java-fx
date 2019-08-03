package application.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.BonDeLivraison;
import application.model.Commande;

public class CommandeDaoImpl implements CommandeDaoInterface {

	@Override
	public void addCommande(Commande cmd) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.save(cmd);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

	@Override
	public void updateCommande(Commande cmd) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.update(cmd);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

	@Override
	public List<Commande> listCommande() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Commande> commandes = session.createQuery("from Commande").list();
		session.close();
		factory.close();
		return	commandes;
	}

	@Override
	public Commande getCommandeById(int id) {
		Commande cmd =null;
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		cmd = (Commande) session.load(Commande.class, new Integer(id));

		session.getTransaction().commit();
		return cmd;
	}

	@Override
	public List<Commande> getCommandesbyMC(String mc) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Commande> commandes = session.createQuery("from Commande WHERE nomClient LIKE '%"+mc+"%' or prenomClient LIKE '%"+mc+"%'").list();
		session.close();
		factory.close();
		return	commandes;
	}

	@Override
	public void removeCommande(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Commande cmd = (Commande) session.load(Commande.class, new Integer(id));
		if(null != cmd) {
			session.delete(cmd);
		}
		session.getTransaction().commit();
		factory.close();
	}

}
