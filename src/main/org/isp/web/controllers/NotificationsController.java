package org.isp.web.controllers;

import org.isp.domain.notifications.NotificationDto;
import org.isp.domain.notifications.NotificationType;
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

    @RequestMapping(value="/allnonreadnotifications",
            method = RequestMethod.GET,
            produces = "application/json")
    public List<NotificationDto> getAllNotApproved(Principal principal) {
        List<NotificationDto> allNonReadNotificationsByUser =
                this.notificationService.getAllNotReadForUser(principal.getName());
        return allNonReadNotificationsByUser;
    }

    @RequestMapping(value="/hasunreadnotifications",
            method = RequestMethod.GET)
    public boolean checkIfUserHasUnreadNotifications(Principal principal) {
        return this.notificationService.checkIfUserHasUnreadNotifications(principal.getName());
    }

    @RequestMapping(value="/markreadbyid/{id}",
            method = RequestMethod.POST)
    public void markReadById(@PathVariable(value = "id") String id, Principal principal) {
        this.notificationService.markReadById(id);
    }

    @RequestMapping(value="/markreadbymessageanduser/{message}",
            method = RequestMethod.POST)
    public void markReadByMessage(@PathVariable(value = "message") String message, Principal principal) {
        this.notificationService.markReadByMessage(message, principal.getName());
    }

    @RequestMapping(value="/createnotification",
            method = RequestMethod.POST)
    public void markReadByMessage(@PathVariable(value = "message") String message) {
        this.notificationService.createForAllUsers(message, NotificationType.INFO);
    }
}
