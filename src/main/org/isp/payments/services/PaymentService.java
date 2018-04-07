package org.isp.payments.services;

import org.isp.payments.models.Payment;
import java.util.List;

public interface PaymentService {
    void makeActivePayment(String taskId);
    List<Payment> fetchAll();
}
