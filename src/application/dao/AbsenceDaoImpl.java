package application.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import application.model.Absence;
import application.model.Facture;

public class AbsenceDaoImpl implements AbsenceDaoInterface {

	@Override
	public List<Absence> getAbsenceByCdj(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Absence> absences = session.createQuery("from Absence where coutDeJour.id=" + id).list();
		session.close();
		factory.close();
		return absences;
	}

	@Override
	public List<Absence> getAbsenceByOuvrierByDate(Date start, Date end, int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		List<Absence> absences = session
				.createQuery(
						"from Absence where ouvrier.id=" + id + "and " + "dateAbsence BETWEEN :startDate AND :endDate")
				.setTimestamp("startDate", start).setTimestamp("endDate", end).list();
		session.close();
		factory.close();
		return absences;
	}

	@Override
	public void addAbsence(Absence absence) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(absence);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

	@Override
	public long countPresence(Date start, Date end,int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		long absences = (long)session
				.createQuery(
						"select count(*) from Absence where ouvrier.id=" + id + "and " + "dateAbsence BETWEEN :startDate AND :endDate")
				.setTimestamp("startDate", start).setTimestamp("endDate", end).uniqueResult();
		session.close();
		factory.close();
		return absences;
	}

	@Override
	public List<Absence> listAbsenceBetwenDate(Date start, Date end,Integer[] ids) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		String query = "from Absence where coutDeJour.id in :ids";
		List<Absence> absences = session
				.createQuery(
						"from Absence where coutDeJour.id in :ids and " + "dateAbsence >= :startDate AND dateAbsence <=:endDate")
				.setParameterList("ids", ids).
				setTimestamp("startDate", start).setTimestamp("endDate", end).list();
		session.close();
		factory.close();
		return absences;
	}

	@Override
	public void removeAbsence(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Absence f = (Absence) session.load(Absence.class, new Integer(id));
		if(null != f) {
			session.delete(f);
		}
		session.getTransaction().commit();
		factory.close();

	}

	@Override
	public List<Absence> listAbsence(Integer[] ids) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		String query = "from Absence where coutDeJour.id in :ids";
		List<Absence> absences = session
				.createQuery(
						"from Absence where coutDeJour.id in :ids")
				.setParameterList("ids", ids).list();
		session.close();
		factory.close();
		return absences;
	}

}
