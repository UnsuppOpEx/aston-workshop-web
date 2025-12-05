package aston.task2.src.main.service;

import aston.task2.src.main.dao.UserDao;
import aston.task2.src.main.entity.User;
import aston.task2.src.main.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    
    private final UserDao userDao;
    
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public User createUser(String name, String email, int age) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        user.setCreatedAt(LocalDateTime.now());
        
        userDao.save(user);
        return user;
    }
    
    @Override
    public User getUser(UUID id) {
        return userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    
    @Override
    public void updateUser(UUID id, String name, String email, int age) {
        User user = getUser(id);
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        userDao.update(user);
    }
    
    @Override
    public void deleteUser(UUID id) {
        getUser(id);
        userDao.delete(id);
    }
}
