package aston.service;

import aston.dto.UserRequest;
import aston.dto.UserResponse;
import aston.entity.User;
import aston.exception.UserNotFoundException;
import aston.mapper.UserMapper;
import aston.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    
    @Mock
    private UserRepository repository;
    
    @Mock
    private UserMapper mapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void createUser() {
        UserRequest request = new UserRequest("John", "john@test.com", 12);
        User user = new User("John", "john@test.com", 12);
        User userDb = new User("John", "john@test.com", 12);
        userDb.setId(UUID.randomUUID());
        userDb.setCreatedAt(LocalDateTime.now());
        
        when(mapper.toEntity(request)).thenReturn(user);
        when(repository.save(user)).thenReturn(userDb);
        when(mapper.toDto(userDb)).thenReturn(new UserResponse(userDb.getId(), userDb.getName(), userDb.getEmail(), userDb.getAge(), userDb.getCreatedAt()));
        
        UserResponse response = userService.createUser(request);
        
        Assertions.assertNotNull(response.id());
        assertEquals("John", response.name());
        assertEquals("john@test.com", response.email());
        assertEquals(12, response.age());
        Assertions.assertNotNull(response.createdAt());
    }
    
    @Test
    void getUser() {
        User user = new User("Alex", "alex@test.com", 15);
        user.setId(UUID.randomUUID());
        user.setCreatedAt(LocalDateTime.now());
        
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt()));
        
        UserResponse actualUser = userService.getUser(user.getId());
        
        Assertions.assertNotNull(actualUser.id());
        assertEquals(user.getName(), actualUser.name());
        assertEquals(user.getEmail(), actualUser.email());
        assertEquals(user.getAge(), actualUser.age());
        Assertions.assertNotNull(actualUser.createdAt());
    }
    
    @Test
    void getUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(repository.findById(userId)).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
        
        assertEquals(UserNotFoundException.class, exception.getClass());
        assertEquals("User with id " + userId + " not found", exception.getMessage());
    }
    
    @Test
    void getAllUsers() {
        User firstUserDb = new User("Alex", "alex@test.com", 15);
        User secondUserDb = new User("Bob", "bob@test.com", 20);
        User thirdUserDb = new User("Kate", "kate@test.com", 25);
        
        when(repository.findAll()).thenReturn(List.of(firstUserDb, secondUserDb, thirdUserDb));
        when(mapper.toDto(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getAge(), u.getCreatedAt());
        });
        
        List<UserResponse> users = userService.getAllUsers();
        
        assertEquals(3, users.size());
        verify(repository).findAll();
        
        Optional<UserResponse> expectedUser = users.stream().filter(u -> u.name().equals("Kate")).findFirst();
        expectedUser.ifPresent(user -> {
            assertEquals("Kate", user.name());
            assertEquals("kate@test.com", user.email());
            assertEquals(25, user.age());
        });
    }
    
    @Test
    void updateUser() {
        UserRequest request = new UserRequest("Alex", "alex@test.com", 15);
        User user = new User("Alex", "alex@test.com", 15);
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        
        user.setName("Alex Junior");
        user.setAge(7);
        
        userService.updateUser(userId, request);
        
        verify(repository).save(user);
    }
    
    @Test
    void deleteUser() {
        User user = new User("Alex", "alex@test.com", 15);
        UUID userId = UUID.randomUUID();
        user.setId(userId);
        userService.deleteUser(userId);
        
        verify(repository).deleteById(userId);
    }
}
