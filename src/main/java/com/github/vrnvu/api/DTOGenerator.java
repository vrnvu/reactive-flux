package com.github.vrnvu.api;

import org.springframework.stereotype.Component;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DTOGenerator {

    private Flux<DTO> flux;

    public DTOGenerator() {
        this.flux = generate();
    }

    public Flux<DTO> fetch() {
        return flux;
    }

    private Flux<DTO> generate() {
        return Flux.interval(Duration.ofSeconds(5))
                .onBackpressureBuffer(10, BufferOverflowStrategy.DROP_OLDEST)
                .map(this::generateFlux)
                .flatMapIterable(e -> e)
                //.log()
                .subscribeOn(Schedulers.elastic())
                //  .share()
                ;
    }

    private List<DTO> generateFlux(Long i) {
        List<DTO> values = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            int value = ThreadLocalRandom.current().nextInt(1000);
            values.add(new DTO(value));
        }
        return values;
    }

}
