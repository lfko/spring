package fb.spring.simplesurvey.repository;

import org.springframework.data.repository.CrudRepository;

import fb.spring.simplesurvey.model.Question;

/**
 * @author fbecke12
 *
 */
public interface QuestionRepository extends CrudRepository<Question, Integer> {

}
