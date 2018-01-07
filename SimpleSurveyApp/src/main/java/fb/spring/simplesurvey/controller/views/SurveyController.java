/**
 * 
 */
package fb.spring.simplesurvey.controller.views;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;

import fb.spring.simplesurvey.model.Answer;
import fb.spring.simplesurvey.model.Survey;
import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.service.AnswerService;
import fb.spring.simplesurvey.service.SurveyService;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author Florian Becker
 *
 */
@Controller
public class SurveyController {

	private static Logger logger = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	private SurveyService sService;

	@Autowired
	private AnswerService aService;

	@Autowired
	private UserService uService;

	private static int questionsCount, questionsCompleted;

	private Survey openSurvey;

	/**
	 * return a list of all registered users (if any)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/surveys", method = RequestMethod.GET)
	public String list(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// load all surveys from the database for listing them in the view
		List<Survey> allSurveys = sService.findAllSurveys();
		model.addAttribute("surveys", allSurveys);

		User currentUser = uService.getUser(auth.getName());

		/**
		 * now we have to check, whether one of the surveys has already been completed
		 * by the current user - if so, we offer a way to view the answers
		 */
		for (Survey s : allSurveys) {

			List<Answer> answersToSurvey = s.getAnswer();
			if (answersToSurvey != null) {

				Answer ans = answersToSurvey.stream()
						.filter(answer -> answer.getRespondent().getId() == currentUser.getId()).findFirst()
						.orElse(null);

				if (ans != null) {

					// lamba expression checks, if the answers list contains an answer object, that
					// references to the current user
					s.setCompletedByUser(true);
				} else {
					s.setCompletedByUser(false);
				}
			}
		}

		return "surveys";
	}

	/**
	 * this is the entry point to begin a new survey
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("survey/{id}")
	public String openSurvey(@PathVariable Integer id, Model model) {

		// lookup the appropriate entity object in the database
		openSurvey = sService.findSurvey(null, id);
		// how many questions will be there?
		questionsCount = openSurvey.getQuestions();
		questionsCompleted = 0;

		model.addAttribute("survey", sService.findSurvey(null, id));

		model.addAttribute("template", "survey_question_" + questionsCompleted);

		model.addAttribute("answer", new Answer());

		return "/questions/survey_page";
	}

	/**
	 * create a new product and save it into the database
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("survey/new")
	public String newSurvey(Model model) {
		model.addAttribute("survey", new Survey());
		return "surveyform";
	}

	/**
	 * create a new product and save it into the database
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("survey/delete/{id}")
	public String deleteSurvey(@PathVariable Integer id) {

		logger.info("survey with id " + id + " will be deleted!");

		sService.deleteSurvey(id);

		return "redirect:/surveys";
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/survey", method = RequestMethod.POST)
	public String saveSurvey(@ModelAttribute Survey survey, @RequestParam(required = false, value = "save") String save,
			@RequestParam(required = false, value = "savestart") String saveStart) {

		sService.addSurvey(survey);

		if (save != null)
			return "redirect:/surveys";
		else
			return "redirect:/survey/" + survey.getId();
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/survey/answer", method = RequestMethod.POST)
	public String saveSurveyAnswers(@ModelAttribute Answer answer, HttpSession session, Model model,
			@RequestParam(required = false, value = "forward") String forward,
			@RequestParam(required = false, value = "backward") String backward) {

		Answer answerToView;
		boolean showingAnswer = false;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currUser = uService.getUser(auth.getName());

		if (backward != null)
			questionsCompleted--;
		else
			questionsCompleted++;

		// set the user, who had answered the question, to the one currently logged in
		answer.setRespondent(currUser);
		answer.setSurvey(openSurvey);

		if (session.getAttribute("showingAnswer") != null)
			showingAnswer = Boolean.valueOf(session.getAttribute("showingAnswer").toString());

		answer.setQuestionId(questionsCompleted);

		if (showingAnswer == false) {
			logger.info("saving answer for question #" + questionsCompleted);
			logger.info("setting values to: ");
			logger.info("questionId: " + answer.getQuestionId());
			logger.info("optionId: " + answer.getOptionId());

			aService.saveAnwser(answer);
		} else {

			/**
			 * lambda expression for searching answers corresponding to the current user' id
			 * in the list of answers, related to the opened survey
			 */
			answerToView = openSurvey.getAnswer().stream().filter(
					a -> a.getQuestionId() == (questionsCompleted + 1) && a.getRespondent().getId() == currUser.getId())
					.findFirst().orElse(null);

			logger.info("loading answer for question " + (questionsCompleted + 1));

			if (answerToView != null) {
				logger.info("found an answer (id: " + answerToView.getId() + ")");
				logger.info("optionId " + answerToView.getOptionId());
				logger.info("questionId " + answerToView.getQuestionId());
				model.addAttribute("answer", answerToView);
			}
		}

		if (questionsCompleted == questionsCount) {

			// if the user has finished all the questions, increment the counter and save it
			// to the survey entity
			if (showingAnswer == false) {
				openSurvey.setCompletions(openSurvey.getCompletions() + 1);

				sService.updateSurvey(openSurvey);
			}

			session.setAttribute("showingAnswer", null);

			return "redirect:/surveys/";
		}

		model.addAttribute("survey", openSurvey);

		model.addAttribute("template", "survey_question_" + questionsCompleted);

		return "/questions/survey_page";
	}

	/**
	 * opens the survey (and its subsequent answer pages) to view the answers a user
	 * has given before
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/survey/{id}/showAnswers", method = RequestMethod.GET)
	public String showSurveyAnswers(@PathVariable Integer id, HttpSession session, Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currUser = uService.getUser(auth.getName());

		logger.info("showAnswers ... ");

		// lookup the appropriate entity object in the database
		openSurvey = sService.findSurvey(null, id);
		// how many questions will be there?
		questionsCount = openSurvey.getQuestions();
		questionsCompleted = 0;

		logger.info("loading answer for question " + (questionsCompleted + 1));

		model.addAttribute("survey", openSurvey);

		Answer answer = openSurvey.getAnswer().stream().filter(
				a -> a.getQuestionId() == (questionsCompleted + 1) && a.getRespondent().getId() == currUser.getId())
				.findFirst().orElse(null);

		if (answer != null) {
			logger.info("found an answer (id: " + answer.getId() + ")");
			logger.info("optionId " + answer.getOptionId());
			logger.info("questionId " + answer.getQuestionId());
			model.addAttribute("answer", answer);
		}

		session.setAttribute("showingAnswer", true);
		model.addAttribute("template", "survey_question_" + questionsCompleted);

		return "/questions/survey_page";
	}
}
