package aston.api.event;

import aston.entity.User;
import java.time.Instant;
import java.util.UUID;

public record UserEvent(EventType type, UUID userId, String email, Instant createdAt) {

  public static UserEvent userCreated(User user) {
    return new UserEvent(EventType.USER_CREATED, user.getId(), user.getEmail(), Instant.now());
  }

  public static UserEvent userDeleted(User user) {
    return new UserEvent(EventType.USER_DELETED, user.getId(), user.getEmail(), Instant.now());
  }

  public enum EventType {
    USER_CREATED,
    USER_DELETED
  }
}
