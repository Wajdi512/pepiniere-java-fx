package application.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.Absence;
import application.model.CoutDeJour;
import application.model.Facture;

public class CoutDeJourDaoImpl implements CoutJourDaoInterface {

	@Override
	public void add(CoutDeJour cdj) {
		// TODO Auto-generated method stub
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(cdj);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public void delete(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		CoutDeJour f = (CoutDeJour) session.load(CoutDeJour.class, new Integer(id));
		if (null != f) {
			session.delete(f);
		}
		session.getTransaction().commit();
		factory.close();
	}

	@Override
	public List<CoutDeJour> getCoutDeJourByOuvrier(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<CoutDeJour> coutDeJours = session.createQuery("from CoutDeJour where ouvrier=" + id).list();
		session.close();
		factory.close();
		return coutDeJours;
	}

	@Override
	public List<CoutDeJour> getCoutDeJourByOuvrierBetwenDate(int id, Date start, Date end) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<CoutDeJour> absences = session
				.createQuery(
						"from CoutDeJour where ouvrier.id=" + id + "and " + "dateCreation BETWEEN :startDate AND :endDate")
				.setTimestamp("startDate", start).setTimestamp("endDate", end).list();
		session.close();
		factory.close();
		return absences;
	}

}
