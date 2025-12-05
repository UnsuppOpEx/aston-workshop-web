package aston.task2.src.main.config;

import org.flywaydb.core.Flyway;

public class FlywayConfig {
    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:54320/user_db",
                        "postgres",
                        "postgres"
                )
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .load();
        
        flyway.migrate();
    }
}
