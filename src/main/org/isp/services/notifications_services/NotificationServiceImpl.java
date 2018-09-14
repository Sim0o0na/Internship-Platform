package org.isp.services.notifications_services;

import org.isp.domain.notifications.Notification;
import org.isp.domain.notifications.NotificationDto;
import org.isp.domain.notifications.NotificationType;
import org.isp.domain.users.entities.User;
import org.isp.repositories.NotificationRepository;
import org.isp.repositories.user_repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private UserRepository userRepository;
    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(UserRepository userRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void create(String notificationMessage, String username, NotificationType type) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("No user with this username!");
        }
        this.notificationRepository.save(new Notification(notificationMessage, user, type));
    }

    @Override
    public void createForAllUsers(String notificationMessage, NotificationType type) {
        this.userRepository.findAll()
                .forEach(u -> this.notificationRepository.save(new Notification(notificationMessage, u, type)));

    }
    @Override
    public List<NotificationDto> getAllNotReadForUser(String username) {
        return this.notificationRepository
                .findAllByIsReadFalseAndUserUsername(username)
                .stream()
                .map(n -> new NotificationDto(n.getMessage(), n.getType().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkIfUserHasUnreadNotifications(String username) {
        return this.notificationRepository.findAllByIsReadFalseAndUserUsername(username).size() > 0;
    }
}
