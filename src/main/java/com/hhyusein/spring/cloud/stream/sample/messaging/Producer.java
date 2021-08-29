package com.hhyusein.spring.cloud.stream.sample.messaging;

import com.hhyusein.spring.cloud.stream.sample.model.Message;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Simple Spring Cloud Stream producer which publishes a message to the specified binding every second. The name of the
 * automatically generated binding is "publish-out-0" binding. For more details on binding naming conventions, check out
 * https://docs.spring.io/spring-cloud-stream/docs/3.1.3/reference/html/spring-cloud-stream
 * .html#_binding_and_binding_names
 */
@Slf4j
@Component
public class Producer {

    public static final int MESSAGE_DELAY_SECONDS = 1;

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.publish-out-0.enabled", havingValue = "true")
    public Supplier<Flux<Message>> publish() {
        return () -> Flux.range(1, Integer.MAX_VALUE)
            .delayElements(Duration.ofSeconds(MESSAGE_DELAY_SECONDS))
            .map(i -> {
                Message message = new Message(UUID.randomUUID().toString());
                log.info("Publishing message with ID={}", message.getId());
                return message;
            });
    }
}
