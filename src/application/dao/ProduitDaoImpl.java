package application.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.Produit;

public class ProduitDaoImpl implements ProduitDao{

	@Override
	public void addProduit(Produit p) {
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
	public void updateProduit(Produit p) {
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
	public List<Produit> listProduit() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Produit> produitsList = session.createQuery("from Produit where actif = true").setMaxResults(40).list();
		session.getTransaction().commit();
		factory.close();
		return	produitsList;
	}

	@Override
	public Produit getProduitById(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		Produit p = (Produit) session.load(Produit.class, new Integer(id));
		session.close();
		factory.close();
		return p;
	}

	@Override
	public void removeProduit(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Produit p = (Produit) session.load(Produit.class, new Integer(id));
		if(null != p) {
			session.delete(p);
		}
		session.getTransaction().commit();
		factory.close();
	}

	@Override
	public List<Produit> listProduitByMC(String mc) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Produit> produitsList = session.createQuery("from Produit where designation LIKE '%"+mc+"%' and actif = true").list();
		session.getTransaction().commit();
		factory.close();
		return	produitsList;
	}

	@Override
	public void mergeProduit(Produit p) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.merge(p);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

}
