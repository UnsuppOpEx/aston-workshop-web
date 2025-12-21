package aston.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
  public static SessionFactory buildSessionFactory(String url, String username, String password) {
    Configuration configuration = new Configuration();
    configuration.configure("hibernate.cfg.xml");
    configuration.setProperty("hibernate.connection.url", url);
    configuration.setProperty("hibernate.connection.username", username);
    configuration.setProperty("hibernate.connection.password", password);

    return configuration.buildSessionFactory();
  }
}
