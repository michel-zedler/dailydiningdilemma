package ddd.impl.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;

@WebListener
public class FlywayMigrateOnStartupListener implements ServletContextListener {

	@Resource(lookup = "java:/dailydinningDS")
	private DataSource dataSource;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setLocations("/migrations/");
		flyway.setCleanOnValidationError(false);
		flyway.setInitOnMigrate(true);
		flyway.setInitVersion("1.0.2");
		flyway.setValidateOnMigrate(true);

		flyway.migrate();
		MigrationInfoService info = flyway.info();

		System.out.println("Result: " + info.current().getVersion());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
