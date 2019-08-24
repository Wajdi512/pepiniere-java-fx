package application.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import application.model.BonDeLivraison;
import application.model.Facture;

public class FactureDaoImpl implements FactureDao {

	@Override
	public void addFacture(Facture f) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(f);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public void updateFacture(Facture f) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.update(f);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}

	}

	@Override
	public List<Facture> listFacture() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Facture> facturesList = session.createQuery("from Facture ORDER BY date DESC").setMaxResults(30).list();
		session.close();
		factory.close();
		return facturesList;
	}

	@Override
	public Facture getFactureById(int id) {
		Facture f = null;
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		Criteria search = session.createCriteria(BonDeLivraison.class);
		try {
			session.beginTransaction();
			search.add(Restrictions.eq("id", id));
			f = (Facture) search.uniqueResult();
			session.getTransaction().commit();
		} finally {
			factory.close();
		}

		return f;

	}

	@Override
	public void removeFacture(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Facture f = (Facture) session.load(Facture.class, new Integer(id));
		if (null != f) {
			session.delete(f);
		}
		session.getTransaction().commit();
		factory.close();

	}

	@Override
	public List<Facture> getFacturesByMC(String mc) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Facture> facturesList = session
				.createQuery("from Facture WHERE nomClient LIKE '%" + mc + "%' or prenomClient LIKE '%" + mc + "%'")
				.list();
		session.close();
		factory.close();
		return facturesList;
	}

	@Override
	public List<Facture> getFactureNonPaye() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Facture> facturesList = session.createQuery("from Facture where paye = true").list();
		session.close();
		factory.close();
		return facturesList;
	}

	@Override
	public List<Facture> getFactureCriteria(AbstractCommonCriteria searchCriteria) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		List<Facture> factures = null;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(Facture.class);
			if (searchCriteria.getId() != null)
				search.add(Restrictions.eq("id", searchCriteria.getId()));
			if (searchCriteria.getClient() != null && !searchCriteria.getClient().isEmpty()) {
				search.add(Restrictions.ilike("nomComplet", searchCriteria.getClient(), MatchMode.ANYWHERE));
			}
			if (searchCriteria.getDateDebut() != null && searchCriteria.getDateFin() != null)
				search.add(Restrictions.between("date", searchCriteria.getDateDebut(), searchCriteria.getDateFin()));
			if (searchCriteria.getChauffeur() != null && !searchCriteria.getChauffeur().isEmpty())
				search.add(Restrictions.ilike("chauffeur", searchCriteria.getChauffeur()));

			if (searchCriteria.isPaye() != null) {
				search.add(Restrictions.eq("paye", searchCriteria.isPaye()));

			}
			factures = search.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

		} finally {
			session.close();
			factory.close();
		}
		return factures;
	}

	@Override
	public Double getTotalCriteria(AbstractCommonCriteria searchCriteria) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		double total = 0;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(Facture.class);
			if (searchCriteria.getId() != null)
				search.add(Restrictions.eq("id", searchCriteria.getId()));
			if (searchCriteria.getClient() != null && !searchCriteria.getClient().isEmpty()) {
				search.add(Restrictions.ilike("nomComplet", searchCriteria.getClient(), MatchMode.ANYWHERE));
			}
			if (searchCriteria.getDateDebut() != null && searchCriteria.getDateFin() != null)
				search.add(Restrictions.between("date", searchCriteria.getDateDebut(), searchCriteria.getDateFin()));
			if (searchCriteria.getChauffeur() != null && !searchCriteria.getChauffeur().isEmpty())
				search.add(Restrictions.ilike("chauffeur", searchCriteria.getChauffeur()));

			if (searchCriteria.isPaye() != null) {
				search.add(Restrictions.eq("paye", searchCriteria.isPaye()));

			}
			search.setProjection(Projections.sum("montant"));
			total = (double) search.uniqueResult();
		} finally {
			session.close();
			factory.close();
		}
		return total;
	}

	@Override
	public Double getMontantPayeCriteria(AbstractCommonCriteria searchCriteria) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		double total = 0;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(Facture.class);
			if (searchCriteria.getId() != null)
				search.add(Restrictions.eq("id", searchCriteria.getId()));
			if (searchCriteria.getClient() != null && !searchCriteria.getClient().isEmpty()) {
				search.add(Restrictions.ilike("nomComplet", searchCriteria.getClient(), MatchMode.ANYWHERE));
			}
			if (searchCriteria.getDateDebut() != null && searchCriteria.getDateFin() != null)
				search.add(Restrictions.between("date", searchCriteria.getDateDebut(), searchCriteria.getDateFin()));
			if (searchCriteria.getChauffeur() != null && !searchCriteria.getChauffeur().isEmpty())
				search.add(Restrictions.ilike("chauffeur", searchCriteria.getChauffeur()));

			if (searchCriteria.isPaye() != null) {
				search.add(Restrictions.eq("paye", searchCriteria.isPaye()));

			}
			search.setProjection(Projections.sum("montantPaye"));
			total = (double) search.uniqueResult();
		} finally {
			session.close();
			factory.close();
		}
		return total;
	}

	@Override
	public List<BonDeLivraison> getBonsDesLivraisons(int idFacture) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		List<BonDeLivraison> bons = new ArrayList<BonDeLivraison>(0);
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(BonDeLivraison.class);
			search.add(Restrictions.eq("facture.id", idFacture));
			bons = search.list();
		} finally {
			session.close();
			factory.close();
		}
		return bons;
	}

}
