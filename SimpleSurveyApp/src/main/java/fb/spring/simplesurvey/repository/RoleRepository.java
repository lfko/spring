/**
 * 
 */
package fb.spring.simplesurvey.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fb.spring.simplesurvey.model.Role;

/**
 * @author Florian "lefko" Becker
 *
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

	/**
	 * add more methods for individual queryies here
	 */

	/**
	 * SELECT * FROM t_role WHERE role=<role>
	 * 
	 * @param role
	 * @return
	 */
	public Role findByRole(String role);
}
