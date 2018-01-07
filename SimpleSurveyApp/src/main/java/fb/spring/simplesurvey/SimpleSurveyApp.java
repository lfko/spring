/**
 * 
 */
package fb.spring.simplesurvey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fb.spring.simplesurvey.model.Role;
import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author Florian Becker
 *
 */
@Component
public class SimpleSurveyApp implements CommandLineRunner {

	@Autowired
	private UserService uService;;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {

		/**
		 * the following codes creates an initial user at startup - to avoid, that this
		 * will happen at every start up, we'll check, if the specific user had been
		 * persisted before
		 */

		if (uService.getUser("admin") == null) {

			// at first, we need to create a new user role - ADMIN
			Role role = new Role();
			role.setRole("ADMIN");

			// since there is a relationship User - Role, we only have to add the freshly
			// created role to the user object
			// when the user object is saved, the role object will be saved accordingly
			List<Role> roles = new ArrayList<Role>();
			roles.add(role);

			User admin = new User();
			admin.setFirstname("Admin");
			admin.setSurname("AdminNachname");
			admin.setLogin("admin");
			admin.setPassword("admin01");
			admin.setRoles(roles);
			admin.setActive(true);

			uService.addUser(admin);
		}

	}

}
