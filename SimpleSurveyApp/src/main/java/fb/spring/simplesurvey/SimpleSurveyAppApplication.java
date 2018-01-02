package fb.spring.simplesurvey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author fbecke12
 *
 *         declares our application as a spring-boot-conform application; finds
 *         and loads all entities, controllers, UI templates and other
 *         application assets
 * 
 */
@SpringBootApplication
public class SimpleSurveyAppApplication {

	public static void main(String[] args) {
		/**
		 * when this is called, it'll start the embedded tomcat application
		 * server (as a default on localhost:8080)
		 */
		SpringApplication.run(SimpleSurveyAppApplication.class, args);
	}
}
