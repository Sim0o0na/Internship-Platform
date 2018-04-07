package org.isp.tasks.models.entities;

import org.hibernate.annotations.GenericGenerator;
import org.isp.users.models.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_applications")
public class TaskApplication {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Task.class)
    private Task task;

    @Column(name = "applied_on")
    private Date appliedOn;

    @Column(name = "is_approved")
    private boolean isApproved;

    public TaskApplication() {
        this.isApproved = false;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
