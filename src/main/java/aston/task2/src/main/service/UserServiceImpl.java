package aston.task2.src.main.service;

import aston.task2.src.main.dao.UserDao;
import aston.task2.src.main.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    
    private final UserDao userDao;
    
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public User createUser(String name, String email, int age) {
        return null;
    }
    
    @Override
    public User getUser(Long id) {
        return null;
    }
    
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
    
    @Override
    public void updateUser(Long id, String name, String email, int age) {
    
    }
    
    @Override
    public void deleteUser(Long id) {
    
    }
}
