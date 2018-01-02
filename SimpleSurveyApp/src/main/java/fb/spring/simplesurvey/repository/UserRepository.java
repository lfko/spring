/**
 * 
 */
package fb.spring.simplesurvey.repository;

import org.springframework.data.repository.CrudRepository;

import fb.spring.simplesurvey.model.User;

/**
 * @author fbecke12
 *
 */
public interface UserRepository extends CrudRepository<User, Integer> {

	/**
	 * 
	 * @param login
	 * @return
	 */
	public User findByLogin(String login);

	public User findByLoginAndPassword(String login, byte[] password);
}
