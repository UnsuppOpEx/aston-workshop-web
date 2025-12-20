package aston.service;

import aston.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class BaseIntegrationTest {
    
    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13.3").withDatabaseName("user_db")
            .withUsername("postgres")
            .withPassword("postgres");
    
    protected static SessionFactory sessionFactory;
    
    
    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.buildSessionFactory(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
    }
    
    @BeforeEach
    void setup() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createMutationQuery("delete from User").executeUpdate();
        tx.commit();
        session.close();
    }
    
    @AfterAll
    static void teardown() {
        sessionFactory.close();
    }
}
