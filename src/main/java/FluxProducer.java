import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class FluxProducer {

    private Consumer<Integer> nextConsumer;
    private Consumer<Throwable> errorsConsumer;

    private Flux<Integer> flux;

    public void subscribe(Consumer<Integer> consumer, Consumer<Throwable> errorsConsumer) {
        if (this.flux == null) {
            this.flux = Flux.push(sink -> {
                this.onError(sink::error);
                this.onNext(sink::next);
            });
            this.flux = this.flux.onBackpressureBuffer(10, BufferOverflowStrategy.DROP_OLDEST);
        }

        this.flux.subscribe(consumer, errorsConsumer);
    }

    public void generateNextValueWithFixedRate() {
        while (true) {
            try {
                int nextValue = ThreadLocalRandom.current().nextInt(2500);
                if (nextValue < 200) {
                    this.errorsConsumer.accept(new RuntimeException("Ups invalid value"));
                } else {
                    this.nextConsumer.accept(nextValue);
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onNext(Consumer<Integer> nextConsumer) {
        this.nextConsumer = nextConsumer;
    }

    public void onError(Consumer<Throwable> errorsConsumer) {
        this.errorsConsumer = errorsConsumer;
    }

}
