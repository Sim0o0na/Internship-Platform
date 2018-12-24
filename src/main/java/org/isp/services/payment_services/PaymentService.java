package org.isp.services.payment_services;

import org.isp.domain.Payment;
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
