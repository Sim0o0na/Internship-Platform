package org.isp.notifications.api;

import org.isp.notifications.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByIsReadFalseAndUserUsername(String username);
}
