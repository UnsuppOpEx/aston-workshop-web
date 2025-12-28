package aston.notification.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
  private final JavaMailSender sender;

  public void sendCreated(String email) {
    sendMail(email, "Аккаунт создан", "Здравствуйте! Ваш аккаунт был успешно создан.");
  }

  public void sendDeleted(String email) {
    sendMail(email, "Аккаунт удалён", "Здравствуйте! Ваш аккаунт был удалён.");
  }

  private void sendMail(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    sender.send(message);
  }
}
