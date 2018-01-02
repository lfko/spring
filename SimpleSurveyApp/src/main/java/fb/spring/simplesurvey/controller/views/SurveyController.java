/**
 * 
 */
package fb.spring.simplesurvey.controller.views;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fb.spring.simplesurvey.model.Answer;
import fb.spring.simplesurvey.model.Survey;
import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.service.AnswerService;
import fb.spring.simplesurvey.service.SurveyService;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author fbecke12
 *
 */
@Controller
public class SurveyController {

	private static Logger logger = LoggerFactory.getLogger(SurveyController.class);

	@Autowired
	SurveyService sService;

	@Autowired
	AnswerService aService;

	@Autowired
	UserService uService;

	private static int questionsCount, questionsCompleted;

	private Survey openSurvey;

	/**
	 * @throws IOException
	 * 
	 */
	@PostConstruct
	private void init() {
		logger.info("@PostConstruct called ...");

	}

	/**
	 * return a list of all registered users (if any)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/surveys", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("surveys", sService.findAllSurveys());
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
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping("/survey")
	public String saveSurvey(@ModelAttribute Survey survey) {

		sService.addSurvey(survey);

		return "redirect:/survey/" + survey.getId();
	}

	/**
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/survey/answer", method = RequestMethod.POST)
	public String saveSurveyAnswers(@ModelAttribute Answer answer, Model model) {

		logger.info(String.valueOf(answer.getOptionId()));
		logger.info(String.valueOf(answer.getQuestionId()));

		User user = uService.getUser("test");

		answer.setRespondent(user);
		answer.setSurvey(openSurvey);

		questionsCompleted++;

		aService.saveAnwser(answer);

		if (questionsCompleted == questionsCount)
			return "redirect:/surveys/";

		model.addAttribute("survey", openSurvey);

		model.addAttribute("template", "survey_question_" + questionsCompleted);

		return "/questions/survey_page";
	}

}
