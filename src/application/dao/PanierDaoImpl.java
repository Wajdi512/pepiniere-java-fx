package application.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.Facture;
import application.model.Panier;
import application.model.Tranche;

public class PanierDaoImpl implements PanierDaoInterface {

	@Override
	public void addPanier(Panier p) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}

	}

	@Override
	public void deletePanier(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Panier p = (Panier) session.load(Panier.class, new Integer(id));
		if(null != p) {
			session.delete(p);
		}
		session.getTransaction().commit();
		factory.close();

	}

	@Override
	public void updatePanier(Panier p) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

	@Override
	public List<Panier> getPanierByFacture(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Panier> paniers = session.createQuery("from Panier where facture.id="+id).list();
		session.close();
		factory.close();
		return paniers;
	}

	@Override
	public List<Panier> getPanierByBon(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Panier> paniers = session.createQuery("from Panier where bon.id="+id).list();
		session.close();
		factory.close();
		return paniers;
	}

	@Override
	public List<Panier> getPanierByCommande(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Panier> paniers = session.createQuery("from Panier where cmd.id="+id).list();
		session.close();
		factory.close();
		return paniers;
	}

	@Override
	public List<Panier> getPanierByDevis(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Panier> paniers = session.createQuery("from Panier where devis.id="+id).list();
		session.close();
		factory.close();
		return paniers;
	}

	@Override
	public void updatePanierMerge(Panier p) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Panier p1 = (Panier) session.merge(p);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public Panier getPanierById(int id) {
		Panier p =null;
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		p = (Panier) session.load(Panier.class, new Integer(id));

		session.getTransaction().commit();
		return p;
	}

}
