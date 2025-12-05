package aston.task2.src.main.service;

import aston.task2.src.main.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(String name, String email, int age);
    
    User getUser(UUID id);
    
    List<User> getAllUsers();
    
    void updateUser(UUID id, String name, String email, int age);
    
    void deleteUser(UUID id);
}
