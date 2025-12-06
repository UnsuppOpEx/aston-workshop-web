package aston.task2.src.main;

import aston.task2.src.main.config.FlywayConfig;
import aston.task2.src.main.config.HibernateUtil;
import aston.task2.src.main.dao.UserDaoImpl;
import aston.task2.src.main.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:54320/user_db";
        String username = "postgres";
        String password = "postgres";
        
        var sessionFactory = HibernateUtil.buildSessionFactory(url, username, password);
        var userDao = new UserDaoImpl(sessionFactory);
        var userService = new UserServiceImpl(userDao);
        
        FlywayConfig.migrate(url, username, password);
        
        logger.info("Flyway migrations applied");
        
        userService.getAllUsers().forEach(u -> logger.info("Existing user: {}", u));
        
        sessionFactory.close();
    }
}
