package application.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public abstract class AbstractDao<T, P> {

	public void add(T t){
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(t);
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}


}
