package org.isp.domain;

import org.hibernate.annotations.GenericGenerator;
import org.isp.domain.tasks.entities.Task;
import org.isp.domain.users.entities.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String id;

    @Column(nullable = false, precision = 2)
    private double cost;

    @Column(name = "due_date")
    private Date dueDate;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.PERSIST)
    private Task task;

    private boolean isPaid;

    private boolean isActive;

    @ManyToOne(targetEntity = User.class)
    private User user;

    public Payment() {
        this.isPaid = false;
        this.isActive = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
