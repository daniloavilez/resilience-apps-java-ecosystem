package br.com.avilez.orderservice.service;

import br.com.avilez.orderservice.model.Order;
import br.com.avilez.orderservice.model.Payment;
import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

@Service
public class PaymentService {

    @Autowired
    private RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "fallbackNewPayment",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
            }
    )
    public Order newPayment(Order order) throws IOException, URISyntaxException {
        // URL comes from Eureka Server
        URL url = new URL("http://payment-service/payment");

//        ModelMapper modelMapper = new ModelMapper();
//        Payment payment = modelMapper.map(order, Payment.class);
        Payment payment = new Payment();
        payment.totalPurchase = BigDecimal.valueOf(1.29);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Payment> entity = new HttpEntity<>(payment, headers);

//        Gson gson = new Gson();
        String response = restTemplate.postForObject(url.toURI(), entity, String.class);

        order.status = "COMPLETED";

        return order;
    }

    public Order fallbackNewPayment(Order order) {
        order.status = "NOT_COMPLETED";

        return order;
    }
}
