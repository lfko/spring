/**
 * 
 */
package fb.spring.simplesurvey.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import fb.spring.simplesurvey.service.UserDetailService;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author Florian Becker
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService uService;

	@Autowired
	UserDetailService userDetailService;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/admin").hasRole("ADMIN").anyRequest()
		// .authenticated().and().formLogin().permitAll().and().authorizeRequests().antMatchers("/console/**")
		// .permitAll().and().csrf().disable();

		// http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/console/**")
		// .permitAll();
		//
		// http.csrf().disable();
		// http.headers().frameOptions().disable();

		// secure every site, so when we start the application and try to enter any
		// site, it will be intercepted by the login view
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").permitAll().antMatchers("/users/**")
				.access("hasRole('USER')").anyRequest().permitAll().antMatchers("/surveys/**")
				.access("hasRole('ADMIN')").anyRequest().permitAll().and().csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/403").defaultSuccessUrl("/home").usernameParameter("login")
				.passwordParameter("password").and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/403");

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// usually, spring security uses its own implementation to validate a user
		// (UserDetailsService) - we can overwrite it by providing our own prepared
		// statements for querying the username and authorities (aka roles)
		auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
	}

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.authenticationProvider(authenticationProvider()); }
	 * 
	 * @Bean public DaoAuthenticationProvider authenticationProvider() {
	 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	 * authProvider.setUserDetailsService(userDetailService);
	 * authProvider.setPasswordEncoder(encoder()); return authProvider; }
	 */

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

}
