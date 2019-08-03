package application.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import application.model.Facture;
import application.model.Ouvrier;

public class OuvrierDaoImpl implements OuvrierDaoInterface {


	@Override
	public void ajouterOuvrier(Ouvrier o) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}

	}

	@Override
	public void update(Ouvrier o) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
		}finally {
			factory.close();
		}

	}

	@Override
	public void supprimerOuvrier(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Ouvrier o = (Ouvrier) session.load(Ouvrier.class, new Integer(id));
		if(null != o) {
			session.delete(o);
		}
		session.getTransaction().commit();
		factory.close();
	}

	@Override
	public List<Ouvrier> listeOuvrier() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Ouvrier> ouvrierList = session.createQuery("from Ouvrier").list();
		session.close();
		factory.close();
		return	ouvrierList;
	}

	@Override
	public List<Ouvrier> listeOuvrierByCriteria(AbstractCommonCriteria searchCriteria) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(Ouvrier.class);

			if (searchCriteria.getClient() != null && !searchCriteria.getClient().isEmpty()) {
				search.add(Restrictions.ilike("nomComplet", searchCriteria.getClient(), MatchMode.ANYWHERE));
			}
			List<Ouvrier> ouvriers = search.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			return ouvriers;
		} finally {
			factory.close();
		}
	}

}
