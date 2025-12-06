package aston.task2.src.main.service;

import aston.task2.src.main.dao.UserDao;
import aston.task2.src.main.entity.User;
import aston.task2.src.main.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserServiceImpl(UserDao userDao) implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Override
    public User createUser(String name, String email, int age) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        user.setCreatedAt(LocalDateTime.now());
        
        userDao.save(user);
        logger.info("User created: {}", user);
        return user;
    }
    
    @Override
    public User getUser(UUID id) {
        return userDao.findById(id).orElseThrow(() -> {
            logger.warn("User not found with id {}", id);
            return new UserNotFoundException(id);
        });
    }
    
    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.findAll();
        logger.info("Retrieved {} users", users.size());
        return users;
    }
    
    @Override
    public void updateUser(UUID id, String name, String email, int age) {
        User user = getUser(id);
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        userDao.update(user);
        logger.info("User updated: {}", user);
    }
    
    @Override
    public void deleteUser(UUID id) {
        getUser(id);
        userDao.delete(id);
        logger.info("User deleted with id {}", id);
    }
}
