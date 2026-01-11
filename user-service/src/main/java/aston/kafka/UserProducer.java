package aston.kafka;

import aston.api.event.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProducer implements UserEventProducer {
  private final KafkaTemplate<String, UserEvent> kafkaTemplate;

  @Value("${kafka.topics.user-events}")
  private String topic;

  @Override
  public void produce(UserEvent event) {
    kafkaTemplate.send(topic, event.userId().toString(), event);
  }
}
