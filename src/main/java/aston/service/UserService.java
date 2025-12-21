package aston.service;

import aston.dto.UserRequest;
import aston.dto.UserResponse;
import java.util.List;
import java.util.UUID;

public interface UserService {
  UserResponse createUser(UserRequest user);

  UserResponse getUser(UUID id);

  List<UserResponse> getAllUsers();

  UserResponse updateUser(UUID id, UserRequest user);

  void deleteUser(UUID id);
}
