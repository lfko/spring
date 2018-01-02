/**
 * 
 */
package fb.spring.simplesurvey.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fb.spring.simplesurvey.model.Survey;
import fb.spring.simplesurvey.model.User;

/**
 * @author fbecke12
 *
 *         repository class which implements basic database CRUD operations; can
 *         be enhanced via Query-Definition-Language
 */
@Repository
public interface SurveyRepository extends CrudRepository<Survey, Integer> {

	/**
	 * 
	 * @param label
	 * @return
	 */
	public Survey findByLabel(String label);

	/**
	 * 
	 * @param creator
	 * @return
	 */
	public List<Survey> findByCreator(User creator);
}
