package org.isp.payments.services;

import org.isp.payments.models.Payment;
import org.isp.payments.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public List<Payment> getAll() {
        List<Payment> allPayments = this.paymentRepository.findAll();
        return allPayments;
    }

    @Override
    public Page<Payment> getAllForUser(Pageable pageable, String user) {
        Page<Payment> paymentsForUser = this.paymentRepository.findAllByTaskAssigneeUsername(pageable, user);
        return paymentsForUser;
    }
}
