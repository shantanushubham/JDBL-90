package multithreading.future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    /*
    It is a powerful, non-blocking async
    computation pipeline.

    It is
    1. Future
    2. Plus callbacks
    3. Plus chaining
    4. Plus composition
    5. Plus Exception Handling
     */

    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
                    sleep(1_000);
                    return 10;
                })
                .thenApply(result -> result * 2)
                .thenAccept(finalResult -> System.out.println("Result: " + finalResult));

        sleep(2_000);
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("Exception");
        }
    }
}
