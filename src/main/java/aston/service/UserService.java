package aston.service;

import aston.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
  User createUser(String name, String email, int age);

  User getUser(UUID id);

  List<User> getAllUsers();

  void updateUser(User user);

  void deleteUser(UUID id);
}
