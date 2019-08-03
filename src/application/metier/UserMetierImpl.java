package application.metier;

import application.dao.UserDaoImpl;
import application.dao.UserDaoInterface;
import application.model.User;

public class UserMetierImpl implements UserMetierInterface{

	private UserDaoInterface userDao;


	public UserMetierImpl() {
		super();
		userDao = new UserDaoImpl();
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	@Override
	public User authentification(String name, String pass) {
		// TODO Auto-generated method stub
		return userDao.getUserByNameAndPassword(name, pass);
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	}


}
