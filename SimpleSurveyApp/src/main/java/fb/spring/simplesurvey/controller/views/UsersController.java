/**
 * 
 */
package fb.spring.simplesurvey.controller.views;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fb.spring.simplesurvey.model.Role;
import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.service.RoleService;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author fbecke12
 *
 */
@Controller
public class UsersController {

	@Autowired
	UserService uService;

	@Autowired
	RoleService rService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * return a list of all registered users (if any)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("users", uService.getUsers());
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

		uService.delete(id);
		return "users";
	}

	@RequestMapping(value = "user/create", method = RequestMethod.POST)
	public String create(@ModelAttribute User newUser) {

		// hash the typed in password and save the hash string value to the database
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

		newUser.setActive(true);

		Role userRole = rService.findByRole("ADMIN");
		newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

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
		return "userform";
	}

}
