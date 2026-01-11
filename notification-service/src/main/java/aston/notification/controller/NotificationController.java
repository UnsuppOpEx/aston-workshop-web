package aston.notification.controller;

import aston.notification.mail.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notifications")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final MailService mailService;

  @PostMapping("/user-created")
  public void userCreated(@RequestParam("email") String email) {
    mailService.sendCreated(email);
  }

  @PostMapping("/user-deleted")
  public void userDeleted(@RequestParam("email") String email) {
    mailService.sendDeleted(email);
  }
}
