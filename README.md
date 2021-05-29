# Resilience Demo

## Consul 

### Running Agent

[Consul](https://www.consul.io/)

In order to start consul we need to give the command below:

`consul agent -dev`

To open consul console [http://localhost:8500](http://localhost:8500)

### Register Service

After that, we need to register our services, to do that we can use the library [spring-cloud-starter-consul-all](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-cloud-starter-consul-all%22).

```java
@SpringBootApplication
public class ServiceDiscoveryApplication {
 
    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceDiscoveryApplication.class)
          .web(true).run(args);
    }
}
```

Just start the application and it will be registered, we can check it out at [http://localhost:8500](http://localhost:8500) (port 8500 is the port default for consul).

Consul will get `spring.application.name` and `server.port` to register.

### Distributed Configuration

This feature allows sync the configuration among all the services. Consul will watch for any configuration changes and then trigger the update of all the services.

We must add the dependency to our gradle or maven file [spring-cloud-starter-consul-config](https://search.maven.org/classic/#search%7Cga%7C1%7Ca%3A%22spring-cloud-starter-consul-config%22).

I added `config/order-service/my.paymentServiceName` to K/V on Consul.

```java
@Autowired
private ConsulProperties consulProperties;

public Order newPayment(Order order) throws IOException, URISyntaxException {
    // URL comes from Consul Agent
    URL url = new URL(String.format("http://%s/payment", consulProperties.paymentServiceName));

    // more code here    
    
}
```

## Hystrix

### HystrixCommand

`HystrixCommand` gives us the ability to mark a method with `Hystrix` functionality.

It allows to give a fallbackMethod, set hystrix configurations and made it run in a specific thread.

```java
 @HystrixCommand(fallbackMethod = "fallbackNewPayment",
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
            }
    )
    public Order newPayment(Order order) throws IOException, URISyntaxException {
        // code here
    }

    public Order fallbackNewPayment(Order order) {
        // fallback code here
    }
```

An link that show how Hystrix works is [How it works?](https://github.com/Netflix/Hystrix/wiki/How-it-Works)

## Swagger

[Order Service Swagger](http://localhost:9092/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)