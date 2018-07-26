package org.isp.notifications.api;

import org.isp.notifications.entity.NotificationDto;
import org.isp.notifications.entity.NotificationType;

import java.util.List;

public interface NotificationService {
    void create(String notificationMessage, String username, NotificationType type);

    void createForAllUsers(String notificationMessage, NotificationType type);

    List<NotificationDto> getAllNotReadForUser(String username);
}
