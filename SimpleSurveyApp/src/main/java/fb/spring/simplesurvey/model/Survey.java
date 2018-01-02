/**
 * 
 */
package fb.spring.simplesurvey.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author fbecke12
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
	 * JPA relationship: one user could has created one or more surveys
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR_ID")
	private User creator;

	/**
	 * 
	 */
	@OneToMany(mappedBy = "survey")
	private List<Answer> answer;

	public void setCreator(User creator) {
		this.creator = creator;
	}

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

}
