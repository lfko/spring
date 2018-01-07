/**
 * 
 */
package fb.spring.simplesurvey.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Florian Becker
 * 
 *         configuration bean for setting up Spring Persistence parameters
 *
 */
@Configuration
public class PersistenceConfig {

	private static final Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

	/**
	 * following values will be read from the application.properties
	 */

	@Value("${spring.datasource.driver-class-name}")
	private String driverName;

	@Value("${spring.datasource.url}")
	private String jdbcUrl;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbUserPass;

	/**
	 * provides a DataSource bean, which can be used for any persistence operation
	 * inside the container
	 * 
	 * @return
	 */
	@Bean(name = "dataSource") // name is case sensitive and shoud be referenced appropriately
	public DriverManagerDataSource datasource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(driverName);
		driverManagerDataSource.setUrl(jdbcUrl);
		driverManagerDataSource.setUsername(dbUser);
		driverManagerDataSource.setPassword(dbUserPass);

		logger.info("JDBC-Connection: " + jdbcUrl);
		logger.info("DB-User: " + dbUser);
		logger.info("DB-Driver: " + driverName);

		return driverManagerDataSource;
	}

}
