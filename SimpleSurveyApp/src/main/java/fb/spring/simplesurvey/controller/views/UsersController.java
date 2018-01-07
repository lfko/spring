/**
 * 
 */
package fb.spring.simplesurvey.controller.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.service.RoleService;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author Florian Becker
 *
 */
@Controller
public class UsersController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	UserService uService;

	@Autowired
	RoleService rService;

	/**
	 * return a list of all registered users (if any)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String list(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		model.addAttribute("users", uService.getUsers());

		// add the currents user name to the page model, so we can decide, whether we
		// want to display a delete button or not (shoud a user delete itself, when he
		// is still logged in?)
		model.addAttribute("loggedInUser", auth.getName());

		return "users";
	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("user/{id}")
	public String showUser(@PathVariable Integer id, Model model) {

		// Now, we’re using a new annotation @Pathvariable to inject the id
		// value from the url path into our controller as the ID variable

		model.addAttribute("user", uService.getUser(id));
		return "users";
	}

	@RequestMapping("user/delete/{id}")
	public String delete(@PathVariable Integer id) {

		// Now, we’re using a new annotation @Pathvariable to inject the id
		// value from the url path into our controller as the ID variable

		logger.info("user with id " + id + " will be deleted!");

		uService.delete(id);

		return "users";
	}

	@RequestMapping(value = "user/create", method = RequestMethod.POST)
	public String create(@ModelAttribute User newUser) {

		newUser.setActive(true);

		// Role userRole = rService.findByRole("ADMIN");
		// newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

		uService.addUser(newUser);

		return "redirect:/users";
	}

	/**
	 * create a new product and save it into the database
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("user/new")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", rService.findAll());

		return "userform";
	}

}
