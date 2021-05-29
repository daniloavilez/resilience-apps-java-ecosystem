package br.com.avilez.orderservice.controller;

import br.com.avilez.orderservice.model.Order;
import br.com.avilez.orderservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1")
public class OrderController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(
            path = "/order",
            consumes = "application/json",
            produces = "application/json"
    )
    public Order newOrder(@RequestHeader(value = "my-test", required = false) String mytest,
                          @RequestBody Order order) throws IOException, URISyntaxException {

//        order = paymentService.newPayment(order);

        order = paymentService.newPaymentWithFeign(order);

        return order;
    }

}
