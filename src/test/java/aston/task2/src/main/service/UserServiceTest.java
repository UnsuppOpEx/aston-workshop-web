package aston.task2.src.main.service;

import aston.task2.src.main.dao.UserDao;
import aston.task2.src.main.dao.UserDaoImpl;
import aston.task2.src.main.entity.User;
import aston.task2.src.main.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends BaseIntegrationTest {
    private static UserService userService;
    
    @BeforeAll
    static void initService() {
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userService = new UserServiceImpl(userDao);
    }
    
    @Test
    void createUser() {
        User user = userService.createUser("John", "john@test.com", 12);
        
        Assertions.assertNotNull(user.getId());
    }
    
    @Test
    void getUser() {
        User user = userService.createUser("Alex", "alex@test.com", 15);
        
        User actualUser = userService.getUser(user.getId());
        
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getAge(), actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
    }
    
    @Test
    void getAllUsers() {
        userService.createUser("Alex", "alex@test.com", 15);
        userService.createUser("Bob", "bob@test.com", 20);
        userService.createUser("Kate", "kate@test.com", 25);
        
        List<User> users = userService.getAllUsers();
        assertEquals(3, users.size());
        
        Optional<User> expectedUser = users.stream().filter(u -> u.getName().equals("Kate")).findFirst();
        expectedUser.ifPresent(user -> {
            assertEquals("Kate", user.getName());
            assertEquals("kate@test.com", user.getEmail());
            assertEquals(25, user.getAge());
        });
    }
    
    @Test
    void updateUser() {
        User user = userService.createUser("Alex", "alex@test.com", 15);
        User actualUser = userService.getUser(user.getId());
        
        assertEquals("Alex", actualUser.getName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(15, actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
        
        actualUser.setName("Alex Junior");
        actualUser.setAge(7);
        userService.updateUser(actualUser);
        
        User updatedUser = userService.getUser(actualUser.getId());
        
        assertEquals("Alex Junior", updatedUser.getName());
        assertEquals(actualUser.getEmail(), updatedUser.getEmail());
        assertEquals(7, updatedUser.getAge());
        Assertions.assertNotNull(updatedUser.getCreatedAt());
    }
    
    @Test
    void deleteUser() {
        User user = userService.createUser("Alex", "alex@test.com", 15);
        User actualUser = userService.getUser(user.getId());
        
        assertEquals("Alex", actualUser.getName());
        assertEquals("alex@test.com", actualUser.getEmail());
        assertEquals(15, actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
        
        userService.deleteUser(actualUser.getId());
        
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(actualUser.getId()));
        
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals("User with id " + actualUser.getId() + " not found", exception.getMessage());
    }
}
