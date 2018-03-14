package org.isp.model.entity.tasks;

import org.hibernate.annotations.GenericGenerator;
import org.isp.model.entity.users.User;

import javax.persistence.*;

@Entity
@Table(name = "task_applications")
public class TaskApplication {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String id;

    @OneToOne
    private User user;

    @OneToOne
    private Task task;

    public TaskApplication() {
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
}
