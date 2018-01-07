/**
 * 
 */
package fb.spring.simplesurvey.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Florian Becker
 *
 *         jpa representation of a survey object
 */
@Entity
@Table(name = "t_survey")
public class Survey {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/**
	 * how many times a survey has been finished
	 */
	private int completions;

	/**
	 * how many questions compound the survey
	 */
	private int questions;

	/**
	 * the survey's label
	 */
	private String label;

	/**
	 * a short, meaningful description
	 */
	private String desc;

	/**
	 * indicates, whether a survey has already been answered by the current user
	 * 
	 * the annotation marks it as a non-persistent property
	 */
	@Transient
	private boolean completedByUser;

	/**
	 * JPA relationship: one user could has created one or more surveys - but there
	 * is no referencing via FK, because this means, if we have to delete users, we
	 * have to delete their surveys as well
	 */
	@OneToOne
	private User creator;

	/**
	 * since there is a OneToMany-relation between Survey and Answer, we'll have to
	 * make sure, that in case, we delete a survey, all belonging answers will be
	 * deleted as well; otherwise, the persistence framework will complain about
	 * entities without FK-relation - to achieve this, let's denote this mapping as
	 * cascading, so FK-relations will be deleted as well automatically
	 */
	@OneToMany(mappedBy = "survey", cascade = CascadeType.REMOVE)
	private List<Answer> answer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompletions() {
		return completions;
	}

	public void setCompletions(int completions) {
		this.completions = completions;
	}

	public int getQuestions() {
		return questions;
	}

	public void setQuestions(int questions) {
		this.questions = questions;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the answers
	 */
	public List<Answer> getAnswer() {
		return answer;
	}

	/**
	 * @return the completedByUser
	 */
	public boolean isCompletedByUser() {
		return completedByUser;
	}

	/**
	 * @param completedByUser
	 *            the completedByUser to set
	 */
	public void setCompletedByUser(boolean completedByUser) {
		this.completedByUser = completedByUser;
	}

	/**
	 * @return the creator
	 */
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

}
