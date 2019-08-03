package application.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.Devis;
import application.model.Facture;

public class DevisDaoImpl implements DevisDaoInterface {

	@Override
	public void addDevis(Devis d) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.save(d);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

	@Override
	public void removeDevis(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Devis d = session.load(Devis.class, new Integer(id));
		if(null != d) {
			session.delete(d);
		}
		session.getTransaction().commit();
		factory.close();
	}

	@Override
	public List<Devis> getAllDevis() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Devis> devisList = session.createQuery("from Devis").list();
		session.close();
		factory.close();
		return	devisList;
	}

	@Override
	public List<Devis> getDevisByMC(String mc) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Devis> devisList = session.createQuery("from Devis WHERE nomClient LIKE '%"+mc+"%' or prenomClient LIKE '%"+mc+"%'").list();
		session.close();
		factory.close();
		return	devisList;
	}

	@Override
	public Devis getDevisById(int id) {
		Devis d =null;
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		d = (Devis) session.load(Devis.class, new Integer(id));

		session.getTransaction().commit();
		return d;
	}

	@Override
	public void updateDevis(Devis d) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.update(d);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

}
