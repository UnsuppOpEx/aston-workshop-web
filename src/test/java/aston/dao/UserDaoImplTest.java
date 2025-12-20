package aston.dao;

import aston.entity.User;
import aston.service.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDaoImplTest extends BaseIntegrationTest {
    private static UserDaoImpl userDao;
    
    @BeforeAll
    static void initService() {
        userDao = new UserDaoImpl(sessionFactory);
    }
    
    @Test
    void save() {
        User user = new User("John", "john@test.com", 12);
        
        userDao.save(user);
        
        Assertions.assertNotNull(user.getId());
        Optional<User> savedUser = userDao.findById(user.getId());
        assertTrue(savedUser.isPresent());
    }
    
    @Test
    void findById() {
        User user = new User("John", "john@test.com", 12);
        
        userDao.save(user);
        
        Optional<User> actualUser = userDao.findById(user.getId());
        assertTrue(actualUser.isPresent());
        assertEquals("John", actualUser.get().getName());
        assertEquals("john@test.com", actualUser.get().getEmail());
        assertEquals(12, actualUser.get().getAge());
        Assertions.assertNotNull(actualUser.get().getCreatedAt());
    }
    
    @Test
    void findAll() {
        userDao.save(new User("Alex", "alex@test.com", 15));
        userDao.save(new User("Bob", "bob@test.com", 20));
        userDao.save(new User("Kate", "kate@test.com", 25));
        
        List<User> users = userDao.findAll();
        assertEquals(3, users.size());
        
        Optional<User> expectedUser = users.stream().filter(u -> u.getName().equals("Kate")).findFirst();
        expectedUser.ifPresent(user -> {
            assertEquals("Kate", user.getName());
            assertEquals("kate@test.com", user.getEmail());
            assertEquals(25, user.getAge());
        });
    }
    
    @Test
    void update() {
        User user = new User("Alex", "alex@test.com", 15);
        userDao.save(user);
        Assertions.assertNotNull(user.getId());
        User actualUser = userDao.findById(user.getId()).orElseThrow();
        
        assertEquals("Alex", actualUser.getName());
        assertEquals("alex@test.com", actualUser.getEmail());
        assertEquals(15, actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
        
        actualUser.setName("Alex Junior");
        actualUser.setAge(7);
        userDao.update(actualUser);
        
        
        User updatedUser = userDao.findById(actualUser.getId()).orElseThrow();
        
        assertEquals("Alex Junior", updatedUser.getName());
        assertEquals("alex@test.com", updatedUser.getEmail());
        assertEquals(7, updatedUser.getAge());
        Assertions.assertNotNull(updatedUser.getCreatedAt());
    }
    
    @Test
    void delete() {
        User user = new User("Alex", "alex@test.com", 15);
        userDao.save(user);
        Assertions.assertNotNull(user.getId());
        User actualUser = userDao.findById(user.getId()).orElseThrow();
        
        assertEquals("Alex", actualUser.getName());
        assertEquals("alex@test.com", actualUser.getEmail());
        assertEquals(15, actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
        
        userDao.delete(actualUser.getId());
        
        Assertions.assertEquals(Optional.empty(), userDao.findById(actualUser.getId()));
        
    }
}
