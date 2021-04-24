package br.com.avilez.orderservice.command;

import br.com.avilez.orderservice.model.Payment;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import rx.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.net.URI;
import java.net.URL;

public class CommandPayment extends HystrixObservableCommand<String> {

    @Autowired
    private RestTemplate restTemplate;

    private final URL url;
    private final Payment payment;

    public CommandPayment(URL url, Payment payment) {
        super(HystrixCommandGroupKey.Factory.asKey("PaymentGroup"));
        this.url = url;
        this.payment = payment;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        HttpEntity<Payment> entity = new HttpEntity<>(payment, headers);

                        String response = restTemplate.postForObject(url.toURI(), entity, String.class);

                        observer.onNext(response);
                        observer.onCompleted();

                    }
                } catch (Exception ex) {
                    observer.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                observer.onNext("false");
                observer.onCompleted();
            }
        });
    }
}
