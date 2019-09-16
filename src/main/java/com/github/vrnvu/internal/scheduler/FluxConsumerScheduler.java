package com.github.vrnvu.internal.scheduler;

public class FluxConsumerScheduler {
    public static void main(String[] args) throws InterruptedException {
        FluxProducerScheduled fluxProducerScheduled = new FluxProducerScheduled();
        fluxProducerScheduled.fetch().subscribe(System.out::println);
        Thread.sleep(5000);
    }

}
