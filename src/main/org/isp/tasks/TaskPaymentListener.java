package org.isp.tasks;

import org.isp.tasks.models.entities.Task;

import javax.persistence.PrePersist;

public class TaskPaymentListener {

    @PrePersist
    public void onTaskCompletion(Task task) {
       task.getPayment().setActive(true);
    }
}