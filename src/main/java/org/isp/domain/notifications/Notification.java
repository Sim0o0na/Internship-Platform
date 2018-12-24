package org.isp.domain.notifications;

import org.isp.domain.users.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "is_read")
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    public Notification() {
    }

    public Notification(String message, User user, NotificationType type) {
        this.message = message;
        this.user = user;
        this.setCreatedOn(new Date());
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    private void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean read) {
        isRead = read;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
