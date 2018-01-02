/**
 * 
 */
package fb.spring.simplesurvey.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fb.spring.simplesurvey.model.Answer;

/**
 * @author fbecke12
 *
 */
@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {

}
