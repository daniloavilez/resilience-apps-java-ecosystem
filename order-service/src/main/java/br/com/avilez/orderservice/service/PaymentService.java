package br.com.avilez.orderservice.service;

import br.com.avilez.orderservice.client.PaymentClient;
import br.com.avilez.orderservice.command.CommandPayment;
import br.com.avilez.orderservice.configuration.ConsulProperties;
import br.com.avilez.orderservice.model.Order;
import br.com.avilez.orderservice.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class PaymentService {

    private RestTemplate restTemplate;

    private PaymentClient paymentClient;

    @Autowired
    private ConsulProperties consulProperties;

    public PaymentService(RestTemplate restTemplate, PaymentClient paymentClient) {
        this.restTemplate = restTemplate;
        this.paymentClient = paymentClient;
    }

    public Order newPaymentWithFeign(Order order) {
        Payment payment = new Payment();
        payment.totalPurchase = BigDecimal.valueOf(1.29);

        boolean wasPaid = paymentClient.pay(payment);

        if (wasPaid) {
            order.status = "COMPLETED";
        } else {
            order.status = "NOT_COMPLETED";
        }

        return order;
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
