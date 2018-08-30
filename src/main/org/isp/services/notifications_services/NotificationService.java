package org.isp.services.notifications_services;

import org.isp.domain.notifications.NotificationDto;
import org.isp.domain.notifications.NotificationType;

import java.util.List;

public interface NotificationService {
    void create(String notificationMessage, String username, NotificationType type);

    void createForAllUsers(String notificationMessage, NotificationType type);

    List<NotificationDto> getAllNotReadForUser(String username);
}
