package org.isp.payments.services;

import org.isp.payments.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;

public interface PaymentService {
    void makeActivePayment(String taskId);
    List<Payment> getAll();
    Page<Payment> getAllForUser(Pageable pageable, String user);
    HashMap<String, Double> getAllForTimeFrameAndUser(String user);
}
