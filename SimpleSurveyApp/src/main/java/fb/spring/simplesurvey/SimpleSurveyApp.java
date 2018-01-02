/**
 * 
 */
package fb.spring.simplesurvey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import fb.spring.simplesurvey.service.SurveyService;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author fbecke12
 *
 */
@Component
public class SimpleSurveyApp implements CommandLineRunner {

	@Autowired
	UserService uService;

	@Autowired
	SurveyService sService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {

		/*
		 * User fbUser = new User(); fbUser.setFirstname("Florian");
		 * fbUser.setSurname("Becker"); fbUser.setLogin("fb");
		 * fbUser.setPassword("fbfbfb".getBytes());
		 * 
		 * uService.addUser(fbUser);
		 * 
		 * Survey survey = new Survey(); survey.setCompletions(0);
		 * survey.setCreator(fbUser); survey.setDesc("My first survey");
		 * survey.setLabel("test survey"); survey.setQuestions(0);
		 * 
		 * sService.addSurvey(survey);
		 */

	}

}
