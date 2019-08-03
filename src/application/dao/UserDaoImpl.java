package application.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import application.model.User;

public class UserDaoImpl implements UserDaoInterface{

	@Override
	public User getUserById(int id) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		User user = null;
		try {
			session.beginTransaction();
			Criteria search = session.createCriteria(User.class);
			search.add(Restrictions.eq("id", id));
			user = (User) search.uniqueResult();
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		return user;
	}

	@Override
	public User getUserByNameAndPassword(String name, String password) {
		// TODO Auto-generated method stub
				SessionFactory factory = DBConnexion.getSessionFactory();
				Session session = factory.getCurrentSession();
				User user = null;
				try {
					session.beginTransaction();
					Criteria search = session.createCriteria(User.class);
					search.add(Restrictions.eq("pseudo", name));
					search.add(Restrictions.eq("motDePasse", password));
					user = (User) search.uniqueResult();
					session.getTransaction().commit();
				} finally {
					factory.close();
				}
				return user;
	}

	@Override
	public User updateUser(User user) {
		SessionFactory factory = DBConnexion.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
		session.beginTransaction();
		session.merge(user);
		session.getTransaction().commit();
		}finally {
			factory.close();

		}
		return user;

	}

}
