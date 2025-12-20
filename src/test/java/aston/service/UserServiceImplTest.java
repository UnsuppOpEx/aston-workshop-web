package aston.service;

import aston.dao.UserDao;
import aston.entity.User;
import aston.exception.UserNotFoundException;
import aston.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
    @Mock
    private UserDao userDao;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void createUser() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        userService.createUser("John", "john@test.com", 12);
        
        verify(userDao).save(captor.capture());
        
        User actualUser = captor.getValue();
        Assertions.assertNull(actualUser.getId());
        assertEquals("John", actualUser.getName());
        assertEquals("john@test.com", actualUser.getEmail());
        assertEquals(12, actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
    }
    
    @Test
    void getUser() {
        User user = new User("Alex", "alex@test.com", 15);
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        user.setCreatedAt(LocalDateTime.now());
        
        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        User actualUser = userService.getUser(userId);
        
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getAge(), actualUser.getAge());
        Assertions.assertNotNull(actualUser.getCreatedAt());
    }
    
    @Test
    void getUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userDao.findById(userId)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
        
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals("User with id " + userId + " not found", exception.getMessage());
    }
    
    @Test
    void getAllUsers() {
        User firstUser = userService.createUser("Alex", "alex@test.com", 15);
        User secondUser = userService.createUser("Bob", "bob@test.com", 20);
        User thirdUser = userService.createUser("Kate", "kate@test.com", 25);
        
        when(userDao.findAll()).thenReturn(List.of(firstUser, secondUser, thirdUser));
        List<User> users = userService.getAllUsers();
        assertEquals(3, users.size());
        verify(userDao).findAll();
        
        Optional<User> expectedUser = users.stream().filter(u -> u.getName().equals("Kate")).findFirst();
        expectedUser.ifPresent(user -> {
            assertEquals("Kate", user.getName());
            assertEquals("kate@test.com", user.getEmail());
            assertEquals(25, user.getAge());
        });
    }
    
    @Test
    void updateUser() {
        User user = new User("Alex", "alex@test.com", 15);
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        
        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        
        user.setName("Alex Junior");
        user.setAge(7);
        
        userService.updateUser(user);
        
        verify(userDao).update(user);
    }
    
    @Test
    void deleteUser() {
        User user = new User("Alex", "alex@test.com", 15);
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        
        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        
        verify(userDao).delete(userId);
    }
}
