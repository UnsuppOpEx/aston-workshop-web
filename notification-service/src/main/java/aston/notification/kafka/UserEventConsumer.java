package aston.notification.kafka;

import aston.notification.api.event.UserEvent;
import aston.notification.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventConsumer {
  private final MailService mailService;

  @KafkaListener(
      topics = "${kafka.topics.user-events}",
      containerFactory = "kafkaListenerContainerFactory")
  public void handle(UserEvent event) {
    switch (event.type()) {
      case USER_CREATED -> mailService.sendCreated(event.email());
      case USER_DELETED -> mailService.sendDeleted(event.email());
    }
  }
}
