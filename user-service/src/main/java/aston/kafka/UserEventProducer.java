package aston.kafka;

import aston.api.event.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserEventProducer {
  private final KafkaTemplate<String, UserEvent> kafkaTemplate;

  @Value("${kafka.topics.user-events}")
  private String topic;

  public void userCreated(UUID userId, String email) {
    kafkaTemplate.send(
        topic,
        userId.toString(),
        new UserEvent(UserEvent.EventType.USER_CREATED, userId, email, Instant.now()));
  }

  public void userDeleted(UUID userId, String email) {
    kafkaTemplate.send(
        topic,
        userId.toString(),
        new UserEvent(UserEvent.EventType.USER_DELETED, userId, email, Instant.now()));
  }
}
