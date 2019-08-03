package application.metier;

import application.model.User;

public interface UserMetierInterface {

	public User getUserById(int id);
	public User authentification(String name,String pass);
	public User update(User user);
}
