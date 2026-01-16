package aston.notification.mail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

  @Mock private JavaMailSender mailSender;

  @InjectMocks private MailService mailService;

  @Test
  void sendAccountUserWasCreated() {
    String email = "test@example.com";

    mailService.sendCreated(email);

    ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

    verify(mailSender).send(captor.capture());

    SimpleMailMessage message = captor.getValue();
    Assertions.assertNotNull(message.getTo());
    assertEquals(email, message.getTo()[0]);
    assertEquals("Аккаунт создан", message.getSubject());
    assertEquals("Здравствуйте! Ваш аккаунт был успешно создан.", message.getText());
  }

  @Test
  void sendAccountUserWasDeleted() {
    String email = "test@example.com";

    mailService.sendDeleted(email);

    ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

    verify(mailSender).send(captor.capture());

    SimpleMailMessage message = captor.getValue();
    Assertions.assertNotNull(message.getTo());
    assertEquals(email, message.getTo()[0]);
    assertEquals("Аккаунт удалён", message.getSubject());
    assertEquals("Здравствуйте! Ваш аккаунт был удалён.", message.getText());
  }
}
