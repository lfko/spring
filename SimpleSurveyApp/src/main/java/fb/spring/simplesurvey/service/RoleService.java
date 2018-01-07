/**
 * 
 */
package fb.spring.simplesurvey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.spring.simplesurvey.model.Role;
import fb.spring.simplesurvey.repository.RoleRepository;

/**
 * @author Florian Becker
 *
 *         this class is merely an abstracting layer for accessing the
 *         RoleRepository
 */
@Service
public class RoleService {

	@Autowired
	RoleRepository rRepository;

	/**
	 * finds a role by its simple name (e.g. 'Admin')
	 * 
	 * @param role
	 * @return
	 */
	public Role findByRole(String role) {
		return rRepository.findByRole(role);
	}

	/**
	 * returns a set with all available roles as a set of Role-objects
	 * 
	 * @return
	 */
	public List<Role> findAll() {
		return (List<Role>) rRepository.findAll();
	}
}
