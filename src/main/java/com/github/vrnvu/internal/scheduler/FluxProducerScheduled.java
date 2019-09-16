package com.github.vrnvu.internal.scheduler;

import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FluxProducerScheduled {

    private Flux<Integer> flux;

    public FluxProducerScheduled() {
        this.flux = generate();
    }

    public Flux<Integer> fetch() {
        return flux;
    }

    private Flux<Integer> generate() {
        return Flux.interval(Duration.ofSeconds(1))
                .onBackpressureBuffer(10, BufferOverflowStrategy.DROP_OLDEST)
                .map(this::generateFlux)
                .flatMapIterable(e -> e)
                .subscribeOn(Schedulers.elastic());
    }

    private List<Integer> generateFlux(Long i) {
        List<Integer> values = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            int value = ThreadLocalRandom.current().nextInt(1000);
            values.add(value);
        }
        return values;
    }

}
