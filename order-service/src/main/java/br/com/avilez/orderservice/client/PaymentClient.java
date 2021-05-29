package br.com.avilez.orderservice.client;

import br.com.avilez.orderservice.model.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "payments", url = "http://localhost:9091")
public interface PaymentClient {

    @RequestMapping(method = RequestMethod.POST, value = "/payment", produces = "application/json")
    boolean pay(Payment payment);
}
