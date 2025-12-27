package aston.kafka;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserEvent(EventType type, UUID userId, String email, LocalDateTime dateTime) {

  public enum EventType {
    USER_CREATED,
    USER_DELETED
  }

  public static UserEvent userCreated(UUID userId, String email) {
    return new UserEvent(EventType.USER_CREATED, userId, email, LocalDateTime.now());
  }

  public static UserEvent userDeleted(UUID userId, String email) {
    return new UserEvent(EventType.USER_DELETED, userId, email, LocalDateTime.now());
  }
}
