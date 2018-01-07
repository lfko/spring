/**
 * 
 */
package fb.spring.simplesurvey.controller.views;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fb.spring.simplesurvey.service.SurveyService;

/**
 * @author fbecke12
 *
 *         spring controller for intercepting calls to the index.html
 */
@Controller
public class IndexController {

	/**
	 * '@Autowired' tells spring to look for the corresponding implementation on its
	 * own
	 */
	@Autowired
	SurveyService sService;

	@RequestMapping(value = { "/home", "/" })
	public String index(Model model, HttpSession session) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		model.addAttribute("user", auth.getName());
		model.addAttribute("details", auth.getDetails());
		model.addAttribute("roles", auth.getAuthorities());

		session.setAttribute("showingAnswer", null);

		return "home";
	}

}
