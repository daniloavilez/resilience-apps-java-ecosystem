package br.com.avilez.paymentservice.controller;

import br.com.avilez.paymentservice.model.Payment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public boolean newPayment(@RequestBody Payment payment) {
        return true;
    }
}
