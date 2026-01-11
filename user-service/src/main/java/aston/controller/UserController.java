package aston.controller;

import aston.api.dto.UserRequest;
import aston.api.dto.UserResponse;
import aston.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @Operation(summary = "Create user")
  @PostMapping
  public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) {
    return userService.createUser(userRequest);
  }

  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable("id") UUID id) {
    return userService.getUser(id);
  }

  @GetMapping
  public List<UserResponse> getAllUsers() {
    return userService.getAllUsers();
  }

  @PutMapping("/{id}")
  public UserResponse updateUser(
      @PathVariable("id") UUID id, @RequestBody @Valid UserRequest userRequest) {
    return userService.updateUser(id, userRequest);
  }

  @Operation(summary = "Delete user")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable("id") UUID id) {
    userService.deleteUser(id);
  }
}
