package aston.mapper;

import aston.api.dto.UserRequest;
import aston.api.dto.UserResponse;
import aston.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public User toEntity(UserRequest dto) {
    User user = new User();
    user.setName(dto.name());
    user.setEmail(dto.email());
    user.setAge(dto.age());
    return user;
  }

  public UserResponse toDto(User user) {
    return new UserResponse(
        user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt());
  }
}
