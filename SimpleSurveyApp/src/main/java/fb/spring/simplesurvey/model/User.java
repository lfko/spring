/**
 * 
 */
package fb.spring.simplesurvey.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author fbecke12
 *
 */
@Entity
@Table(name = "t_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * the user's first name
	 */
	private String firstname;

	/**
	 * the user's surname
	 */
	private String surname;

	/**
	 * the user's unique login
	 */
	@Column(name = "login", unique = true)
	private String login;

	/**
	 * the user's password, obfuscated as a byte[]
	 */
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;

	/**
	 * flag indicating, whether the user account is active or not
	 */
	private boolean active;

	/**
	 * JPA relationship: one user could have created one or more surveys
	 */
	@OneToMany(mappedBy = "creator")
	private List<Survey> surveys;

	/**
	 * 
	 */
	@OneToMany(mappedBy = "respondent")
	private List<Answer> answers;

	/**
	 * a set representing the user' role(s) - pay attention to the
	 * ManyToMany-annotation, declaring that an user has multiple role(s) and vice
	 * versa
	 * 
	 * Hibernate creates an extra join table for our relation ('user_role'), which
	 * we can access afterwards and which establishes the link between a user and
	 * role(s)
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
