package org.isp.payments.controllers;

import org.isp.payments.models.Payment;
import org.isp.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/admin/payments")
public class PaymentsAdminController {
    private PaymentService paymentService;

    @Autowired
    public PaymentsAdminController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/activate/{taskId}", method = RequestMethod.GET)
    private String makePaymentActive(@PathVariable(value = "taskId") String taskId, Model model){
        try {
            this.paymentService.makeActivePayment(taskId);
            model.addAttribute("successMsg");
        } catch (IllegalArgumentException iae) {
            model.addAttribute("error", iae.getMessage());
        }
        return "admin/admin-base";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    private String allPaymentsPanel(Model model){
        List<Payment> allPayments = this.paymentService.fetchAll();
        model.addAttribute("payments", allPayments);
        return "admin/payments/all-payments-partial";
    }
}
