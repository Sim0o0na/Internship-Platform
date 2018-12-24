package org.isp.repositories;

import org.isp.domain.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByIsReadFalseAndUserUsername(String username);
    Notification findByMessageAndUserUsername(String message, String username);
}
