package dao;

import java.util.List;

import model.Role;
import model.User;

public interface IUserDAO {
	List<User> getAllUser();
	User getUserById(int id);
	User checkUser(String username);
	User getUserByEmail(String email);
	User getUserByPhone(String phone);
    boolean addUser(User user);
    void updateUser(User user);
	boolean deleteUser(int id);
}
