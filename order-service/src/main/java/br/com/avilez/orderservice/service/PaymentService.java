package br.com.avilez.orderservice.service;

import br.com.avilez.orderservice.command.CommandPayment;
import br.com.avilez.orderservice.configuration.ConsulProperties;
import br.com.avilez.orderservice.model.Order;
import br.com.avilez.orderservice.model.Payment;
import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;

@Service
public class PaymentService {

    private RestTemplate restTemplate;

    @Autowired
    private ConsulProperties consulProperties;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Order newPayment(Order order) throws IOException, URISyntaxException {
        // URL comes from Consul Agent
        URL url = new URL(String.format("http://%s/payment", consulProperties.paymentServiceName));

        Payment payment = new Payment();
        payment.totalPurchase = BigDecimal.valueOf(1.29);

        Observable<String> commandPayment = new CommandPayment(url, payment).observe();

        commandPayment.subscribe(new Observer<String>() {
                 @Override
                 public void onCompleted() {
                     order.status = "COMPLETED";
                 }

                 @Override
                 public void onError(Throwable e) {
                     order.status = "NOT_COMPLETED";
                 }

                 @Override
                 public void onNext(String s) {
                    
                 }
             });

        return order;
    }

    public Order fallbackNewPayment(Order order) {
        order.status = "NOT_COMPLETED";

        return order;
    }
}
