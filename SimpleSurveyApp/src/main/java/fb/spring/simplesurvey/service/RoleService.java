/**
 * 
 */
package fb.spring.simplesurvey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.spring.simplesurvey.model.Role;
import fb.spring.simplesurvey.repository.RoleRepository;

/**
 * @author Florian "lefko" Becker
 *
 *         this class is merely an abstracting layer for accessing the
 *         RoleRepository
 */
@Service
public class RoleService {

	@Autowired
	RoleRepository rRepository;

	/**
	 * 
	 * @param role
	 * @return
	 */
	public Role findByRole(String role) {
		return rRepository.findByRole(role);
	}
}
