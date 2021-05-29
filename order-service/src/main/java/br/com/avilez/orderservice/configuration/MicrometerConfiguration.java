package br.com.avilez.orderservice.configuration;

import feign.Feign;
import feign.micrometer.MicrometerCapability;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MicrometerCapability.class)
public class MicrometerConfiguration {

    @Bean
    public Feign.Builder micrometerCapability(MeterRegistry meterRegistry) {
        return Feign.builder()
                .addCapability(new MicrometerCapability(meterRegistry));
    }
}
