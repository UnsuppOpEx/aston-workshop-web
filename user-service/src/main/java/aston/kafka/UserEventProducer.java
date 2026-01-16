package aston.kafka;

import aston.api.event.UserEvent;

public interface UserEventProducer {
  void produce(UserEvent event);
}
