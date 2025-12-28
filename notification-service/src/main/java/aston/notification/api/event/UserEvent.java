package aston.notification.api.event;

import java.time.Instant;
import java.util.UUID;

public record UserEvent(EventType type, UUID userId, String email, Instant createdAt) {

  public enum EventType {
    USER_CREATED,
    USER_DELETED
  }
}
