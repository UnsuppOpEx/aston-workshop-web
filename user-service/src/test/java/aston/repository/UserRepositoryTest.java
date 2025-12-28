package aston.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import aston.entity.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserRepositoryTest extends BaseIntegrationTest {

  @Autowired private UserRepository userRepository;

  @Test
  void save() {
    User user = new User("John", "john@test.com", 12);

    userRepository.save(user);

    Assertions.assertNotNull(user.getId());
    Optional<User> savedUser = userRepository.findById(user.getId());
    assertTrue(savedUser.isPresent());
  }

  @Test
  void findById() {
    User user = new User("John", "john@test.com", 12);

    userRepository.save(user);

    Optional<User> actualUser = userRepository.findById(user.getId());
    assertTrue(actualUser.isPresent());
    assertEquals("John", actualUser.get().getName());
    assertEquals("john@test.com", actualUser.get().getEmail());
    assertEquals(12, actualUser.get().getAge());
    Assertions.assertNotNull(actualUser.get().getCreatedAt());
  }

  @Test
  void findAll() {
    userRepository.save(new User("Alex", "alex@test.com", 15));
    userRepository.save(new User("Bob", "bob@test.com", 20));
    userRepository.save(new User("Kate", "kate@test.com", 25));

    List<User> users = userRepository.findAll();
    assertEquals(3, users.size());

    Optional<User> expectedUser =
        users.stream().filter(u -> u.getName().equals("Kate")).findFirst();
    expectedUser.ifPresent(
        user -> {
          assertEquals("Kate", user.getName());
          assertEquals("kate@test.com", user.getEmail());
          assertEquals(25, user.getAge());
        });
  }

  @Test
  void update() {
    User user = new User("Alex", "alex@test.com", 15);
    userRepository.save(user);
    Assertions.assertNotNull(user.getId());
    User actualUser = userRepository.findById(user.getId()).orElseThrow();

    assertEquals("Alex", actualUser.getName());
    assertEquals("alex@test.com", actualUser.getEmail());
    assertEquals(15, actualUser.getAge());
    Assertions.assertNotNull(actualUser.getCreatedAt());

    actualUser.setName("Alex Junior");
    actualUser.setAge(7);
    userRepository.save(actualUser);

    User updatedUser = userRepository.findById(actualUser.getId()).orElseThrow();

    assertEquals("Alex Junior", updatedUser.getName());
    assertEquals("alex@test.com", updatedUser.getEmail());
    assertEquals(7, updatedUser.getAge());
    Assertions.assertNotNull(updatedUser.getCreatedAt());
  }

  @Test
  void delete() {
    User user = new User("Alex", "alex@test.com", 15);
    userRepository.save(user);
    Assertions.assertNotNull(user.getId());
    User actualUser = userRepository.findById(user.getId()).orElseThrow();

    assertEquals("Alex", actualUser.getName());
    assertEquals("alex@test.com", actualUser.getEmail());
    assertEquals(15, actualUser.getAge());
    Assertions.assertNotNull(actualUser.getCreatedAt());

    userRepository.deleteById(actualUser.getId());

    Assertions.assertEquals(Optional.empty(), userRepository.findById(actualUser.getId()));
  }
}
