package org.isp.payments.controllers;

import org.isp.payments.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/payments")
public class PaymentsAdminController {
    private PaymentService paymentService;

    @Autowired
    public PaymentsAdminController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/activate/{taskId}", method = RequestMethod.GET)
    private String makePaymentActive(@PathVariable(value = "taskId") String taskId,
                                     Model model, RedirectAttributes redirectAttributes){
        try {
            this.paymentService.makeActivePayment(taskId);
            model.addAttribute("successMsg");
        } catch (IllegalArgumentException iae) {
            model.addAttribute("error", iae.getMessage());
        }
        return "admin/admin";
    }
}
