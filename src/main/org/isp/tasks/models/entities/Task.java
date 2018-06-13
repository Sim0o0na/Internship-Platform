package org.isp.tasks.models.entities;

import org.hibernate.annotations.GenericGenerator;
import org.isp.payments.models.Payment;
import org.isp.tasks.TaskPaymentListener;
import org.isp.users.models.entities.User;
import org.springframework.context.event.EventListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
//@EntityListeners(TaskPaymentListener.class)
public class Task {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private Date dueDate;

    private boolean isCompleted;

    @ManyToOne(targetEntity = User.class)
    private User assignee;

    @OneToMany(mappedBy = "task")
    private Set<TaskApplication> taskApplications;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    private String type;

    @ManyToMany(mappedBy = "tasks")
    private Set<TaskRequirement> taskRequirements;

    public Task() {
        this.isCompleted = false;
        this.payment = new Payment();
        this.payment.setTask(this);
        this.taskRequirements = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Set<TaskApplication> getTaskApplications() {
        return taskApplications;
    }

    public void setTaskApplications(Set<TaskApplication> taskApplications) {
        this.taskApplications = taskApplications;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<TaskRequirement> getTaskRequirements() {
        return taskRequirements;
    }

    public void setTaskRequirements(Set<TaskRequirement> taskRequirements) {
        this.taskRequirements = taskRequirements;
    }
}
