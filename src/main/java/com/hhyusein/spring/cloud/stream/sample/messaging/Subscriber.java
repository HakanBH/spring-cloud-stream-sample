package com.hhyusein.spring.cloud.stream.sample.messaging;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Subscriber {

    @Bean
    @ConditionalOnProperty(value = "spring.cloud.stream.bindings.receive-in-0.enabled", havingValue = "true")
    public Consumer<String> receive() {
        return message -> log.info("Message received. Payload={}", message);
    }

    // Another way to define a consumer using Project Reactor is to use a Function with a Mono<Void> return type.
    // See example below:
    //    @Bean
    //    @ConditionalOnProperty(value = "spring.cloud.stream.binding.receive-in-0.enabled", havingValue = "true")
    //    public Function<Flux<String>, Mono<Void>> receive() {
    //        return flux -> flux.map(message -> {
    //            log.info("Message received. Payload={}", message);
    //            return message;
    //        }).then();
    //    }
}
