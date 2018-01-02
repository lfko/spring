/**
 * 
 */
package fb.spring.simplesurvey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.spring.simplesurvey.model.Answer;
import fb.spring.simplesurvey.repository.AnswerRepository;

/**
 * @author lfko
 *
 */
@Service
public class AnswerService {

	@Autowired
	AnswerRepository repository;

	public Answer saveAnwser(Answer answer) {
		return repository.save(answer);
	}

}
