package org.isp.domain.applications;

import org.hibernate.annotations.GenericGenerator;
import org.isp.domain.tasks.entities.Task;
import org.isp.domain.users.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_applications")
public class TaskApplication {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Task.class)
    private Task task;

    @Column(name = "applied_on")
    private Date appliedOn;

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "is_declined")
    private boolean isDeclined;

    public TaskApplication() {
    }

    public TaskApplication(User user, Task task) {
        this.user = user;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Date getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(Date appliedOn) {
        this.appliedOn = appliedOn;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean getIsDeclined() {
        return isDeclined;
    }

    public void setIsDeclined(boolean declined) {
        isDeclined = declined;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isDeclined() {
        return isDeclined;
    }

    public void setDeclined(boolean declined) {
        isDeclined = declined;
    }
}
