package org.isp.payments.controllers;

import org.isp.payments.models.Payment;
import org.isp.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class PaymentsController {
    private PaymentService paymentService;

    @Autowired
    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/payments/all", method = RequestMethod.GET)
    public String getPaymentsForUser(Pageable pageable, Model model, Principal principal) {
        Page<Payment> paymentsForUser = this.paymentService.getAllForUser(pageable, principal.getName());
        model.addAttribute("sumToReceive",
                paymentsForUser.getContent().stream()
                .filter(p -> p.isActive() && !p.isPaid())
                .mapToDouble(Payment::getCost).sum());
        model.addAttribute("payments", paymentsForUser);
        model.addAttribute("pagesCount", paymentsForUser.getTotalPages());
        model.addAttribute("user", principal.getName());
        return "users/user-payments";
    }
}