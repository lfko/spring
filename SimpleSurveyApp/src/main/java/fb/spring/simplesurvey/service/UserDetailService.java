/**
 * 
 */
package fb.spring.simplesurvey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fb.spring.simplesurvey.model.LoginUser;
import fb.spring.simplesurvey.model.User;

/**
 * @author Florian "lefko" Becker
 *
 */
@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	UserService uService;

	@Override
	public UserDetails loadUserByUsername(String login) {

		User user = uService.getUser(login);

		if (user == null) {
			throw new UsernameNotFoundException(login);
		}

		return new LoginUser(user);
	}

}
