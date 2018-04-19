package org.isp.tasks.models.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TaskCreateDto {
    @NotNull(message = "Title cannot be empty!")
    private String title;

    @NotNull(message = "Description cannot be empty!")
    private String description;

    @NotNull(message = "Due date not valid!")
    private String dueDate;

    @NotNull
    private double paymentCost;

    @NotNull
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(double paymentCost) {
        this.paymentCost = paymentCost;
    }
}
