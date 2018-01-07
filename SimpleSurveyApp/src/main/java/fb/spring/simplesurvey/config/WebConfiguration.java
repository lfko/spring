/**
 * 
 */
package fb.spring.simplesurvey.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Florian Becker
 *
 */
@Configuration
public class WebConfiguration {

	/**
	 * registers the embedded database console as a bean, so we can access it later
	 * 
	 * 
	 * @return
	 */
	@Bean
	ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
		registrationBean.addUrlMappings("/console/*");
		return registrationBean;
	}

}
