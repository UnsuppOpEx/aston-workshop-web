package aston.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {
  private final KafkaTemplate<String, UserEvent> kafkaTemplate;

  public void userCreated(UUID userId, String email) {
    kafkaTemplate.send("user-events", userId.toString(), UserEvent.userCreated(userId, email));
  }

  public void userDeleted(UUID userId, String email) {
    kafkaTemplate.send("user-events", userId.toString(), UserEvent.userDeleted(userId, email));
  }
}
