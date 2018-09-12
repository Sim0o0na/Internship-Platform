package org.isp.domain.notifications;

public class NotificationDto {
    private Long id;
    private String message;
    private String type;

    public NotificationDto() {
    }

    public NotificationDto(String message, String type) {
        this.message = message;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
