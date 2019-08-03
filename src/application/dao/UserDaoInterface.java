package application.dao;

import application.model.User;

public interface UserDaoInterface {

	public User getUserById(int id);
	public User getUserByNameAndPassword(String name,String password);
	public User updateUser(User user);
}
