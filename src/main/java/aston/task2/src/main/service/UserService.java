package aston.task2.src.main.service;

import aston.task2.src.main.entity.User;

import java.util.List;

public interface UserService {
    User createUser(String name, String email, int age);
    
    User getUser(Long id);
    
    List<User> getAllUsers();
    
    void updateUser(Long id, String name, String email, int age);
    
    void deleteUser(Long id);
}
