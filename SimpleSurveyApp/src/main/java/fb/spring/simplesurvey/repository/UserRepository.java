/**
 * 
 */
package fb.spring.simplesurvey.repository;

import org.springframework.data.repository.CrudRepository;

import fb.spring.simplesurvey.model.User;

/**
 * @author Florian Becker
 *
 */
public interface UserRepository extends CrudRepository<User, Integer> {

	/**
	 * 
	 * @param login
	 * @return
	 */
	public User findByLogin(String login);

	/**
	 * convenient login method: it merely reads (SQL-SELECT) login and password from
	 * the database and returns an user entity object, if the submitted combination
	 * of login and password could be found
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	public User findByLoginAndPassword(String login, String password);
}
