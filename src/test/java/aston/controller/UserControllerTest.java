package aston.controller;

import aston.dto.UserResponse;
import aston.exception.UserNotFoundException;
import aston.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    private final UUID userId = UUID.randomUUID();
    
    @Test
    void createUser() throws Exception {
        UserResponse response = new UserResponse(userId, "John", "john@test.com", 30, LocalDateTime.now());
        
        when(userService.createUser(any())).thenReturn(response);
        
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("""
                                                                                                       {
                                                                                                         "name": "John",
                                                                                                         "email": "john@test.com",
                                                                                                         "age": 30
                                                                                                       }
                                                                                                   """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("John"));
    }
    
    @Test
    void getUserById() throws Exception {
        when(userService.getUser(userId)).thenReturn(new UserResponse(userId, "Alex", "alex@test.com", 25, LocalDateTime.now()));
        
        mockMvc.perform(get("/api/users/{id}", userId)).andExpect(status().isOk()).andExpect(jsonPath("$.email").value("alex@test.com"));
    }
    
    @Test
    void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(new UserResponse(UUID.randomUUID(), "Alex", "alex@test.com", 20, LocalDateTime.now()),
                                                           new UserResponse(UUID.randomUUID(), "Ben", "ben@test.com", 30, LocalDateTime.now())));
        
        mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2));
    }
    
    @Test
    void updateUser() throws Exception {
        when(userService.updateUser(eq(userId), any())).thenReturn(new UserResponse(userId, "Alex Junior", "alex@test.com", 10, LocalDateTime.now()));
        
        mockMvc.perform(put("/api/users/{id}", userId).contentType(MediaType.APPLICATION_JSON).content("""
                                                                                                                   {
                                                                                                                     "name": "Alex",
                                                                                                                     "email": "alex@test.com",
                                                                                                                     "age": 10
                                                                                                                   }
                                                                                                               """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alex Junior"));
    }
    
    @Test
    void deleteUser() throws Exception {
        doNothing().when(userService).deleteUser(userId);
        
        mockMvc.perform(delete("/api/users/{id}", userId)).andExpect(status().isNoContent());
    }
    
    @Test
    void getUser_notFound() throws Exception {
        when(userService.getUser(userId)).thenThrow(new UserNotFoundException(userId));
        
        mockMvc.perform(get("/api/users/{id}", userId)).andExpect(status().isNotFound());
    }
}
