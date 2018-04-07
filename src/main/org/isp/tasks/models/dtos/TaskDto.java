package org.isp.tasks.models.dtos;

import java.util.Date;

public class TaskDto {
    private String id;

    private String title;

    private String description;

    private Date dueDate;

    private double cost;

    private boolean isPaymentActive;

    private boolean isAssigned;

    public TaskDto() {
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

    public boolean isPaymentActive() {
        return isPaymentActive;
    }

    public void setPaymentActive(boolean paymentActive) {
        isPaymentActive = paymentActive;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
