package dao;

import domain.User;

public interface UserDao {

	void add(User user);

	void update(User user);

	User find(String id);

	User find(String username, String password);

	boolean findUser(String username);

}