/**
 * 
 */
package fb.spring.simplesurvey.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import fb.spring.simplesurvey.model.User;
import fb.spring.simplesurvey.service.UserService;

/**
 * @author Florian Becker
 * 
 *         custom AuthenticationProver for verifying and authenticating user
 *         login requests
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	/**
	 * dependency injected service bean
	 */
	@Autowired
	private UserService uService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// username
		String login = authentication.getName();

		// password
		Object credentials = authentication.getCredentials();
		String password = credentials.toString();

		// mit den eingegebenen Daten wird nun versucht, den Benutzer zu
		// verifizieren, d.h. die Kombination aus Login und Passwort wird mit
		// der Datenbank abgeglichen
		User user = uService.login(login, password);

		if (user == null) {
			logger.info("user could not be found/authenticated!");
			throw new BadCredentialsException("Authentication of user " + login + " failed!");
		}

		// Der Benutzer konnte verifiziert werden, nun werden seine
		// Rollen/Rechte geprueft

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		// iteriere durch das Rollen-Set des Benutzers und fuege die einzelnen
		// Rollen-Namen der Liste hinzu
		user.getRoles()
				.forEach(rolle -> grantedAuthorities.add(new SimpleGrantedAuthority(rolle.getRole().toUpperCase())));

		logger.info("Benutzer " + login + " hat folgende Rechte: ");
		grantedAuthorities.forEach(authority -> logger.info(authority.getAuthority()));

		// Abschluss: der Benutzer ist verifiziert und seine Rollen uebertragen,
		// nun wird er als Authentication-Objekt zurueckgegeben
		// Der Token kann im Anwendungskontext verwendet werden, um bspw. die
		// Authorities des Benutzers dynamisch abzufragen
		Authentication auth = new UsernamePasswordAuthenticationToken(login, password, grantedAuthorities);

		return auth;
	}

	/**
	 * Zeigt an, ob das uebergebene Authentication-Objekt durch den
	 * AuthenticationProvider geprueft werden kann
	 * 
	 * @param authentication
	 * @return
	 */
	@Override
	public boolean supports(java.lang.Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
