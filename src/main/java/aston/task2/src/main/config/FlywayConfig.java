package aston.task2.src.main.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {
    public static void migrate(String dbUrl, String username, String password) {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        dbUrl,
                        username,
                        password
                )
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .load();
        
        flyway.migrate();
    }
}
