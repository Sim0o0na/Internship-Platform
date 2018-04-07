package org.isp.tasks.models.dtos;

public class TaskDto {
    private String id;

    private String title;

    private String description;

    private String dueDate;

    private double cost;

    private boolean isPaymentActive;


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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
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
}
