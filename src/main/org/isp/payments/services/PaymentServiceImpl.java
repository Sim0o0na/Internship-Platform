package org.isp.payments.services;

import org.isp.payments.models.Payment;
import org.isp.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void makeActivePayment(String taskId) {
        Payment payment = this.paymentRepository.findByTaskId(taskId);
        if(payment == null) {
            throw new IllegalArgumentException("There is no payment created for this task!");
        } else if (payment.getTask().getAssignee() == null) {
            throw new IllegalArgumentException("Cannot set active payment to non-assigned task!");
        } else if (payment.isActive()) {
            throw new IllegalArgumentException("Task has active payment!");
        }
        payment.setActive(true);
        this.paymentRepository.save(payment);
    }
}
