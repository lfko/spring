/**
 * 
 */
package fb.spring.simplesurvey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.repository.UserRepository;

/**
 * @author Florian Becker
 *
 */
@Service
public class UserService {

	@Autowired
	UserRepository repository;

	/**
	 * service method for returning a list with all currently registered users
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		return (List<User>) repository.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(int id) {

		return repository.findOne(id);
	}

	public User getUser(String login) {

		return repository.findByLogin(login);
	}

	public User addUser(User user) {

		return repository.save(user);
	}

	public void delete(Integer id) {

		repository.delete(id);
	}

	public User login(String login, String password) {

		return repository.findByLoginAndPassword(login, password);
	}

}
