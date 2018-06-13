package org.isp.notifications;

import org.isp.users.models.entities.User;

import java.util.List;

public interface NotificationService {
    void create(String notificationMessage, String username);

    void createForAllUsers(String notificationMessage);

    List<NotificationDto> getAllNotReadForUser(String username);
}
