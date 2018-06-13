package org.isp.payments.services;

import org.isp.payments.models.Payment;
import org.isp.payments.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Override
    public HashMap<String, Double> getAllForTimeFrameAndUser(String user) {
        List<Payment> paymentsForUser = this.paymentRepository.findAllByTaskAssigneeUsername(user);
        if (paymentsForUser.isEmpty()) {
            throw new IllegalArgumentException("No payments for user!");
        }
        return groupCostByMonth(paymentsForUser);
    }

    private HashMap<String, Double> groupCostByMonth(List<Payment> payments) {
        HashMap<String, Double> grouped = new HashMap<>();
        for (Payment payment : payments) {
            String month = Month.of(payment.getDueDate().getMonth()).name();
            if(grouped.containsKey(month)) {
                grouped.put(month, grouped.get(month) + payment.getCost());
            } else {
                grouped.put(month, payment.getCost());
            }
        }
        return grouped;
    }
}
