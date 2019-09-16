package com.github.vrnvu.internal;

import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class FluxConsumer {
    public static void main(String[] args) throws InterruptedException {
        FluxProducer fluxProducer = new FluxProducer();
        fluxProducer.subscribe(FluxConsumer::processNextValue, FluxConsumer::processError);
        CompletableFuture.runAsync(fluxProducer::generateNextValueWithFixedRate);
        Thread.sleep(5000);
    }

    private static void processNextValue(int value) {
        Mono.justOrEmpty(value)
                .subscribe(System.out::println);
    }

    private static void processError(Throwable error) {
        Mono.justOrEmpty(error)
                .subscribe(System.out::println);
    }
}
