package org.isp.web.controllers;

import org.isp.domain.notifications.NotificationDto;
import org.isp.services.notifications_services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RestController
@RequestMapping("/notifications")
public class NotificationsController {
    private NotificationService notificationService;

    @Autowired
    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value="/allnonreadnotifications", method=RequestMethod.GET, produces = "application/json")
    public List<NotificationDto> getAllNotApproved(Principal principal) {
        List<NotificationDto> allNonReadNotificationsByUser = this.notificationService.getAllNotReadForUser(principal.getName());
        return allNonReadNotificationsByUser;
    }
}
