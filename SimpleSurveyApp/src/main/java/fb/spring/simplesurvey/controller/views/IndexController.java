/**
 * 
 */
package fb.spring.simplesurvey.controller.views;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	/**
	 * '@Autowired' tells spring to look for the corresponding implementation on its
	 * own
	 */
	@Autowired
	SurveyService sService;

	@RequestMapping("*")
	public String index(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		logger.info(auth.getName());

		for (GrantedAuthority ga : auth.getAuthorities()) {
			logger.info(ga.getAuthority());
		}

		return "home";
	}

}
