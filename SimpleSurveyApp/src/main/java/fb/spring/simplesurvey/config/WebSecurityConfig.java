/**
 * 
 */
package fb.spring.simplesurvey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fb.spring.simplesurvey.security.CustomAuthenticationProvider;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author Florian Becker
 *
 *         configuration bean for setting up most of the Spring Security
 *         parameters (or - at least - the ones needed here)
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService uService;

	/**
	 * DI bean
	 */
	@Autowired
	private CustomAuthenticationProvider customAuthProvider;

	/**
	 * tells the spring security framework to ignore securing the following folders
	 * (as they are used internally only)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

	/**
	 * main security configuration - here we declare, which pages are secured, how
	 * they are secured and what should happen, if the user has not provided any
	 * authentication
	 * 
	 * configuration parameters are evaluated in the given order, which means, that
	 * preceding configurations can abolish later ones
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * this is the most basic configuration: allow any request, which had been
		 * authenticated via form (and was sent as http basic request) - it will not use
		 * an individual login, but instead one provided by the application server
		 */
		// http.authorizeRequests().anyRequest().authenticated().and().httpBasic();

		/**
		 * as in the previous sample, but this time we will use our own login page
		 */
		// http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();

		/*
		 * now for a more complex configuration: we only want specific users to view and
		 * access certain pages (depending on their role) - furthermore, secure every
		 * site, so when we start the application and try to enter any site, it will be
		 * intercepted by the login view
		 */
		// @formatter:off
		http.authorizeRequests()
				.antMatchers("/console/**").permitAll()
		.and()
        .csrf().disable() //fuer Testzwecke Schutz gegen Cross Site Forging
        .authorizeRequests() //all other pages are protected and a valid login is needed
            .anyRequest().authenticated()
        .and() //allows every request access to the login-page, irregardless of its authentication status
            .formLogin()
            .loginPage("/login").usernameParameter("login")
            .failureUrl("/login-error.html")
            .defaultSuccessUrl("/home")
            .permitAll()
        .and()
            .sessionManagement()
            .maximumSessions(1).and().sessionFixation().none()    
        .and()
            .logout()
            .invalidateHttpSession(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
			.and().exceptionHandling().accessDeniedPage("/403");
		http.headers().frameOptions().disable();
		
		// @formatter:on
	}

	/**
	 * set our custom auth provider as the method of choice
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthProvider);
	}

}
