package org.isp.tasks.models.dtos;

import org.isp.util.validation.TaskCreation;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Validated(value = TaskCreation.class)
public class TaskDto {
    private String id;

    @NotNull(message = "Title cannot be empty!")
    private String title;

    @NotNull(message = "Description cannot be empty!")
    private String description;

    @NotNull(message = "Due date not valid!")
    private Date dueDate;

    @NotNull
    private double paymentCost;

    private boolean isPaymentActive;

    private boolean isAssigned;

    @NotNull
    private List<TaskRequirementDto> taskRequirements;

    @NotNull
    private String type;

    public TaskDto() {
        this.taskRequirements = new ArrayList<>();
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

    public double getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(double paymentCost) {
        this.paymentCost = paymentCost;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TaskRequirementDto> getTaskRequirements() {
        return taskRequirements;
    }

    public void setTaskRequirements(List<TaskRequirementDto> taskRequirements) {
        this.taskRequirements = taskRequirements;
    }
}
