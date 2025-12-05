package aston.task2.src.main.dao;

import aston.task2.src.main.entity.User;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {
    
    private final SessionFactory sessionFactory;
    
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    @Override
    public void save(User user) {
    }
    
    @Override
    public User findById(Long id) {
        return null;
    }
    
    @Override
    public List<User> findAll() {
        return List.of();
    }
    
    @Override
    public void update(User user) {
    
    }
    
    @Override
    public void delete(Long id) {
    
    }
}
