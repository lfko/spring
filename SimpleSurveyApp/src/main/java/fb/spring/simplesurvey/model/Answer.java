package fb.spring.simplesurvey.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Florian Becker
 *
 */
@Entity
@Table(name = "t_answer")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * id of the question form, which had been answered
	 */
	private int questionId;

	/**
	 * id of the answer, which had been chosen
	 */
	private int optionId;

	/**
	 * reference to an existing survey entity; ManyToOne-Relationship because one or
	 * more answers belong to a survey
	 * 
	 * Specifies the ANSWER table does not contain a survey column, but an SURVEY_ID
	 * column with a foreign key. And creates a join to lazily fetch the survey
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SURVEY_ID")
	private Survey survey;

	/**
	 * reference to the user, who has answered the question
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private User respondent;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            the questionId to set
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return the optionId
	 */
	public int getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId
	 *            the optionId to set
	 */
	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}

	/**
	 * @param survey
	 *            the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	/**
	 * @return the user
	 */
	public User getRespondent() {
		return respondent;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setRespondent(User respondent) {
		this.respondent = respondent;
	}

}
