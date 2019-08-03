package application.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.Absence;
import application.model.Avance;

public class AvanceDaoImpl implements AvanceDaoInterface {

	@Override
	public List<Avance> getAllAvanceByOuvrier(int idOuvrier) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Avance> avances = session.createQuery("from Avance where ouvrier.id=" + idOuvrier).list();
		session.close();
		factory.close();
		return avances;
	}

	@Override
	public void addAvance(Avance avance) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(avance);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public List<Avance> getAvanceOuvrierByMonth(Date startDate, Date endDate, int idOuvrier) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		String month = "";

		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Avance> avances = session
				.createQuery("from Avance where " + "ouvrier.id =" + idOuvrier + " " + "and "
						+ "dateAvance BETWEEN :startDate AND :endDate")
				.setTimestamp("startDate", startDate).setTimestamp("endDate", endDate).list();
		session.close();
		factory.close();
		return avances;
	}

	@Override
	public Double getSommeAvance(Date startDate, Date endDate, int idOuvrier) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		try {
			double sommeAvance = (Double)session
					.createQuery(
							"select sum(montant) from Avance where ouvrier.id=" + idOuvrier + "and " + "dateAvance BETWEEN :startDate AND :endDate")
					.setTimestamp("startDate", startDate).setTimestamp("endDate", endDate).uniqueResult();
			return sommeAvance;
		}catch (NullPointerException e) {
			return 0d;
		}finally {
			session.close();
			factory.close();
		}
	}

	@Override
	public void removeAvance(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Avance f = (Avance) session.load(Avance.class, new Integer(id));
		if(null != f) {
			session.delete(f);
		}
		session.getTransaction().commit();
		factory.close();

	}

}
