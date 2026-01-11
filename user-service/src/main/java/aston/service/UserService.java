package aston.service;

import aston.api.dto.UserRequest;
import aston.api.dto.UserResponse;
import java.util.List;
import java.util.UUID;

public interface UserService {
  UserResponse createUser(UserRequest user);

  UserResponse getUser(UUID id);

  List<UserResponse> getAllUsers();

  UserResponse updateUser(UUID id, UserRequest user);

  void deleteUser(UUID id);
}
