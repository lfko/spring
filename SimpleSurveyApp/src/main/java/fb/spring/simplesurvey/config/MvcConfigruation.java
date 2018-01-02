/**
 * 
 */
package fb.spring.simplesurvey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author fbecke12
 *
 */
public class MvcConfigruation extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// register our freshly created views (HTML) to the controller in charge
		// and add them to the web server context
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/greeting").setViewName("greeting");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/foo").setViewName("home");
	}

	/**
	 * injects the BCryptPasswordEncoder into our context, which can be later used
	 * by an autowired object (e.g. in a service)
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
}
