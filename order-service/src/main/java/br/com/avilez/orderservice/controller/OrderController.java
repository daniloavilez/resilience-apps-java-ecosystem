package br.com.avilez.orderservice.controller;

import br.com.avilez.orderservice.model.Order;
import br.com.avilez.orderservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public Order newOrder(@RequestBody Order order) throws IOException, URISyntaxException {

        order = paymentService.newPayment(order);

        return order;
    }

}
