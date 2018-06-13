package org.isp.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    private NotificationService notificationService;

    @Autowired
    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void create(String notificationMessage, String username) {
        if (notificationMessage.isEmpty()) {
            throw new IllegalArgumentException("Notification message cannot be empty!");
        }
        this.notificationService.create(notificationMessage, username);
    }
}
