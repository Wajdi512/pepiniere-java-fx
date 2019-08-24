package application.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import application.model.BonDeLivraison;

public class BonDeLivraisonDaoImpl implements BonDeLivraisonDaoInterface {

	@Override
	public void addBonDeLivraison(BonDeLivraison bon) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(bon);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public void updateBonDeLivraison(BonDeLivraison bon) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.update(bon);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}

	}

	@Override
	public List<BonDeLivraison> listBonDeLivraison() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<BonDeLivraison> bonDeLivraisonList = session.createQuery("from BonDeLivraison ORDER BY date DESC")
				.setMaxResults(30).list();
		session.close();
		factory.close();
		return bonDeLivraisonList;
	}

	@Override
	public BonDeLivraison getBonDeLivraisonById(int id) {

		BonDeLivraison bon = null;
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		Criteria search = session.createCriteria(BonDeLivraison.class);
		try {
			session.beginTransaction();
			search.add(Restrictions.eq("id", id));
			bon = (BonDeLivraison) search.uniqueResult();
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		return bon;
	}

	@Override
	public void removeBonDeLivraison(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		BonDeLivraison bon = (BonDeLivraison) session.load(BonDeLivraison.class, new Integer(id));
		if (null != bon) {
			session.delete(bon);
		}
		session.getTransaction().commit();
		factory.close();
	}

	@Override
	public List<BonDeLivraison> getBonDeLivraisonByMC(String mc) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<BonDeLivraison> bonDeLivraisonList = session
				.createQuery(
						"from BonDeLivraison WHERE nomClient LIKE '%" + mc + "%' or prenomClient LIKE '%" + mc + "%'")
				.list();
		session.close();
		factory.close();
		return bonDeLivraisonList;
	}

	@Override
	public List<BonDeLivraison> getBonDeLivraisonNonPaye() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<BonDeLivraison> bonDeLivraisonList = session.createQuery("from BonDeLivraison WHERE paye = true").list();
		session.close();
		factory.close();
		return bonDeLivraisonList;
	}

	@Override
	public List<BonDeLivraison> getBonDeLivraisonSansFacture() {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<BonDeLivraison> bonDeLivraisonList = session.createQuery("from BonDeLivraison WHERE facture = null")
				.list();
		session.close();
		factory.close();
		return bonDeLivraisonList;
	}

	@Override
	public void updateBonDeLivraisonMerge(BonDeLivraison bon) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(bon);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public void mergeBonDeLivraisonMerge(BonDeLivraison bon) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.merge(bon);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public List<BonDeLivraison> getBonDeLivraisonCriteria(AbstractCommonCriteria searchCriteria) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		List<BonDeLivraison> bonDeLivraisonList = null;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(BonDeLivraison.class);
			if (searchCriteria.getId() != null)
				search.add(Restrictions.eq("id", searchCriteria.getId()));
			if (searchCriteria.getClient() != null && !searchCriteria.getClient().isEmpty()) {
				search.add(Restrictions.ilike("nomComplet", searchCriteria.getClient(), MatchMode.ANYWHERE));
			}
			if (searchCriteria.getDateDebut() != null) {
				search.add(Restrictions.ge("date", new Timestamp(searchCriteria.getDateDebut().getTime())));
				// search.add(Restrictions.ti));
				// search.add(Restrictions.lt("date",
				// searchCriteria.getDateFin()));

			}
			if (searchCriteria.getDateFin() != null) {
				search.add(Restrictions.le("date", new Timestamp(searchCriteria.getDateFin().getTime())));
				// search.add(Restrictions.ti));
				// search.add(Restrictions.lt("date",
				// searchCriteria.getDateFin()));

			}
			if (searchCriteria.getChauffeur() != null && !searchCriteria.getChauffeur().isEmpty())
				search.add(Restrictions.ilike("chauffeur", searchCriteria.getChauffeur()));

			if (searchCriteria.isPaye() != null) {
				search.add(Restrictions.eq("paye", searchCriteria.isPaye()));

			}
			bonDeLivraisonList = search.list();
		} finally {
			session.close();
			factory.close();
		}
		return bonDeLivraisonList;

	}

	@Override
	public Double getTotalCriteria(AbstractCommonCriteria searchCriteria) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		double total = 0;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(BonDeLivraison.class);
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
			factory.close();
		}
		return total;
	}

	@Override
	public Double getMontantPayeCriteria(AbstractCommonCriteria searchCriteria) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		double total = 0;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(BonDeLivraison.class);
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

}
