package aston.service;

import aston.dto.UserRequest;
import aston.dto.UserResponse;
import aston.entity.User;
import aston.exception.UserNotFoundException;
import aston.mapper.UserMapper;
import aston.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public UserResponse createUser(UserRequest user) {
    User userEntity = userMapper.toEntity(user);
    User userDb = userRepository.save(userEntity);
    logger.info("User created: {}", user);
    return userMapper.toDto(userDb);
  }

  @Override
  public UserResponse getUser(UUID id) {
    User userDb =
        userRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  logger.warn("User not found with id {}", id);
                  return new UserNotFoundException(id);
                });
    return userMapper.toDto(userDb);
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<User> users = userRepository.findAll();
    logger.info("Retrieved {} users", users.size());
    return users.stream().map(userMapper::toDto).toList();
  }

  @Override
  public UserResponse updateUser(UUID id, @NotNull UserRequest user) {
    User userDb = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    userDb.setName(user.name());
    userDb.setEmail(user.email());
    userDb.setAge(user.age());
    userRepository.save(userDb);
    logger.info("User updated: {}", user);
    return userMapper.toDto(userDb);
  }

  @Override
  public void deleteUser(UUID id) {
    logger.info("User deleted with id {}", id);
    userRepository.deleteById(id);
  }
}
