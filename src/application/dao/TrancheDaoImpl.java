package application.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import application.model.BonDeLivraison;
import application.model.Facture;
import application.model.Tranche;

public class TrancheDaoImpl implements TrancheDaoInterface{

	@Override
	public List<Tranche> getTranchePaye() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tranche> getTrancheByIdFacture(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		Criteria search = null;
		List<Tranche> tranches = new ArrayList<Tranche>(0);
		try {
			session.beginTransaction();
			search = session.createCriteria(Tranche.class);
			search.add(Restrictions.eq("facture.id", id));
			tranches = search.list();
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		return tranches;
	}

	@Override
	public List<Tranche> getTrancheByIdBon(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Tranche> tranches = session.createQuery("from Tranche where bonDeLivraison.id="+id).list();
		session.close();
		factory.close();

		return tranches;
	}

	@Override
	public void addTranche(Tranche t) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

	@Override
	public void supprimerTranche(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Tranche t = (Tranche) session.load(Tranche.class, new Integer(id));
		if(null != t) {
			session.delete(t);
		}
		session.getTransaction().commit();
		factory.close();
	}

}
