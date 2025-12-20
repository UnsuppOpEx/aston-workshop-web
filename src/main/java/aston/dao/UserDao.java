package aston.dao;

import aston.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {
    void save(User user);
    
    Optional<User> findById(UUID id);
    
    List<User> findAll();
    
    void update(User user);
    
    void delete(UUID id);
}
