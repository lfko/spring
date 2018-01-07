/**
 * 
 */
package fb.spring.simplesurvey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.spring.simplesurvey.misc.exception.NoDataFoundException;
import fb.spring.simplesurvey.model.Survey;
import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.repository.SurveyRepository;

/**
 * @author fbecke12
 *
 *         spring service class, which holds and provides the business logic for
 *         dealing with Survey entities
 */
@Service
public class SurveyService {

	@Autowired
	private SurveyRepository repository;

	@Autowired
	private UserService uService;

	/**
	 * service method for adding a new survey to the application/into the database
	 * 
	 * @param label
	 *            Naming label
	 * @param desc
	 *            Description
	 * 
	 * @return Survey entity instance
	 */
	public Survey addNewSurvey(String label, String desc) {

		Survey survey = new Survey();
		// since we are adding a new survey here, there are still no completions
		// as well as questions available
		survey.setCompletions(0);
		survey.setQuestions(0);
		survey.setDesc(desc);
		survey.setLabel(label);

		return repository.save(survey);
	}

	public Survey addSurvey(Survey survey) {

		return repository.save(survey);
	}

	public Survey updateSurvey(Survey survey) {

		return repository.save(survey);
	}

	/**
	 * service method for deleting an existing survey
	 * 
	 * @param id
	 */
	public void deleteSurvey(int id) {

		repository.delete(id);
	}

	/**
	 * service method for finding an existing survey, either by label or id
	 * 
	 * @param label
	 * @param id
	 * @return
	 */
	public Survey findSurvey(String label, int id) {

		// if the caller provided a label, we shall use this for querying the
		// database for the wanted survey

		if (label != null)
			return repository.findByLabel(label);
		else
			return repository.findOne(id);

	}

	/**
	 * service method for finding all saved surveys, irregardless of its creator
	 * 
	 * @return
	 */
	public List<Survey> findAllSurveys() {
		return (List<Survey>) repository.findAll();
	}

	/**
	 * service method for finding all saved surveys to a given user
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public List<Survey> findAllSurveysToUser(String login) throws NoDataFoundException {

		// at first, let us find the corresponding user aka the creator
		User creator = uService.getUser(login);

		// then look for the associated surveys
		if (creator != null) {
			return repository.findByCreator(creator);
		} else
			throw new NoDataFoundException("No surveys were found for the given login");
	}
}
