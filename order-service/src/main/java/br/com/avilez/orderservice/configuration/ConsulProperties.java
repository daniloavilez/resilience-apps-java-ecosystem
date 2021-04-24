package br.com.avilez.orderservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("my")
@Configuration
@Data
public class ConsulProperties {
    public String paymentServiceName;
}
