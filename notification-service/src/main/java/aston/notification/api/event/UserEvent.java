package aston.notification.api.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserEvent(EventType type, UUID userId, String email, LocalDateTime dateTime) {

  public enum EventType {
    USER_CREATED,
    USER_DELETED
  }
}
